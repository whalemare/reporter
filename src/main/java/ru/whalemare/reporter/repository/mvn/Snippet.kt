package ru.whalemare.reporter.repository.mvn

data class Snippet(
    val type: Type,
    val value: String) {

    enum class Type {
        MAVEN,
        GRADLE,
        SBT,
        IVY,
        GRAPE,
        LEININGEN,
        BUILDER
    }
}
