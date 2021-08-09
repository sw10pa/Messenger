package ge.sgurgenidze.stsertsvadze.messenger.registration

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
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
            val nickname = nicknameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val occupation = whatIDoEditText.text.toString()
            if (nickname == "" || password == "" || occupation == "") {
                Toast.makeText(this@RegistrationActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                presenter.registerUser(User(nickname, password, occupation))
            }
        }
    }

    override fun onRegistrationSuccess() {
        Log.d("registration", "successful")
    }

    override fun onRegistrationFailed() {
        Log.d("registration", "failed")
    }

}
