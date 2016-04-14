package com.github.kotlin_everywhere.rpc

import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.browser.window

enum class Method {
    GET, POST
}

abstract class BaseEndpoint(protected val remote: Remote, url: String, protected val method: Method) {
    protected val _url = url

    val url: String
        get() = remote.baseUri + _url
}

class Endpoint<T>(remote: Remote, url: String, method: Method) : BaseEndpoint(remote, url, method) {
    fun fetch(): Promise<T> {
        val body: (Response) -> Promise<T> = { it.json() }
        val requestInit = jsObject<RequestInit> {
            method = this@Endpoint.method.name
        }
        return Promise.resolve(window.fetch(url, requestInit) as Promise<Response>).then(body)
    }

    fun <P : Any> with(): EndpointWithParam<T, P> {
        return EndpointWithParam(remote, _url, method)
    }
}

@native
private fun encodeURIComponent(string: String): String

class EndpointWithParam<R, P : Any>(remote: Remote, url: String, method: Method) : BaseEndpoint(remote, url, method) {
    private val responseToJson: (Response) -> Promise<R> = { it.json() }

    fun fetch(param: P): Promise<R> {
        val url = this.url + if (method == Method.GET) "?data=${encodeURIComponent(JSON.stringify(param))}" else ""
        val requestInit = jsObject<RequestInit> {
            method = this@EndpointWithParam.method.name
            headers = json("Content-Type" to "application/json")
            if (this@EndpointWithParam.method !in listOf(Method.GET)) {
                this.body = JSON.stringify(param)
            }
        }
        return Promise.resolve(window.fetch(url, requestInit) as Promise<Response>).then(responseToJson)
    }

    fun fetch(body: P.() -> Unit): Promise<R> {
        return fetch(jsObject(body))
    }
}

abstract class Remote {
    lateinit var baseUri: String

    fun <T> get(url: String): Endpoint<T> {
        return Endpoint(this, url, Method.GET)
    }

    fun <T> post(url: String): Endpoint<T> {
        return Endpoint(this, url, Method.POST)
    }
}

fun <T> jsObject(body: (T.() -> Unit)? = null): T {
    val obj: T = js("({})")
    if (body != null) {
        obj.body()
    }
    return obj
}