package writer

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
interface Writer<Item> {
    fun write(items: List<Item>)
}
