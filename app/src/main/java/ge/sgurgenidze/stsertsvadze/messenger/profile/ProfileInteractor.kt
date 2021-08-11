package ge.sgurgenidze.stsertsvadze.messenger.profile

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class ProfileInteractor(var presenter: IProfilePresenter) {
    fun updateProfile(oldNickname: String, newNickname: String, occupation: String) {
        val database = Firebase.database
        val usersReference = database.reference.child("users")
        val queryRef = usersReference.orderByChild("nickname").equalTo(newNickname)
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()
                if (users == null || users.count() == 0 || users.iterator().next().value.nickname == oldNickname) {
                    val newQueryRef = usersReference.orderByChild("nickname").equalTo(oldNickname)
                    newQueryRef.addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val newUsers = snapshot.getValue<Map<String, User>>()
                            if (newUsers != null) {
                                val entry = newUsers.iterator().next()
                                val key = entry.key
                                val updatedInfo = HashMap<String, Any>()
                                updatedInfo.put("nickname", newNickname)
                                updatedInfo.put("occupation", occupation)
                                usersReference.child(key).updateChildren(updatedInfo)
                                presenter.onUpdateSuccess(newNickname, occupation)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            presenter.onUpdateFailed("Unknown Error. Try again!")
                        }

                    })
                } else {
                    presenter.onUpdateFailed("Nickname Already Taken!")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onUpdateFailed("Unknown Error. Try again!")
            }

        })
    }
}