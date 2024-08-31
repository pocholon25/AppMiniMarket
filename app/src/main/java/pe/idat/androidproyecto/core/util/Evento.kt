package pe.idat.androidproyecto.core.util

class Evento<out T>(private val content: T) {

    private var hasBeenHandled = false

    // Devuelve el contenido y evita que se use de nuevo
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    // Devuelve el contenido, incluso si ya ha sido manejado
    fun peekContent(): T = content
}