package org.baibei.keyboardtrackertgbot.services;

import org.baibei.keyboardtrackertgbot.pojo.other.RequestMethod;
import org.baibei.keyboardtrackertgbot.pojo.user.User;
import org.baibei.keyboardtrackertgbot.pojo.user.UserResponse;
import org.baibei.keyboardtrackertgbot.pojo.user.UsersResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RequestService {

    private final WebClient client;

    public RequestService(WebClient webClient) {
        this.client = webClient;
    }

    public User send(String url, RequestMethod method, UserResponse userResponse) {
        try {
            HttpMethod httpMethod =
                    switch (method) {
                        case GET -> HttpMethod.GET;
                        case POST -> HttpMethod.POST;
                        case PATCH -> HttpMethod.PATCH;
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

            return new User(Objects.requireNonNull(response.block()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<User> getAll() {
        try {
            HttpMethod httpMethod = HttpMethod.GET;

            WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(httpMethod);
            WebClient.RequestBodySpec bodySpec = uriSpec.uri("http://localhost:8080/api/users");
            WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(new UsersResponse());

            WebClient.ResponseSpec responseSpec = headersSpec.header(
                            HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                    .acceptCharset(StandardCharsets.UTF_8)
                    .ifNoneMatch("*")
                    .ifModifiedSince(ZonedDateTime.now())
                    .retrieve();

            Mono<UsersResponse> response = headersSpec.retrieve()
                    .bodyToMono(UsersResponse.class);

            UsersResponse usersResponse = Objects.requireNonNull(response.block());
            ArrayList<User> users = new ArrayList<>();

            for (UserResponse user : usersResponse.getUsers()) {
                users.add(new User(user));
            }

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
