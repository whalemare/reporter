@file:Suppress("JUnit5MalformedNestedClass")

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.whalemare.reporter.model.Artifact
import ru.whalemare.reporter.parser.ArtifactParser

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class TestArtifactParser {

    @Nested
    class GroupConvention {

        lateinit var parser: ArtifactParser
        lateinit var artifacts: List<Artifact>

        @BeforeEach
        fun setup(){
            parser = ArtifactParser(
                rawDependencies = "implementation group: 'pl.droidsonroids.retrofit2', name: 'converter-jspoon', version: '1.2.2'",
                prefixes = listOf(
                    "implementation"
                )
            )
            artifacts = parser.provide()
        }

        @Test
        fun `should parse group`(){
            val artifact = artifacts.first()
            assertEquals("pl.droidsonroids.retrofit2", artifact.group)
        }

        @Test
        fun `should parse name`(){
            val artifact = artifacts.first()
            assertEquals("converter-jspoon", artifact.name)
        }

        @Test
        fun `should parse version`(){
            val artifact = artifacts.first()
            assertEquals("1.2.2", artifact.version)
        }

        @Test
        fun `should parse group convention`() {
            parser = ArtifactParser(
                rawDependencies = "implementation group: 'pl.droidsonroids.retrofit2', name: 'converter-jspoon', version: '1.2.2'",
                prefixes = listOf(
                    "implementation"
                )
            )
            val artifacts = parser.provide()
            assertEquals(1, artifacts.size)
        }
    }

    @Nested
    class PlainConvention {
        lateinit var parser: ArtifactParser

        @Test
        fun `should return properly list size with () dependencies`(){
            val artifacts = ArtifactParser(
                rawDependencies = """
                implementation("com.squareup.okhttp3:okhttp:3.14.2")
                implementation("org.slf4j:slf4j-log4j12:1.8.0-beta4")
                """.trimIndent()
            ).provide()

            assertEquals(2, artifacts.size)
        }

        @Test
        fun `should return properly artifacts with () dependencies double-quotas`(){
            val artifacts = ArtifactParser(
                rawDependencies = """
                implementation("com.squareup.okhttp3:okhttp:3.14.2")
                """.trimIndent()
            ).provide()

            val expectedFirst = Artifact(
                scheme = "com.squareup.okhttp3:okhttp:3.14.2",
                group = "com.squareup.okhttp3",
                name = "okhttp",
                version = "3.14.2"
            )

            assertEquals(expectedFirst.group, artifacts[0].group)
            assertEquals(expectedFirst.name, artifacts[0].name)
            assertEquals(expectedFirst.version, artifacts[0].version)
            assertEquals(expectedFirst.scheme, artifacts[0].scheme)
        }

        @Test
        fun `should return properly artifacts with () dependencies with braces`(){
            val artifacts = ArtifactParser(
                rawDependencies = """
                implementation('com.github.ihsanbal:LoggingInterceptor:3.0.0') {
            //        exclude group: 'org.json', module: 'json'
                }
                implementation 'info.picocli:picocli:4.0.0-alpha-3'
                """.trimIndent()
            ).provide()

            val expectedFirst = Artifact(
                scheme = "com.squareup.okhttp3:okhttp:3.14.2",
                group = "com.squareup.okhttp3",
                name = "okhttp",
                version = "3.14.2"
            )

            assertEquals(expectedFirst.group, artifacts[0].group)
            assertEquals(expectedFirst.name, artifacts[0].name)
            assertEquals(expectedFirst.version, artifacts[0].version)
            assertEquals(expectedFirst.scheme, artifacts[0].scheme)
        }

        @Test
        fun `should return properly size of list`() {
            parser = ArtifactParser(
                // first line will be filtered in blacklist
                rawDependencies = """
            implementation fileTree(dir: 'libs', include: ['*.jar'])
            implementation 'com.android.support:support-compat:28.0.0'

            //Dagger 2
            implementation 'com.google.dagger:dagger:2.14.1'
            implementation 'com.google.dagger:dagger-producers:2.14.1'
            annotationProcessor 'com.google.dagger:dagger-compiler:2.14.1'
            api 'org.glassfish:javax.annotation:10.0-b28'
            implementation 'com.google.auto.factory:auto-factory:1.0-beta3'
            annotationProcessor 'com.google.auto.factory:auto-factory:1.0-beta3'
            """,
                prefixes = listOf(
                    "implementation",
                    "annotationProcessor",
                    "kapt",
                    "api",
                    "testImplementation",
                    "androidTestImplementation"
                ),
                blacklist = listOf("fileTree(dir: 'libs', include: ['*.jar'])")
            )
            val artifacts = parser.provide()
            assertEquals(7, artifacts.size)
        }

        @Test
        fun `should return properly parsed artifacts`() {
            parser = ArtifactParser(
                """
            implementation fileTree(dir: 'libs', include: ['*.jar'])
            implementation 'com.android.support:support-compat:28.0.0'

            //Dagger 2
            api 'org.glassfish:javax.annotation:10.0-b28'
            """,
                listOf(
                    "implementation",
                    "annotationProcessor",
                    "kapt",
                    "api",
                    "testImplementation",
                    "androidTestImplementation"
                )
            )
            val artifacts = parser.provide()
            val expected = listOf(
                Artifact(
                    "com.android.support:support-compat:28.0.0",
                    "com.android.support",
                    "support-compat",
                    "28.0.0"
                ),
                Artifact(
                    "org.glassfish:javax.annotation:10.0-b28",
                    "org.glassfish",
                    "javax.annotation",
                    "10.0-b28"
                )
            )
            assertIterableEquals(expected, artifacts)
        }
    }
}
