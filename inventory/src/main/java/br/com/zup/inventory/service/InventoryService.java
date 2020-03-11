package br.com.zup.inventory.service;

import br.com.zup.inventory.entity.Inventory;

import java.util.Optional;

public interface InventoryService {

    Optional<Inventory> findById(String itemId);

}
