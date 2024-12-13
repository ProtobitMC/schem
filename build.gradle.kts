plugins {
    `java-library`
    `maven-publish`
}

group = "dev.hollowcube"
version = System.getenv("TAG_VERSION") ?: "dev"
description = "Schematic reader and writer for Minestom"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    compileOnly(libs.minestom)
    testImplementation(libs.minestom)

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.bundles.logback)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)

    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven("https://maven.protobit.space/snapshots") {
            name = "protobit"

            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications.create<MavenPublication>("maven") {
        groupId = "dev.hollowcube"
        artifactId = "schem"
        version = project.version.toString()

        from(project.components["java"])

        pom {
            name.set(artifactId)
            description.set(project.description)
            url.set("https://github.com/ProtobitMC/schem")

            licenses {
                license {
                    name.set("MIT")
                    url.set("https://github.com/ProtobitMC/schem/blob/main/LICENSE")
                }
            }

            developers {
                developer {
                    id.set("mworzala")
                    name.set("Matt Worzala")
                    email.set("matt@hollowcube.dev")
                }
            }

            scm {
                connection.set("scm:git:git://github.com/ProtobitMC/schem.git")
                developerConnection.set("scm:git:git@github.com:ProtobitMC/schem.git")
                url.set("https://github.com/ProtobitMC/schem")
                tag.set(System.getenv("TAG_VERSION") ?: "HEAD")
            }

            ciManagement {
                system.set("Github Actions")
                url.set("https://github.com/ProtobitMC/schem/actions")
            }
        }
    }
}