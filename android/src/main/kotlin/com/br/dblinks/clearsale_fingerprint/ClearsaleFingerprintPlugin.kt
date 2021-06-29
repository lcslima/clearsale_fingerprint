package com.br.dblinks.clearsale_fingerprint

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import sale.clear.behavior.android.exceptions.CaptureWasStartedException
import sale.clear.behavior.android.exceptions.SessionIDAlreadyUsedException

class ClearsaleFingerprintPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

    private lateinit var channel: MethodChannel
    private lateinit var clearsale: Clearsale
    private lateinit var context: Context
    private lateinit var activity: Activity

    override fun onDetachedFromActivity() {
        TODO("Not yet implemented")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        TODO("Not yet implemented")
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity;
    }

    override fun onDetachedFromActivityForConfigChanges() {
        TODO("Not yet implemented")
    }

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext

        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "clearsale_fingerprint")
        channel.setMethodCallHandler(this)
    }


    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "init" -> {
                clearsale = Clearsale(activity, call.argument<String>("appKey"))
                result.success("Clearsale started with appkey: ${call.argument<String>("appKey")}")
            }
            "start" -> {
                try {
                    clearsale.start()
                } catch (e: CaptureWasStartedException) {
                    Log.d("Clearsale", "Capture was already started")
                }
            }
            "stop" -> clearsale.stop()
            "generateSessionID" -> result.success(clearsale.generateSessionID())
            "collectDeviceInformation" -> {
                try {
                    clearsale.collectDeviceInformation(call.argument("sessionId") ?: "")
                } catch (e: SessionIDAlreadyUsedException) {
                    Log.d("Clearsale", "Session id was already used")
                }

            }
            else -> result.notImplemented()
        }
    }


    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
