package ge.sgurgenidze.stsertsvadze.messenger.login

import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class LoginInteractor(var presenter: ILoginPresenter) {
    fun login(nickname: String, password: String) {
        val database = Firebase.database
        val usersReference = database.reference.child("users")
        val queryRef = usersReference.orderByChild("nickname").equalTo(nickname)
        queryRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()
                if (users != null && users.count() != 0) {
                    val entry = users.iterator().next()
                    val user = entry.value
                    if (nickname == user.nickname && password == user.password) {
                        presenter.onLoginSuccess(User(user.nickname, user.password, user.occupation, entry.key))
                    } else {
                        presenter.onLoginFailed("Invalid Credentials")
                    }
                } else {
                    presenter.onLoginFailed("Invalid Credentials")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onLoginFailed("Unknown Error. Try again!")
            }
        })
    }
}
