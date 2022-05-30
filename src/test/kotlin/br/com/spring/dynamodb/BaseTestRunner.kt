package br.com.spring.dynamodb

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ExtendWith(
    MockKExtension::class
)
abstract class BaseTestRunner
