package com.example.test.utils

/**
 * Created by Android.Developer.
 * @version 1.0
 */
object AppConstants {

    val SPLASH_DELAY: Long = 1000
    val TOAST_DELAY: Int = 2000
    val POST_DELAY: Long = 2000
    val STATUS_CODE_SUCCESS = "success"
    val STATUS_CODE_FAILED = "failed"

    val API_STATUS_CODE_LOCAL_ERROR = 0

    val DB_NAME = "app.db"
    val PREF_NAME = "app_pref"

    val NULL_INDEX = -1L

    val PERMISSION_REQUEST_CALL_PHONE = 101

    val STATIC_PAGE_INDEX = "STATIC_PAGE_INDEX"

    val STATUS_CODE_TOKEN_EXPIRE = 401

    enum class ApiSuccessStatus(val statusCode: Int) {
        RESETPASSWORD(501), //ResetId
        SUCCESS(200)//Success,
    }

    enum class ApiFailureStatus(val statusCode: Int) {
        FORBIDDEN(403), // LoginAttemptRemaining
        TEMPPASSWORD(502),  //Temp PasswordTimeout
        MAXATTEMPT(503),  // LoginAttemptMsg
        BLOCKUSER(504),  // BlockUser
        NOTFOUND(404),
        UNAUTHORIZED(401)
    }


}
