
package repository.mvn

data class Page<out T>(
    /**
     * The current page number.
     */
    val number: Int,
    /**
     * The maximum items to fetch per page.
     */
    val limit: Int,
    /**
     * The actual result collection.
     */
    val items: List<T>,
    /**
     * The total number of pages.
     */
    val totalPages: Int,
    /**
     * The total number of items.
     */
    val totalItems: Long) {

    companion object {

        @JvmStatic
        fun <T> empty(): Page<T> {
            return Page(1, 10, emptyList(), 0, 0)
        }
    }
}
