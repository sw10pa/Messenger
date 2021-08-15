package ge.sgurgenidze.stsertsvadze.messenger.users

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class UsersInteractor(var presenter: IUsersPresenter) {
    fun fetchUsers(searchNickname: String, loggedNickname: String) {
        val dbRef = Firebase.database.reference
        val usersRef = dbRef.child("users")
        var queryRef: Query? = null
        queryRef = usersRef.orderByKey()
        queryRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()
                if (users != null) {
                    val usersList = ArrayList<User>()
                    for ((_, user) in users) {
                        val userNickname = user.nickname!!
                        if (userNickname != loggedNickname) {
                            if (isPrefix(searchNickname, userNickname)) {
                                usersList.add(user)
                            }
                        }
                    }
                    presenter.onUsersFetched(usersList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onUsersFetched(ArrayList<User>())
            }

        })
    }

    private fun isPrefix(n1: String, n2: String): Boolean {
        if (n1 == "") return true
        if ((n1.length <= n2.length) && (n1 == n2.substring(0, n1.length))) return true
        return false
    }

    fun getUserId(nickname: String) {
        val dbRef = Firebase.database.reference
        val usersRef = dbRef.child("users")
        val queryRef = usersRef.orderByChild("nickname").equalTo(nickname)
        queryRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue<Map<String, User>>()!!
                val entry = users.iterator().next()
                val userId = entry.key
                presenter.onGetUserIdSuccess(nickname, userId)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.onGetUserIdFailed("Unknown error.Try again!")
            }

        })
    }
}