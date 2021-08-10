package ge.sgurgenidze.stsertsvadze.messenger.main

import android.content.Context
import android.os.Bundle
import android.content.Intent
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.homepage.HomepageActivity
import ge.sgurgenidze.stsertsvadze.messenger.login.LoginActivity
import ge.sgurgenidze.stsertsvadze.messenger.registration.RegistrationActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFirstActivity()
    }

    private fun openFirstActivity() {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val nickname = pref.getString("nickname", "")
        if (nickname != "") {
            val password = pref.getString("password", "")
            val occupation = pref.getString("occupation", "")
            val intent = Intent(this, HomepageActivity::class.java)
            intent.putExtra("nickname", nickname)
            intent.putExtra("password", password)
            intent.putExtra("occupation", occupation)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}
