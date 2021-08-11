package ge.sgurgenidze.stsertsvadze.messenger.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.homepage.HomepageActivity
import ge.sgurgenidze.stsertsvadze.messenger.login.LoginActivity

class ProfileActivity : AppCompatActivity(), IProfileView {

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
        nicknameEditText = findViewById(R.id.nicknameEditText)
        occupationEditText = findViewById(R.id.occupationEditText)
        updateButton = findViewById(R.id.updateButton)
        signOutButton = findViewById(R.id.signOutButton)
    }

    fun setListeners() {
        updateButton.setOnClickListener {
            onUpdateButtonClicked()
        }
        signOutButton.setOnClickListener {
            onSignOutButtonClicked()
        }
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
