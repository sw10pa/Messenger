package ge.sgurgenidze.stsertsvadze.messenger.users

import ge.sgurgenidze.stsertsvadze.messenger.model.User

class UsersPresenter(var view: IUsersView): IUsersPresenter {
    var interactor = UsersInteractor(this)

    override fun fetchUsers(searchNickname: String, loggedNickname: String) {
        interactor.fetchUsers(searchNickname, loggedNickname)
    }

    override fun getUserId(nickname: String) {
        interactor.getUserId(nickname)
    }

    override fun onUsersFetched(users: List<User>) {
        view.onUsersFetched(users)
    }

    override fun onGetUserIdSuccess(nickname: String, userId: String) {
        view.onGetUserIdSuccess(nickname, userId)
    }

    override fun onGetUserIdFailed(message: String) {
        view.onGetUserIdFailed(message)
    }
}