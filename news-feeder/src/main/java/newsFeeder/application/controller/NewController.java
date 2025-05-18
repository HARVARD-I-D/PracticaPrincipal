package newsFeeder.application.controller;

import newsFeeder.infrastructure.port.NewProvider;
import newsFeeder.infrastructure.port.NewStore;
import newsFeeder.application.domain.New;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewController {
    private final NewProvider newProvider;
    private final NewStore newStore;
    private final Timer timer;

    public NewController(NewProvider newProvider, NewStore newStore) {
        this.newProvider = newProvider;
        this.newStore = newStore;
        this.timer = new Timer(true);
    }

    public void run() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run(){
                List<New> news = newProvider.provide();
                for (New aNew : news) {
                    newStore.save(aNew);
                }
            }

        };
        long delay = 0;
        long period = 1000 * 60 * 60;

        timer.scheduleAtFixedRate(timerTask, delay, period);
        System.out.println("NewController iniciado: ejecución cada 1 hora.");
    }

    public void stop() { timer.cancel(); }
}
