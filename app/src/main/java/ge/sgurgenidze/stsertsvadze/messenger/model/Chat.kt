package ge.sgurgenidze.stsertsvadze.messenger.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Chat(val id: String? = null, val message: Message? = null, val user: User? = null, val lastActiveTime: Long? = null)