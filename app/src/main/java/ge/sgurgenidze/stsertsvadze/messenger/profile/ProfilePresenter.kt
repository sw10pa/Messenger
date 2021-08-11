package ge.sgurgenidze.stsertsvadze.messenger.profile

class ProfilePresenter(var view: IProfileView): IProfilePresenter {
    var interactor = ProfileInteractor(this)

    override fun updateProfile(oldNickname: String, newNickname: String, occupation: String) {
        interactor.updateProfile(oldNickname, newNickname, occupation)
    }

    override fun onUpdateSuccess(nickname: String, occupation: String) {
        view.onUpdateSuccess(nickname, occupation)
    }

    override fun onUpdateFailed(message: String) {
        view.onUpdateFailed(message)
    }
}
