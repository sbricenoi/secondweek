package com.example.secondweek.data

/**
 * Modelo de datos para representar un usuario del sistema
 * @param name Nombre completo del usuario
 * @param email Correo electrónico único del usuario
 * @param password Contraseña del usuario (en producción debería estar hasheada)
 * @param preference Nivel de preferencia de accesibilidad (Alta, Media, Baja)
 * @param acceptTerms Indica si el usuario aceptó los términos y condiciones
 * @param gender Género del usuario
 */
data class User(
    val name: String,
    val email: String,
    val password: String,
    val preference: String,
    val acceptTerms: Boolean,
    val gender: String
) {
    init {
        require(name.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.isNotBlank()) { "El email no puede estar vacío" }
        require(password.length >= 6) { "La contraseña debe tener al menos 6 caracteres" }
        require(acceptTerms) { "Debe aceptar los términos y condiciones" }
    }
}

/**
 * Repositorio singleton para gestionar usuarios en memoria
 * Implementa el patrón Repository para abstraer el acceso a datos
 */
object UserRepository {
    private const val MAX_USERS = 5
    private val users: MutableList<User> = mutableListOf()

    /**
     * Agrega un nuevo usuario al repositorio
     * @param user Usuario a agregar
     * @return Result<Unit> Success si se agregó correctamente, Failure con el error específico
     */
    fun addUser(user: User): Result<Unit> {
        return when {
            users.size >= MAX_USERS -> {
                Result.failure(IllegalStateException("Capacidad máxima alcanzada ($MAX_USERS usuarios)"))
            }
            users.any { it.email.equals(user.email, ignoreCase = true) } -> {
                Result.failure(IllegalArgumentException("El email ${user.email} ya está registrado"))
            }
            else -> {
                users.add(user)
                Result.success(Unit)
            }
        }
    }

    /**
     * Autentica un usuario con email y contraseña
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return true si las credenciales son válidas, false en caso contrario
     */
    fun authenticate(email: String, password: String): Boolean {
        return users.any { user ->
            user.email.equals(email.trim(), ignoreCase = true) && 
            user.password == password
        }
    }

    /**
     * Obtiene todos los usuarios registrados
     * @return Lista inmutable de usuarios
     */
    fun getAllUsers(): List<User> = users.toList()

    /**
     * Obtiene la cantidad actual de usuarios registrados
     * @return Número de usuarios registrados
     */
    fun getUserCount(): Int = users.size

    /**
     * Verifica si se puede agregar más usuarios
     * @return true si hay espacio disponible, false si se alcanzó el límite
     */
    fun canAddMoreUsers(): Boolean = users.size < MAX_USERS

    /**
     * Busca un usuario por email
     * @param email Email a buscar
     * @return Usuario encontrado o null si no existe
     */
    fun findUserByEmail(email: String): User? {
        return users.find { it.email.equals(email.trim(), ignoreCase = true) }
    }

    // Método para pruebas unitarias únicamente
    internal fun clearForTesting() {
        users.clear()
    }
}


