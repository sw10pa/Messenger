package ge.sgurgenidze.stsertsvadze.messenger.homepage

import android.os.Bundle
import android.view.View
import android.content.Intent
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.profile.ProfileActivity

class HomepageActivity : AppCompatActivity() {

    private lateinit var chatsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        initPrivateVariables()
    }

    private fun initPrivateVariables() {
        chatsRecyclerView = findViewById(R.id.chatsRecyclerView)
        chatsRecyclerView.adapter = ChatsAdapter()
    }

    fun profileImageButtonClicked(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

}
