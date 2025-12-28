package pl.prompthub

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PromptHubBackendApplication

fun main(args: Array<String>) {
    runApplication<PromptHubBackendApplication>(*args)
}