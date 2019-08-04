package com.android.data.remote

import com.android.data.remote.models.responses.error.ErrorResponse
import com.android.domain.exceptions.EstablishConnectionException
import com.android.domain.exceptions.RemoteException
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type
import java.net.UnknownHostException


internal class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<Any, Any> {
        return RxCallAdapterWrapper(
            (original.get(
                returnType,
                annotations,
                retrofit
            ) as CallAdapter<Any, Any>?)!!
        )
    }

    private class RxCallAdapterWrapper(private val wrapped: CallAdapter<Any, Any>) : CallAdapter<Any, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }

        override fun adapt(call: Call<Any>): Any {
            val result = wrapped.adapt(call)
            if (result is Single<*>) {
                return result
                    .doOnSuccess {
                        throwUnhappyCases(it)
                    }
                    .onErrorResumeNext { Single.error(asRetrofitException(it)) }
            }

            if (result is Observable<*>) {
                return result
                    .doOnNext { throwUnhappyCases(it) }
                    .onErrorResumeNext(Function { Observable.error(asRetrofitException(it)) })
            }

            return if (result is Completable) {
                result.onErrorResumeNext { Completable.error(asRetrofitException(it)) }
            } else result
        }

        private fun throwUnhappyCases(response: Any) {
            if (response is ErrorResponse && response.errorData.error.isNotEmpty()) {
                throw RemoteException(0, 0, response.errorData.error[0].msg)
            }
        }

        private fun asRetrofitException(throwable: Throwable): Throwable {
            // We had non-200 http error
            when (throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    val bodyAsString = response.errorBody()?.string()

                    return try {
                        val body = Gson().fromJson(bodyAsString, ErrorResponse::class.java)
                        RemoteException(response.code(), 0, body.errorData.error[0].msg)
                    } catch (e: Exception) {
                        RemoteException(
                            response.code(), 0, bodyAsString
                                ?: "${throwable.javaClass.name} -> ${e.javaClass.name}"
                        )
                    }
                }
                is UnknownHostException -> return EstablishConnectionException()
                is RemoteException -> return throwable
                else -> return RemoteException(0, 0, throwable.javaClass.name)
            }
        }
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }
    }
}