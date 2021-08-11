package ge.sgurgenidze.stsertsvadze.messenger.chat

import android.os.Bundle
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initPrivateVariables()
    }

    private fun initPrivateVariables() {
        messagesRecyclerView = findViewById(R.id.messagesRecyclerView)
        messagesRecyclerView.adapter = MessagesAdapter()
    }

}
