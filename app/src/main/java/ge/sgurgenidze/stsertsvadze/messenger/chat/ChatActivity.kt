package ge.sgurgenidze.stsertsvadze.messenger.chat

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.model.Message

class ChatActivity : AppCompatActivity(), IChatView {

    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var nicknameTextView: TextView
    private lateinit var backImageButton: ImageButton
    private lateinit var sendImageButton: ImageButton
    private lateinit var messageEditText: EditText
    private lateinit var progressDialog: ProgressDialog
    private var sender = 0

    var presenter = ChatPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initPrivateVariables()
        addListeners()
        initMessages()
    }

    private fun initPrivateVariables() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView)
        nicknameTextView = findViewById(R.id.nicknameTextView)
        backImageButton = findViewById(R.id.backImageButton)
        sendImageButton = findViewById(R.id.sendImageButton)
        messageEditText = findViewById(R.id.messageEditText)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("Wait while loading...")
    }

    private fun addListeners() {
        backImageButton.setOnClickListener {
            finish()
        }
        sendImageButton.setOnClickListener {
            onSendClicked()
        }
        messageEditText.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                onSendClicked()
                true
            } else {
                false
            }
        }
    }

    private fun onSendClicked() {
        val text = messageEditText.text.toString()
        val chatId = intent.getStringExtra("chatId")!!
        if (text == "") {
            Toast.makeText(this@ChatActivity, "Cannot send an empty message", Toast.LENGTH_SHORT).show()
        } else {
            presenter.sendMessage(chatId, sender, text)
            messageEditText.setText("")
        }
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
        progressDialog.show()
        presenter.fetchChats(chatId)
    }

    override fun onFetchSuccess(messages: List<Message>) {
        progressDialog.dismiss()
        val adapter = MessagesAdapter(messages, sender)
        messagesRecyclerView.adapter = adapter
    }

    override fun onFetchFailed(message: String) {
        progressDialog.dismiss()
        Toast.makeText(this@ChatActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        finish()
    }

}
