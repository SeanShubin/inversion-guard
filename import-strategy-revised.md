# Source Dependency Import Strategy (Revised)

## Design Principle

**Separation of Concerns in project-specification.json:**
- `modules`: Controls which modules depend on which (dependency graph only)
- `entryPoints`: Marks which modules are executable entry points
- `exports`: Marks which modules are designed for import by other projects (NEW)

Keep each section focused on its single responsibility.

## Proposed Solution: Explicit Exports + Signature Validation

### Two-Layer Defense

**Layer 1: Explicit Export Declaration** (Required - blocks most issues)
- New `exports` section declares which modules can be imported
- Fast check - just verify module is in the list
- Clear intent - "these modules are our public API"
- Prevents importing application internals

**Layer 2: Signature Collision Detection** (Safety net - catches edge cases)
- Parse exported modules from all source dependencies
- Detect duplicate interface/class signatures
- Catches conflicts between explicitly exported modules

### Example Configuration

#### kotlin-reusable/project-specification.json
```json
{
  "modules": {
    "dynamic-core": [],
    "dynamic-json": ["dynamic-core", "di-delegate", "jackson", "jackson-time"],
    "di-contract": [],
    "di-delegate": ["di-contract"],
    "internal-utilities": []
  },
  "exports": [
    "dynamic-core",
    "dynamic-json",
    "di-contract",
    "di-delegate"
  ]
}
```

**NOT exported:** `internal-utilities` (only for kotlin-reusable internal use)

#### jvmspec/project-specification.json
```json
{
  "modules": {
    "contract": [],
    "analysis": ["contract"],
    "infrastructure": ["contract"],
    "model": ["contract"],
    "runtime": ["contract"]
  },
  "exports": [
    "analysis",
    "infrastructure",
    "model",
    "runtime"
  ]
}
```

**NOT exported:** `contract` (jvmspec internal, duplicates kotlin-reusable)

#### inversion-guard/project-specification.json
```json
{
  "sourceDependencies": [
    {
      "sourceProjectPath": "../jvmspec",
      "moduleMapping": {
        "analysis": "jvmspec-analysis",
        "infrastructure": "jvmspec-infrastructure",
        "model": "jvmspec-model",
        "runtime": "jvmspec-runtime"
      }
    },
    {
      "sourceProjectPath": "../kotlin-reusable",
      "moduleMapping": {
        "dynamic-core": "dynamic-core",
        "dynamic-json": "dynamic-json",
        "di-contract": "di-contract",
        "di-delegate": "di-delegate"
      }
    }
  ]
}
```

### Import Validation Workflow

```
For each source dependency import:

  Phase 1: Export Check (Required)
  ─────────────────────────────────
  For each module in moduleMapping:
    1. Load source project's project-specification.json
    2. Check if module exists in "exports" section
    3. If NO:
       ERROR: "Cannot import non-exported module
               Module: jvmspec/contract
               Reason: Not listed in jvmspec exports section
               Available exports: [analysis, infrastructure, model, runtime]"
    4. If YES: Continue to Phase 2

  Phase 2: Signature Validation (Safety Net)
  ───────────────────────────────────────────
  1. Parse all interfaces/classes from all imported modules
  2. Build signature map: qualified_name → source_module
  3. Detect duplicates:
     - Same qualified name from different sources
     - Same interface signature in different packages
  4. If conflicts found:
       ERROR: "Signature collision detected
               FilesContract found in:
                 - kotlin-reusable/di-contract (exported)
                 - jvmspec/contract (exported)
               These modules should not both be exported.
               Resolution: Make jvmspec depend on kotlin-reusable"
  5. If no conflicts: Import successful
```

## Error Examples

### Error 1: Attempting to import non-exported module
```
$ build-project

ERROR: Cannot import non-exported module
  Source: ../jvmspec
  Module: contract
  Problem: 'contract' is not in jvmspec's exports list

  Available exports from jvmspec:
    - analysis
    - infrastructure
    - model
    - runtime

  Resolution:
    Option 1: Remove 'contract' from your moduleMapping
    Option 2: Contact jvmspec maintainer to export 'contract'

  Note: If this is an internal module, consider depending on
        kotlin-reusable/di-contract instead
```

