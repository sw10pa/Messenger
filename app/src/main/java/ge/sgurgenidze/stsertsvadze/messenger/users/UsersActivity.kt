package ge.sgurgenidze.stsertsvadze.messenger.users

import android.os.Bundle
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class UsersActivity : AppCompatActivity() {

    private lateinit var usersRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        initPrivateVariables()
    }

    private fun initPrivateVariables() {
        usersRecyclerView = findViewById(R.id.usersRecyclerView)
        usersRecyclerView.adapter = UsersAdapter()
    }

}
