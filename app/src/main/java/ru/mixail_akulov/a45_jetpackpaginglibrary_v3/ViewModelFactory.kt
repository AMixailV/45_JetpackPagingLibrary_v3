package ru.mixail_akulov.a45_jetpackpaginglibrary_v3

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

typealias ViewModelCreator<VM> = (savedStateHandle: SavedStateHandle) -> VM

class ViewModelFactory<VM : ViewModel>(
    owner: SavedStateRegistryOwner,
    private val viewModelCreator: ViewModelCreator<VM>
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle,
    ): T {
        return viewModelCreator(handle) as T
    }

}

inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(noinline creator: ViewModelCreator<VM>): Lazy<VM> {
    return viewModels { ViewModelFactory(this, creator) }
}