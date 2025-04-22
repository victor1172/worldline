package com.worldline.interview;

public class WidgetMachine {
    private Engine engine;

    public WidgetMachine(Engine engine) {
        this.engine = engine;
    }

    public int produceWidgets(int quantity) {
        engine.start();
        int cost = 0;

        if (engine.isRunning()) {
            cost = produce(quantity);
        }

        engine.stop();
        return cost;
    }

    private int produce(int quantity) {
        int batchSize = engine.getBatchSize();
        double costPerBatch = engine.getCostPerBatch();

        int totalProduced = 0;
        int batchCount = 0;

        while (totalProduced < quantity) {
            totalProduced += batchSize;
            batchCount++;
        }

        // 四捨五入到整數，確保返回類型為 int
        return (int) Math.round(batchCount * costPerBatch);
    }
}
