package ge.sgurgenidze.stsertsvadze.messenger.registration

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import android.widget.Button
import android.content.Intent
import android.content.Context
import android.widget.EditText
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import ge.sgurgenidze.stsertsvadze.messenger.model.User
import ge.sgurgenidze.stsertsvadze.messenger.homepage.HomepageActivity

class RegistrationActivity : AppCompatActivity(), IRegistrationView {
    
    private lateinit var nicknameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var whatIDoEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var progressDialog: ProgressDialog

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

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Wait while loading...")
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
            progressDialog.show()
            presenter.registerUser(User(nickname, password, occupation))
        }
    }

    override fun onRegistrationSuccess(user: User) {
        progressDialog.dismiss()
        Toast.makeText(this@RegistrationActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
        saveLoggedUser(user)
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    private fun saveLoggedUser(user: User) {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("userId", user.id).apply()
        editor.putString("nickname", user.nickname).apply()
        editor.putString("occupation", user.occupation).apply()
    }

    override fun onRegistrationFailed(message: String) {
        progressDialog.dismiss()
        Toast.makeText(this@RegistrationActivity, message, Toast.LENGTH_SHORT).show()
    }

}
