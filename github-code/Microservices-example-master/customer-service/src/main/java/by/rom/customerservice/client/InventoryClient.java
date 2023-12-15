package by.rom.customerservice.client;

import by.rom.customerservice.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

    private final WebClient.Builder webClient;

    public InventoryResponse sendRequest(String nameProduct){

        return webClient.build().get()
                .uri("http://inventory-service/api/product/inventory",
                        uriBuilder -> uriBuilder.queryParam("nameProduct", nameProduct).build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    logTraceResponse(response);
                    return Mono.error(new IllegalStateException(
                            String.format("There isn't product in stock: %s, try again.", nameProduct)
                    ));
                })
                .bodyToMono(InventoryResponse.class)
                .block();
    }

    public static void logTraceResponse(ClientResponse response) {
        if (log.isTraceEnabled()) {
            response.bodyToMono(String.class)
                    .subscribe(body -> log.info("Response body: {}", body));
        }
    }
}
