
import model.Artifact
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class TestArtifactParser {

    lateinit var parser: ArtifactParser

    @Test
    fun `should return properly size of list`() {
        parser = ArtifactParser(
            // first line will be filtered in blacklist
            """
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
            listOf(
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
            Artifact("com.android.support:support-compat:28.0.0", "com.android.support", "support-compat", "28.0.0"),
            Artifact("org.glassfish:javax.annotation:10.0-b28", "org.glassfish", "javax.annotation", "10.0-b28")
        )
        assertIterableEquals(expected, artifacts)
    }
}
