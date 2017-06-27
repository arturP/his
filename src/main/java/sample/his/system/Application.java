package sample.his.system;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;
import sample.his.system.client.HolidayClient;
import sample.his.system.client.HolidayClientInterface;
import org.apache.http.client.HttpClient;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import javax.net.ssl.SSLContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;


@SpringBootApplication
@PropertySource("classpath:holidayClient.properties")
public class Application {

    @Value("${address}")
    private String address = "https://abc.com";
    @Value("${apiKey}")
    private String apiKey = "XYZ-ABC-1234";

    private final String KEY_STORE_PATH = "/jssecacerts";
    private final String KEYSTORE_PASSWORD = "changeit";
    private final String SUPPORTED_PROTOCOL = "TLSv1.2";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public HolidayClientInterface restOperations(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new HolidayClient(address, apiKey, new RestTemplate(clientHttpRequestFactory));
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(HttpClient httpClient) {
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    public HttpClient httpClient() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream inStream = new FileInputStream(new File(this.getClass().getResource(KEY_STORE_PATH).toURI()));
        try {
            keyStore.load(inStream, KEYSTORE_PASSWORD.toCharArray());
        } finally {
            inStream.close();
        }

        SSLContext sslcontext =
                SSLContexts.custom().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy()).build();
        SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslcontext, new String[]{SUPPORTED_PROTOCOL}, null,
                        new NoopHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
