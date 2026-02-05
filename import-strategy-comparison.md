# Source Dependency Import Strategy Comparison

## The Core Problem

Two independent source projects (jvmspec, kotlin-reusable) both provide FilesContract:
- kotlin-reusable: utility library designed for reuse
- jvmspec: application that duplicated the utility for self-containment
- inversion-guard: imports from both, creating collision

## Approach 1: Signature Collision Detection

### Mechanism
At import time, parse all interfaces/classes from all source dependencies and detect duplicate signatures.

### Strengths
- **Works with any source project** - not limited to projects using your generator
- **Catches actual conflicts** - directly addresses the duplicate FilesContract problem
- **No source project changes** - can import from existing projects as-is
- **Flexible** - import from various sources (open source, legacy, etc.)

### Weaknesses
- **Reactive, not proactive** - only catches problems after they exist
- **Doesn't prevent root cause** - doesn't stop projects from duplicating functionality
- **Implementation complexity** - must parse and compare all signatures
- **False positives** - legitimate identical signatures across unrelated interfaces
- **No intent communication** - unclear which project "owns" a contract
- **Runtime cost** - must scan all code during import

### Example Error
```
ERROR: Signature collision detected
  FilesContract found in:
    - jvmspec/contract → jvmspec-contract
    - kotlin-reusable/di-contract → di-contract
  Resolution: Choose one source dependency or consolidate at source
```

## Approach 2: Explicit Exportable Marking

### Mechanism
Source projects mark modules as "exportable" in project-specification.json. Only allow imports from marked modules.

### Strengths
- **Proactive** - prevents imports from projects not designed for export
- **Communicates intent** - "this module is designed to be imported by others"
- **Simple implementation** - just check boolean flag
- **Fast** - no parsing required, just metadata check
- **Architectural discipline** - encourages projects to think about export contracts
- **Clear boundaries** - separates "internal modules" from "public modules"

### Weaknesses
- **Only works with generated projects** - can't import from other sources
- **Doesn't detect collisions** - both projects could mark FilesContract as exportable
- **Requires source project updates** - must modify all source projects
- **Brittle** - what if you need utilities from non-conforming projects?
- **Incomplete solution** - doesn't solve the actual duplication problem

### Example Configuration
```json
// kotlin-reusable/project-specification.json
"modules": {
  "di-contract": {
    "dependencies": [],
    "exportable": true,
    "provides": ["FilesContract", "MessageDigestContract"]
  }
}

// jvmspec/project-specification.json
"modules": {
  "contract": {
    "dependencies": [],
    "exportable": false  // Application module, not for export
  }
}
```

### Example Error
```
ERROR: Cannot import from non-exportable module
  Module: jvmspec/contract
  Reason: Not marked as exportable in project-specification.json
  Resolution: Contact source project maintainer to mark as exportable
```

## Architectural Analysis

### What went wrong?

**jvmspec** duplicated FilesContract because:
- It was written before kotlin-reusable existed, OR
- It wanted to be self-contained, OR
- The author didn't know kotlin-reusable existed

**The right architecture:**
```
kotlin-reusable (utility library)
  ├── di-contract (exportable)
  └── di-delegate (exportable)

jvmspec (application)
  ├── depends on: kotlin-reusable source dependency
  ├── contract (NOT exportable - uses kotlin-reusable's contracts)
  └── model (NOT exportable - jvmspec-specific)

inversion-guard (application)
  ├── depends on: kotlin-reusable (gets di-contract, di-delegate)
  └── depends on: jvmspec (gets jvmspec-specific modules, NOT contract)
```

### What each approach catches

**Signature collision detection:**
- ✅ Catches: jvmspec.contract.FilesContract duplicates di.contract.FilesContract
- ❌ Doesn't prevent: jvmspec including FilesContract in the first place
- ❌ Doesn't guide: which project should own FilesContract

**Exportable marking:**
- ✅ Prevents: importing jvmspec/contract if marked non-exportable
- ❌ Doesn't catch: both projects marking FilesContract as exportable
- ✅ Guides: jvmspec author to not export utility contracts

## Hybrid Approaches

### Approach 3: Exportable + Collision Detection

Combine both mechanisms:
1. Only allow imports from exportable modules (intent)
2. Detect signature collisions across exportable modules (safety)

**Benefits:**
- Intent: exportable marking communicates design
- Safety: collision detection prevents duplicates
- Best of both worlds

**Drawbacks:**
- Most complex implementation
- Both checks required for every import

**Example:**
```json
// kotlin-reusable
"di-contract": {
  "exportable": true,
  "provides": ["FilesContract", "MessageDigestContract", "SystemContract"]
}

// jvmspec
"contract": {
  "exportable": true,
  "provides": ["FilesContract"]  // COLLISION!
}
```

**Error:**
```
ERROR: Signature collision in exportable modules
  FilesContract provided by:
    - kotlin-reusable/di-contract (marked exportable)
    - jvmspec/contract (marked exportable)
  Resolution: One project should depend on the other, not both export
```

