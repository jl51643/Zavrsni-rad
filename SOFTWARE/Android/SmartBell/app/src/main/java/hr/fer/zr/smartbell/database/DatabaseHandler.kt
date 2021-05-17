package hr.fer.zr.smartbell.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.fer.zr.smartbell.models.CameraModel

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "CameraDatabase"
        private const val TABLE_CAMERAS = "CameraTable"

        private const val KEY_ID = "_id"
        private const val KEY_NAME = "name"
        private const val KEY_URL = "url"
        private const val KEY_SUBSCRIBED = "subscribed"
        private const val KEY_TOPIC = "topic"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TASKS_TABLE = ("CREATE TABLE $TABLE_CAMERAS(" +
                "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$KEY_NAME TEXT, " +
                "$KEY_URL TEXT, " +
                "$KEY_SUBSCRIBED INTEGER DEFAULT 0, " +
                "$KEY_TOPIC TEXT)")
        db?.execSQL(CREATE_TASKS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVeersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CAMERAS)
        onCreate(db)
    }

    fun addCamera(cameraModel: CameraModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, cameraModel.name)
        contentValues.put(KEY_URL, cameraModel.url)
        if (cameraModel.subscribed)
            contentValues.put(KEY_SUBSCRIBED, 1)
        else
            contentValues.put(KEY_SUBSCRIBED, 0)
        contentValues.put(KEY_TOPIC, cameraModel.topic)

        val success = db.insert(TABLE_CAMERAS, null, contentValues)

        db.close()
        return success
    }

    fun getCameras(): ArrayList<CameraModel> {
        val cameraList: ArrayList<CameraModel> = ArrayList()

        val selectQuery = "SELECT * FROM $TABLE_CAMERAS"

        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var url: String
        var subscribed = false
        var topic: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                url = cursor.getString(cursor.getColumnIndex(KEY_URL))
                if (cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBED)) == 0)
                    subscribed = false
                else if (cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBED)) == 1)
                    subscribed = true
                topic = cursor.getString(cursor.getColumnIndex(KEY_TOPIC))

                val cameraModel = CameraModel(id = id, name = name, url = url, subscribed = subscribed, topic = topic)
                cameraList.add(cameraModel)

            } while (cursor.moveToNext())
        }

        return cameraList
    }

    fun deleteCamera(camera: CameraModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, camera.id)

        val success = db.delete(TABLE_CAMERAS, KEY_ID + "=" + camera.id, null)

        db.close()
        return success
    }

    fun updateSubscription(camera: CameraModel): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        if (camera.subscribed)
            contentValues.put(KEY_SUBSCRIBED, 1)
        else
            contentValues.put(KEY_SUBSCRIBED, 0)

        val success = db.update(TABLE_CAMERAS, contentValues, KEY_ID + "=" + camera.id, null)

        db.close()
        return success
    }

    fun getCameraForId (id: Int) : CameraModel? {
        val db = this.readableDatabase

        val query = "SELECT * FROM $TABLE_CAMERAS WHERE _id = $id"

        val cursor: Cursor? = db.rawQuery(query, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getInt(cursor.getColumnIndex(KEY_ID)) != id)
                        continue

                    val cameraId = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                    val name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                    val url = cursor.getString(cursor.getColumnIndex(KEY_URL))
                    var subscribed = false
                    if (cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBED)) == 0)
                        subscribed = false
                    else if (cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBED)) == 1)
                        subscribed = true
                    val topic = cursor.getString(cursor.getColumnIndex(KEY_TOPIC))

                    val cameraModel = CameraModel(id = cameraId, name = name, url = url, subscribed = subscribed, topic = topic)

                    return cameraModel

                } while (cursor.moveToNext())
            }
        }

        return null
    }
    
}