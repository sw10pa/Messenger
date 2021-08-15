package ge.sgurgenidze.stsertsvadze.messenger.homepage

import android.content.Context
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ge.sgurgenidze.stsertsvadze.messenger.chat.ChatActivity
import ge.sgurgenidze.stsertsvadze.messenger.model.Chat
import ge.sgurgenidze.stsertsvadze.messenger.profile.ProfileActivity
import ge.sgurgenidze.stsertsvadze.messenger.users.UsersActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomepageActivity : AppCompatActivity(), IHomepageView, CoroutineScope {

    private lateinit var chatsRecyclerView: RecyclerView
    private lateinit var searchEditText: EditText
    private lateinit var addFloatingActionButton: FloatingActionButton
    private var chats: List<Chat>? = null
    var presenter = HomepagePresenter(this)

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        initPrivateVariables()
        addListeners()
        initChats()
        setDebounce()
    }

    private fun initPrivateVariables() {
        searchEditText = findViewById(R.id.searchEditText)
        addFloatingActionButton = findViewById(R.id.addFloatingActionButton)
        chatsRecyclerView = findViewById(R.id.chatsRecyclerView)
        chatsRecyclerView.adapter = ChatsAdapter(this, ArrayList<Chat>())
    }

    private fun addListeners() {
        addFloatingActionButton.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initChats() {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val nickname = prefs.getString("nickname", "")
        presenter.fetchChats(nickname!!)
    }

    fun profileImageButtonClicked(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    override fun onFetchSuccess(chats: List<Chat>) {
        this.chats = chats
        chatsRecyclerView.adapter = ChatsAdapter(this, chats)
    }

    override fun onFetchFailed(message: String) {
        Toast.makeText(this@HomepageActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun openChat(chatId: String, nickname: String) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("nickname", nickname)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        initChats()
    }

    private fun setDebounce() {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
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

                    if (name.length > 3) {
                        val filtered = ArrayList<Chat>()
                        for (chat in chats!!) {
                            val nickname = chat.user!!.nickname!!
                            if ((name.length <= nickname.length) && (name == nickname.substring(0, name.length))) {
                                filtered.add(chat)
                            }
                        }
                        val adapter = ChatsAdapter(this@HomepageActivity, filtered)
                        chatsRecyclerView.adapter = adapter
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        }

        searchEditText.addTextChangedListener(watcher)
    }
}
