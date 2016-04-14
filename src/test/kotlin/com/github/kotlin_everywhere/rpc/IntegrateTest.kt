package com.github.kotlin_everywhere.rpc

import org.junit.Test
import kotlin.test.assertEquals


interface Index {
    val version: String
}

interface Echo {
    val message: String
}

interface EchoParam {
    var message: String
}

fun <T> jsObject(body: (T.() -> Unit)? = null): T {
    val obj: T = js("({})")
    if (body != null) {
        obj.body()
    }
    return obj
}

class IntegrateTest {
    @Test
    fun testIntegrate() {
        val remote = object : Remote() {
            val index = get<Index>("/")
            val echo = get<Echo>("/echo").with<EchoParam>()
        }
        remote.baseUri = "http://localhost:3333"

        // test url building
        assertEquals("http://localhost:3333/", remote.index.url)
        // test url building for EndpointWith Param
        assertEquals("http://localhost:3333/echo", remote.echo.url)

        // test get method
        remote.index
                .fetch()
                .then { index -> assertEquals("1.0.0", index.version) }
                .assertAsync()

        // test get method with param
        remote.echo
                .fetch(jsObject<EchoParam> { message = "hello, world" })
                .then { echo -> assertEquals("hello, world", echo.message) }
                .assertAsync()

        // test post method
    }
}