
package repository.mvn

import pl.droidsonroids.jspoon.annotation.Selector

internal class ArtifactVersionsPage {

    @Selector("a.vbtn.release")
    lateinit var versions: List<String>

}
