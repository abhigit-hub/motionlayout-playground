package com.footinit.motionlayoutplayground.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.motionlayoutplayground.model.CustomModel
import com.footinit.motionlayoutplayground.utils.ViewUtils

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var customModelList = listOf(
        CustomModel("1", "Move-All-Views (ImageFilter, PropertySet, KeyFrameSet)", ViewUtils.getRandomColor()),
        CustomModel("2", "Move-A-View (In Progress)", ViewUtils.getRandomColor()),
        CustomModel("3", "Move-A-View (Constraints in two layout files)", ViewUtils.getRandomColor()),
        CustomModel("4", "Move-A-View (Constraints in motion-scene file)", ViewUtils.getRandomColor()),
        CustomModel("5", "Move-A-View (ImageFilter)", ViewUtils.getRandomColor()),
        CustomModel("6", "Move-A-View (Toggle Alpha using PropertySet)", ViewUtils.getRandomColor()),
        CustomModel("7", "Move-A-View (Arc Motion using KeyFrame)", ViewUtils.getRandomColor())
    )

    private var _customModelList = MutableLiveData(customModelList)

    fun getCustomModelList(): LiveData<List<CustomModel>> {
        return _customModelList
    }
}