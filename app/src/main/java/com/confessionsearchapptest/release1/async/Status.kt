package com.confessionsearchapptest.release1.async

//This is borrowing an implementation of Async Coroutines from Ladrahul25's async coroutines github. Credit goes to him

class StatusState {
    enum class Status {
        PENDING,RUNNING,FINISHED
    }
}