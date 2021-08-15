package ge.sgurgenidze.stsertsvadze.messenger.users

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class UsersAdapter(var activity: UsersActivity, var users: List<User>): RecyclerView.Adapter<UsersViewHolder>() {

    override fun getItemCount(): Int {
        return users.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val nicknameTextView = holder.itemView.findViewById<TextView>(R.id.nicknameTextView)
        val occupationTextView = holder.itemView.findViewById<TextView>(R.id.occupationTextView)
        val user = users[position]
        nicknameTextView.text = user.nickname
        occupationTextView.text = user.occupation
        holder.itemView.setOnClickListener {
            activity.startChat(user.nickname!!)
        }
    }

}

class UsersViewHolder(view: View): RecyclerView.ViewHolder(view)
