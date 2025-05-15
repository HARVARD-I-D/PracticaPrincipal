package oilHexagonal.application.controller;

import oilHexagonal.domain.port.OilProvider;
import oilHexagonal.domain.port.OilStore;
import oilHexagonal.domain.model.OilPrice;
import java.util.List;

public class Controller {
    private final OilProvider oilProvider;
    private final OilStore oilStore;

    public Controller(OilProvider oilProvider, OilStore oilStore) {
        this.oilProvider = oilProvider;
        this.oilStore = oilStore;
    }

    public void run(){
        //TODO Crear TimerTask para ejecutar periodicamente
        List<OilPrice> prices = oilProvider.provide();
        for (OilPrice price : prices) {
            oilStore.save(price);
        }
    }
}
