package com.github.kotlin_everywhere.rpc

import com.github.kotlin_everywhere.JavaScript.assertAsync
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


interface Index {
    val version: String
}

interface Echo {
    val message: String
}

interface EchoParam {
    var message: String
}

interface Add {
    val result: Int
}

interface AddParam {
    var value1: Int
    var value2: Int
}

class IntegrationTest {
    @Test
    fun testIntegrate() {
        val remote = object : Remote() {
            val index = get<Index>("/")
            val echo = get<Echo>("/echo").with<EchoParam>()
            var add = post<Add>("/add").with<AddParam>()
            var postOnly = post<Boolean>("/post-only")
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
                .fetch { message = "hello, world" }
                .then { echo -> assertEquals("hello, world", echo.message) }
                .assertAsync()

        // test post method without param
        remote.postOnly
                .fetch()
                .then { boolean -> assertTrue(boolean) }
                .assertAsync()

        // test post method with param
        remote.add
                .fetch { value1 = 1; value2 = 2 }
                .then { add -> assertEquals(3, add.result) }
                .assertAsync()
    }
}