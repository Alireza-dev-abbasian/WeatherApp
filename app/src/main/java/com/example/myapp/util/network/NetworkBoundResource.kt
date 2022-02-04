package com.example.myapp.util.network

import android.util.Log
import com.example.myapp.data.enums.ErrorType
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = {}
) = flow<Resource<ResultType>> {

    emit(Resource.Loading(null))
    val data = query().first()

    val flow = run {
        emit(Resource.Loading("", data))
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.Error("اتصال اینترنت خود را بررسی کنید", it, ErrorType.SERVER_ERROR) }
        }
    }
    emitAll(flow)

}