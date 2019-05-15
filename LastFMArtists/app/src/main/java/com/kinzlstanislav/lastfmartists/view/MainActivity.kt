package com.kinzlstanislav.lastfmartists.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kinzlstanislav.lastfmartists.R
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
