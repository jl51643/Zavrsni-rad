package hr.fer.zr.smartbell.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessaging
import hr.fer.zr.smartbell.R
import hr.fer.zr.smartbell.adapters.CameraAdapter
import hr.fer.zr.smartbell.database.DatabaseHandler
import hr.fer.zr.smartbell.viewModel.CameraViewModel
import hr.fer.zr.smartbell.viewModel.factory.CameraViewModelFactory

class SubscribeToTopicActivity : AppCompatActivity() {

    lateinit var cameraAdapter: CameraAdapter
    lateinit var viewModel: CameraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_to_topic)

        supportActionBar?.title = "Manage Cameras"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listOfCamerasView = findViewById<RecyclerView>(R.id.listOfCameras)
        listOfCamerasView.layoutManager = LinearLayoutManager(applicationContext)

        val decorator = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.cell_divider
            )!!
        )
        listOfCamerasView.addItemDecoration(decorator)

        val databaseHandler: DatabaseHandler = DatabaseHandler(applicationContext)
        val viewModelFactory = CameraViewModelFactory(databaseHandler = databaseHandler)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CameraViewModel::class.java)

        cameraAdapter = CameraAdapter(viewModel)
        listOfCamerasView.adapter = cameraAdapter

        viewModel.camerasList.value?.let { cameraAdapter.submitList(it) }

        viewModel.camerasList.observe(this, Observer {
            cameraAdapter.notifyDataSetChanged()
        })


        findViewById<FloatingActionButton>(R.id.addNewCamera).setOnClickListener { view ->
            val intent = Intent(this, AddCameraActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getCameras()
    }

}