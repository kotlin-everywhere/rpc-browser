package com.github.kotlin_everywhere.rpc

import org.junit.Test
import kotlin.browser.window
import kotlin.test.assertEquals

@native("QUnit.test") fun qunitTest(name: String, body: (assert: dynamic) -> Unit)

@native
interface QUnitAssert {
    fun async(): () -> Unit
}

@native
interface QUnitModule {
    val assert: QUnitAssert
}

@native
interface QUnitConfig {
    val current: QUnitModule
}

@native
object QUnit {
    val config: QUnitConfig
}

@native
class Promise<T> {
    constructor(body: (resolve: (T) -> Unit) -> Unit)

    constructor(body: (resolve: (T) -> Unit, reject: (Any) -> Unit) -> Unit)


    fun finally(body: () -> Unit): Promise<T>

    fun delay
}


fun promised(body: (() -> Unit) -> Unit) {
    val promise = Promise<Unit> { resolve ->
        body {
            resolve(Unit)
        }
    }
    promise.finally(QUnit.config.current.assert.async())
}

interface Index {
    val version: String
}

class IntegrateTest {
    @Test
    fun testIntegrate() {
        Promise.timeout()
        promised { done ->
            window.setTimeout({
                assertEquals("1", "1");
                assertEquals("1", "2");
                done()
            }, 100)
        }
        //
        //        val rpc = object : RPC() {
        //            val index = get<Index>("/")
        //        }
        //
        //        rpc.baseUri = "http://www.example.com/api/v2"
        //        rpc.index()
        //                .then {
        //                }
        //                .finnaly {
        //
        //                }
    }
}