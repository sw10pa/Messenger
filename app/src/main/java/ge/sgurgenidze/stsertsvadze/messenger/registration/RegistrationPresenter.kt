package ge.sgurgenidze.stsertsvadze.messenger.registration

import ge.sgurgenidze.stsertsvadze.messenger.model.User

class RegistrationPresenter(var view: IRegistrationView): IRegistrationPresenter {
    var interactor = RegistrationInteractor(this)

    override fun registerUser(user: User) {
        interactor.registerUser(user)
    }

    override fun onRegistrationSuccess(user: User) {
        view.onRegistrationSuccess(user)
    }

    override fun onRegistrationFailed(message: String) {
        view.onRegistrationFailed(message)
    }
}