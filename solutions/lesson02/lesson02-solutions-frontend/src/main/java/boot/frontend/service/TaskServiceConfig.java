package boot.frontend.service;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.*;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class TaskServiceConfig {

    // tag::restTemplate[]
    @Bean
    RestTemplate restTemplate() {
        var httpClient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }
    // end::restTemplate[]

    private CloseableHttpClient clientAcceptingAnyValidCertificate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // @formatter:off
        // tag::sslany
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                                .setSslContext(SSLContextBuilder.create()
                                        .build())
                                .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                                .build())
                        .build())
                .build();
        // end::sslany
        // @formatter:on
        return httpClient;
    }

    private CloseableHttpClient sslClientNoHostVerification() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        // @formatter:off
        // tag::sslnohost[]
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                                .setSslContext(SSLContextBuilder.create()
                                        .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                                        .build())
                                .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                                .build())
                        .build())
                .build();
        // end::sslnohost[]
        // @formatter:on
        return httpClient;
    }

    private CloseableHttpClient sslClientTrustAll()
            throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        // @formatter:off
        // tag::ssltrustall
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustAllStrategy()).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                                .setSslContext(SSLContextBuilder.create()
                                        .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                                        .build())
                                .build())
                        .build())
                .build();
        // end::ssltrustall
        // @formatter:on
        return httpClient;
    }

    private CloseableHttpClient sslClientTrustSelfSigned() throws KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, CertificateException, IOException {
        // @formatter:off
        // tag::sslselfsigned[]
        KeyStore keyStore = KeyStore.getInstance(new File("mykeystore.jks"), "secret".toCharArray());
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                                .setSslContext(sslContext)
                                .build())
                        .build()).build();
        // end::sslselfsigned[]
        // @formatter:on
        return httpClient;
    }

    private CloseableHttpClient sslClientCustomSocketFactory() throws KeyManagementException, NoSuchAlgorithmException,
            KeyStoreException, CertificateException, IOException {
        // @formatter:off
        // tag::sslsf
        KeyStore keyStore = KeyStore.getInstance(new File("mykeystore.jks"), "secret".toCharArray());
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslContext,
                new String[] { "TLSv1" },
                null,
                new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(sslsf)
                        .build())
                .build();
        // end::sslsf
        // @formatter:on
        return httpClient;
    }
}
