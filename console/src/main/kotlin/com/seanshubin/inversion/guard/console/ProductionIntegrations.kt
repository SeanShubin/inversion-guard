package com.seanshubin.inversion.guard.console

import com.seanshubin.inversion.guard.composition.Integrations
import com.seanshubin.inversion.guard.di.contract.FilesContract
import com.seanshubin.inversion.guard.di.delegate.FilesDelegate
import java.time.Clock

class ProductionIntegrations(
    override val commandLineArgs: Array<String>
) : Integrations {
    override val files: FilesContract = FilesDelegate.defaultInstance()
    override val clock: Clock = Clock.systemUTC()
    override val emit: (Any?) -> Unit = ::println
}
