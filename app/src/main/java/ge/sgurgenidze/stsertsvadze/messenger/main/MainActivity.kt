package ge.sgurgenidze.stsertsvadze.messenger.main

import android.os.Bundle
import android.content.Intent
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openFirstActivity()
    }

    private fun openFirstActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}
