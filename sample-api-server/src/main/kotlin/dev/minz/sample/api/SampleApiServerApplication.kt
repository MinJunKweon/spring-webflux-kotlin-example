package dev.minz.sample.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SampleApiServerApplication

fun main(args: Array<String>) {
    runApplication<SampleApiServerApplication>(*args)
}
