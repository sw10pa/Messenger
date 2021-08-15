package ge.sgurgenidze.stsertsvadze.messenger.homepage

import android.content.Context
import android.os.Bundle
import android.view.View
import android.content.Intent
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.chat.ChatActivity
import ge.sgurgenidze.stsertsvadze.messenger.model.Chat
import ge.sgurgenidze.stsertsvadze.messenger.profile.ProfileActivity

class HomepageActivity : AppCompatActivity(), IHomepageView {

    private lateinit var chatsRecyclerView: RecyclerView

    var presenter = HomepagePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        initPrivateVariables()
        initChats()
    }

    private fun initPrivateVariables() {
        chatsRecyclerView = findViewById(R.id.chatsRecyclerView)
        chatsRecyclerView.adapter = ChatsAdapter(this, ArrayList<Chat>())
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
        chatsRecyclerView.adapter = ChatsAdapter(this, chats)
    }

    override fun onFetchFailed(message: String) {
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

}
