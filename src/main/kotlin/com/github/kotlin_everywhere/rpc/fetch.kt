package com.github.kotlin_everywhere.rpc

import org.w3c.fetch.Response
import kotlin.browser.window


private val mockResponseBodies = mutableListOf<String>()

fun <E : Any> MutableList<E>.shift(): E? {
    if (isNotEmpty()) {
        val e = first()
        remove(e)
        return e
    }
    return null
}


fun fetch(url: String): Promise<Response> {
    val body: () -> Promise<Response> = {
        val responseBody: Any? = mockResponseBodies.shift()
        if (responseBody != null) {
            Promise.resolve(Response(responseBody))
        } else {
            Promise.resolve(window.fetch(url))
        }
    }
    return Promise.resolve().then(body)
}

fun <T : Promise0> T.mockFetch(vararg responses: String): T {
    mockResponseBodies.addAll(responses)
    finally { mockResponseBodies.removeAll(responses) }
    return this
}

