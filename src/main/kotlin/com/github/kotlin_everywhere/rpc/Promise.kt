@file:Suppress("unused")

package com.github.kotlin_everywhere.rpc

@native
interface Promise0 {
    fun then(body: () -> Unit): Promise0 = noImpl
    fun <T> then(body: () -> Promise<T>): Promise<T> = noImpl

    fun catch(body: (e: dynamic) -> Unit): Promise0 = noImpl

    fun finally(body: () -> Unit): Promise0 = noImpl
}

@native
class Promise<T> : Promise0 {
    constructor(body: (resolve: (T) -> Unit) -> Unit)

    constructor(body: (resolve: (T) -> Unit, reject: (Any) -> Unit) -> Unit)

    fun then(body: (T) -> Unit): Promise0
    fun <U> then(body: (T) -> U): Promise<U>
    fun <U> then(body: (T) -> Promise<U>): Promise<U>

    fun <T> catch(body: (e: dynamic) -> T): Promise<T>

    fun finally(body: (T) -> Unit): Promise<T>

    companion object {
        fun delay(ms: Int): Promise0
        fun resolve(): Promise0
        fun <T> resolve(promise: Promise<T>): Promise<T>
        fun <T> resolve(value: T): Promise<T>
    }
}