import 'dart:async';

import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class ClearsaleFingerprint {
  final String appKey;

  static const MethodChannel _channel = const MethodChannel('clearsale_fingerprint');
  static const platformVersion = 42;

  ClearsaleFingerprint(this.appKey) {
    _channel.invokeMethod('init', <String, dynamic>{'appKey': appKey});
  }

  Future<String> generateSessionID() async {
    return await _channel.invokeMethod('generateSessionID');
  }

  collectDeviceInformation(String sessionId) async {
    await _channel.invokeMethod('collectDeviceInformation', {'sessionId': sessionId});
  }

  start() async {
    await _channel.invokeMethod('start');
  }

  stop() async {
    await _channel.invokeMethod('stop');
  }
}
