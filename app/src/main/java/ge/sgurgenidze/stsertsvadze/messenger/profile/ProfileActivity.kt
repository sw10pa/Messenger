package ge.sgurgenidze.stsertsvadze.messenger.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.os.StrictMode
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.content.Context
import android.widget.ImageButton
import android.provider.MediaStore
import android.Manifest.permission.*
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.graphics.*
import androidx.core.content.ContextCompat
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.login.LoginActivity
import java.io.File
import android.graphics.PorterDuff

import android.graphics.PorterDuffXfermode

import android.graphics.Bitmap





class ProfileActivity : AppCompatActivity(), IProfileView {

    private lateinit var avatarImageButton: ImageButton
    private lateinit var nicknameEditText: EditText
    private lateinit var occupationEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var signOutButton: Button

    var presenter = ProfilePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initViews()
        setListeners()

        fillProfileData()
    }

    fun initViews() {
        avatarImageButton = findViewById(R.id.avatarImageButton)
        nicknameEditText = findViewById(R.id.nicknameEditText)
        occupationEditText = findViewById(R.id.occupationEditText)
        updateButton = findViewById(R.id.updateButton)
        signOutButton = findViewById(R.id.signOutButton)
    }

    fun setListeners() {
        avatarImageButton.setOnClickListener {
            onAvatarImageButtonClicked()
        }
        updateButton.setOnClickListener {
            onUpdateButtonClicked()
        }
        signOutButton.setOnClickListener {
            onSignOutButtonClicked()
        }
    }

    fun onAvatarImageButtonClicked() {
        if (requestPermissionIfNeeded()) {
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 100)
        }
    }

    private fun requestPermissionIfNeeded(): Boolean {
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().build())

        val listPermissionsNeeded = arrayListOf<String>()
        for (permission in listOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)) {
            if (this.let { ContextCompat.checkSelfPermission(it, permission) } != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission)
            }
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            this.let { ActivityCompat.requestPermissions(it, listPermissionsNeeded.toTypedArray(), 0) }
            return false
        }

        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            val uri = data?.data!!
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = this.contentResolver?.query(uri, projection, null, null, null)!!

            cursor.moveToFirst()

            val columnIndex: Int = cursor.getColumnIndex(projection[0])
            val imagePath: String = cursor.getString(columnIndex)

            cursor.close()

            setImage(imagePath)
        }
    }

    private fun setImage(imagePath: String) {
        val imageFile = File(imagePath)
        val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
        val circledImageBitmap = getCircledBitmap(imageBitmap)

        avatarImageButton.background = null
        avatarImageButton.setImageBitmap(circledImageBitmap)
    }

    private fun getCircledBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rectangle = Rect(0, 0, bitmap.width, bitmap.height)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle((bitmap.width / 2).toFloat(), (bitmap.height / 2).toFloat(), (bitmap.width / 2).toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rectangle, rectangle, paint)
        return output
    }

    fun onUpdateButtonClicked() {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val oldNickname = pref.getString("nickname", "")
        val newNickname = nicknameEditText.text.toString()
        val occupation = occupationEditText.text.toString()
        if (newNickname != "" && occupation != "") {
            presenter.updateProfile(oldNickname!!, newNickname, occupation)
        } else {
            Toast.makeText(this@ProfileActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    fun onSignOutButtonClicked() {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("nickname", "").apply()
        editor.putString("password", "").apply()
        editor.putString("occupation", "").apply()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun fillProfileData() {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val nickname = prefs.getString("nickname", "")
        val occupation = prefs.getString("occupation", "")
        nicknameEditText.setText(nickname)
        occupationEditText.setText(occupation)
    }

    fun homepageImageButtonClicked(view: View) {
        finish()
    }

    override fun onUpdateSuccess(nickname: String, occupation: String) {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("nickname", nickname).apply()
        editor.putString("occupation", occupation).apply()
        Toast.makeText(this@ProfileActivity, "Profile information updated successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateFailed(message: String) {
        Toast.makeText(this@ProfileActivity, message, Toast.LENGTH_SHORT).show()
    }

}
