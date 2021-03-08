package com.peterlaurence.book.pawk.introduction.classes.whenwithinterface

class SomeType

interface Result
data class Success(val data: SomeType) : Result
data class Failure(val error: Throwable?) : Result

fun processResult(result: Result): SomeType? =
    when (result) {
        is Success -> result.data
        is Failure -> null
        else -> throw IllegalArgumentException("unknown result type")
    }
