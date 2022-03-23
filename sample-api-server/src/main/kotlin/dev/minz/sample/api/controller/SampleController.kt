package dev.minz.sample.api.controller

import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.NumberFormat
import java.util.Locale

@RestController
class SampleController {
    companion object {
        private val log = LoggerFactory.getLogger(SampleController::class.java)
    }

    @GetMapping("/no-delay")
    fun noDelayRequest(
        @RequestParam("code") code: Int?,
        @RequestParam("desc") desc: String?,
    ): ResponseEntity<String> {
        val statusCode = code ?: 200
        log.info("no-delay :: $code")
        return ResponseEntity.status(statusCode).body("$statusCode ($desc)")
    }

    @GetMapping("/delay-nonblock")
    suspend fun delayNonBlock(
        @RequestParam("ms") ms: Long?,
        @RequestParam("desc") desc: String?,
    ): String {
        val delayMillis = ms ?: 0L
        delay(delayMillis)
        log.info("delay-nonblock :: $ms ms ($desc)")
        return "${NumberFormat.getNumberInstance(Locale.US).format(delayMillis)}ms delayed. ($desc)"
    }

    @GetMapping("/delay-block")
    fun delayBlock(
        @RequestParam("ms") ms: Long?,
        @RequestParam("desc") desc: String?,
    ): String {
        val delayMillis = ms ?: 0L
        Thread.sleep(delayMillis)
        log.info("delay-block :: $ms ms ($desc)")
        return "${NumberFormat.getNumberInstance(Locale.US).format(delayMillis)}ms delayed. ($desc)"
    }
}
