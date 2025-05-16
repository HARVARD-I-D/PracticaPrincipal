package oilFeeder.application.controller;

import oilFeeder.infrastructure.port.OilProvider;
import oilFeeder.infrastructure.port.OilStore;
import oilFeeder.application.domain.model.OilPrice;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    private final OilProvider oilProvider;
    private final OilStore oilStore;
    private final Timer timer;

    public Controller(OilProvider oilProvider, OilStore oilStore) {
        this.oilProvider = oilProvider;
        this.oilStore = oilStore;
        this.timer = new Timer(true);
    }

    public void run(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<OilPrice> prices = oilProvider.provide();
                System.out.println("Precios obtenidos: " + prices.size());
                for (OilPrice price : prices) {
                    System.out.println("Dato recibido: " + price.getTs() + ", " + price.getValue());
                    oilStore.save(price);
                }
            }
        };
        long delay = 0;
        long period = 1000 * 60 * 60;

        timer.scheduleAtFixedRate(task, delay, period);
        System.out.println("Controller iniciado: ejecución cada 1 hora.");
    }

    public void stop() {
        timer.cancel();
    }
}
