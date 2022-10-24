package com.avocat.http_wizard

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.ExperimentalComposeUiApi
import com.avocat.http_wizard.obj.Query
import com.avocat.http_wizard.ui.Main
import com.avocat.http_wizard.ui.theme.HEADWizardTheme
import okhttp3.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var client = OkHttpClient()

    private var url = ""
        set(value) {
            editor.putString("url", value).apply()
            field = value
        }
    private var method = "POST"
        set(value) {
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
    private var queries = mutableStateListOf<Query>()
        set(value) {
            editor.putString(
                "queries", value.joinToString("&")
            )
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("avocat::httpwizard", 0)
        editor = sharedPreferences.edit()

        with(sharedPreferences) {
            method = this.getString("method", "POST").toString()
            proxy = this.getString("proxy", "").toString()
            proxyPort = this.getString("port", "80").toString()
            url = this.getString("url", "").toString()
        }

        setContent {
            HEADWizardTheme {
                Main(
                    apiUrlCallback = { url = it },
                    methodChangedCallback = { method = it },
                    sendCallback = { onResponse, onFailure -> sendReq(onResponse, onFailure) },
                    openTg = { openTg() },
                    proxyChanged = { hostname, port ->
                        proxy = hostname
                        if (port != "")
                            proxyPort = port
                    },
                    queriesChanged = {
                        queries = it
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