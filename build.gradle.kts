import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/////////////////
// ATTN: CHANGE ME
val starsectorDirectory = "C:/Program Files (x86)/Fractal Softworks/Starsector"
val jarPath = "$rootDir/jars"
/////////////////

plugins {
    kotlin("jvm") version "1.3.60"
    java
    id("org.jetbrains.dokka") version "1.4.10"
}

version = "1.0.0"

val starsectorCoreDirectory = "$starsectorDirectory/starsector-core"
val starsectorModDirectory = "$starsectorDirectory/mods"
val jarFileName = "Questgiver-$version.jar"

repositories {
    maven(url = uri("$projectDir/libs"))
    jcenter()
}

dependencies {
    val kotlinVersionInLazyLib = "1.3.61"

    // Get kotlin sdk from LazyLib during runtime, only use it here during compile time
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersionInLazyLib")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersionInLazyLib")


    compileOnly(fileTree("$starsectorModDirectory/LazyLib/jars") { include("*.jar") })

    // Starsector jars and dependencies
    compileOnly(fileTree(starsectorCoreDirectory) {
        include("*.jar")
//        exclude("*_obf.jar")
    })
}

tasks {
    named<Jar>("jar")
    {
        destinationDirectory.set(file(jarPath))
        archiveFileName.set(jarFileName)
        finalizedBy(dokkaGfm)
    }

    register<Copy>("update Wisp's mods with latest QG version") {
        dependsOn(jar)
        val destinations = listOf(
                file("$rootDir/../stories/libs"),
                file("$rootDir/../Gates-Awakened/libs")
        )
        val file = file("$jarPath/$jarFileName")

        destinations.forEach { dest ->
            copy {
                from(file)
                println("Copying: $file (exists: ${file.exists()}) to $dest")
                into(dest)
            }
        }
    }

    dokkaGfm.configure {
        outputDirectory.set(file("$rootDir/docs"))
    }
}

// Compile to Java 6 bytecode so that Starsector can use it
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.6"
}