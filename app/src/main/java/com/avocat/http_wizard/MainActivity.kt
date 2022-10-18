package com.avocat.http_wizard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalUriHandler
import androidx.core.view.WindowCompat
import com.avocat.http_wizard.ui.Main
import com.avocat.http_wizard.ui.theme.HEADWizardTheme
import okhttp3.*
import java.io.IOException


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    private var url = ""
    private var method = "POST"
    private var res: Response? = null

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            HEADWizardTheme {
                Main(
                    apiUrlCallback = { url = it },
                    methodChangedCallback = { method = it },
                    sendCallback = { sendReq() },
                    openTg = { openTg() }
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
    private fun sendReq() {
        // fix URL
        if (!url.endsWith('/') && !url.contains(Regex("[&?]")))
            url += '/'

        if (method == "POST")
            return

        val builder = Request.Builder()
            .url(url)
            .method(method, null)

        val req = builder.build()

        client.newCall(req).enqueue(
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    res = null
                }
                override fun onResponse(call: Call, response: Response) {
                    println(response)
                    res = response
                }
            }
        )
    }
}