import Flutter
import UIKit
import AppTrackingTransparency

public class SwiftClearsaleFingerprintPlugin: NSObject, FlutterPlugin {
    lazy var clearsale: Clearsale? = nil

    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "clearsale_fingerprint", binaryMessenger: registrar.messenger())
        let instance = SwiftClearsaleFingerprintPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)

        if #available(iOS 14, *) {
            ATTrackingManager.requestTrackingAuthorization { _ in
            }
        }
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {

        let args = call.arguments as? Dictionary<String, String>
        switch call.method {
        case "init":
            let appKey : String = args?["appKey"] ?? "";
            clearsale = Clearsale(appkey: appKey)
            result("Clearsale started with appkey: " + appKey);
        case "generateSessionID":
            result(clearsale?.generateSessionId())
        case "collectDeviceInformation":
            let sessionId : String = args?["sessionId"] ?? "";
            if(sessionId.isEmpty){
                result("Error: sessionId is empty");
            } else{
                clearsale?.collectDeviceInformation(sessionId: sessionId)
            }
            result("Collected device information with sessionId: " + sessionId )
        default:
            result("No method")
        }
    }
}
