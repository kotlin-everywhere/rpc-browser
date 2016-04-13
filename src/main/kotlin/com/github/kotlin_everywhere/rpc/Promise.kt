@file:Suppress("unused")

package com.github.kotlin_everywhere.rpc

@native
interface Promise0 {
    fun then(body: () -> Unit): Promise0 = noImpl

    fun catch(body: (e: dynamic) -> Unit): Promise0 = noImpl

    fun finally(body: () -> Unit): Promise0 = noImpl
}

@native
class Promise<T> : Promise0 {

    constructor(body: (resolve: (T) -> Unit) -> Unit)

    constructor(body: (resolve: (T) -> Unit, reject: (Any) -> Unit) -> Unit)

    fun then(body: (T) -> Unit): Promise0 = noImpl
    fun <U> then(body: (T) -> U): Promise<U> = noImpl

    fun <T> catch(body: (e: dynamic) -> T): Promise<T>

    fun finally(body: (T) -> Unit): Promise<T> = noImpl

    companion object {
        fun delay(ms: Int): Promise0 = noImpl
        fun <T> resolve(promise: Promise<T>): Promise<T>
    }
}