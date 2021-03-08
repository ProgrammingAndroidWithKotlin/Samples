package com.peterlaurence.book.pawk.introduction.classes.whenwithsealedclass

class SomeType

sealed class Result
data class Success(val data: SomeType) : Result()
data class Failure(val error: Throwable?) : Result()

fun processResult(result: Result): SomeType? =
    when (result) {
        is Success -> result.data
        is Failure -> null
    }