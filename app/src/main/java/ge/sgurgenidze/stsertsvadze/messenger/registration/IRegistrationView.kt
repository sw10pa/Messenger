package ge.sgurgenidze.stsertsvadze.messenger.registration

import ge.sgurgenidze.stsertsvadze.messenger.model.User

interface IRegistrationView {
    fun onRegistrationSuccess(user: User)
    fun onRegistrationFailed(message: String)
}