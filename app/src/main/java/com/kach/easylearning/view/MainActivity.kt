package com.kach.easylearning.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.kach.easylearning.R
import com.kach.easylearning.viewmodels.MainActivityViewModel
import com.kach.easylearning.viewmodels.ViewModelFactory

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
