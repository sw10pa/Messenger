package ge.sgurgenidze.stsertsvadze.messenger.chat

import java.util.*
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.model.Message

class MessagesAdapter(var messages: List<Message>, val sender: Int): RecyclerView.Adapter<MessagesViewHolder>() {

    override fun getItemCount(): Int {
        return (messages.count() + 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val view: View

        if (viewType == 0) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.sent_message, parent, false)
        }  else if (viewType == 100) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.empty_message, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.received_message, parent, false)
        }

        return MessagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        if (position != messages.count()) {
            val message = messages[position]
            val messageTextView = holder.itemView.findViewById<TextView>(R.id.messageTextView)
            val timeTextView = holder.itemView.findViewById<TextView>(R.id.timeTextView)
            messageTextView.text = message.message.toString()
            val time = message.time!!
            val date = Date(time)
            timeTextView.text = date.toHourAndMinuteFormat()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position != messages.count()) {
            val curr = messages[position].sender!!.toInt()
            if (sender == 0) {
                return curr
            } else {
                return 1 - curr
            }
        } else {
            return 100
        }
    }

    fun Date.toHourAndMinuteFormat(): String {
        val sdf= SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(this)
    }

}

class MessagesViewHolder(view: View): RecyclerView.ViewHolder(view)
