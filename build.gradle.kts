/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.8/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    id("application")
    id("org.javamodularity.moduleplugin") version "1.8.12"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id ("org.beryx.jlink") version "2.25.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

application {
    mainClass.set("algeo01_23002.gui.MainApp")
}

javafx {
    version = "22.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

val javafxVersion = "21" // Use the latest version

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    // Add platform-specific dependencies

    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("io.github.mkpaz:atlantafx-base:2.0.1")
    implementation("io.github.palexdev:materialfx:11.17.0")

    implementation(libs.guava)
}


jlink {
    imageZip.set(project.file("${buildDir}/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}

tasks.named<org.gradle.api.Task>("jlinkZip") {
    group = "distribution"
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    modularity.inferModulePath.set(true)
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/java"))
        }
        resources {
            setSrcDirs(listOf("src/resources"))
        }
    }
    test {
        java {
            setSrcDirs(listOf("test/java"))
        }
        resources {
            setSrcDirs(listOf("test/resources"))
        }
    }
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "algeo01_23002.Main"  // Replace with your main class
        )
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    // Handle duplicate files
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("--add-opens", "java.base/java.lang=ALL-UNNAMED")
}


