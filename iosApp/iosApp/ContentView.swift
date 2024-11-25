import UIKit
import SwiftUI
import ComposeApp


struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @EnvironmentObject var lnManager: LocalNotificationManager
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard).task {
                try? await lnManager.requestAuthorization()
            } // Compose has own keyboard handler
    }
}



