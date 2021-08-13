package ge.sgurgenidze.stsertsvadze.messenger.homepage

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.sgurgenidze.stsertsvadze.messenger.model.Chat
import ge.sgurgenidze.stsertsvadze.messenger.model.Message
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class HomepageInteractor(var presenter: IHomepagePresenter) {
    fun fetchChats(nickname: String) {
        val dbReference = Firebase.database.reference
        val usersReference = dbReference.child("users")
        val queryRef = usersReference.orderByChild("nickname").equalTo(nickname)
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()
                val userId = users?.iterator()?.next()?.key
                fetchChatsByUserId(userId!!)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onFetchFailed("Unknown Error. Try again!")
            }
        })
    }

    private fun fetchChatsByUserId(userId: String) {
        val dbReference = Firebase.database.reference
        val chatList = ArrayList<Chat>()
        val chatReference = dbReference.child("chat")
        var queryRef = chatReference.orderByChild("lastActiveTime")
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chats = snapshot.getValue<Map<String, Map<String, Any>>>()
                if (chats != null && chats.count() > 0) {
                    for ((chatId, chat) in chats) {
                        val id1 = chatId.substring(0, 20)
                        val id2 = chatId.substring(20)
                        val iter = chat.iterator()
                        var entry = iter.next()
                        if (entry.key == "lastActiveTime") entry = iter.next()
                        val messageMap = entry.value as HashMap<*, *>
                        val message = Message(messageMap["message"] as String, messageMap["sender"] as Long, messageMap["time"] as Long)
                        if (userId == id1) {
                            fillChatList(chatId, id2, message, chatList)
                        } else if (userId == id2) {
                            fillChatList(chatId, id1, message, chatList)
                        }
                    }
                } else {
                    presenter.onFetchSuccess(ArrayList<Chat>())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onFetchFailed("Unknown Error. Try again!")
            }
        })
    }

    fun fillChatList(chatId: String, userId: String, message: Message, chatList: ArrayList<Chat>) {
        val dbReference = Firebase.database.reference
        val usersReference = dbReference.child("users")
        val queryRef = usersReference.orderByKey().equalTo(userId)
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()

                if (users != null && users.count() != 0) {
                    val entry = users.iterator().next()
                    val chat = Chat(chatId, message, entry.value)
                    chatList.add(chat)
                }
                presenter.onFetchSuccess(chatList)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onFetchFailed("Unknown Error. Try again!")
            }

        })
    }
}