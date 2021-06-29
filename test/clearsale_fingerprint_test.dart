import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:clearsale_fingerprint/clearsale_fingerprint.dart';

void main() {
  const MethodChannel channel = MethodChannel('clearsale_fingerprint');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(ClearsaleFingerprint.platformVersion, '42');
  });
}
