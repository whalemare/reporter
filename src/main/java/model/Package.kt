package model

/**
 * Describes artifact scheme like:
 *
 * @param scheme com.android.support.test.espresso:espresso-core:2.2.2
 * @param groupId com.android.support.test.espresso
 * @param artifactId espresso-core
 * @param version 2.2.2
 *
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class Package(
    val scheme: String,
    val groupId: String,
    val artifactId: String,
    val version: String
)
