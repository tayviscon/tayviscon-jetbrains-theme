package ru.tayviscontheme.jetbrains.activities

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import ru.tayviscontheme.jetbrains.TayvisconMeta
import ru.tayviscontheme.jetbrains.notifications.TayvisconNotification
import ru.tayviscontheme.jetbrains.settings.TayvisconSettings

class TayvisconStartupActivity : StartupActivity, DumbAware {
    override fun runActivity(project: Project) {
        val settings = TayvisconSettings.instance
        if(settings.version.isEmpty()) {
            settings.version = TayvisconMeta.currentVersion
            TayvisconNotification.notifyFirstlyDownLoaded(project)
            return
        }
        if(TayvisconMeta.currentVersion != settings.version) {
            settings.version = TayvisconMeta.currentVersion
            TayvisconNotification.notifyReleaseNote(project)
        }
    }
}