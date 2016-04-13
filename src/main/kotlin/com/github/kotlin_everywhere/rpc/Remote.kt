package com.github.kotlin_everywhere.rpc

import org.w3c.fetch.Response

class Endpoint<T>(private val remote: Remote, url: String) {
    private val _url = url

    val url: String
        get() = remote.baseUri + _url

    fun fetch(): Promise<T> {
        val body: (Response) -> Promise<T> = { it.json() }
        return fetch(url).then(body)
    }
}

abstract class Remote {
    lateinit var baseUri: String

    fun <T> get(url: String): Endpoint<T> {
        return Endpoint(this, url)
    }
}
