package com.br.dblinks.clearsale_fingerprint

import android.content.Context
import sale.clear.behavior.android.Behavior

class Clearsale(context: Context?, appKey: String?) {
    private var mInstance: Behavior = Behavior.getInstance(context, appKey);

    fun start() {
        this.mInstance.start();
    }

    fun stop() {
        this.mInstance.stop();
    }

    fun generateSessionID(): String? {
        return this.mInstance.generateSessionID();
    }

    fun collectDeviceInformation(sessionId: String) {
        mInstance.collectDeviceInformation(sessionId);
    }

    fun getInstance(): Behavior {
        return this.mInstance;
    }
}