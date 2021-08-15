package ge.sgurgenidze.stsertsvadze.messenger.users

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.chat.ChatActivity
import ge.sgurgenidze.stsertsvadze.messenger.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

class UsersActivity() : AppCompatActivity(), CoroutineScope, IUsersView {

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var backImageButton: ImageButton

    var presenter = UsersPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        initPrivateVariables()
        setDebounce()
        setFocusOnSearch()
        addListeners()
    }

    private fun initPrivateVariables() {
        searchEditText = findViewById(R.id.searchEditText)
        backImageButton = findViewById(R.id.backImageButton)
        usersRecyclerView = findViewById(R.id.usersRecyclerView)
        usersRecyclerView.adapter = UsersAdapter(this, ArrayList<User>())
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private fun setDebounce() {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val loggedNickname = prefs.getString("nickname", "")
        val watcher = object : TextWatcher {
            private var name = ""

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString().trim()
                if (text == name) {
                    return
                }

                name = text
                launch {
                    delay(500)

                    if (text != name)
                        return@launch

                    if (name.isEmpty()) {
                        presenter.fetchUsers("", loggedNickname!!)
                    } else if (name.length > 3) {
                        presenter.fetchUsers(name, loggedNickname!!)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        }

        searchEditText.addTextChangedListener(watcher)
    }

    private fun setFocusOnSearch() {
        searchEditText.requestFocus()
    }

    private fun addListeners() {
        backImageButton.setOnClickListener {
            finish()
        }
    }

    override fun onUsersFetched(users: List<User>) {
        if (users.count() == 0) {
            val adapter = UsersAdapter(this, ArrayList<User>())
            usersRecyclerView.adapter = adapter
            Toast.makeText(this@UsersActivity, "Users not found", Toast.LENGTH_SHORT).show()
        } else {
            val adapter = UsersAdapter(this, users)
            usersRecyclerView.adapter = adapter
        }
    }

    override fun onGetUserIdSuccess(nickname: String, userId: String) {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val loggedUserId = prefs.getString("userId", "")!!
        var chatId = "$userId$loggedUserId"
        if (userId > loggedUserId) {
            chatId = "$loggedUserId$userId"
        }
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("nickname", nickname)
        startActivity(intent)
    }

    override fun onGetUserIdFailed(message: String) {
        Toast.makeText(this@UsersActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun startChat(nickname: String) {
        presenter.getUserId(nickname)
    }
}
