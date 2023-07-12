import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

/////////////////
// ATTN: CHANGE ME
val props = Properties().apply {
    load(project.rootProject.file("local.properties").reader())
}
val starsectorDirectory = props.getProperty("gamePath") //"C:/Program Files (x86)/Fractal Softworks/Starsector"
val jarPath = "$rootDir/jars"
/////////////////

plugins {
    kotlin("jvm") version "1.6.21"
    java
    id("org.jetbrains.dokka") version "1.4.10"
}

version = "4.0.0"

val starsectorCoreDirectory = props["gameCorePath"] ?: "${starsectorDirectory}/starsector-core"
val starsectorModDirectory = props["modsPath"] ?: "${starsectorDirectory}/mods"
val artifactFolder = "wisp/questgiver/$version"
val jarFileName = "Questgiver-$version.jar"
val pomFileName = "Questgiver-$version.pom"
val sourcesJarFileName = "Questgiver-$version-sources.jar"
val javadocJarFileName = "Questgiver-$version-javadoc.jar"

repositories {
    maven(url = uri("$projectDir/libs"))
    mavenCentral()
}

dependencies {
    println("Mod folder: $starsectorModDirectory")
    val kotlinVersionInLazyLib = "1.5.31"

    // Get kotlin sdk from LazyLib during runtime, only use it here during compile time
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersionInLazyLib")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersionInLazyLib")

    compileOnly(fileTree("$starsectorModDirectory/LazyLib/jars") { include("*.jar") })

    // This grabs local files from the /libs folder, see `repositories` block.
    compileOnly("starfarer:starfarer-api:1.0.0")

    // JsonPath - 2.3.0 is the last version with Java 7 support - requires IO libs, forbidden by game
//    implementation("com.jayway.jsonpath:json-path:2.3.0") {
//        exclude("kotlin")
//        exclude("org.intellij.lang")
//        exclude("org.jetbrains.kotlin")
//    }

    // Starsector jars and dependencies
    compileOnly(fileTree(starsectorCoreDirectory) {
        include(
           //"starfarer.api.jar",
           //"starfarer.api.zip",
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
        // Build fat jar with all dependencies bundled.
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
//        val sourcesMain = sourceSets.main.get()
//        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
//        from(sourcesMain.output)


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
            file("$rootDir/../persean-chronicles/libs/$artifactFolder"),
//            file("$rootDir/../Gates-Awakened/libs")
            //file("$rootDir/../Trophy-Planet/libs")
        )
        val file = file("$jarPath/$jarFileName")
        val sourcesFile = file("$jarPath/$sourcesJarFileName")
//        val javadocFile = file("$jarPath/$javadocJarFileName")

        val pomFileText = """
                <project>
                  <modelVersion>4.0.0</modelVersion>
                 
                  <groupId>wisp</groupId>
                  <artifactId>questgiver</artifactId>
                  <version>$version</version>
                </project>
        """.trimIndent()

        destinations.forEach { dest ->
            File(dest, pomFileName).apply {
                parentFile.mkdirs()
                writeText(pomFileText)
            }
            copy {
                // Don't copy javadoc jar.
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

java {
    sourceCompatibility = JavaVersion.VERSION_1_6
    targetCompatibility = JavaVersion.VERSION_1_6
}

// Compile to Java 6 bytecode so that Starsector can use it
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.6"
}