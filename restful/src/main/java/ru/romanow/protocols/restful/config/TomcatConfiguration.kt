package ru.romanow.protocols.restful.config

import org.apache.catalina.connector.Connector
import org.apache.coyote.http11.Http11NioProtocol
import org.apache.tomcat.util.net.SSLHostConfig
import org.apache.tomcat.util.net.SSLHostConfigCertificate
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.ResourceUtils


@Configuration
@EnableConfigurationProperties(HttpsProperties::class)
@ConditionalOnProperty("server.https.enabled", havingValue = "true")
class TomcatConfiguration(
    private val httpsProperties: HttpsProperties
) : WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    override fun customize(factory: TomcatServletWebServerFactory) {
        factory.addAdditionalTomcatConnectors(createSslConnector())
    }

    @Bean
    fun createSslConnector(): Connector {
        val connector = Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL)
        val protocol = connector.protocolHandler as Http11NioProtocol
        connector.secure = true
        protocol.isSSLEnabled = true
        connector.scheme = "https"
        connector.port = httpsProperties.port!!

        val sslConfig = SSLHostConfig().also {
            it.hostName = protocol.defaultSSLHostConfigName
            it.sslProtocol = "TLS"
        }
        httpsProperties.ciphers?.let { sslConfig.ciphers = httpsProperties.ciphers }
        protocol.addSslHostConfig(sslConfig)

        val certificate = SSLHostConfigCertificate(sslConfig, SSLHostConfigCertificate.Type.UNDEFINED)
        certificate.certificateKeystoreFile = ResourceUtils.getURL(httpsProperties.keyStore!!).toString()
        certificate.certificateKeystorePassword = httpsProperties.keyStorePassword

        sslConfig.addCertificate(certificate)

        return connector
    }
}
