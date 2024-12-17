package org.baibei.keyboardtrackerdesktop.services;

import org.baibei.keyboardtrackerdesktop.pojo.other.RequestMethod;
import org.baibei.keyboardtrackerdesktop.pojo.user.User;
import org.baibei.keyboardtrackerdesktop.pojo.user.UserResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

@Service
public class RequestService {

    private final WebClient client;
    private String standardToken;

    public RequestService(WebClient webClient) {
        this.client = webClient;
    }

    public User send(String url, RequestMethod method, UserResponse userResponse) throws Exception {
        HttpMethod httpMethod =
                switch (method) {
            case GET -> HttpMethod.GET;
            case POST -> HttpMethod.POST;
            case PATCH -> HttpMethod.PATCH;
            case PUT -> HttpMethod.PUT;
            case DELETE -> HttpMethod.DELETE;
        };

        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(httpMethod);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(url);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(userResponse);

        WebClient.ResponseSpec responseSpec = headersSpec.header(
                HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve();

        Mono<UserResponse> response = headersSpec.retrieve()
                .bodyToMono(UserResponse.class);

        UserResponse userResponse1 = response.block();

        User user = new User();
        user.setUsername(userResponse1.getUsername());
        user.setPasswordS(userResponse1.getPassword());
        user.setKeys(userResponse1.getKeys());
        user.setWords(userResponse1.getWords());

        return user;
    }

    public void setStandardToken(String token) {
        this.standardToken = token;
    }

    public String getStandardToken() {
        return standardToken;
    }
}
