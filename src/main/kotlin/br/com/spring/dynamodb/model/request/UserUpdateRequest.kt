package br.com.spring.dynamodb.model.request

import br.com.spring.dynamodb.model.interfaces.User
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserUpdateRequest(
    val nickName: String? = null,
    val lastName: String? = null,
    override val country: String? = null,
    override val name: String? = null,
    override val birthDate: String? = null
) : User
