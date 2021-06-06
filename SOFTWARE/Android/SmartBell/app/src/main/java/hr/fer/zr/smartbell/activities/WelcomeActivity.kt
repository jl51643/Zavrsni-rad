package hr.fer.zr.smartbell.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import hr.fer.zr.smartbell.R

class WelcomeActivity : AppCompatActivity() {

    private lateinit var houseImageView: ImageView
    private lateinit var settingsImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        houseImageView = findViewById(R.id.welcome_iv)
        houseImageView.setOnClickListener {
            val intent = Intent(this, CameraViewActivity::class.java)
            startActivity(intent)
        }

        settingsImageView = findViewById(R.id.manageCamerasIV)
        settingsImageView.setOnClickListener {
            val intent = Intent(this, SubscribeToTopicActivity::class.java)
            startActivity(intent)
        }
    }

}