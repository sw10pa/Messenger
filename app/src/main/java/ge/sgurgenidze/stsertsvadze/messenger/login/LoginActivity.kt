package ge.sgurgenidze.stsertsvadze.messenger.login

import android.os.Bundle
import android.view.View
import android.content.Intent
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.registration.RegistrationActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun signUpButtonClicked(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

}
