package ge.sgurgenidze.stsertsvadze.messenger.users

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter: RecyclerView.Adapter<UsersViewHolder>() {

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

    }

}

class UsersViewHolder(view: View): RecyclerView.ViewHolder(view)
