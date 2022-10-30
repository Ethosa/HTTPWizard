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
import androidx.compose.ui.text.input.TextFieldValue
import com.avocat.http_wizard.obj.Query
import com.avocat.http_wizard.ui.Main
import com.avocat.http_wizard.ui.theme.HEADWizardTheme
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.Proxy


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    // client for request sending
    private var client = OkHttpClient()

    // request URL
    private var url = ""
        set(value) {
            editor.putString("url", value).apply()
            field = value
        }
    //request method
    private var method = "POST"
        set(value) {
            editor.putString("method", value).apply()
            field = value
        }
    // request proxy host
    private var proxy = ""
        set(value) {
            editor.putString("proxy", value).apply()
            field = value
        }
    // request proxy port
    private var proxyPort = "80"
        set(value) {
            editor.putString("port", value).apply()
            field = value
        }
    // list of url queries
    private var queries = mutableStateListOf<Query>()
    // list of request headers
    private var headers = mutableStateListOf<Query>()
        set(value) {
            var s = setOf<String>()
            for (i in value) {
                s = s.plus(i.name.text + ":-:-:" + i.value.text)
            }
            editor.putStringSet("headers", s).apply()
            field = value
        }
    // list of x-www-form-urlencoded
    private var formUrlencoded = mutableStateListOf<Query>()
        set(value) {
            var s = setOf<String>()
            for (i in value) {
                s = s.plus(i.name.text + ":-:-:" + i.value.text)
            }
            editor.putStringSet("x-www-form-urlencoded", s).apply()
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
            val uri: Uri
            try {
                uri = Uri.parse(url)
                for (param in uri.queryParameterNames) {
                    queries.add(Query(
                        TextFieldValue(param), TextFieldValue(uri.getQueryParameter(param).toString())
                    ))
                }
            } catch (e: Exception) { }
            val h = this.getStringSet("headers", setOf())?.toSet()
            if (h != null) {
                for (i in h) {
                    val splitted = i.split(":-:-:")
                    headers.add(Query(
                        TextFieldValue(splitted[0]), TextFieldValue(splitted[1])
                    ))
                }
            }
            if (queries.size == 0)
                queries.add(Query())
            if (headers.size == 0)
                headers.add(Query())
            if (formUrlencoded.size == 0)
                formUrlencoded.add(Query())
        }

        setContent {
            HEADWizardTheme {
                Main(
                    apiUrlCallback = { url = it },
                    methodChangedCallback = { method = it },
                    sendCallback = { onResponse, onFailure -> sendReq(onResponse, onFailure) },
                    openTg = { openTg() },
                    onProxyChanged = { hostname, port ->
                        proxy = hostname
                        if (port != "")
                            proxyPort = port
                    },
                    onQueriesChanged = {
                        queries = it
                    },
                    onHeadersChanged = {
                        headers = it
                    },
                    onFormUrlencodedChanged = {
                        formUrlencoded = it
                    },
                    queries,
                    headers,
                    formUrlencoded,
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