package ge.sgurgenidze.stsertsvadze.messenger.users

import ge.sgurgenidze.stsertsvadze.messenger.model.User

interface IUsersPresenter {
    fun fetchUsers(searchNickname: String, loggedNickname: String)
    fun getUserId(nickname: String)
    fun onUsersFetched(users: List<User>)
    fun onGetUserIdSuccess(nickname: String, userId: String)
    fun onGetUserIdFailed(message: String)
}