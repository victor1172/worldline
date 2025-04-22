package com.worldline.interview;

public class InternalCombustionEngine implements Engine {

    private boolean running;
    private int fuelLevel;
    private FuelType requiredFuelType;
    private FuelType fuelType;
    private boolean initialized = false;

    public InternalCombustionEngine(FuelType requiredFuelType) {
        if (FuelType.PETROL != requiredFuelType && FuelType.DIESEL != requiredFuelType ) {
            throw new IllegalArgumentException("Steam engine only supports PETROL or DIESEL.");
        }

        this.requiredFuelType = requiredFuelType;
        running = false;
        fuelLevel = 0;
    }

    public void start() {
        if (fuelLevel > 0 && initialized) {
            running = true;
        } else {
            throw new IllegalStateException("Not able to start engine.");
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
        return  requiredFuelType;
    }

    @Override
    public int getBatchSize() {
        return 8;
    }

    @Override
    public double getCostPerBatch() {
        if (fuelType == null) {
            throw new IllegalStateException("No fuel type set for internal combustion engine.");
        }
        
        switch (fuelType) {
            case PETROL:
                return 9.00;
            case DIESEL:
                return 12.00;
            default:
                throw new IllegalStateException("Unsupported fuel type for internal combustion engine.");
        }
        
    }

}
