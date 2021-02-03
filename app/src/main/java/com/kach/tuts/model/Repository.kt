package com.kach.tuts.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kach.tuts.remote.TutsService
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class Repository @Inject constructor(private val tutsService: TutsService) {
    val questionList: LiveData<List<TutsQuestion>> get() = questionListData
    val collectionList: LiveData<List<TutsCollection>> get() = collectionListData
    val tempCollectionList: LiveData<List<TutsCollectionTemp>> get() = tempCollectionListData

    private val questionListData = MutableLiveData<List<TutsQuestion>>()
    private val collectionListData = MutableLiveData<List<TutsCollection>>()
    private val tempCollectionListData = MutableLiveData<List<TutsCollectionTemp>>()

    fun requestQuestions(collection: TutsCollection) {
        //TODO SETVALUE
    }

    fun loadAll() {
        val collectionListTemp = mutableListOf<TutsCollectionTemp>()
        //tutsService.getCollections()
        collectionListTemp.addAll(collectionsMock.map {
            TutsCollectionTemp(
                id = it.id,
                authorId = it.authorId,
                created = it.created,
                description = it.description,
                title = it.title,
                totalItems = Random.nextInt(1, 100)
            )
        })
        tempCollectionListData.value = collectionListTemp
        questionListData.value = questionsMock
    }

    private val collectionsMock = mutableListOf(
        TutsCollection(
            id = "dasdas",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            title = "Some title"
        ),
        TutsCollection(
            id = "dasdasd",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            title = "Интегралы"
        ),
        TutsCollection(
            id = "dasdasdd",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            title = "Юмор 101"
        ),
        TutsCollection(
            id = "dasdasddd",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            title = "Пределы"
        ),
        TutsCollection(
            id = "dasdasdddd",
            authorId = "Алтавил И.Х.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            title = "Дифференциальные уравнения"
        )
    )

    val questionsMock = mutableListOf(
        TutsQuestion(
            id = "dasdas",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            data = "Ты пидор?"
        ),
        TutsQuestion(
            id = "dasdasd",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            data = "Ты пидор?"
        ),
        TutsQuestion(
            id = "dasdasdd",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            data = "Ты пидор?"
        ),
        TutsQuestion(
            id = "dasdasddd",
            authorId = "Качмазов А.А.",
            created = "2021-02-02T20:37:41.415Z",
            description = "Some description",
            data = "Ты пидор?"
        )
    )
}