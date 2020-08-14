package com.example.test.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.example.test.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Android.Developer.
 * @version 1.0
 */


object CommonUtils {

    private var text: String? = null

    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels

    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels

    fun showLoadingDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        //        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    //2019-02-02T15:00:00+01:00
//    2018-12-11T19:30:00+01:00
    fun getFormattedDate(dateString: String?): String {
        if (dateString != null && dateString.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val convertedDate = dateFormat.parse(dateString)
                val simpleDate = SimpleDateFormat("dd MMMM yyyy")
                simpleDate.timeZone = TimeZone.getTimeZone("UTC")
                return simpleDate.format(convertedDate).capitalizeWords()
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
        }
        return ""
    }

    fun getFormattedTime(dateString: String?): String {
        if (dateString != null && dateString.isNotEmpty()) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val convertedDate = dateFormat.parse(dateString)
                val simpleDate = SimpleDateFormat("HH:mm")
                simpleDate.timeZone = TimeZone.getTimeZone("UTC")
                return simpleDate.format(convertedDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
        }
        return ""
    }

    //07-03-2019
    fun getFormattedDate2(dateString: String?): String {
        if (dateString != null && dateString.isNotEmpty()) {

            val inputFormat = SimpleDateFormat("dd-MM-yyyy")
            val outputFormat = SimpleDateFormat("dd MMMM yyyy")
            val date = inputFormat.parse(dateString)
            return outputFormat.format(date).capitalizeWords()


            /*val dateFormat = SimpleDateFormat("dd-mm-yyyy")
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val convertedDate = dateFormat.parse(dateString)
                val simpleDate = SimpleDateFormat("dd MMMM yyyy")
//                simpleDate.timeZone = TimeZone.getTimeZone("UTC")
                return simpleDate.format(convertedDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }*/
        }
        return ""
    }

    fun getFormattedCurrency(langCode: String, value: Double?): String {
        val locale: Locale = if (langCode == "system") {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Resources.getSystem().configuration.locales.get(0)
            } else {
                Resources.getSystem().configuration.locale
            }
        } else {
            Locale(langCode)
        }
        return try {
            val nf = NumberFormat.getNumberInstance(locale)
            nf.format(value).replace("¤", "")
//            val symbols = DecimalFormatSymbols(locale)
//            val df = DecimalFormat("0.##", symbols)
//            val numberz = df.format(value)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isEmailValid(email: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isPasswordValid(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN = "(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }


    private const val key128 = "8080808080808080"                  // 16 bytes for AES128
    private const val iv = "8080808080808080"                 // 16 bytes for AES128

    fun encryptAES(string: String): ByteArray? {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(
            Cipher.ENCRYPT_MODE,
            SecretKeySpec(key128.toByteArray(), "AES"),
            IvParameterSpec(iv.toByteArray())
        )
        return cipher.doFinal(string.toByteArray())
    }

    fun isSimAvailable(context: Context): Boolean {
        val tm =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager  //gets the current TelephonyManager
        return tm.simState != TelephonyManager.SIM_STATE_ABSENT
    }

    fun hideKeyboard(activity: Activity) {
        // Check if no view has focus:
        val view = activity.currentFocus
        if (view != null) {
//            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            try {
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
//            val background = ContextCompat.getDrawable(activity, com.frieslandlease.R.mipmap.login_bg)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(activity, android.R.color.transparent)
            window.navigationBarColor =
                ContextCompat.getColor(activity, android.R.color.transparent)
//            window.setBackgroundDrawable(background)
        }
    }

    fun readFileFromRawDirectory(context: Context, resourceId: Int): String {
        val iStream = context.resources.openRawResource(resourceId)
        var byteStream: ByteArrayOutputStream? = null
        try {
            val buffer = ByteArray(iStream.available())
            iStream.read(buffer)
            byteStream = ByteArrayOutputStream()
            byteStream.write(buffer)
            byteStream.close()
            iStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return byteStream!!.toString()
    }

    fun fromHtml(html: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }


    fun convert2DecPlace(value: Double?): Double {
        val formatter: NumberFormat = NumberFormat.getNumberInstance()
        formatter.minimumFractionDigits = 2
        formatter.maximumFractionDigits = 2
        formatter.format(value)
        return formatter.format(value).toDouble()
    }

    fun wordCap(value: String): String {
        val strArrayOBJ =
            value.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = StringBuilder()
        for (s in strArrayOBJ) {
            val cap = s.substring(0, 1).toUpperCase(Locale.getDefault()) + s.substring(1)
            builder.append("$cap ")
        }
        return builder.toString()
    }
}