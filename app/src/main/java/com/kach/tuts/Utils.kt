package com.kach.tuts

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.kach.tuts.viewmodels.ViewModelFactory

inline fun <reified VM : ViewModel> Fragment.activityViewModels() =
    activityViewModels<VM>(factoryProducer = ::ViewModelFactory)

inline fun <reified VM : ViewModel> Fragment.viewModels() =
    viewModels<VM>(factoryProducer = ::ViewModelFactory)