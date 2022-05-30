package br.com.spring.dynamodb.service

import br.com.spring.dynamodb.model.entity.User
import br.com.spring.dynamodb.model.request.UserSaveRequest
import br.com.spring.dynamodb.model.request.UserUpdateRequest
import br.com.spring.dynamodb.model.response.UserSaveResponse
import br.com.spring.dynamodb.repository.UserRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class UserService(
    val repository: UserRepository
) {
    val logger = KotlinLogging.logger {}

    fun saveUser(
        userSaveRequest: UserSaveRequest,
        document: String
    ): UserSaveResponse {
        logger.info { "saveUser: saving user, document: $document " }
        val response =
            repository.save(
                User(
                    document = document,
                    lastName = userSaveRequest.lastName,
                    country = userSaveRequest.country,
                    nickName = userSaveRequest.nickName,
                    birthDate = userSaveRequest.birthDate,
                    name = userSaveRequest.name
                )
            )
        return response.also {
            logger.info {
                "saveUser: saved user" +
                    "document: $document" +
                    "Id: ${response.id}"
            }
        }
    }

    fun getUser(id: String, document: String): User {

        logger.info { "getUser: finding user,document: $document, Id: $id" }
        return repository.get(document = document, id = id)
            .also {
                logger.info {
                    "getUser: find successfully for ," +
                        "document: $document, Id: $id"
                }
            }
    }

    fun updateUser(
        userUpdateRequest: UserUpdateRequest,
        document: String
    ) {
        logger.info {
            "updateUser: updating to document: $document," +
                " Id:${userUpdateRequest.id}"
        }

        val entityToUpdate = repository.get(document = document, id = userUpdateRequest.id)
        val user = User(
            userId = userUpdateRequest.id,
            document = document,
            nickName = userUpdateRequest.nickName ?: entityToUpdate.nickName,
            country = userUpdateRequest.country ?: entityToUpdate.country,
            lastName = userUpdateRequest.lastName ?: entityToUpdate.lastName,
            name = userUpdateRequest.name ?: entityToUpdate.name,
            birthDate = userUpdateRequest.birthDate ?: entityToUpdate.birthDate
        )
        repository.update(user)
            .also {
                logger.info {
                    "updateUser: update user with success, id: ${user.userId}"
                }
            }
    }
}
