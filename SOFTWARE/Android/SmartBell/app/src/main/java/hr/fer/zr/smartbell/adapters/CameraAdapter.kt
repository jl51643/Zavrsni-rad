package hr.fer.zr.smartbell.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.messaging.FirebaseMessaging
import hr.fer.zr.smartbell.R
import hr.fer.zr.smartbell.models.CameraModel
import hr.fer.zr.smartbell.viewModel.CameraViewModel

class CameraAdapter(cameraViewModel: CameraViewModel) : RecyclerView.Adapter<CameraAdapter.ViewHolder>() {

    private var cameraViewModel : CameraViewModel = cameraViewModel

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var cameraId: TextView? = null
        private var subscribedSwitch: SwitchCompat? = null
        private var deleteCamera: ImageView? = null
        private var topicName: TextView? = null

        init {
            cameraId = itemView.findViewById(R.id.camraIdTV)
            subscribedSwitch = itemView.findViewById(R.id.switchSubscription)
            deleteCamera = itemView.findViewById(R.id.binImage)
            topicName = itemView.findViewById(R.id.topicTV)
        }

        fun bind (camera: CameraModel) {
            cameraId?.text = camera.id.toString()
            subscribedSwitch?.text = camera.name
            val subscriptionStatus = subscribedSwitch?.isChecked
            if (subscriptionStatus != camera.subscribed) {
                subscribedSwitch?.isChecked = camera.subscribed
            }
            topicName?.text = camera.topic
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val cameraListElement = inflater.inflate(R.layout.camera_element_layout, parent, false)
        val cameraId = cameraListElement.findViewById<TextView>(R.id.camraIdTV)
        val subscribedSwitch = cameraListElement.findViewById<SwitchCompat>(R.id.switchSubscription)
        val deleteCamera = cameraListElement.findViewById<ImageView>(R.id.binImage)

        subscribedSwitch.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            val id = cameraId.text.toString().toInt()
            val cameraModel = cameraViewModel.getCameraForId(id)
            cameraModel?.subscribed = b
            if (cameraModel != null) {
                cameraViewModel.updateSubscription(cameraModel)

                if (b) {
                    subscribeToTopic(cameraModel.topic, context)
                } else {
                    unsubscribeFromTopic(cameraModel.topic, context)
                }
            }
        }

        deleteCamera.setOnClickListener {
            val id = cameraId.text.toString().toInt()
            val cameraModel = cameraViewModel.getCameraForId(id)
            if (cameraModel != null) {
                cameraViewModel.deleteCamera(cameraModel)
            }
        }

        return ViewHolder(cameraListElement)
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

    private fun subscribeToTopic(topic: String, context: Context) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(context, "Failed to subscribe", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun unsubscribeFromTopic(topic: String, context: Context) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "You are not subscribed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to unsubscribe", Toast.LENGTH_SHORT).show()
                }
            }
    }

}