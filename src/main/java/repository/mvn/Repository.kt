
package repository.mvn

import java.net.URI

data class Repository(

    val id: String,
    val name: String,
    val uri: URI
)
