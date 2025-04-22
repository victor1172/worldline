package com.worldline.interview;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WidgetMachineTest {
    private Engine mockEngine;
    private WidgetMachine machine;

    @Before
    public void setUp() {
        // 創建一個簡單的模擬引擎用於測試
        mockEngine = new Engine() {
            private boolean running = false;
            private FuelType fuelType = FuelType.PETROL;
            
            @Override
            public void start() { running = true; }
            
            @Override
            public void stop() { running = false; }
            
            @Override
            public boolean isRunning() { return running; }
            
            @Override
            public void fill(FuelType fuelType, int level) {
                this.fuelType = fuelType;
            }
            
            @Override
            public FuelType getFuelType() { return fuelType; }
            
            @Override
            public int getBatchSize() { return 8; }
            
            @Override
            public double getCostPerBatch() { return 9.0; }
        };
        
        machine = new WidgetMachine(mockEngine);
    }

    @Test
    public void testProduceExactBatchSize() {
        // 測試生產剛好一個批次的數量
        int cost = machine.produceWidgets(8);
        assertEquals(9, cost); // 1個批次 * 9.0成本 = 9
    }

    @Test
    public void testProduceLessThanBatchSize() {
        // 測試生產少於一個批次的數量
        int cost = machine.produceWidgets(3);
        assertEquals(9, cost); // 仍需要1個完整批次
    }

    @Test
    public void testProduceMultipleBatches() {
        // 測試生產多個批次的數量
        int cost = machine.produceWidgets(12);
        assertEquals(18, cost); // 需要2個批次 * 9.0成本 = 18
    }

    @Test
    public void testProduceZeroQuantity() {
        // 測試生產0個產品
        int cost = machine.produceWidgets(0);
        assertEquals(0, cost);
    }

    @Test(expected = IllegalStateException.class)
    public void testProduceWithEngineFailure() {
        // 創建一個會在啟動時拋出異常的引擎
        Engine failingEngine = new Engine() {
            @Override
            public void start() { throw new IllegalStateException("Engine failed to start"); }
            @Override
            public void stop() {}
            @Override
            public boolean isRunning() { return false; }
            @Override
            public void fill(FuelType fuelType, int level) {}
            @Override
            public FuelType getFuelType() { return FuelType.PETROL; }
            @Override
            public int getBatchSize() { return 8; }
            @Override
            public double getCostPerBatch() { return 9.0; }
        };
        
        WidgetMachine failingMachine = new WidgetMachine(failingEngine);
        failingMachine.produceWidgets(5); // 應該拋出異常
    }
}