package br.com.zup.inventory.event;

import java.io.Serializable;
import java.util.List;

public class InventoryValidateEvent implements Serializable {

    private static final long serialVersionUID = -300314529458086552L;
    private List<String> listItemId;

    public InventoryValidateEvent(List<String> listItemId) {
        this.listItemId = listItemId;
    }

    public InventoryValidateEvent() {
    }

    public List<String> getListItemId() {
        return listItemId;
    }

    public void setListItemId(List<String> listItemId) {
        this.listItemId = listItemId;
    }
}
