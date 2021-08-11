package ge.sgurgenidze.stsertsvadze.messenger.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.widget.EditText
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.homepage.HomepageActivity
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class RegistrationActivity : AppCompatActivity(), IRegistrationView {
    
    private lateinit var nicknameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var whatIDoEditText: EditText
    private lateinit var signUpButton: Button

    var presenter = RegistrationPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        
        initViews()

        setListeners()
    }
    
    fun initViews() {
        nicknameEditText = findViewById(R.id.nicknameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        whatIDoEditText = findViewById(R.id.whatIDoEditText)
        signUpButton = findViewById(R.id.signUpButton)
    }

    fun setListeners() {
        signUpButton.setOnClickListener {
            onSignUpButtonClicked()
        }
    }

    fun onSignUpButtonClicked() {
        val nickname = nicknameEditText.text.toString()
        val password = passwordEditText.text.toString()
        val occupation = whatIDoEditText.text.toString()
        if (nickname == "" || password == "" || occupation == "") {
            Toast.makeText(this@RegistrationActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            presenter.registerUser(User(nickname, password, occupation))
        }
    }

    override fun onRegistrationSuccess(user: User) {
        Toast.makeText(this@RegistrationActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
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

    override fun onRegistrationFailed(message: String) {
        Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_SHORT).show()
    }

}
