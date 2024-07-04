package com.nickagas.calling_app

import android.net.Uri
import android.telecom.Connection
import android.telecom.ConnectionRequest
import android.telecom.ConnectionService
import android.telecom.PhoneAccount.CAPABILITY_SELF_MANAGED
import android.telecom.PhoneAccountHandle
import android.telecom.TelecomManager
import android.util.Log

class MyConnectionService : ConnectionService() {
    override fun onCreateOutgoingConnection(
        phoneAccountHandle: PhoneAccountHandle?,
        connectionRequest: ConnectionRequest?
    ): Connection? {
        // Handle outgoing call creation
        Log.i("nagas onCreateOutgoingConnection","init ")
        return YourConnection(phoneAccountHandle, connectionRequest?.address)
    }

    override fun onCreateOutgoingConnectionFailed(
        phoneAccountHandle: PhoneAccountHandle?,
        connectionRequest: ConnectionRequest?
    ) {
        // Handle outgoing call creation failure
        Log.i("nagas onCreateOutgoingConnectionFailed","init ")
    }

    override fun onCreateIncomingConnection(
        phoneAccountHandle: PhoneAccountHandle?,
        connectionRequest: ConnectionRequest?
    ): Connection? {
        // Handle incoming call creation
        Log.i("nagas onCreateIncomingConnection","init ")
        return YourConnection(phoneAccountHandle, connectionRequest?.address)
    }

    override fun onCreateIncomingConnectionFailed(
        phoneAccountHandle: PhoneAccountHandle?,
        connectionRequest: ConnectionRequest?
    ) {
        // Handle incoming call creation failure
        Log.i("OUTGOINGCALLFAILED","d")
    }
}

class YourConnection(
    phoneAccountHandle: PhoneAccountHandle?,
    address: Uri?
) : Connection() {
    init {
        setAddress(address, TelecomManager.PRESENTATION_ALLOWED)
        setConnectionCapabilities(CAPABILITY_SELF_MANAGED)
//        setAudioModeIsVoip(true)
        Log.i("nagas YourConnection","init ")
    }

    override fun onStateChanged(state: Int) {
        // Handle state changes of the call
        Log.i("nagas YourConnection","onStateChanged "+state.toString())
    }
}