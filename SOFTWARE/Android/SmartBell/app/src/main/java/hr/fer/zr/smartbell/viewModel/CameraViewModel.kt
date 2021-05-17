package hr.fer.zr.smartbell.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.fer.zr.smartbell.database.DatabaseHandler
import hr.fer.zr.smartbell.models.CameraModel

class CameraViewModel(private val databaseHandler: DatabaseHandler) : ViewModel() {

    val camerasList: MutableLiveData<ArrayList<CameraModel>> = MutableLiveData()

    fun getCameras() {
        camerasList.value = databaseHandler.getCameras()
    }

    fun addCamera(camera: CameraModel) {
        databaseHandler.addCamera(camera)
    }

    fun deleteCamera(camera: CameraModel) : Int {
        val success = databaseHandler.deleteCamera(camera)
        getCameras()
        return success
    }

    fun updateSubscription(camera: CameraModel) : Int {
        val success = databaseHandler.updateSubscription(camera)
        //getCameras()
        return success
    }

    fun getCameraForId(id: Int) : CameraModel? {
        return databaseHandler.getCameraForId(id)
    }
}