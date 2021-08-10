package ge.sgurgenidze.stsertsvadze.messenger.login

import ge.sgurgenidze.stsertsvadze.messenger.model.User

interface ILoginPresenter {
    fun login(nickname: String, password: String)
    fun onLoginSuccess(user: User)
    fun onLoginFailed(message: String)
}