package ru.whalemare.reporter

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */

fun main() {
    Reporter("""

    // Design
    implementation 'com.android.support:support-compat:28.0.0'
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:recyclerview-v7:28.0.0'
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.2'


    //DI: Dagger 2
    implementation 'com.google.dagger:dagger:2.14.1'
    implementation 'com.google.dagger:dagger-producers:2.14.1'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.14.1'
    api 'org.glassfish:javax.annotation:10.0-b28'
    implementation 'com.google.auto.factory:auto-factory:1.0-beta3'
    annotationProcessor 'com.google.auto.factory:auto-factory:1.0-beta3'

    // UI
    implementation 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
    implementation 'com.jakewharton.timber:timber:4.3.1'
    implementation 'io.reactivex:rxandroid:1.2.1'
    implementation 'io.reactivex:rxjava:1.2.1'
    implementation 'com.jakewharton.rxbinding:rxbinding:0.4.0'
    implementation 'com.squareup.retrofit2:retrofit:2.1.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava:2.1.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
    implementation 'com.squareup.okhttp3:okhttp:3.4.1'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.4.1'
    implementation 'com.google.guava:guava:23.4-android'
    implementation 'com.google.code.findbugs:jsr305:2.0.1'
    implementation 'com.asksira.android:webviewsuite:1.0.3'

    // Tests
    testImplementation "org.robolectric:robolectric:3.1.2"
    testImplementation "org.mockito:mockito-core:1.10.19"
    testImplementation 'junit:junit:4.12'

    androidTestImplementation 'com.android.support:support-annotations:25.0.0'
    androidTestImplementation 'com.squareup:javawriter:2.5.1'

    androidTestImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:0.5'
    androidTestImplementation 'com.android.support.test:rules:0.5'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:2.2.2'
    androidTestImplementation "org.mockito:mockito-core:1.10.19"
    androidTestImplementation "com.google.dexmaker:dexmaker:1.2"
    androidTestImplementation "com.google.dexmaker:dexmaker-mockito:1.2"
    testAnnotationProcessor "com.google.auto.factory:auto-factory:1.0-beta3"
    """
    ).run()
}
