package ge.sgurgenidze.stsertsvadze.messenger.homepage

import ge.sgurgenidze.stsertsvadze.messenger.model.Chat

interface IHomepageView {
    fun onFetchSuccess(chats: List<Chat>)
    fun onFetchFailed(message: String)
    fun openChat(chatId: String, nickname: String)
}