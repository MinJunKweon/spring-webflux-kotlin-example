package dev.minz.example.webflux

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfiguration {

    @Bean
    fun webClient(): WebClient {
        val httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(3))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1500)
            .doOnConnected {
                it.addHandlerLast(ReadTimeoutHandler(2000, TimeUnit.MILLISECONDS))
                it.addHandlerLast(WriteTimeoutHandler(2500, TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }
}
