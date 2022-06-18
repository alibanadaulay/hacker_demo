package id.bluebird.mall.hacker_demo

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.security.cert.Extension

@ExperimentalCoroutinesApi
abstract class TestCoroutineRule : Extension, BeforeEachCallback, AfterEachCallback {

    private val _dispatcher = StandardTestDispatcher()
    private val _scope = TestScope(_dispatcher)
    val scope = _scope

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(_dispatcher)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}