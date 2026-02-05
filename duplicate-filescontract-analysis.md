# Root Cause Analysis: Duplicate FilesContract

## The Problem

We have two identical `FilesContract` interfaces in different packages:
- `com.seanshubin.inversion.guard.jvmspec.contract.FilesContract` (imported from jvmspec)
- `com.seanshubin.inversion.guard.di.contract.FilesContract` (imported from kotlin-reusable)

This required a 300+ line `FilesContractAdapter` to bridge between them.

## Root Cause

### Source Dependency Structure

**inversion-guard project** (this project) has two source dependencies:

1. **From jvmspec** (lines 138-149 in project-specification.json):
   - Imports: contract → jvmspec-contract
   - Contains: FilesContract, FilesContractChecked, FilesDelegate, FilesDelegateChecked

2. **From kotlin-reusable** (lines 151-159 in project-specification.json):
   - Imports: di-contract → di-contract
   - Contains: FilesContract, FilesContractChecked, MessageDigestContract, SystemContract
   - Imports: di-delegate → di-delegate
   - Contains: FilesDelegate, FilesDelegateChecked, MessageDigestDelegate, SystemDelegate

### The Conflict

Both `jvmspec` and `kotlin-reusable` are **independent projects** that each define their own `FilesContract`:
- `jvmspec` has: `com.seanshubin.jvmspec.contract.FilesContract`
- `kotlin-reusable` has: `com.seanshubin.kotlin.reusable.di.contract.FilesContract`

When we import from both projects, we get duplicate (but identical) interfaces with different package names.

## Type of Problem

This is an **"overlapping module content"** problem:
- Two source dependencies provide the same functionality (FilesContract)
- The interfaces are identical in signature but different in qualified name
- This creates a bridging problem requiring an adapter

This is NOT a transitive dependency problem (jvmspec doesn't depend on kotlin-reusable).

## Potential Solutions

### Solution 1: Standardize on kotlin-reusable (Recommended)

Since kotlin-reusable has MORE contracts (MessageDigest, System) and is described as "Reusable Kotlin Utilities":
1. Use `di-contract` and `di-delegate` from kotlin-reusable everywhere
2. Remove `jvmspec-contract` from the import (jvmspec.contract module shouldn't exist here)
3. Update jvmspec modules to depend on di-contract from kotlin-reusable
4. Delete FilesContractAdapter

### Solution 2: Consolidate in kotlin-reusable at Source

Make `jvmspec` project depend on `kotlin-reusable` as a source dependency:
- Remove FilesContract from jvmspec
- Have jvmspec use kotlin-reusable's di-contract
- Then inversion-guard would transitively get di-contract through jvmspec

## Detection Strategy

To prevent this in the future, we need to detect **overlapping module content**:

### What to Check

When importing modules from multiple source dependencies:
1. **Interface collision**: Same interface name exists in multiple imported modules
2. **Package similarity**: Similar functionality in different packages (FilesContract in both jvmspec.contract and di.contract)
3. **Semantic duplication**: Identical method signatures across interfaces in different packages

### Implementation Ideas

1. **Static analysis during import**:
   - Parse all imported modules
   - Build a map of: interface name → [list of fully qualified names]
   - Flag any interface name that appears in multiple packages
   - Require explicit resolution before continuing

2. **Manifest file tracking**:
   - Each imported module records what it provides
   - Import tool checks for conflicts before importing
   - User must explicitly choose which source to use

3. **Compile-time detection**:
   - Use existing code structure tools to detect:
     - Identical interface signatures across packages
     - Adapter classes (300+ line delegators are a code smell)

### Example Detection Rule

```
For each source dependency import:
  1. Scan all interfaces in imported modules
  2. Build signature map: interface_signature → package_name
  3. If any signature appears > 1 time with different packages:
     ERROR: "Interface conflict detected:
            - FilesContract found in: jvmspec.contract
            - FilesContract found in: di.contract
            Choose one source dependency or consolidate at source"
```

## Recommended Action

1. **Immediate**: Use Solution 1 (standardize on kotlin-reusable)
   - Benefits: kotlin-reusable has more complete contract set
   - Removes 300+ line adapter class
   - Simplifies dependency graph

2. **Long-term**: Implement detection strategy
   - Add import-time conflict detection
   - Prevent future duplication
   - Make overlapping content a failure state

## Questions for Further Investigation

1. Should jvmspec depend on kotlin-reusable to avoid this?
2. Is there a use case for jvmspec having its own contracts independent of kotlin-reusable?
3. Should we have a "contract consolidation" layer that both projects depend on?
