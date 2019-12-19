package de.alpharogroup.user.auth.filter

import de.alpharogroup.user.auth.enums.HeaderKeyNames
import de.alpharogroup.user.auth.filter.HttpServletRequestExtensions.getRequestBody
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.util.*
import javax.servlet.http.HttpServletRequest

object HttpServletRequestExtensions {

    @JvmStatic
    fun getBody(request: HttpServletRequest): String {
        return request.getRequestBody()
    }
    @JvmStatic
    fun getPath(request: HttpServletRequest): String {
        return request.getApplicationPath()
    }
    @JvmStatic
    fun getAuthorizationHeader(request: HttpServletRequest): Optional<String> {
        return request.getJwtToken()
    }

    fun HttpServletRequest.getRequestBody(): String = IOUtils.toString(this.inputStream, this.characterEncoding)
    fun HttpServletRequest.getJwtToken(): Optional<String> =
            if (this.getHeader(HeaderKeyNames.AUTHORIZATION) != null && this.getHeader(HeaderKeyNames.AUTHORIZATION).startsWith(HeaderKeyNames.BEARER_PREFIX)) {
                Optional.of(this.getHeader(HeaderKeyNames.AUTHORIZATION).substring(7))
            } else Optional.empty()

    fun HttpServletRequest.getApplicationPath(): String = this.getRequestURI().substring(this.getContextPath().length);
}