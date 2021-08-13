package ge.sgurgenidze.stsertsvadze.messenger.chat

import ge.sgurgenidze.stsertsvadze.messenger.model.Message

interface IChatPresenter {
    fun fetchChats(chatId: String)
    fun sendMessage(chatId: String, sender: Int, message: String)
    fun onFetchSuccess(messages: List<Message>)
    fun onFetchFailed(message: String)
}