package com.footinit.motionlayoutplayground.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.footinit.motionlayoutplayground.databinding.Activity06Binding

class Activity_06 : AppCompatActivity() {

    private lateinit var binding: Activity06Binding

    companion object {
        val TAG = Activity_06::class.java.simpleName

        val KEY_MESSAGE = "title"

        fun getStartIntent(context: Context, message: String): Intent {
            val intent = Intent(context, Activity_06::class.java)
            intent.putExtra(KEY_MESSAGE, message)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity06Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)

        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setTitle(intent.getStringExtra(KEY_MESSAGE))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}