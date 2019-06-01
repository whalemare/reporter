import com.squareup.moshi.Moshi

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
inline fun <reified E> Moshi.toJson(element: E): String {
    return adapter(E::class.java).toJson(element)!!
}

inline fun <reified E> Moshi.fromJson(json: String): E? {
    return adapter(E::class.java).fromJson(json)
}

