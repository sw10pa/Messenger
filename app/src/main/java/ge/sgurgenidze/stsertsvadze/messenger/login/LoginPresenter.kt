package ge.sgurgenidze.stsertsvadze.messenger.login

import ge.sgurgenidze.stsertsvadze.messenger.model.User

class LoginPresenter(var view: ILoginView): ILoginPresenter {
    val interactor = LoginInteractor(this)

    override fun login(nickname: String, password: String) {
        interactor.login(nickname, password)
    }

    override fun onLoginSuccess(user: User) {
        view.onLoginSuccess(user)
    }

    override fun onLoginFailed(message: String) {
        view.onLoginFailed(message)
    }
}