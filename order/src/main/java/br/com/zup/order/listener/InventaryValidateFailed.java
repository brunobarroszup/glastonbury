package br.com.zup.order.listener;

import br.com.zup.order.event.InventoryValidateEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.IOException;

@Configuration
public class InventaryValidateFailed {

    private String bootstrap;
    private ObjectMapper objectMapper;

    public InventaryValidateFailed(@Value(value = "${spring.kafka.bootstrap-servers}") String bootstrap,
                                   ObjectMapper objectMapper) {
        this.bootstrap = bootstrap;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "inventory-validate-failed", groupId = "payment-group-id")
    public void listen(String message) throws IOException {
        InventoryValidateEvent event = this.objectMapper.readValue(message, InventoryValidateEvent.class);
        System.out.println("Received event: chegou");

    }

}
