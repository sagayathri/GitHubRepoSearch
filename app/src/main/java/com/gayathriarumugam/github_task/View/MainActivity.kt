package com.gayathriarumugam.github_task.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gayathriarumugam.github_task.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.frag_container, SearchFragment()).commit()
    }
}