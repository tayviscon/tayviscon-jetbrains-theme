fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.9.0"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "1.3.1"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

// Configure project's dependencies
repositories {
    mavenCentral()
}

// Set the JVM language level used to compile sources and generate files - Java 11 is required since 2020.3
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

// Configure Gradle IntelliJ Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))

    updateSinceUntilBuild.set(false)
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version.set(properties("pluginVersion"))
    path.set("${project.projectDir}/CHANGELOG.md")
    groups.set(emptyList())
}


tasks {
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))

        val description = """
            <div>
              <p>Tayviscon Theme for JetBrains</p>
              <br />
              <p>
                <img
                  alt="Screenshot"
                  src="https://github.com/tayviscon/tayviscon-jetbrains-theme/raw/main/screenshot.png"
                  width="600"
                />
              </p>
              <h2>Install</h2>
              <p>
                All instructions can be found at
                <a href="https://github.com/tayviscon/tayviscon-jetbrains-theme/blob/main/INSTALL.md">theme repository</a>.
              </p>
            </div>
        """.trimIndent()

        pluginDescription.set(description)
        changeNotes.set(provider { changelog.getLatest().toHTML() })
    }

    runPluginVerifier {
        ideVersions.set(
                properties("pluginVerifierIdeVersions")
                        .split(",")
                        .map(String::trim)
                        .filter(String::isNotEmpty)
        )
        failureLevel.set(
                listOf(
                        org.jetbrains.intellij.tasks.RunPluginVerifierTask.FailureLevel.COMPATIBILITY_PROBLEMS,
                        org.jetbrains.intellij.tasks.RunPluginVerifierTask.FailureLevel.INVALID_PLUGIN
                )
        )
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getProperty("jetbrains.token"))
    }
}
