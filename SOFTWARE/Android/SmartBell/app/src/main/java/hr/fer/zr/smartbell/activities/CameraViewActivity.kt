package hr.fer.zr.smartbell.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.fer.zr.smartbell.R
import hr.fer.zr.smartbell.adapters.CameraListAdapter
import hr.fer.zr.smartbell.database.DatabaseHandler
import hr.fer.zr.smartbell.viewModel.CameraViewModel
import hr.fer.zr.smartbell.viewModel.factory.CameraViewModelFactory

class CameraViewActivity : AppCompatActivity() {

    lateinit var cameraListAdapter: CameraListAdapter
    lateinit var viewModel: CameraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)

        val listOfCamerasImageView = findViewById<RecyclerView>(R.id.cameraRecycleView)
        listOfCamerasImageView.layoutManager = LinearLayoutManager(applicationContext)

        val decorator = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.cell_divider
            )!!
        )
        listOfCamerasImageView.addItemDecoration(decorator)

        val databaseHandler = DatabaseHandler(applicationContext)
        val viewModelFactory = CameraViewModelFactory(databaseHandler = databaseHandler)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CameraViewModel::class.java)

        cameraListAdapter = CameraListAdapter(viewModel)
        listOfCamerasImageView.adapter = cameraListAdapter

        viewModel.camerasList.value?.let { cameraListAdapter.submitList(it) }

        viewModel.camerasList.observe(this, Observer {
            cameraListAdapter.notifyDataSetChanged()
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCameras()
    }
}