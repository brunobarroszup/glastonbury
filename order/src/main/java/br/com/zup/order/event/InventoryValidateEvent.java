package br.com.zup.order.event;

import java.util.List;

public class InventoryValidateEvent {
    private List<String> listItemId;

    public InventoryValidateEvent(List<String> listItemId) {
        this.listItemId = listItemId;
    }

    public List<String> getListItemId() {
        return listItemId;
    }

    public void setListItemId(List<String> listItemId) {
        this.listItemId = listItemId;
    }
}
