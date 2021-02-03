package com.kach.tuts.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.kach.tuts.R
import com.kach.tuts.viewmodels.MainActivityViewModel
import com.kach.tuts.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels(factoryProducer = ::ViewModelFactory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.selectedCollection.observe(this) { selected ->
            val controller = findNavController(R.id.nav_fragment)
            selected?.let { controller.navigate(R.id.collections_to_test) } ?: run { controller.navigateUp() }
        }
    }

    override fun onBackPressed() {
        viewModel.selectedCollection.value?.let { viewModel.setSelectedCollection(null) }
            ?: run { super.onBackPressed() }
    }
}
