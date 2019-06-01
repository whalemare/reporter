/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
interface Provider<Item> {
    fun provide(): Item
}
