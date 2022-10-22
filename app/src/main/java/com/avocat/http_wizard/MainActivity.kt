package com.avocat.http_wizard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.view.WindowCompat
import com.avocat.http_wizard.ui.Main
import com.avocat.http_wizard.ui.theme.HEADWizardTheme
import okhttp3.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    private var url = ""
        set(value) {
            editor.putString("url", value).apply()
            field = value
        }
    private var method = "POST"
        set(value) {
            println(method)
            editor.putString("method", value).apply()
            field = value
        }
    private var proxy = ""
        set(value) {
            editor.putString("proxy", value).apply()
            field = value
        }
    private var proxyPort = "80"
        set(value) {
            editor.putString("port", value).apply()
            field = value
        }

    private var client = OkHttpClient()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(BuildConfig.APPLICATION_ID, 0)
        editor = sharedPreferences.edit()

        with(sharedPreferences) {
            method = this.getString("method", "POST").toString()
            proxy = this.getString("proxy", "").toString()
            proxyPort = this.getString("port", "80").toString()
            url = this.getString("url", "").toString()
        }

        setContent {
//            WindowCompat.setDecorFitsSystemWindows(window, false)
            HEADWizardTheme {
                Main(
                    apiUrlCallback = { url = it },
                    methodChangedCallback = { method = it },
                    sendCallback = { x, y, z -> sendReq(x, y, z) },
                    openTg = { openTg() },
                    proxyChanged = { hostname, port ->
                        proxy = hostname
                        if (port != "")
                            proxyPort = port
                    },
                    sharedPreferences
                )
            }
        }
    }

    /**
     * Opens my telegram
     */
    private fun openTg() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/ethosa"))
        startActivity(intent)
    }

    /**
     * Builds and sends request
     */
    private fun sendReq(
            res: MutableState<Response?>,
            onResponse: (r: Response) -> Unit = {},
            onFailure: (e: IOException) -> Unit = {}
    ) {
        // fix URL
        if (!url.endsWith('/') && !url.contains(Regex("[&?]")))
            url += '/'

        if (method == "POST")
            return

        val builder = Request.Builder()
            .url(url)
            .method(method, null)

        val req = builder.build()

        if (proxy != "" && proxyPort != "")
            client = OkHttpClient.Builder().proxy(
                Proxy(Proxy.Type.HTTP, InetSocketAddress(proxy, proxyPort.toInt()))
            ).build()

        client.newCall(req).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    onFailure(e)
                }
                override fun onResponse(call: Call, response: Response) {
                    onResponse(response)
                }
            }
        )
    }
}