### Error 2: Signature collision between exported modules
```
$ build-project

ERROR: Signature collision in exported modules
  Interface: FilesContract
  Signature: java.nio.file.Files wrapper with 50+ methods

  Found in:
    1. kotlin-reusable/di-contract
       - Package: com.seanshubin.kotlin.reusable.di.contract
       - Exported: yes
       - Description: "Reusable Kotlin Utilities"

    2. jvmspec/contract
       - Package: com.seanshubin.jvmspec.contract
       - Exported: yes
       - Description: "JVM Specification"

  Problem: Two exported modules provide identical interfaces

  Resolution:
    These projects should not both export FilesContract.

    Recommended fix:
      1. Make jvmspec depend on kotlin-reusable
      2. Remove 'contract' from jvmspec exports
      3. Update jvmspec code to use kotlin-reusable/di-contract

    Alternative: If these truly are different contracts,
                 ensure they have different signatures/purposes
```

## Benefits of This Approach

### 1. Separation of Concerns
- `modules`: Dependency graph (unchanged)
- `entryPoints`: Execution entry points (unchanged)
- `exports`: Public API for import (new, separate concern)

### 2. Explicit Intent
```json
"exports": ["analysis", "model"]  // Clear: these are our public API
```

Not exported means "internal implementation detail, do not import"

### 3. Maintainability
When you see:
```json
"exports": ["di-contract", "di-delegate"]
```

You know these modules are:
- Used by other projects (breaking changes affect downstream)
- Part of the public API (should be stable)
- Designed for reuse (not application-specific)

### 4. Fast Failure
Most invalid imports caught in Phase 1 (export check):
- No parsing required
- Instant feedback
- Clear error messages

### 5. Safety Net
Phase 2 (signature validation) catches edge cases:
- Both projects export the same interface
- Undeclared conflicts
- Copy-paste duplication

## Implementation Details

### Adding "exports" Section

**For utility libraries** (kotlin-reusable):
```json
{
  "exports": [
    "dynamic-core",
    "dynamic-json",
    "di-contract",
    "di-delegate"
  ]
}
```
Export most/all modules - designed for reuse

**For applications** (jvmspec, inversion-guard):
```json
{
  "exports": [
    "analysis",
    "model"
  ]
}
```
Export only domain-specific modules that other apps might need.
Do NOT export utilities (should come from kotlin-reusable).

**For leaf applications** (no downstream consumers):
```json
{
  "exports": []
}
```
No exports - this is the end of the dependency chain.

### Validation Implementation

```kotlin
// Phase 1: Export validation
fun validateExportable(
    sourceProject: ProjectSpecification,
    moduleMapping: Map<String, String>
): ValidationResult {
    val exports = sourceProject.exports ?: emptyList()
    val nonExportedModules = moduleMapping.keys.filterNot { it in exports }

    if (nonExportedModules.isNotEmpty()) {
        return ValidationResult.Failure(
            "Cannot import non-exported modules: $nonExportedModules\n" +
            "Available exports: $exports"
        )
    }
    return ValidationResult.Success
}

// Phase 2: Signature collision detection
fun detectSignatureCollisions(
    allImports: List<ImportedModule>
): ValidationResult {
    val signatureMap = mutableMapOf<InterfaceSignature, MutableList<ModuleSource>>()

    allImports.forEach { module ->
        module.interfaces.forEach { iface ->
            signatureMap
                .getOrPut(iface.signature) { mutableListOf() }
                .add(ModuleSource(module.sourceProject, module.name))
        }
    }

    val collisions = signatureMap.filter { it.value.size > 1 }

    if (collisions.isNotEmpty()) {
        return ValidationResult.Failure(
            "Signature collisions detected:\n" +
            collisions.entries.joinToString("\n") { (sig, sources) ->
                "  ${sig.name} found in: ${sources.joinToString(", ")}"
            }
        )
    }
    return ValidationResult.Success
}
```

## How This Would Have Prevented FilesContract Duplication

### Scenario: Current State (before fix)

**inversion-guard tries to import:**
```json
"sourceDependencies": [
  {
    "sourceProjectPath": "../jvmspec",
    "moduleMapping": {
      "contract": "jvmspec-contract"  // ← Problem: importing contract
    }
  },
  {
    "sourceProjectPath": "../kotlin-reusable",
    "moduleMapping": {
      "di-contract": "di-contract"
    }
  }
]
```

