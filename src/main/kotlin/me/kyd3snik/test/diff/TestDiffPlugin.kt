package me.kyd3snik.test.diff

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import me.kyd3snik.test.diff.changes.CollectChangesTask
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("UNUSED") // used in plugin definition
class TestDiffPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val changesFile = CollectChangesTask.register(project).flatMap { it.output }

        project.pluginManager.withPlugin("com.android.application") {
            // TODO: old extension, probably will become deprecated soon
            project.extensions.findByType(AppExtension::class.java)
                ?.applicationVariants
                ?.all { variant -> TestDiffTask.register(project, variant, changesFile) }

            project.extensions.findByType(LibraryExtension::class.java)
                ?.libraryVariants
                ?.all { variant -> TestDiffTask.register(project, variant, changesFile) }
        }

    }
}