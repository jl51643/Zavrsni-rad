package hr.fer.zr.smartbell.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hr.fer.zr.smartbell.R
import hr.fer.zr.smartbell.adapters.CameraAdapter
import hr.fer.zr.smartbell.adapters.CameraListAdapter
import hr.fer.zr.smartbell.database.DatabaseHandler
import hr.fer.zr.smartbell.viewModel.CameraViewModel
import hr.fer.zr.smartbell.viewModel.factory.CameraViewModelFactory

class MainActivity : AppCompatActivity() {

    //private lateinit var imageView: ImageView
    //private lateinit var reloadButton: Button

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

        val databaseHandler: DatabaseHandler = DatabaseHandler(applicationContext)
        val viewModelFactory = CameraViewModelFactory(databaseHandler = databaseHandler)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CameraViewModel::class.java)

        cameraListAdapter = CameraListAdapter(viewModel)
        listOfCamerasImageView.adapter = cameraListAdapter

        viewModel.camerasList.value?.let { cameraListAdapter.submitList(it) }

        viewModel.camerasList.observe(this, Observer {
            cameraListAdapter.notifyDataSetChanged()
        })

        /*imageView = findViewById(R.id.recyclerCameraIV)
        reloadButton = findViewById(R.id.recyclerReloadButton)*/

        /*reloadButton.setOnClickListener {
            reloadCamera("http://161.53.19.52/SnapshotJPEG?Resolution=384x288&Quality=Standard&View=Normal&Count=71694700")
            reloadButton.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    reloadButton.isEnabled = true
                }
            },5000)
        }*/

        //reloadCamera("http://161.53.19.52/SnapshotJPEG?Resolution=384x288&Quality=Standard&View=Normal&Count=71694700")
    }

    /*private fun reloadCamera(url: String) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.security_cam)
                .error(R.drawable.error_image)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView)
    }*/

    override fun onResume() {
        super.onResume()
        viewModel.getCameras()
    }
}