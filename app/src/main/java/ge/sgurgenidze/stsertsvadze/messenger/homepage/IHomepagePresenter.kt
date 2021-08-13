package ge.sgurgenidze.stsertsvadze.messenger.homepage

import ge.sgurgenidze.stsertsvadze.messenger.model.Chat

interface IHomepagePresenter {
    fun fetchChats(nickname: String)
    fun onFetchSuccess(chats: List<Chat>)
    fun onFetchFailed(message: String)
}