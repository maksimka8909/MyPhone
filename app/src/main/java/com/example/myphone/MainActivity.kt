package com.example.myphone

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.recyclerView


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val PERMISSION_REQUEST_CODE = 123
    var contacts = mutableListOf<Contact>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // Уже есть разрешение
            getContacts()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение получено
                getContacts()
            } else {
                // Разрешение отклонено
                Log.e(TAG, "Permission Denied")
            }
        }
    }

    private fun getContacts() {
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val cursor = contentResolver.query(
            CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.let {
            while (cursor.moveToNext()) {
                    val displayName =
                        cursor.getString(cursor.getColumnIndexOrThrow(CommonDataKinds.Phone.DISPLAY_NAME))
                    val displayNumber =
                        cursor.getString(cursor.getColumnIndexOrThrow(CommonDataKinds.Phone.NUMBER))
                    val displayID =
                        cursor.getString(cursor.getColumnIndexOrThrow(CommonDataKinds.Phone.CONTACT_ID))
                    var displayPhoto =
                        cursor.getString(cursor.getColumnIndexOrThrow(CommonDataKinds.Phone.PHOTO_URI))
                    if(displayPhoto == null){
                        displayPhoto = R.drawable.ic_launcher_background.toString()
                    }
                    contacts.add(Contact(displayID.toLong(),displayName,displayNumber,displayPhoto))
            }
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = ContactAdapter(contacts)
            cursor.close()
        }
    }
}
