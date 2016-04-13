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
        remote.baseUri = "http://www.example.com/api/v2"

        assertEquals("http://www.example.com/api/v2/", remote.index.url)

        remote.index
                .fetch()
                .then { index -> assertEquals("1.0.0", index.version) }
                .assertAsync()
    }
}