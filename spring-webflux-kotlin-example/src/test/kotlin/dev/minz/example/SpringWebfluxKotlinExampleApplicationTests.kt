package dev.minz.example

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.concurrent.Executors

@SpringBootTest
class SpringWebfluxKotlinExampleApplicationTests {

    @Autowired
    private lateinit var webClient: WebClient

    @Test
    fun requestSingleNoDelaySuccess() {
        val response = runBlocking {
            webClient
                .get()
                .uri("/no-delay?code=200")
                .retrieve()
                .awaitBody<String>()
        }
        println(response)
    }

    /**
     * Request Non-Blocking API
     * delay : 1000ms
     * times : 1000 times
     */
    @Test
    fun request1000msDelaySuccessNonBlock1000Times() {
        val dispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        val result = runBlocking(dispatcher) {
            (0 until 1500).map {
                async {
                    webClient
                        .get()
                        .uri("/delay-nonblock?ms=1000&desc=$it")
                        .retrieve()
                        .awaitBody<String>()
                }
            }.awaitAll()
        }
        println(result)
    }

    /**
     * Request Blocking API
     * delay : 1000ms
     * times : 1000 times
     */
    @Test
    fun request1000msDelaySuccessBlock1000Times() {
        val dispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        val result = runBlocking(dispatcher) {
            (0 until 1500).map {
                async {
                    webClient
                        .get()
                        .uri("/delay-block?ms=1000&desc=$it")
                        .retrieve()
                        .awaitBody<String>()
                }
            }.awaitAll()
        }
        println(result)
    }
}
