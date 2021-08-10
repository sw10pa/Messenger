package ge.sgurgenidze.stsertsvadze.messenger.login

import ge.sgurgenidze.stsertsvadze.messenger.model.User

interface ILoginView {
    fun onLoginSuccess(user: User)
    fun onLoginFailed(message: String)
}