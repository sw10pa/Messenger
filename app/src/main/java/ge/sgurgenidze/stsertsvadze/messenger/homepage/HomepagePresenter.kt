package ge.sgurgenidze.stsertsvadze.messenger.homepage

import ge.sgurgenidze.stsertsvadze.messenger.model.Chat

class HomepagePresenter(var view: IHomepageView): IHomepagePresenter {
    var interactor = HomepageInteractor(this)

    override fun fetchChats(nickname: String) {
        interactor.fetchChats(nickname)
    }

    override fun onFetchSuccess(chats: List<Chat>) {
        view.onFetchSuccess(chats)
    }

    override fun onFetchFailed(message: String) {
        view.onFetchFailed(message)
    }
}