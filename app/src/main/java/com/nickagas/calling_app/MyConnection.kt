package com.nickagas.calling_app

import android.telecom.CallAudioState
import android.telecom.Connection
import android.telecom.DisconnectCause
import android.util.Log

class MyConnection : Connection() {

    init {
        Log.d("nagas","MyConnection init")
        connectionCapabilities = CAPABILITY_SUPPORT_HOLD
        connectionProperties = PROPERTY_SELF_MANAGED
//        setInitialized()
//        setActive()

    }

    override fun onShowIncomingCallUi() {
        Log.d("nagas","MyConnection onShowIncomingCallUi")
        super.onShowIncomingCallUi()
    }

    override fun onCallAudioStateChanged(state: CallAudioState?) {
        Log.d("nagas","MyConnection onCallAudioStateChanged $state")
        super.onCallAudioStateChanged(state)
    }

    override fun onAnswer() {
        Log.d("nagas","MyConnection onAnswer ")
        super.onAnswer()
    }

    override fun onReject() {
        Log.d("nagas","MyConnection onReject ")
        super.onReject()
    }
    override fun onDisconnect() {
        Log.d("nagas","MyConnection onDisconnect")
        setDisconnected(DisconnectCause(DisconnectCause.LOCAL))
        destroy()
    }

    override fun onAbort() {
        Log.d("nagas","MyConnection onAbort")
        setDisconnected(DisconnectCause(DisconnectCause.CANCELED))
        destroy()
    }

    override fun onHold() {
        setOnHold()
        Log.d("nagas","MyConnection onHold")
    }

    override fun onUnhold() {
        Log.d("nagas","MyConnection onUnhold")
        setActive()
    }

    init {

    }
//    fun setInitialized() {
//        setInitialized()
//        setActive()
//    }
}
