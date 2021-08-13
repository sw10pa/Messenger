package ge.sgurgenidze.stsertsvadze.messenger.chat

import ge.sgurgenidze.stsertsvadze.messenger.model.Message

interface IChatView {
    fun onFetchSuccess(messages: List<Message>)
    fun onFetchFailed(message: String)
}