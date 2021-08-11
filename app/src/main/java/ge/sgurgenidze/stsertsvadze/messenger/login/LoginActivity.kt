package ge.sgurgenidze.stsertsvadze.messenger.login

import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.content.Context
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.model.User
import ge.sgurgenidze.stsertsvadze.messenger.homepage.HomepageActivity
import ge.sgurgenidze.stsertsvadze.messenger.registration.RegistrationActivity

class LoginActivity : AppCompatActivity(), ILoginView {

    private lateinit var nicknameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    var presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

        setListeners()
    }

    fun initViews() {
        nicknameEditText = findViewById(R.id.nicknameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signInButton = findViewById(R.id.signInButton)
        signUpButton = findViewById(R.id.signUpButton)
    }

    fun setListeners() {
        signInButton.setOnClickListener {
            signInButtonClicked()
        }
        signUpButton.setOnClickListener {
            signUpButtonClicked()
        }
    }

    fun signInButtonClicked() {
        val nickname = nicknameEditText.text.toString()
        val password = passwordEditText.text.toString()
        if (nickname != "" && password != "") {
            presenter.login(nickname, password)
        } else {
            Toast.makeText(this@LoginActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signUpButtonClicked() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(user: User) {
        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
        saveLoggedUser(user)
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    fun saveLoggedUser(user: User) {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("nickname", user.nickname).apply()
        editor.putString("password", user.password).apply()
        editor.putString("occupation", user.occupation).apply()
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }

}
