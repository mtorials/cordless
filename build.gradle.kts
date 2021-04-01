plugins {
    kotlin("js") version "1.4.31"
    //kotlin("plugin.serialization") version "1.4.31"
}

group = "de.mtorials"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    testImplementation(kotlin("test-js"))

    // Frontend
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
    implementation("org.jetbrains:kotlin-css:1.0.0-pre.149-kotlin-1.4.31")

    //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

    // My own
    implementation("de.mtorials:dial-phone:v1.1.2-alpha")
}

kotlin {
    js(IR) {
        browser {
            runTask {
                cssSupport.enabled = true
            }
            webpackTask {
                cssSupport.enabled = true
            }
        }
        binaries.executable()
    }
}