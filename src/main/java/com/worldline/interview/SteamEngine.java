package com.worldline.interview;

public class SteamEngine implements Engine {

    private boolean running;
    private int fuelLevel;
    private FuelType requiredFuelType;
    private FuelType fuelType;
    private boolean initialized = false;

    public SteamEngine(FuelType requiredFuelType) {
        if (FuelType.WOOD != requiredFuelType && FuelType.COAL != requiredFuelType) {
            throw new IllegalArgumentException("Steam engine only supports WOOD or COAL.");
        }
        
        this.requiredFuelType = requiredFuelType;
        running = false;
        fuelLevel = 0;
    }

    public void start() {
        if (fuelLevel > 0 && initialized) {
            running = true;
        } else {
            throw new IllegalStateException("Cannot start steam engine.");
        }
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void fill(FuelType fuelType, int fuelLevel) {
        if (this.requiredFuelType != fuelType) {
            throw new IllegalStateException("Engine requires " + this.requiredFuelType + " fuel, cannot use " + fuelType + " fuel.");
        }
        
        this.fuelType = fuelType;
        this.fuelLevel = Math.min(Math.max(fuelLevel, 0), 100);
        this.initialized = true;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    @Override
    public int getBatchSize() {
        return 2;
    }

    @Override
    public double getCostPerBatch() {
        if (fuelType == null) {
            throw new IllegalStateException("No fuel type set for steam engine.");
        }

        switch (fuelType) {
            case WOOD:
                return 4.35;
            case COAL:
                return 5.65;
            default:
                throw new IllegalStateException("Unsupported fuel type for steam engine.");
        }
    }
}
