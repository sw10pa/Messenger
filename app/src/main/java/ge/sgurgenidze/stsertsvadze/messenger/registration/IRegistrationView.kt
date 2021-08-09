package ge.sgurgenidze.stsertsvadze.messenger.registration

interface IRegistrationView {
    fun onRegistrationSuccess()
    fun onRegistrationFailed(message: String)
}