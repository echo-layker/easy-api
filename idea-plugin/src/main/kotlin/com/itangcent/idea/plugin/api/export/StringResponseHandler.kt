package com.itangcent.idea.plugin.api.export

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpResponseException
import org.apache.http.client.ResponseHandler
import org.apache.http.util.EntityUtils
import java.io.IOException

class StringResponseHandler : ResponseHandler<String> {

    @Throws(IOException::class)
    fun handleEntity(entity: HttpEntity): String {
        return EntityUtils.toString(entity) ?: ""
    }

    @Throws(HttpResponseException::class, IOException::class)
    override fun handleResponse(response: HttpResponse): String? {
        val statusLine = response.statusLine
        val entity = response.entity

        return try {
            if (entity == null) null else this.handleEntity(entity)
        } catch (e: Exception) {
            if (statusLine.statusCode >= 300) {
                EntityUtils.consume(entity)
                throw HttpResponseException(statusLine.statusCode, statusLine.reasonPhrase)
            }
            "empty response"
        }
    }
}
