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

        // test url building
        assertEquals("http://www.example.com/api/v2/", remote.index.url)

        // test get method
        remote.index
                .fetch()
                .then { index -> assertEquals("1.0.0", index.version) }
                .mockFetch("""{"version": "1.0.0"}""")
                .assertAsync()

        // test post method
    }
}