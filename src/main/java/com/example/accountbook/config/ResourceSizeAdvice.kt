package com.example.accountbook.config

import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice


@ControllerAdvice
class ResourceSizeAdvice : ResponseBodyAdvice<Collection<*>> {

    val logger = LoggerFactory.getLogger(ResourceSizeAdvice::class.java)

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {

        return Collection::class.java.isAssignableFrom(returnType.parameterType)
    }

    override fun beforeBodyWrite(
        body: Collection<*>?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Collection<*>? {

//        logger.info("Resource size: ${body?.size}")

//        response.headers.add("X-Total-Count", body?.size.toString())
        return body
    }
}
