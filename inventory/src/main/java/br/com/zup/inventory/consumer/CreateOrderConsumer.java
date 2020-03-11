package br.com.zup.inventory.consumer;

import br.com.zup.inventory.entity.Inventory;
import br.com.zup.inventory.event.InventoryValidateEvent;
import br.com.zup.inventory.event.OrderCreatedEvent;
import br.com.zup.inventory.publisher.InventoryValidateFailedPublisher;
import br.com.zup.inventory.publisher.InventoryValidateSuccessPublisher;
import br.com.zup.inventory.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class CreateOrderConsumer {
    private final ObjectMapper objectMapper;
    private final InventoryService inventoryService;
    private final InventoryValidateSuccessPublisher inventoryValidateSuccessPublisher;
    private final InventoryValidateFailedPublisher inventoryValidateFailedPublisher;

    public CreateOrderConsumer(ObjectMapper objectMapper, InventoryService inventoryService, InventoryValidateSuccessPublisher inventoryValidateSuccessPublisher, InventoryValidateFailedPublisher inventoryValidateFailedPublisher) {
        this.objectMapper = objectMapper;
        this.inventoryService = inventoryService;
        this.inventoryValidateSuccessPublisher = inventoryValidateSuccessPublisher;
        this.inventoryValidateFailedPublisher = inventoryValidateFailedPublisher;
    }

    @KafkaListener(topics = "created-orders", groupId = "inventory-group-id")
    public void listen(String message) throws IOException {
        OrderCreatedEvent event = this.objectMapper.readValue(message, OrderCreatedEvent.class);
        System.out.println("Received event: " + event.getCustomerId());

        InventoryValidateEvent inventoryValidateEvent = new InventoryValidateEvent(event.getItemIds());

        if (!validateMessage(event)) {
            inventoryValidateFailedPublisher.send(inventoryValidateEvent);
        } else {
            inventoryValidateSuccessPublisher.send(inventoryValidateEvent);
        }
    }

    private boolean validateMessage(OrderCreatedEvent event) {

        if (!(event.getItemIds().size() > 0)) {
            return false;
        } else if (event.getCustomerId().isEmpty()) {
            return false;
        } else if (event.getOrderId().isEmpty()) {
            return false;
        }
        for (String itemId : event.getItemIds()
        ) {
            Optional<Inventory> inventory = inventoryService.findById(itemId);
            if (!inventory.isPresent()) {
                return false;
            } else {
                if (inventory.get().getAmount().compareTo(event.getAmount()) == 2) {
                    return false;
                }
            }
        }
        return true;
    }
}
