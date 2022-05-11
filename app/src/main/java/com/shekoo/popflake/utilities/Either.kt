package com.shekoo.popflake.utilities

sealed class Either<S,E> {
    data class Success<S,E>(val data:S) : Either<S, E>()
    data class Error<S,E>(val errorCode:E ) : Either<S, E>()
}
