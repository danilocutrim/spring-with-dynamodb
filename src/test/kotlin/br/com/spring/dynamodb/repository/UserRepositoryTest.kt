package br.com.spring.dynamodb.repository

import br.com.spring.dynamodb.BaseTestRunner
import br.com.spring.dynamodb.constants.TABLE_NAME
import br.com.spring.dynamodb.exception.NotFoundException
import br.com.spring.dynamodb.model.entity.User
import br.com.spring.dynamodb.util.DefaultValuesMock.Companion.DOCUMENT
import br.com.spring.dynamodb.util.DefaultValuesMock.Companion.ID
import br.com.spring.dynamodb.util.MockUser
import br.com.spring.dynamodb.util.MockUser.Companion.validEntityForUpdate
import br.com.spring.dynamodb.util.MockUser.Companion.validEntityToSave
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UserRepositoryTest : BaseTestRunner() {
    @MockK(relaxed = true)
    lateinit var mapper: DynamoDBMapper

    @MockK(relaxed = true)
    lateinit var dynamoDb: DynamoDB

    @InjectMockKs
    lateinit var repository: UserRepository

    @Test
    fun `GIVEN all valid atributtes, WHEN saveUser, THEN return a valid response`() {
        every {
            mapper.save(validEntityToSave)
        } returns Unit

        val result = repository.save(validEntityToSave)
        assertEquals(MockUser.saveUserResponse, result)

        verify(exactly = 1) {
            mapper.save(validEntityToSave)
        }
    }

    @Test
    fun `GIVEN all valid, WHEN findUserByName, THEN return a validUser`() {
        every {
            mapper.load(User::class.java, DOCUMENT, ID)
        } returns validEntityToSave

        val result = repository.get(DOCUMENT, ID)
        assertEquals(validEntityToSave, result)

        verify(exactly = 1) {
            mapper.load(User::class.java, DOCUMENT, ID)
        }
    }

    @Test
    fun `GIVEN all valid, WHEN  findUserByName, THEN throw NotFoundException`() {
        every {
            mapper.load(User::class.java, DOCUMENT, ID)
        } returns null

        assertThrows<NotFoundException> {
            repository.get(DOCUMENT, ID)
        }

        verify(exactly = 1) {
            mapper.load(User::class.java, DOCUMENT, ID)
        }
    }

    @Test
    fun `GIVEN all valid, when updateUser, THEN return OK `() {
        repository.update(validEntityForUpdate)

        verify {
            dynamoDb.getTable(TABLE_NAME)
        }
    }
}
