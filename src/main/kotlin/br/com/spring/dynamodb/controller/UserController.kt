package br.com.spring.dynamodb.controller

import br.com.spring.dynamodb.model.entity.User
import br.com.spring.dynamodb.model.request.UserSaveRequest
import br.com.spring.dynamodb.model.request.UserUpdateRequest
import br.com.spring.dynamodb.model.response.UserSaveResponse
import br.com.spring.dynamodb.service.UserService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/v1/user")
class UserController(
    private val userService: UserService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    fun saveUser(
        @RequestBody userSaveRequest: UserSaveRequest,
        @RequestHeader("document") document: String
    ): UserSaveResponse {
        logger.info { "saveUser: saving User " }
        val response = userService.saveUser(
            userSaveRequest = userSaveRequest
        )
        return response.also {
            logger.info {
                "saveUser: saved user," +
                    " document: $document ,Id: ${response.id}"
            }
        }
    }

    @GetMapping
    fun getUser(
        @RequestParam("id") @NotBlank id: String,
        @RequestParam("document") document: String
    ): User {
        logger.info { "getUser: finding user by document: $document Id: $id" }
        return userService.getUser(
            document = document,
            id = id,
        ).also {
            logger.info {
                "getUser: find successfully to " +
                    "document: $document Id: $id"
            }
        }
    }

    @PutMapping("/{userId}")
    fun updateUser(
        @Valid @RequestBody userUpdate: UserUpdateRequest,
        @PathVariable userId: String,
        @NotBlank @RequestHeader("document") document: String
    ) {
        logger.info {
            "updateUser: updating user to document: $document, Id:" +
                userId
        }
        userService.updateUser(
            userUpdateRequest = userUpdate,
            document = document,
            userId = userId
        ).also {
            logger.info {
                "updateUser: updated user to document: $document, Id:" +
                    userId
            }
        }
    }
}
