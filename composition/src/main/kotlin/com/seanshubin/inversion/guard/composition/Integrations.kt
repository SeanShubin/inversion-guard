package com.seanshubin.inversion.guard.composition

import com.seanshubin.inversion.guard.di.contract.FilesContract
import java.time.Clock

interface Integrations {
    val commandLineArgs: Array<String>
    val files: FilesContract
    val clock: Clock
    val emit: (Any?) -> Unit
}
