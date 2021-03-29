package hr.fer.zr.smartbell

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class FirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("TAG", "The token refreshed $token")
    }
}