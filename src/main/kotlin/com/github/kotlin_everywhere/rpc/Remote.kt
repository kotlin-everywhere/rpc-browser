package com.github.kotlin_everywhere.rpc

import org.w3c.fetch.Response
import kotlin.browser.window

abstract class BaseEndpoint(protected val remote: Remote, url: String) {
    protected val _url = url

    val url: String
        get() = remote.baseUri + _url
}

class Endpoint<T>(remote: Remote, url: String) : BaseEndpoint(remote, url) {
    fun fetch(): Promise<T> {
        val body: (Response) -> Promise<T> = { it.json() }
        return Promise.resolve(window.fetch(url) as Promise<Response>).then(body)
    }

    fun <P : Any> with(): EndpointWithParam<T, P> {
        return EndpointWithParam(remote, _url)
    }
}

@native
private fun encodeURIComponent(string: String): String

class EndpointWithParam<R, P : Any>(remote: Remote, url: String) : BaseEndpoint(remote, url) {
    fun fetch(param: P): Promise<R> {
        val body: (Response) -> Promise<R> = { it.json() }
        val url = "${this.url}?data=${encodeURIComponent(JSON.stringify(param))}"
        return Promise.resolve(window.fetch(url) as Promise<Response>).then(body)
    }
}

abstract class Remote {
    lateinit var baseUri: String

    fun <T> get(url: String): Endpoint<T> {
        return Endpoint(this, url)
    }
}
