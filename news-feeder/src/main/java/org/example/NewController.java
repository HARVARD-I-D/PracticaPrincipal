package org.example;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewController {
    private final NewProvider newProvider;
    private final NewStore newStore;

    public NewController(NewProvider newProvider, NewStore newStore) {
        this.newProvider = newProvider;
        this.newStore = newStore;
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            List<New> news = newProvider.provide();
            for (New aNew : news){
                newStore.save(aNew);
            }
        }
    };
    timer.schedule(task,);

}
