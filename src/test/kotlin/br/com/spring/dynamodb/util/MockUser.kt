package br.com.spring.dynamodb.util

import br.com.spring.dynamodb.model.entity.User
import br.com.spring.dynamodb.model.request.UserSaveRequest
import br.com.spring.dynamodb.model.response.UserSaveResponse

class MockUser {
    companion object {

        val validUserToSave = UserSaveRequest(
            lastName = "42342qqqdjdjd",
            birthDate = "Approved",
            nickName = "500",
            country = "Santo André",
            name = "umNomer"
        )

        val saveUserResponse = UserSaveResponse(
            id = "2e2b858b-efa3-49ae-9079-b730bd6deb03",
            "23444"
        )

        fun savedUser() = User(
            userId = "2e2b858b-efa3-49ae-9079-b730bd6deb03",
            document = "23444",
            lastName = "42342qqqdjdjd",
            birthDate = null,
            nickName = null,
            country = null
        )

        val validEntityToSave = User(
            userId = "2e2b858b-efa3-49ae-9079-b730bd6deb03",
            document = "23444",
            lastName = "42342qqqdjdjd",
            nickName = null,
            country = null
        )

        val validEntityForUpdate = User(
            userId = "2e2b858b-efa3-49ae-9079-b730bd6deb03",
            document = "23444",
            lastName = "42342qqqdjdjd",
            birthDate = "Approved",
            nickName = "500",
            country = "errorMessage"
        )
    }
}
