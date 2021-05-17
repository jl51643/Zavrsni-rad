package hr.fer.zr.smartbell.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.fer.zr.smartbell.database.DatabaseHandler
import hr.fer.zr.smartbell.viewModel.CameraViewModel

class CameraViewModelFactory(private val databaseHandler: DatabaseHandler) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CameraViewModel(databaseHandler) as T
    }
}