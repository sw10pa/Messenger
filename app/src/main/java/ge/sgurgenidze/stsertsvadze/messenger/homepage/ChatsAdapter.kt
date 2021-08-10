package ge.sgurgenidze.stsertsvadze.messenger.homepage

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.recyclerview.widget.RecyclerView

class ChatsAdapter: RecyclerView.Adapter<ChatsViewHolder>() {

    override fun getItemCount(): Int {
        return 100
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.short_chat, parent, false)
        return ChatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {

    }

}

class ChatsViewHolder(view: View): RecyclerView.ViewHolder(view)
