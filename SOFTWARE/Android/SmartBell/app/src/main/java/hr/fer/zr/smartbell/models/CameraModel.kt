package hr.fer.zr.smartbell.models

import java.io.Serializable

data class CameraModel(
        var id: Int,
        val name: String,
        val url: String,
        var subscribed: Boolean,
        val topic: String
) : Serializable
