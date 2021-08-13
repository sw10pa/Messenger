package ge.sgurgenidze.stsertsvadze.messenger.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(var id: String? = null, val nickname: String? = null, val password: String? = null, val occupation: String? = null)
