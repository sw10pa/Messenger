package ge.sgurgenidze.stsertsvadze.messenger.registration

import ge.sgurgenidze.stsertsvadze.messenger.model.User

interface IRegistrationPresenter {
    fun registerUser(user: User)
    fun onRegistrationSuccess(user: User)
    fun onRegistrationFailed(message: String)
}