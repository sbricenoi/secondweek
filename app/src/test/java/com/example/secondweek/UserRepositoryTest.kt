package com.example.secondweek

import com.example.secondweek.data.User
import com.example.secondweek.data.UserRepository
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    @Before
    fun setup() {
        UserRepository.clearForTesting()
    }

    @Test
    fun registerUpToFiveUsers_andAuthenticate() {
        val base = User(
            name = "Test",
            email = "a@a.com",
            password = "secret123",
            preference = "Alta",
            acceptTerms = true,
            gender = "Femenino"
        )
        repeat(5) { idx ->
            val u = base.copy(email = "user${idx}@example.com")
            val result = UserRepository.addUser(u)
            assertTrue(result.isSuccess)
        }
        // Sixth should fail
        val extra = base.copy(email = "user5@example.com")
        assertFalse(UserRepository.addUser(extra).isSuccess)

        // Auth exists
        assertTrue(UserRepository.authenticate("user0@example.com", "secret123"))
        // Wrong pass
        assertFalse(UserRepository.authenticate("user0@example.com", "wrong"))
        // Unknown
        assertFalse(UserRepository.authenticate("nouser@example.com", "secret123"))
    }
}


