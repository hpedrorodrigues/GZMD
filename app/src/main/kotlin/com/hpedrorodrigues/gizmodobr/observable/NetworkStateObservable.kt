package com.hpedrorodrigues.gizmodobr.observable

import java.util.Observable

object NetworkStateObservable : Observable() {

    fun stateConnectionChanged() {
        setChanged()
        notifyObservers()
    }
}