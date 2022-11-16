package ru.tayviscontheme.jetbrains.notifications

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import org.intellij.lang.annotations.Language
import ru.tayviscontheme.jetbrains.TayvisconMeta

object TayvisconNotification {
    @Language("HTML")
    private val whatsNew = """
        <ul>
            <li>this theme is not completely conform the specification of tayviscon-theme</li>
        </ul>
    """.trimIndent()

    @Language("HTML")
    private val footerMessage = """
        <p>Thank you for choosing Tayviscon</p>
        <br>
        <p>
            <a href="https://github.com/tayviscon/tayviscon-jetbrains-theme">GitHub</a>
        </p>
    """.trimIndent()

    @Language("HTML")
    private val releaseNote = """
        <div>
            <h3>What's New?</h3>
            <div>$whatsNew</div>
            <div>$$footerMessage</div>
        </div>
    """.trimIndent()

    @Language("HTML")
    private val welcomeMessage = """
        <div>
            $footerMessage
        </div>
    """.trimIndent()

    private const val notificationGroupId = "Tayviscon Theme"

    @JvmField
    val notificationIcon = IconLoader.getIcon("/icons/tayviscon-logo.svg", javaClass)

    fun notifyReleaseNote(project: Project) {
        val title = "Tayviscon Theme updated to v${TayvisconMeta.currentVersion}"
        val notification = NotificationGroupManager.getInstance().getNotificationGroup(notificationGroupId)
                .createNotification(title, releaseNote, NotificationType.INFORMATION)
        notification.icon = notificationIcon
        notification.notify(project)
    }

    fun notifyFirstlyDownLoaded(project: Project) {
        val title = "Tayviscon Theme is installed"
        val notification = NotificationGroupManager.getInstance().getNotificationGroup(notificationGroupId)
                .createNotification(title, welcomeMessage, NotificationType.INFORMATION)
        notification.icon = notificationIcon
        notification.notify(project)
    }


}