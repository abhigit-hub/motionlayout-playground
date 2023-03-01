package com.footinit.motionlayoutplayground.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.footinit.motionlayoutplayground.R
import com.footinit.motionlayoutplayground.ui.*
import com.footinit.motionlayoutplayground.databinding.ActivityMainBinding
import com.footinit.motionlayoutplayground.ext.showToast
import com.footinit.motionlayoutplayground.utils.ViewUtils
import com.footinit.motionlayoutplayground.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainGridAdapter.Callback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MainGridAdapter

    private val mainViewModel: MainViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews()
        setUpAdapter()
        initObservers()
    }

    private fun initViews() {
        ViewUtils.setUpColors(resources.getStringArray(R.array.colors))
        setUpToolbar()
    }

    private fun setUpAdapter() {
        adapter = MainGridAdapter(this@MainActivity)
        val layoutManager = GridLayoutManager(applicationContext, 2)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding.rvMain.layoutManager = layoutManager
        binding.rvMain.adapter = adapter
    }

    private fun initObservers() {
        mainViewModel.getCustomModelList().observe(this@MainActivity) {
            adapter.updateList(it)
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onItemSelected(id: Int, message: String) {
        when (id) {
            1 -> startActivity(Activity_01.getStartIntent(applicationContext, message))
            2 -> applicationContext.showToast("In Progress")
            3 -> startActivity(Activity_03.getStartIntent(applicationContext, message))
            4 -> startActivity(Activity_04.getStartIntent(applicationContext, message))
            5 -> startActivity(Activity_05.getStartIntent(applicationContext, message))
            6 -> startActivity(Activity_06.getStartIntent(applicationContext, message))
            7 -> startActivity(Activity_07.getStartIntent(applicationContext, message))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.setCallback(this@MainActivity)
    }

    override fun onPause() {
        adapter.removeCallback()
        super.onPause()
    }
}