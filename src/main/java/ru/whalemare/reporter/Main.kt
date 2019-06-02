package ru.whalemare.reporter

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */

fun main() {
    Reporter("""
        implementation 'org.jsoup:jsoup:1.12.1'
        implementation 'pl.droidsonroids:jspoon:1.2.2'
        implementation group: 'pl.droidsonroids.retrofit2', name: 'converter-jspoon', version: '1.2.2'

        implementation("com.squareup.okhttp3:okhttp:3.14.2")
        implementation "org.funktionale:funktionale-memoization:1.2"

        implementation("org.slf4j:slf4j-log4j12:1.8.0-beta4")
        implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    """
    ).run()
}
