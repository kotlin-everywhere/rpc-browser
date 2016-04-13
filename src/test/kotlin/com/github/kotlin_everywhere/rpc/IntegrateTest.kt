package com.github.kotlin_everywhere.rpc

import org.junit.Test
import kotlin.test.assertEquals


interface Index {
    val version: String
}

class IntegrateTest {
    @Test
    fun testIntegrate() {
        val remote = object : Remote() {
            val index = get<Index>("/")

        }
        remote.baseUri = "http://localhost:3333"

        // test url building
        assertEquals("http://localhost:3333/", remote.index.url)

        // test get method
        remote.index
                .fetch()
                .then { index -> assertEquals("1.0.0", index.version) }
                .assertAsync()

        // test post method
    }
}