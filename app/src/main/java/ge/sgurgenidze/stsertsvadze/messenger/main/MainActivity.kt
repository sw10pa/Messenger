package ge.sgurgenidze.stsertsvadze.messenger.main

import android.os.Bundle
import android.content.Intent
import android.content.Context
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.login.LoginActivity
import ge.sgurgenidze.stsertsvadze.messenger.homepage.HomepageActivity
import ge.sgurgenidze.stsertsvadze.messenger.users.UsersActivity

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
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}
