package ge.sgurgenidze.stsertsvadze.messenger.profile

interface IProfileView {
    fun onUpdateSuccess(nickname: String, occupation: String)
    fun onUpdateFailed(message: String)
}