### Approach 4: Content Manifest with Ownership

Modules declare what they provide, import tool checks for ownership conflicts.

**Mechanism:**
```json
// kotlin-reusable/project-specification.json
"exports": {
  "di-contract": {
    "provides": {
      "FilesContract": "com.seanshubin.kotlin.reusable.di.contract.FilesContract",
      "MessageDigestContract": "..."
    }
  }
}
```

**Benefits:**
- Fast - no parsing, just compare manifests
- Clear - explicit about what each module provides
- Flexible - could allow same interface name if different packages/purposes

**Drawbacks:**
- Requires maintaining manifest (out of sync risk)
- Could generate manifest automatically from code

### Approach 5: Dependency Chain Validation

Detect when you're importing from projects that should depend on each other.

**Mechanism:**
```json
// jvmspec/project-specification.json
"sourceDependencies": [
  {
    "sourceProjectPath": "../kotlin-reusable",
    "moduleMapping": {
      "di-contract": "di-contract"
    }
  }
]
```

If inversion-guard tries to import from both:
```
WARNING: Redundant source dependency
  You're importing from: kotlin-reusable and jvmspec
  But jvmspec already depends on kotlin-reusable
  Suggestion: Import jvmspec only (transitively gets kotlin-reusable modules)
```

**Benefits:**
- Encourages correct dependency structure
- Reduces redundant imports
- Detects architectural issues

**Drawbacks:**
- Requires projects to declare their source dependencies
- Complex dependency resolution

### Approach 6: Namespace Ownership Registry

Central registry declares which project owns which namespace.

**Mechanism:**
```json
// global registry or convention
{
  "di.contract.*": "kotlin-reusable",
  "jvmspec.model.*": "jvmspec",
  "jvmspec.contract.*": "DEPRECATED - use kotlin-reusable/di-contract"
}
```

**Benefits:**
- Clear ownership
- Can deprecate namespaces

**Drawbacks:**
- Requires central coordination
- Brittle - namespace changes break things

## Recommended Solution

### Primary Recommendation: Exportable + Manifest + Collision Detection

**Three-layer defense:**

1. **Exportable flag** (intent layer)
   - Modules declare `"exportable": true/false`
   - Fast check, communicates intent
   - Prevents accidental imports from application modules

2. **Content manifest** (declaration layer)
   - Exportable modules declare what they provide
   - Can be auto-generated from code
   - Fast comparison, no parsing

3. **Signature collision detection** (safety layer)
   - Validates manifest accuracy
   - Catches conflicts between exportable modules
   - Deep check for actual duplicates

**Example workflow:**
```
Import phase:
  1. Check: Is module marked exportable?
     NO → FAIL: "Module not marked exportable"
     YES → Continue

  2. Check: Does manifest show conflicts with other imports?
     YES → FAIL: "FilesContract provided by multiple sources"
     NO → Continue

  3. Parse and validate: Does actual code match manifest?
     NO → WARN: "Manifest out of sync, regenerate"
     YES → Import successful
```

**Why this combination works:**

- **Fast path**: Most rejections caught by exportable flag (cheap)
- **Clear errors**: Manifest provides concrete conflict details
- **Validation**: Signature detection ensures manifest accuracy
- **Intent**: Exportable flag forces architectural thinking
- **Flexibility**: Can import from any conforming project

### Implementation Priorities

**Phase 1: Exportable flag** (simplest, highest value)
- Add `"exportable": true/false` to module definitions
- Reject imports from non-exportable modules
- Forces source projects to declare intent

**Phase 2: Content manifest** (moderate complexity, prevents most issues)
- Add `"provides": [...]` to exportable modules
- Check for conflicts across manifests
- Can be auto-generated during build

**Phase 3: Signature validation** (complex, catches edge cases)
- Parse actual code
- Validate against manifest
- Detect undeclared conflicts

## Decision Criteria

Choose based on your priorities:

**Prioritize simplicity?** → Exportable flag only
- Pros: Easy to implement, clear intent
- Cons: Doesn't detect collisions

**Prioritize safety?** → Signature collision detection only
- Pros: Catches actual problems
- Cons: Reactive, no intent communication

**Prioritize flexibility?** → Collision detection only
- Pros: Works with any source
- Cons: Complex, runtime cost

**Prioritize correctness?** → Full combination (recommended)
- Pros: Defense in depth, clear intent, catches problems
- Cons: Most implementation work

## Alternative: Fix at Source

**Orthogonal to detection strategies:**

Make jvmspec depend on kotlin-reusable:
```json
// jvmspec/project-specification.json
"sourceDependencies": [
  {
    "sourceProjectPath": "../kotlin-reusable",
    "moduleMapping": {
      "di-contract": "contract"  // Use kotlin-reusable's contract
    }
  }
]
```

Then jvmspec wouldn't duplicate FilesContract.

**Detection strategies help find this architectural issue, but don't solve it automatically.**
