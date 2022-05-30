package br.com.spring.dynamodb.repository

import br.com.spring.dynamodb.constants.TABLE_NAME
import br.com.spring.dynamodb.constants.USER_NOT_FOUND
import br.com.spring.dynamodb.exception.NotFoundException
import br.com.spring.dynamodb.model.entity.User
import br.com.spring.dynamodb.model.response.UserSaveResponse
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.model.ReturnValue
import mu.KotlinLogging
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val mapper: DynamoDBMapper, private val dynamoDb: DynamoDB) :
    UserRepositoryInterface<User> {

    val logger = KotlinLogging.logger { }

    override fun save(entity: User): UserSaveResponse {
        logger.debug { "save: Saving user, document: ${entity.document}" }

        mapper.save(entity)
        return UserSaveResponse(
            id = entity.userId,
            document = entity.document
        ).also {
            logger.debug { "save: Save user, document: ${entity.document}" }
        }
    }

    override fun get(document: String, id: String): User {
        return mapper.load(User::class.java, document, id) ?: throw NotFoundException(
            USER_NOT_FOUND
        )
    }

    override fun update(entity: User) {

        logger.debug {
            "update: update user," +
                " document: ${entity.document}" +
                " id: ${entity.userId}"
        }
        val updateItemSpec = UpdateItemSpec().withPrimaryKey(
            "document",
            entity.document,
            "userId",
            entity.userId
        )
            .withUpdateExpression(
                " set birthDate = :bd," +
                    " country = :ct," +
                    " lastName = :ln," +
                    " userName = :na," +
                    " nickName = :nn"
            )
            .withValueMap(
                ValueMap()
                    .withString(":bd", entity.birthDate)
                    .withString(":nn", entity.nickName)
                    .withString(":ct", entity.country)
                    .withString(":ln", entity.lastName)
                    .withString(":na", entity.name)
            )
            .withReturnValues(ReturnValue.UPDATED_NEW)
        dynamoDb.getTable(TABLE_NAME).updateItem(updateItemSpec)

        logger.debug {
            "update: updated user Success," +
                " document: ${entity.document}" +
                " id: ${entity.userId}"
        }
    }
}
