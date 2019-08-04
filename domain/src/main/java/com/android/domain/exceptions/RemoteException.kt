package com.android.domain.exceptions

class RemoteException(
    private val httpStatusCode: Int,
    val responseCode: Int, responseMessage: String?
) : Exception(responseMessage) {
    fun isUnauthorized() = httpStatusCode in 400..499
}