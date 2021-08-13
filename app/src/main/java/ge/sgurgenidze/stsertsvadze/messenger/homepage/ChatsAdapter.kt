package ge.sgurgenidze.stsertsvadze.messenger.homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import ge.sgurgenidze.stsertsvadze.messenger.R
import androidx.recyclerview.widget.RecyclerView
import ge.sgurgenidze.stsertsvadze.messenger.model.Chat
import java.text.SimpleDateFormat
import java.util.*

class ChatsAdapter(var view: IHomepageView, var chats: List<Chat>): RecyclerView.Adapter<ChatsViewHolder>() {

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.short_chat, parent, false)
        return ChatsViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val nickname = holder.itemView.findViewById<TextView>(R.id.nicknameTextView)
        val lastMessageText = holder.itemView.findViewById<TextView>(R.id.lastMessageTextView)
        val lastMessageTime = holder.itemView.findViewById<TextView>(R.id.lastMessageTimeTextView)
        val chat = chats[chats.size - position - 1]
        nickname.text = chat.user?.nickname
        var msg = chat.message?.message!!
        if (msg.length > 43) {
            msg = msg.substring(0, 44)
            msg += "..."
        }
        lastMessageText.text = msg
        val time = chat.message.time!!
        val currTime = System.currentTimeMillis()
        var diff = currTime - time
        if (diff < 3600000) {
            diff /= 60000
            lastMessageTime.text = "$diff min"
        } else if (diff < 86400000) {
            diff /= 3600000
            lastMessageTime.text = "$diff hour"
        } else {
            val date = Date(time)
            lastMessageTime.text = date.toDayAndMonthsFormat()
        }
        holder.itemView.setOnClickListener {
            view.openChat(chats[chats.size - position - 1].id!!, chats[chats.size - position - 1].user?.nickname!!)
        }
    }

    fun Date.toDayAndMonthsFormat(): String {
        val sdf= SimpleDateFormat("dd-MM", Locale.getDefault())
        val dt = sdf.format(this)
        val months = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")
        var day = dt.substring(0, 2)
        val m = dt.substring(3).toInt()
        if (day[0] == '0') {
            day = dt.substring(1, 2)
        }
        val month = months[m]
        return "$day $month"
    }

}

class ChatsViewHolder(view: View): RecyclerView.ViewHolder(view)