### If jvmspec had NOT exported contract:

**Phase 1 catches it:**
```
ERROR: Cannot import non-exported module
  Module: jvmspec/contract
  Not in exports: [analysis, infrastructure, model, runtime]

BUILD FAILED
```

Import never happens. FilesContractAdapter never needed.

### If jvmspec HAD exported contract:

**Phase 1 passes** (both modules exported)

**Phase 2 catches it:**
```
ERROR: Signature collision detected
  FilesContract in:
    - kotlin-reusable/di-contract
    - jvmspec/contract

  Both modules exported identical interfaces.
  Fix: Remove one from exports, or consolidate at source.

BUILD FAILED
```

Forces architectural decision before allowing import.

## Migration Path

### Step 1: Add "exports" section to all source projects

**kotlin-reusable** (utility library):
```json
"exports": ["dynamic-core", "dynamic-json", "di-contract", "di-delegate"]
```

**jvmspec** (application with reusable components):
```json
"exports": ["analysis", "infrastructure", "model", "runtime"]
// contract NOT exported - internal, duplicates kotlin-reusable
```

**inversion-guard** (leaf application):
```json
"exports": []
// Nothing exported - end of dependency chain
```

### Step 2: Implement Phase 1 validation

Add export checking to import tool:
- Load source project specification
- Verify each imported module is in exports list
- Fail fast with clear error

### Step 3: Fix broken imports

Update inversion-guard:
```json
// Remove: jvmspec/contract import
// Keep: kotlin-reusable/di-contract import
```

Replace `jvmspec.contract.FilesContract` with `di.contract.FilesContract` in code.

### Step 4: Implement Phase 2 validation

Add signature collision detection:
- Parse exported modules
- Build signature map
- Detect duplicates
- Report collisions

### Step 5: Delete adapter

Remove FilesContractAdapter.kt (no longer needed).

## Comparison to Original Approaches

### Original "Exportable Marking" Approach
```json
// ❌ Violated single responsibility
"modules": {
  "di-contract": {
    "dependencies": [],
    "exportable": true  // Mixed concerns
  }
}
```

### Revised Approach
```json
// ✅ Separated concerns
"modules": {
  "di-contract": []  // Just dependencies
},
"exports": [
  "di-contract"  // Separate export declaration
]
```

## Edge Cases Handled

### 1. Module exists but not exported
```json
// kotlin-reusable
"modules": {
  "internal-utilities": []
},
"exports": []  // internal-utilities NOT exported
```

**Result:** Cannot import, fast failure in Phase 1

### 2. Both projects export same interface
```json
// kotlin-reusable exports di-contract
// jvmspec exports contract (duplicates di-contract)
```

**Result:** Signature collision detected in Phase 2

### 3. Transitive source dependencies

If jvmspec depended on kotlin-reusable:
```json
// jvmspec/project-specification.json
"sourceDependencies": [
  {
    "sourceProjectPath": "../kotlin-reusable",
    "moduleMapping": {
      "di-contract": "contract"
    }
  }
]
```

Then jvmspec wouldn't need to export contract (uses kotlin-reusable's).
inversion-guard imports from jvmspec, transitively gets contracts.

**Validation:** Phase 2 wouldn't detect collision (jvmspec doesn't export contract).

### 4. Empty exports (leaf applications)
```json
"exports": []
```

**Meaning:** This project is not designed to be imported by others.

**Result:** Any attempt to import fails in Phase 1.

## Recommended Implementation Order

**Priority 1: Phase 1 (Export Checking)**
- Required for architectural discipline
- Fast, simple implementation
- Highest value per unit of work
- Prevents most problems

**Priority 2: Phase 2 (Signature Validation)**
- Safety net for edge cases
- Catches conflicts between exported modules
- More complex (parsing required)
- Lower priority but important for correctness

## Decision Summary

✅ **Use explicit exports section** - separates concerns, clear intent
✅ **Use signature validation** - safety net for edge cases
✅ **Keep modules section simple** - only dependency graph
✅ **Fail fast** - export check catches most issues immediately
✅ **Defense in depth** - signature check catches remaining conflicts
