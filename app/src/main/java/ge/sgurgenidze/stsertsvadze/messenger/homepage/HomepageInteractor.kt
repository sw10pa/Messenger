package ge.sgurgenidze.stsertsvadze.messenger.homepage

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.sgurgenidze.stsertsvadze.messenger.model.Chat
import ge.sgurgenidze.stsertsvadze.messenger.model.Message
import ge.sgurgenidze.stsertsvadze.messenger.model.User
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomepageInteractor(var presenter: IHomepagePresenter) {
    fun fetchChats(nickname: String) {
        val dbReference = Firebase.database.reference
        val usersReference = dbReference.child("users")
        val queryRef = usersReference.orderByChild("nickname").equalTo(nickname)
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()
                val userId = users?.iterator()?.next()?.key
                fetchChatsByUserId(userId!!, nickname)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onFetchFailed("Unknown Error. Try again!")
            }
        })
    }

    private fun fetchChatsByUserId(userId: String, nickname: String) {
        val dbReference = Firebase.database.reference
        val chatList = ArrayList<Chat>()
        val chatReference = dbReference.child("chat")
        addListener(chatReference, nickname)
        val queryRef = chatReference.orderByChild("lastActiveTime")
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chats = snapshot.getValue<Map<String, Map<String, Any>>>()
                if (chats != null && chats.count() > 0) {
                    for ((chatId, chat) in chats) {
                        val id1 = chatId.substring(0, 20)
                        val id2 = chatId.substring(20)
                        var lastActiveTime = 0.toLong()
                        var maxTime = 0.toLong()
                        var messageMap: HashMap<*, *>? = null
                        for ((key, message) in chat) {
                            if (key == "lastActiveTime") {
                                lastActiveTime = message as Long
                            } else {
                                val tmp = message as HashMap<*, *>
                                if ((tmp["time"] as Long) > maxTime) {
                                    messageMap = tmp
                                    maxTime = tmp["time"] as Long
                                }
                            }
                        }
                        val message = Message(messageMap!!["message"] as String, messageMap["sender"] as Long, messageMap["time"] as Long)
                        if (userId == id1) {
                            fillChatList(chatId, id2, message, chatList, lastActiveTime)
                        } else if (userId == id2) {
                            fillChatList(chatId, id1, message, chatList, lastActiveTime)
                        }
                    }
                    if (chatList.count() == 0) {
                        presenter.onFetchFailed("Chats not found")
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

    private fun addListener(chatRef: DatabaseReference, nickname: String) {
        chatRef.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                fetchChats(nickname)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    fun fillChatList(chatId: String, userId: String, message: Message, chatList: ArrayList<Chat>, lastActiveTime: Long) {
        val dbReference = Firebase.database.reference
        val usersReference = dbReference.child("users")
        val queryRef = usersReference.orderByKey().equalTo(userId)

        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()

                if (users != null && users.count() != 0) {
                    val entry = users.iterator().next()
                    val chat = Chat(chatId, message, entry.value, lastActiveTime)
                    chatList.add(chat)
                }
                val comparator = Comparator { c1: Chat, c2: Chat ->
                    if (c1.lastActiveTime!! > c2.lastActiveTime!!) return@Comparator 1
                    if (c1.lastActiveTime < c2.lastActiveTime) return@Comparator -1
                    return@Comparator 0
                }
                chatList.sortWith(comparator)
                presenter.onFetchSuccess(chatList)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onFetchFailed("Unknown Error. Try again!")
            }

        })
    }
}