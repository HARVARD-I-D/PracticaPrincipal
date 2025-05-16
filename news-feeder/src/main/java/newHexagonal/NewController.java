package newHexagonal;

import java.util.List;

public class NewController {
    private final NewProvider newProvider;
    private final NewStore newStore;

    public NewController(NewProvider newProvider, NewStore newStore) {
        this.newProvider = newProvider;
        this.newStore = newStore;
    }

    /*
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
    timer.schedule(task,100000);

     */
    public void run() {
        List<New> news = newProvider.provide();
        for (New aNew : news){
            newStore.save(aNew);
        }
    }
}
