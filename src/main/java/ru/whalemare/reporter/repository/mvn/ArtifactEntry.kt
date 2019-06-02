package ru.whalemare.reporter.repository.mvn

import java.time.LocalDate

data class ArtifactEntry(

    val groupId: String,
    val id: String,
    val license: String?,
    val description: String,
    val lastRelease: LocalDate
)
