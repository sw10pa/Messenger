package ge.sgurgenidze.stsertsvadze.messenger.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(val message: String? = null, val sender: Long? = null, val time: Long? = null)