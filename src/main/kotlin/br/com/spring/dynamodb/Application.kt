package br.com.spring.dynamodb

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {

    val app = SpringApplication(Application::class.java)
    app.run(*args)
}
