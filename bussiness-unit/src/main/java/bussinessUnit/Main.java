package bussinessUnit;


import bussinessUnit.application.Controller;
import bussinessUnit.infrastructure.adapter.broker.BrokerEventService;
import bussinessUnit.infrastructure.adapter.datamart.SQLiteDatamartRepository;
import bussinessUnit.infrastructure.adapter.eventStore.HistoricsAccessor;

public class Main {
    public static void main(String args[]){
        SQLiteDatamartRepository datamartRepository = new SQLiteDatamartRepository();
        BrokerEventService brokerEventService = new BrokerEventService();
        HistoricsAccessor historicsAccessor = new HistoricsAccessor();

        Controller controller = new Controller(datamartRepository, historicsAccessor, brokerEventService);
        controller.loadAndStoreHistoricData();
        controller.runLiveBroker();

        //TODO Crear el CLI o el GUI según de tiempo
    }
}
