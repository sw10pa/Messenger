package ge.sgurgenidze.stsertsvadze.messenger.chat

import ge.sgurgenidze.stsertsvadze.messenger.model.Message

class ChatPresenter(var view: IChatView): IChatPresenter {
    var interactor = ChatInteractor(this)

    override fun fetchChats(chatId: String) {
        interactor.fetchChats(chatId)
    }

    override fun sendMessage(chatId: String, sender: Int, message: String) {
        interactor.sendMessage(chatId, sender, message)
    }

    override fun onFetchSuccess(messages: List<Message>) {
        view.onFetchSuccess(messages)
    }

    override fun onFetchFailed(message: String) {
        view.onFetchFailed(message)
    }
}