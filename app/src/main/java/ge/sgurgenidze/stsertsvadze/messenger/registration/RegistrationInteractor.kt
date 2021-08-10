package ge.sgurgenidze.stsertsvadze.messenger.registration

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class RegistrationInteractor(var presenter: IRegistrationPresenter) {
    fun registerUser(user: User) {
        val database = Firebase.database
        val usersReference = database.reference.child("users")
        val queryRef = usersReference.orderByChild("nickname").equalTo(user.nickname)
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()
                if (users == null || users.count() == 0) {
                    usersReference.push().key?.let {
                        usersReference.child(it).setValue(user)
                    }
                    presenter.onRegistrationSuccess()
                } else {
                    presenter.onRegistrationFailed("Nickname Already Taken!")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onRegistrationFailed("Unknown Error. Try again!")
            }

        })
    }
}