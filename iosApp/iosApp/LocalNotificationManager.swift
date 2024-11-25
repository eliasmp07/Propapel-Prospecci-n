//
//  LocalNotificationManager.swift
//  iosApp
//
//  Created by m1 on 24/11/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import NotificationCenter

class LocalNotificationManager: ObservableObject {
    let notificationCenter = UNUserNotificationCenter.current()
    func requestAuthorization() async throws {
        try await  notificationCenter.requestAuthorization(options: [.sound, .badge, .alert])
    }
}

import UserNotifications

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        UNUserNotificationCenter.current().delegate = self
        return true
    }
}

extension AppDelegate: UNUserNotificationCenterDelegate {
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification) async -> UNNotificationPresentationOptions {
        [.sound, .banner]
    }
}
