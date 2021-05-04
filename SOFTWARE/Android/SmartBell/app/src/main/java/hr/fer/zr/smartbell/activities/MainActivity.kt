package hr.fer.zr.smartbell.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import hr.fer.zr.smartbell.R

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var reloadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        reloadButton = findViewById(R.id.reload_button)

        reloadButton.setOnClickListener {
            reloadCamera()
            reloadButton.isEnabled = false

            Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
                override fun run() {
                    reloadButton.isEnabled = true
                }
            },5000)
        }

        reloadCamera()
    }

    private fun reloadCamera() {
        Glide.with(this)
                .load("http://161.53.19.52/SnapshotJPEG?Resolution=384x288&Quality=Standard&View=Normal&Count=71694700")
                .placeholder(R.drawable.security_cam)
                .error(R.drawable.error_image)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView)
    }
}