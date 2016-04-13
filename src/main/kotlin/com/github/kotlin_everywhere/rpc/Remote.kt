package com.github.kotlin_everywhere.rpc

import com.github.kotlin_everywhere.rpc.fetch as _fetch

class Method<T>(private val remote: Remote, url: String) {
    private val _url = url

    val url: String
        get() = remote.baseUri + _url

    fun fetch(): Promise<T> {
        return Promise.resolve(_fetch(url)).then<T> { it.json() }
    }
}

abstract class Remote {
    lateinit var baseUri: String

    fun <T> get(url: String): Method<T> {
        return Method(this, url)
    }
}
