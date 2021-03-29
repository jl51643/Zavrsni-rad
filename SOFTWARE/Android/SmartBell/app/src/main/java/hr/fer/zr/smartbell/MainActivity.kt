package hr.fer.zr.smartbell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        Glide.with(this)
                .load("http://161.53.19.52/SnapshotJPEG?Resolution=384x288&Quality=Standard&View=Normal&Count=71694700")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView)
    }
}