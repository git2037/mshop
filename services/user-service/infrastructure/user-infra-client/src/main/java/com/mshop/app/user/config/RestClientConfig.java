package com.mshop.app.user.config;

import com.mshop.app.user.constant.OAuth2ClientConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {
    private static final int CONNECTION_TIMEOUT_SECONDS = 3;
    private static final int READ_TIMEOUT_SECONDS = 2;

    @Bean("workerRestClient")
    public RestClient workerRestClient(@Qualifier("workerOAuth2ClientManager") OAuth2AuthorizedClientManager authorizedClientManager) {
        return configureRestClient(authorizedClientManager);
    }

    @Bean("apiRestClient")
    public RestClient restClient(@Qualifier("restOAuth2ClientManager") OAuth2AuthorizedClientManager authorizedClientManager) {
        return configureRestClient(authorizedClientManager);
    }

    @Bean("workerOAuth2ClientManager")
    public OAuth2AuthorizedClientManager workerOAuth2ClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider provider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository,
                        authorizedClientService
                );

        manager.setAuthorizedClientProvider(provider);

        return manager;
    }

    @Bean("restOAuth2ClientManager")
    public OAuth2AuthorizedClientManager restOAuth2ClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .clientCredentials()
                        .refreshToken()
                        .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    private RestClient configureRestClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor requestInterceptor =
                new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);

        requestInterceptor.setClientRegistrationIdResolver(defaultId -> OAuth2ClientConstant.USER_SERVICE_CLIENT);

        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.defaults()
                .withConnectTimeout(Duration.ofSeconds(CONNECTION_TIMEOUT_SECONDS))
                .withReadTimeout(Duration.ofSeconds(READ_TIMEOUT_SECONDS));

        ClientHttpRequestFactory factory = ClientHttpRequestFactoryBuilder.detect()
                .build(settings);

        return RestClient
                .builder()
                .requestFactory(factory)
                .requestInterceptor(requestInterceptor)
                .build();
    }
}
