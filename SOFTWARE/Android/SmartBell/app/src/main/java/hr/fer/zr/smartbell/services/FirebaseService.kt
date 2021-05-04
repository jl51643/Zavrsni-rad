package hr.fer.zr.smartbell.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.e("TAG", "The token refreshed $token")
    }
    
}