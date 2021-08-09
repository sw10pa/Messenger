package ge.sgurgenidze.stsertsvadze.messenger.registration

import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import ge.sgurgenidze.stsertsvadze.messenger.model.User

class RegistrationInteractor(var presenter: IRegistrationPresenter) {
    fun registerUser(user: User) {
        val database = Firebase.database
        val usersReference = database.getReference("users")
        usersReference.push().key?.let {
            usersReference.child(it).setValue(user)
        }
        presenter.onRegistrationSuccess()
    }
}