import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/////////////////
// ATTN: CHANGE ME
val starsectorDirectory = "C:/Program Files (x86)/Fractal Softworks/Starsector1.95.1-RC6"
val jarPath = "$rootDir/jars"
/////////////////

plugins {
    kotlin("jvm") version "1.5.31"
    java
    id("org.jetbrains.dokka") version "1.4.10"
}

version = "3.1.0"

val starsectorCoreDirectory = "$starsectorDirectory/starsector-core"
val starsectorModDirectory = "$starsectorDirectory/mods"
val jarFileName = "Questgiver-$version.jar"
val sourcesJarFileName = "Questgiver-$version-sources.jar"
val javadocJarFileName = "Questgiver-$version-javadoc.jar"

repositories {
    maven(url = uri("$projectDir/libs"))
    mavenCentral()
}

dependencies {
    val kotlinVersionInLazyLib = "1.5.31"

    // Get kotlin sdk from LazyLib during runtime, only use it here during compile time
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersionInLazyLib")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersionInLazyLib")

    implementation(fileTree("$starsectorModDirectory/LazyLib/jars") { include("*.jar") })

    // Starsector jars and dependencies
    api(fileTree(starsectorCoreDirectory) {
        include(
            "starfarer.api.jar",
            "starfarer.api.zip",
            "starfarer_obf.jar",
            "xstream-1.4.10.jar",
            "json.jar",
            "log4j-1.2.9.jar",
            "lwjgl.jar",
            "lwjgl_util.jar"
        )
//        exclude("*_obf.jar")
    })
}

java {
    withSourcesJar()
//    withJavadocJar()
}

tasks {
    named<Wrapper>("wrapper") {
        distributionType = Wrapper.DistributionType.BIN
    }

//    named<Jar>("javadocJar") {
//        destinationDirectory.set(file(jarPath))
//        archiveFileName.set(javadocJarFileName)
//        archiveClassifier.set("javadoc")
//    }

    named<org.gradle.jvm.tasks.Jar>("kotlinSourcesJar") {
        destinationDirectory.set(file(jarPath))
        archiveFileName.set(sourcesJarFileName)
        archiveClassifier.set("sources")
    }

    named<Jar>("jar")
    {
        destinationDirectory.set(file(jarPath))
        archiveFileName.set(jarFileName)
        from(sourceSets.main.get().allSource)
//        finalizedBy(dokkaGfm)
    }

    register<Copy>("update Wisp's mods with latest QG version") {
        dependsOn(jar)
        dependsOn(kotlinSourcesJar)
//        dependsOn("javadocJar")
        val destinations = listOf(
            file("$rootDir/../stories/libs"),
//            file("$rootDir/../Gates-Awakened/libs")
            //file("$rootDir/../Trophy-Planet/libs")
        )
        val file = file("$jarPath/$jarFileName")
        val sourcesFile = file("$jarPath/$sourcesJarFileName")
//        val javadocFile = file("$jarPath/$javadocJarFileName")

        destinations.forEach { dest ->
            copy {
                from(file, sourcesFile)//, javadocFile)
                println("Copying: $file (exists: ${file.exists()}) to $dest")
                into(dest)
            }
        }
    }

//    dokkaHtml.configure {
//        outputDirectory.set(file("$rootDir/docs"))
//    }
}

// Compile to Java 6 bytecode so that Starsector can use it
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.6"
}