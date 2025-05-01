package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OilProcessorTest {

    @Test
    void testOilPricesAreAdded() {
        // Preparar datos de prueba
        JsonArray testData = new JsonArray();

        JsonObject entry1 = new JsonObject();
        entry1.addProperty("date", "2023-01-01");
        entry1.addProperty("value", 85.0);
        testData.add(entry1);

        JsonObject entry2 = new JsonObject();
        entry2.addProperty("date", "2023-01-02");
        entry2.addProperty("value", 87.5);
        testData.add(entry2);

        // Procesar datos
        OilProcessor processor = new OilProcessor();
        processor.Processor(testData, OilType.Brent);

        // Obtener resultados
        ArrayList<OilPrice> prices = processor.getOilPrices();

        // Comprobar tamaño
        assertEquals(2, prices.size());

        // Comprobar contenido
        assertEquals(Instant.parse("2023-01-01T00:00:00Z"), prices.get(0).getTs());
        assertEquals(85.0, prices.get(0).getValue());
        assertEquals("Brent", prices.get(0).getType());
    }
}