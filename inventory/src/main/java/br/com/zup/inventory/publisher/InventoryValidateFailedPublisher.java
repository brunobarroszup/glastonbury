package br.com.zup.inventory.publisher;

import br.com.zup.inventory.event.InventoryValidateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryValidateFailedPublisher {
    @Autowired
    private final KafkaTemplate<String, InventoryValidateEvent> kafkaTemplate;

    public InventoryValidateFailedPublisher(KafkaTemplate<String, InventoryValidateEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(InventoryValidateEvent message){
        kafkaTemplate.send("inventory-validate-failed", message);
    }
}
