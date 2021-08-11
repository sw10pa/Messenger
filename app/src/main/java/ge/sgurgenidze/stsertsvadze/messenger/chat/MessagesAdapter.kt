package ge.sgurgenidze.stsertsvadze.messenger.chat

import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.recyclerview.widget.RecyclerView

class MessagesAdapter: RecyclerView.Adapter<MessagesViewHolder>() {

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val view: View

        if (viewType == 0) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.sent_message, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.received_message, parent, false)
        }

        return MessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {

    }

    override fun getItemViewType(position: Int): Int {
        return (position % 2)
    }

}

class MessagesViewHolder(view: View): RecyclerView.ViewHolder(view)
