package br.com.spring.dynamodb.repository

import br.com.spring.dynamodb.model.entity.User
import br.com.spring.dynamodb.model.response.UserSaveResponse

interface UserRepositoryInterface<T : User> {
    fun save(entity: T): UserSaveResponse
    fun get(document: String, id: String): T
    fun update(entity: T)
}
