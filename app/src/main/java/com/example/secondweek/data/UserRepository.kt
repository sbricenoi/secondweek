package com.example.secondweek.data

data class User(
    val name: String,
    val email: String,
    val password: String,
    val preference: String,
    val acceptTerms: Boolean,
    val gender: String
)

object UserRepository {
    private const val MAX_USERS = 5
    private val users: MutableList<User> = mutableListOf()

    fun addUser(user: User): Result<Unit> {
        if (users.size >= MAX_USERS) {
            return Result.failure(IllegalStateException("Capacidad máxima alcanzada ($MAX_USERS)"))
        }
        if (users.any { it.email.equals(user.email, ignoreCase = true) }) {
            return Result.failure(IllegalArgumentException("Email ya registrado"))
        }
        users.add(user)
        return Result.success(Unit)
    }

    fun authenticate(email: String, password: String): Boolean {
        return users.any { it.email.equals(email, ignoreCase = true) && it.password == password }
    }

    fun getAllUsers(): List<User> = users.toList()

    // For unit testing only
    fun clearForTesting() {
        users.clear()
    }
}


