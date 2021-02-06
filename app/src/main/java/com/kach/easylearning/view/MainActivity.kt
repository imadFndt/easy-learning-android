package com.kach.easylearning.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.kach.easylearning.R
import com.kach.easylearning.databinding.MainActivityBinding
import com.kach.easylearning.view.util.setupWithNavController
import com.kach.easylearning.viewmodels.MainViewModel
import com.kach.easylearning.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels(factoryProducer = ::ViewModelFactory)
    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    lateinit var currentNavController: LiveData<NavController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        currentNavController = setupBottomNavigationBar()
        currentNavController.observe(this) { controller ->
            viewModel.setNavState(controller.graph.id)
        }
        viewModel.navState.observe(this) { navState ->
            currentNavController.value?.let { navState?.navigateToDestination(it) }
            currentNavController.value?.graph?.id?.let { updateToolbar(it) }
        }
    }

    private fun updateToolbar(id: Int) {
        binding.toolbar.navigationIcon = null
        binding.toolbar.title =
            when (id) {
                R.id.main_nav_graph -> {
                    when (currentNavController.value?.currentDestination?.id) {
                        R.id.collections_list -> getString(R.string.home)
                        else -> {
                            binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_new_24)
                            binding.toolbar.setNavigationOnClickListener { onBackPressed() }
                            viewModel.selectedCollection.value?.title
                        }
                    }
                }
                R.id.favorites_nav_graph -> getString(R.string.favorite)
                R.id.profile_nav_graph -> getString(R.string.profile)
                else -> throw IllegalArgumentException()
            }
    }

    private fun setupBottomNavigationBar(): LiveData<NavController> =
        binding.bottomNavigationView.setupWithNavController(
            navGraphIds = listOf(
                R.navigation.main_nav_graph,
                R.navigation.favorites_nav_graph,
                R.navigation.profile_nav_graph
            ),
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_fragment,
        )

    override fun onBackPressed() {
        viewModel.selectedCollection.value?.let { viewModel.setSelectedCollection(null) }
            ?: run { super.onBackPressed() }
    }
}
