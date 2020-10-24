package io.kdimla.trackblocker.fakes

import io.kdimla.trackblocker.trackerdata.source.TrackerDataClient

class FakeTrackerDataClient : TrackerDataClient {
    var shouldMatch = false
    var lastState: State = State.MatchNotChecked
    override fun matches(pageUrl: String, interceptedUrl: String): Boolean {
        lastState = State.MatchRequested(pageUrl, interceptedUrl)
        return shouldMatch
    }

    sealed class State {
        object MatchNotChecked : State()
        data class MatchRequested(
            var paramPageUrl: String,
            var paramInterceptedUrl: String
        ) : State()
    }
}