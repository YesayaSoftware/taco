package com.candybytes.taco.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.candybytes.taco.api.TacoService
import com.candybytes.taco.data.repositories.CategoriesRepository
import com.candybytes.taco.vo.Category
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException

/**
 * CategoriesViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param categoriesRepository The repository that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during activity
 * or fragment lifecycle events.
 */
class CategoriesViewModel @ViewModelInject constructor(
    private val tacoService: TacoService,
    private val categoriesRepository: CategoriesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by CategoriesViewModel.
     *
     * Since we pass viewModelJob, you can cancel all coroutines launched by uiScope by calling
     * viewModelJob.cancel()
     */
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Event triggered for network error. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Flag to display the error message. Views should use this to get access
     * to the data.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _categories: LiveData<List<Category>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true

            viewModelScope.launch {
                try {
                    categoriesRepository.refreshCategories()
                    _eventNetworkError.value = false
                    _isNetworkErrorShown.value = false
                    _dataLoading.value = false

                } catch (networkError: IOException) {
                    // Show a Toast error message and hide the progress bar.
                    _eventNetworkError.value = true
                    _dataLoading.value = false
                }
            }
        }

        categoriesRepository.observeCategories().switchMap { categories }

    }

    val categories: LiveData<List<Category>> = _categories

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val isDataLoadingError = MutableLiveData<Boolean>()

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        loadCategories(true)
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [CategoryDataSource]
     */
    fun loadCategories(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    fun refresh() {
        _forceUpdate.value = true
    }

    /**
     * Resets the network error flag.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    override fun onCleared() {
        super.onCleared()

        viewModelScope.cancel()
    }



//    val totalTacoCategories = liveData {
//        try {
//            emit(tacoService.getAllCategoriesAsync())
//        } catch (e: Exception) {
//            Timber.e(e)
//        }
//    }.map { "Loaded ${it.size} categories\nImplement a list view and show all category elements." }


}
