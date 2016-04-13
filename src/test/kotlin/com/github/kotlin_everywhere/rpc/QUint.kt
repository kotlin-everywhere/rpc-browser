package com.github.kotlin_everywhere.rpc

@native
interface QUnitAssert {
    fun async(): () -> Unit
}

@native
interface QUnitTest {
    val assert: QUnitAssert
    fun pushFailure(message: String, stackTrace: dynamic)
}

@native
interface QUnitConfig {
    val current: QUnitTest
}

@native
object QUnit {
    val config: QUnitConfig = noImpl
}

fun <T : Promise0> T.assertAsync() {
    catch { e ->
        QUnit.config.current.pushFailure(e.message, e.stack)
        throw e
    }.finally(QUnit.config.current.assert.async())
}