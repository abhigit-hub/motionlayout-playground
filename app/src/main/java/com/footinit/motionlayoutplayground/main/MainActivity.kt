package com.footinit.motionlayoutplayground.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.footinit.motionlayoutplayground.R
import com.footinit.motionlayoutplayground.activity.*
import com.footinit.motionlayoutplayground.extensions.showToast
import com.footinit.motionlayoutplayground.model.CustomModel
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity(), MainGridAdapter.Callback {

    lateinit var adapter: MainGridAdapter

    lateinit var colors: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colors = resources.getStringArray(R.array.colors)

        setUpToolbar()

        setUpAdapter()
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.app_name)
    }

    private fun setUpAdapter() {
        adapter = MainGridAdapter(this@MainActivity, prepareList())
        val layoutManager = GridLayoutManager(applicationContext, 2)
        layoutManager.orientation = RecyclerView.VERTICAL
        rvMain.layoutManager = layoutManager
        rvMain.adapter = adapter
    }

    private fun prepareList(): ArrayList<CustomModel> {
        val list = ArrayList<CustomModel>()
        list.add(CustomModel("1", "Move-All-Views (ImageFilter, PropertySet, KeyFrameSet)", getRandomColor()))
        list.add(CustomModel("2", "Move-A-View (In Progress)", getRandomColor()))
        list.add(CustomModel("3", "Move-A-View (Constraints in two layout files)", getRandomColor()))
        list.add(CustomModel("4", "Move-A-View (Constraints in motion-scene file)", getRandomColor()))
        list.add(CustomModel("5", "Move-A-View (ImageFilter)", getRandomColor()))
        list.add(CustomModel("6", "Move-A-View (Toggle Alpha using PropertySet)", getRandomColor()))
        list.add(CustomModel("7", "Move-A-View (Arc Motion using KeyFrame)", getRandomColor()))
        /*        list.add(new CustomModel("5", "E", getRandomColor()));
        list.add(new CustomModel("6", "F", getRandomColor()));
        list.add(new CustomModel("7", "G", getRandomColor()));*/
        return list
    }

    private fun getRandomColor(): Int {
        return Color.parseColor(colors[(0..colors.size - 1).random()])
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

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
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