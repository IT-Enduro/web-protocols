package ru.romanow.protocols.restful.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.util.*
import kotlin.text.Charsets.UTF_8

@Component
class LoggingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestWrapper = ContentCachingRequestWrapper(request)
        val responseWrapper = ContentCachingResponseWrapper(response)
        try {
            val requestUid = UUID.randomUUID().toString().substring(24)
            val requestBody = requestWrapper.contentAsByteArray.let { if (it.isNotEmpty()) it else "empty" }
            logger.debug(
                "Request (id: $requestUid) ${requestWrapper.method} '${requestWrapper.requestURI}' " +
                    "payload: [$requestBody]"
            )

            filterChain.doFilter(requestWrapper, responseWrapper)

            val responseBody =
                String(responseWrapper.contentAsByteArray, UTF_8).ifEmpty { "empty" }
            logger.debug(
                "Response (id: $requestUid) ${requestWrapper.method} '${requestWrapper.requestURI}' " +
                    "payload: [$responseBody]"
            )
        } finally {
            responseWrapper.copyBodyToResponse()
        }
    }
}
