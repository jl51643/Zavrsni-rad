package hr.fer.zr.smartbell.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hr.fer.zr.smartbell.R
import hr.fer.zr.smartbell.models.CameraModel
import hr.fer.zr.smartbell.viewModel.CameraViewModel

class CameraListAdapter(cameraViewModel: CameraViewModel) : RecyclerView.Adapter<CameraListAdapter.ViewHolder>() {

    private var cameraViewModel : CameraViewModel = cameraViewModel

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cameraImage: ImageView = itemView.findViewById(R.id.recyclerCameraIV)
        var reloadButton: Button? = null
        var cameraId: TextView? = null
        var cameraName: TextView? = null

        init {
            reloadButton = itemView.findViewById(R.id.recyclerReloadButton)
            cameraId = itemView.findViewById(R.id.cameraIdRecyclerTV)
            cameraName = itemView.findViewById(R.id.cameraNameRecycler)
        }

        fun bind(camera : CameraModel) {
            cameraId?.text = camera.id.toString()
            cameraName?.text = camera.name
            reloadCamera(camera.url, itemView.context, cameraImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val cameraImageListElement = inflater.inflate(R.layout.show_camera_list_element, parent, false)
        val cameraImageView = cameraImageListElement.findViewById<ImageView>(R.id.recyclerCameraIV)
        val cameraId = cameraImageListElement.findViewById<TextView>(R.id.cameraIdRecyclerTV)
        val reloadButton = cameraImageListElement.findViewById<Button>(R.id.recyclerReloadButton)

        reloadButton.setOnClickListener {
            val id = cameraId.text.toString().toInt()
            val camera = cameraViewModel.getCameraForId(id)
            if (camera != null) {
                reloadCamera(camera.url, it.context, cameraImageView)
                reloadButton.isEnabled = false

                Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                    override fun run() {
                        reloadButton.isEnabled = true
                    }
                },5000)
            }

        }

        return ViewHolder(cameraImageListElement)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                holder.bind(cameraViewModel.camerasList.value!![position])
            }
        }
    }

    override fun getItemCount(): Int {
        return if (cameraViewModel.camerasList.value != null) {
            cameraViewModel.camerasList.value!!.size
        } else {
            0
        }
    }

    fun submitList(cameraList: ArrayList<CameraModel>) {
        cameraViewModel.camerasList.value = cameraList
    }

    private fun reloadCamera(url: String, context: Context, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.security_cam)
            .error(R.drawable.error_image)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView)
    }
}