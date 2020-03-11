package br.com.zup.inventory.service.impl;

import br.com.zup.inventory.entity.Inventory;
import br.com.zup.inventory.repository.InventoryRepository;
import br.com.zup.inventory.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(br.com.zup.inventory.repository.InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Optional<Inventory> findById(String itemId) {
        Optional<Inventory> inventory = inventoryRepository.findById(itemId);
        return inventory;
    }
}
