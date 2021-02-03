package com.kach.tuts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kach.tuts.model.Repository
import com.kach.tuts.model.TutsCollection
import com.kach.tuts.model.TutsCollectionTemp
import com.kach.tuts.model.TutsQuestion
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val collectionList: LiveData<List<TutsCollectionTemp>> get() = tempCollectionListData
    val questionList: LiveData<List<TutsQuestion>> get() = repository.questionList
    val selectedCollection: LiveData<TutsCollectionTemp?> get() = selectedCollectionData

    private val tempCollectionListData = repository.tempCollectionList

    private val selectedCollectionData = MutableLiveData<TutsCollectionTemp>()

    init {
        repository.loadAll()
    }

    fun requestQuestions(collection: TutsCollection) {
        repository.requestQuestions(collection)
    }

    fun setSelectedCollection(collection: TutsCollectionTemp?) {
        selectedCollectionData.value = collection
    }
}