package hr.fer.zr.smartbell.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import hr.fer.zr.smartbell.R
import hr.fer.zr.smartbell.database.DatabaseHandler
import hr.fer.zr.smartbell.models.CameraModel
import hr.fer.zr.smartbell.viewModel.CameraViewModel
import hr.fer.zr.smartbell.viewModel.factory.CameraViewModelFactory
import java.util.*

class AddCameraActivity : AppCompatActivity() {

    private lateinit var addNewCameraButton: Button
    private lateinit var newCameraName: TextView
    private lateinit var newCameraUrl: TextView
    private lateinit var newCameraTopic: TextView
    private lateinit var UUIDGeneratorButton: Button

    lateinit var viewModel: CameraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_camera)

        newCameraName = findViewById(R.id.newCameraName)
        newCameraUrl = findViewById(R.id.newCameraUrl)
        addNewCameraButton = findViewById(R.id.addNewCameraButton)
        newCameraTopic = findViewById(R.id.newCameraTopic)
        UUIDGeneratorButton = findViewById(R.id.generateTopicButton)

        val databaseHandler: DatabaseHandler = DatabaseHandler(applicationContext)
        val viewModelFactory = CameraViewModelFactory(databaseHandler = databaseHandler)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CameraViewModel::class.java)

        addNewCameraButton.setOnClickListener {
            if (newCameraName.text.isNullOrEmpty() || newCameraUrl.text.isNullOrEmpty() || newCameraTopic.text.isNullOrEmpty()) {
                Snackbar.make(it, "Camera name, camera URL and unique name are required", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            } else {
                val camera = CameraModel(0,
                                        name = newCameraName.text.toString(),
                                        url = newCameraUrl.text.toString(),
                                        subscribed = false,
                                        topic = newCameraTopic.text.toString())
                viewModel.addCamera(camera)

                finish()
            }
        }

        UUIDGeneratorButton.setOnClickListener {
            val topicUUID = UUID.randomUUID()
            newCameraTopic.text = topicUUID.toString()
        }
    }
}