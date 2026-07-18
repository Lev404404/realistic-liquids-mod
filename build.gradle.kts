plugins {
    id("fabric-loom") version "1.6.12"
    id("maven-publish")
}

version = "1.0.0"
group = "com.lev404404.realisticliquids"
base { archivesName.set("realistic-liquids-mod") }

repositories {
    mavenCentral()
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "Mojang"
        url = uri("https://libraries.minecraft.net/")
    }
}

loom {
    accessWidenerPath.set(file("src/main/resources/realistic_liquids.accesswidener"))
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.15.11")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.97.2+1.21.1")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filteringCharset = "Utf-8"
        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE") { rename { "${it}_${base.archivesName}" } }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.remapJar) {
                builtBy(tasks.remapJar)
            }
            artifact(tasks.sourcesJar) {
                builtBy(tasks.remapSourcesJar)
            }
        }
    }
}
