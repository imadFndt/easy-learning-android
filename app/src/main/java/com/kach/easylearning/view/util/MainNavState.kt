package com.kach.easylearning.view.util

import com.kach.easylearning.R

class MainNavState : NavState() {
    override var graphId: Int = R.id.main_nav_graph
    override val destinations = listOf(R.id.collections_list, R.id.collection_description, R.id.questions)
    override var actualDestinationId: Int = destinations[0]
    override val forwardDirections = listOf(R.id.collections_to_description, R.id.description_to_questions)

    var isItemSelected = false
        set(value) {
            field = value
            updateDestination()
        }

    var isTestGoing = false
        set(value) {
            if (!isItemSelected) throw IllegalStateException()
            field = value
            updateDestination()
        }

    private fun updateDestination() {
        actualDestinationId = when {
            isTestGoing && isItemSelected -> destinations[2]
            isItemSelected -> destinations[1]
            else -> destinations[0]
        }
    }

}