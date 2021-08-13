package ge.sgurgenidze.stsertsvadze.messenger.chat

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.model.Message

class ChatActivity : AppCompatActivity(), IChatView {

    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var nicknameTextView: TextView
    private var sender = 0

    var presenter = ChatPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initPrivateVariables()
        initMessages()
    }

    private fun initPrivateVariables() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView)
        nicknameTextView = findViewById(R.id.nicknameTextView)
    }

    private fun initMessages() {
        val chatId = intent.getStringExtra("chatId")!!
        val nickname = intent.getStringExtra("nickname")!!
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val userId = prefs.getString("userId", "")
        if (chatId.substring(0, 20) != userId) {
            sender = 1
        }
        nicknameTextView.text = nickname
        presenter.fetchChats(chatId)
    }

    override fun onFetchSuccess(messages: List<Message>) {
        val adapter = MessagesAdapter(messages, sender)
        messagesRecyclerView.adapter = adapter
    }

    override fun onFetchFailed(message: String) {
        Toast.makeText(this@ChatActivity, message, Toast.LENGTH_SHORT).show()
    }

}
