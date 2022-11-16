package ru.tayviscontheme.jetbrains

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId

object TayvisconMeta {
    val currentVersion: String
        get() = PluginManagerCore.getPlugin(PluginId.getId("com.tayviscon.idea"))?.version ?:""
}