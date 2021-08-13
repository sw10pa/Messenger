package ge.sgurgenidze.stsertsvadze.messenger.chat

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.sgurgenidze.stsertsvadze.messenger.model.Message

class ChatInteractor(var presenter: IChatPresenter) {
    fun fetchChats(chatId: String) {
        val dbReference = Firebase.database.reference
        val chatReference = dbReference.child("chat")
        val queryRef = chatReference.orderByKey().equalTo(chatId)
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<Map<String, Map<String, Any>>>()
                val messages = ArrayList<Message>()
                if (value != null) {
                    val chat = value.iterator().next().value
                    for (ch in chat) {
                        if (ch.key != "lastActiveTime") {
                            val map = ch.value as HashMap<*, *>
                            val message = Message(map["message"] as String, map["sender"] as Long, map["time"] as Long)
                            messages.add(message)
                        }
                    }
                    val comparator = Comparator { c1: Message, c2: Message ->
                        if (c1.time!! > c2.time!!) return@Comparator 1
                        if (c1.time < c2.time) return@Comparator -1
                        return@Comparator 0
                    }
                    messages.sortWith(comparator)
                    presenter.onFetchSuccess(messages)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onFetchFailed("Unknown Error. Try again!")
            }

        })
    }

    fun sendMessage(chatId: String, sender: Int, message: String) {
        val time = System.currentTimeMillis()
        val msg = Message(message, sender.toLong(), time)
        val dbReference = Firebase.database.reference
        val chatReference = dbReference.child("chat").child(chatId)
        chatReference.push().key?.let {
            chatReference.child(it).setValue(msg)
        }
    }
}