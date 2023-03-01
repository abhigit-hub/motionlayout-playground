package com.footinit.motionlayoutplayground.di

import com.footinit.motionlayoutplayground.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { MainViewModel(get()) }
}