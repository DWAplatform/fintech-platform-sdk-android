package com.dwaplatform.android.keys

data class CheckPin(val userid: String, val token: String, val status: CheckPinState)

enum class CheckPinState {
    SUCCESS, CODE_ERROR, LIMIT_REACHED
}
