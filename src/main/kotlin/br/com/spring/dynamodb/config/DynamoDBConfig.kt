package br.com.spring.dynamodb.config

import com.amazonaws.SDKGlobalConfiguration
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import java.util.Arrays

@Configuration
class DynamoDBConfig(
    private val env: Environment,
    @Value("\${aws.dynamo.endpoint}")
    private val endpoint: String,

    @Value("\${aws.accesskey}")
    private val accessKey: String,

    @Value("\${aws.secretkey}")
    private val secretKey: String,

    @Value("\${aws.region}")
    private val region: String
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    fun mapper(): DynamoDBMapper {
        return DynamoDBMapper(dynamoDBConfiguration())
    }

    @Bean
    fun dynamoDb(): DynamoDB {
        return DynamoDB(dynamoDBConfiguration())
    }

    fun dynamoDBConfiguration(): AmazonDynamoDB {
        if (env.activeProfiles.contains("dev")) {
            logger.info { "Amazon DYNAMODB running with profile = ${Arrays.toString(env.activeProfiles)}" }

            System.setProperty(SDKGlobalConfiguration.DISABLE_CERT_CHECKING_SYSTEM_PROPERTY, "true")

            return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
                .build()
        }

        logger.info { "Amazon DYNAMODB running with profile = ${Arrays.toString(env.activeProfiles)}" }
        return AmazonDynamoDBClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey)))
            .withRegion(region)
            .build()
    }
}
