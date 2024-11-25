import SwiftUI

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor private var appDelegate: AppDelegate
    @StateObject var lnManager = LocalNotificationManager()
    var body: some Scene {
        WindowGroup {
            ContentView().environmentObject(lnManager)
        }
    }
}
