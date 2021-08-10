package ge.sgurgenidze.stsertsvadze.messenger.profile

interface IProfilePresenter {
    fun updateProfile(oldNickname: String, newNickname: String, occupation: String)
    fun onUpdateSuccess(nickname: String, occupation: String)
    fun onUpdateFailed(message: String)
}