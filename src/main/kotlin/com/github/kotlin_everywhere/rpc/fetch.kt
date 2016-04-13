package com.github.kotlin_everywhere.rpc

import org.w3c.fetch.Response

@native
fun fetch(url: String): Promise<Response>

