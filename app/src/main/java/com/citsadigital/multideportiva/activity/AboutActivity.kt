package com.citsadigital.multideportiva.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.citsadigital.multideportiva.R
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        aboutOpenWebsite.setOnClickListener {
            val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://${application.getString(R.string.text_about_website)}")
            )

            intent.resolveActivity(packageManager).let { startActivity(intent) }
        }
    }
}
