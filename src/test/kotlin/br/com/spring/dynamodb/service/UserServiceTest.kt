package br.com.spring.dynamodb.service

import br.com.spring.dynamodb.BaseTestRunner
import br.com.spring.dynamodb.exception.NotFoundException
import br.com.spring.dynamodb.repository.UserRepository
import br.com.spring.dynamodb.util.DefaultValuesMock.Companion.DOCUMENT
import br.com.spring.dynamodb.util.DefaultValuesMock.Companion.ID
import br.com.spring.dynamodb.util.MockUser
import br.com.spring.dynamodb.util.MockUser.Companion.saveUserResponse
import br.com.spring.dynamodb.util.MockUser.Companion.validUserToSave
import com.amazonaws.AmazonServiceException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserServiceTest : BaseTestRunner() {
    @MockK
    private lateinit var repository: UserRepository

    @InjectMockKs
    private lateinit var userService: UserService

    @Test
    fun `GIVEN all valid, WHEN savelastNameUser, THEN return a valid user id`() {
        val expected = saveUserResponse.copy()
        every {
            repository.save(any())
        } returns saveUserResponse

        val userSave =
            userService.saveUser(validUserToSave)
        assertEquals(expected, userSave)
        assertNotSame(expected, userSave)

        verify(exactly = 1) {
            repository.save(any())
        }
    }

    @Test
    fun `GIVEN a user, WHEN savelastNameUser, THEN return AmazonException`() {
        every {
            repository.save(any())
        } throws AmazonServiceException("")

        assertThrows<AmazonServiceException> {
            userService.saveUser(validUserToSave)
        }

        verify(exactly = 1) {
            repository.save(any())
        }
    }

    @Test
    fun `GIVEN a user, WHEN savelastNameUser, THEN return Exception`() {
        every {
            repository.save(any())
        } throws Exception("")

        assertThrows<Exception> {
            userService.saveUser(validUserToSave)
        }

        verify(exactly = 1) {
            repository.save(any())
        }
    }

    @Test
    fun `GIVEN all valid, WHEN getUser, THEN return AmazonServiceException`() {
        every {
            repository.get(any(), any())
        } throws AmazonServiceException("")

        assertThrows<AmazonServiceException> {
            userService.getUser(ID, DOCUMENT)
        }

        verify(exactly = 1) {
            repository.get(any(), any())
        }
    }

    @Test
    fun `GIVEN a user, WHEN getUser, THEN return Exception`() {
        every {
            repository.get(any(), any())
        } throws Exception("")

        assertThrows<Exception> {
            userService.getUser(ID, DOCUMENT)
        }

        verify(exactly = 1) {
            repository.get(any(), any())
        }
    }

    @Test
    fun `GIVEN all valid, WHEN getUser, THEN return NotFoundException`() {
        every {
            repository.get(any(), any())
        } throws NotFoundException("")

        assertThrows<NotFoundException> {
            userService.getUser(ID, DOCUMENT)
        }

        verify(exactly = 1) {
            repository.get(any(), any())
        }
    }

    @Test
    fun `GIVEN a valid user id, WHEN getUser, THEN return a valid User`() {
        val expected = MockUser.savedUser()
        every {
            repository.get(any(), any())
        } returns MockUser.savedUser()

        val user = MockUser.saveUserResponse.id?.let {
            userService.getUser(
                id = it, document = DOCUMENT
            )
        }

        assertEquals(expected.userId, user?.userId)
        assertNotSame(expected, user)

        verify(exactly = 1) {
            repository.get(any(), any())
        }
    }
}
