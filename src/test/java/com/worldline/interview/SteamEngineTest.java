package com.worldline.interview;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SteamEngineTest {
    private SteamEngine engine;

    @Before
    public void setUp() {
        engine = new SteamEngine(FuelType.WOOD);
    }

    @Test
    public void testInitialState() {
        assertFalse("Engine should not be running initially", engine.isRunning());
    }

    @Test(expected = IllegalStateException.class)
    public void testStartWithoutFuel() {
        engine.start();
    }

    @Test
    public void testFillWithValidWoodFuel() {
        engine.fill(FuelType.WOOD, 50);
        assertEquals(FuelType.WOOD, engine.getFuelType());
    }

    @Test
    public void testFillWithValidCoalFuel() {
        engine = new SteamEngine(FuelType.COAL);
        engine.fill(FuelType.COAL, 50);
        assertEquals(FuelType.COAL, engine.getFuelType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFuelTypeInConstructor() {
        new SteamEngine(FuelType.PETROL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFillWithInvalidFuel() {
        engine.fill(FuelType.PETROL, 50);
    }

    @Test(expected = IllegalStateException.class)
    public void testFillWithWrongRequiredFuel() {
        // 嘗試填充與建構時指定的燃料類型不符的燃料
        engine.fill(FuelType.COAL, 50);
    }

    @Test
    public void testFillWithCorrectRequiredFuel() {
        // 填充與建構時指定的燃料類型相符的燃料
        engine.fill(FuelType.WOOD, 50);
        assertEquals(FuelType.WOOD, engine.getFuelType());
        engine.start();
        assertTrue(engine.isRunning());
    }

    @Test
    public void testFillWithOverMaxLevel() {
        engine.fill(FuelType.WOOD, 150);
        engine.fill(FuelType.WOOD, 50);
        engine.start();
        assertTrue(engine.isRunning());
    }

    @Test
    public void testStartAndStop() {
        engine.fill(FuelType.WOOD, 50);
        engine.start();
        assertTrue("Engine should be running after start", engine.isRunning());
        
        engine.stop();
        assertFalse("Engine should not be running after stop", engine.isRunning());
    }

    @Test
    public void testGetBatchSize() {
        assertEquals(2, engine.getBatchSize());
    }

    @Test
    public void testGetCostPerBatchWithWood() {
        engine.fill(FuelType.WOOD, 50);
        assertEquals(4.35, engine.getCostPerBatch(), 0.001);
    }

    @Test
    public void testGetCostPerBatchWithCoal() {
        engine.fill(FuelType.COAL, 50);
        assertEquals(5.65, engine.getCostPerBatch(), 0.001);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetCostPerBatchWithoutFuel() {
        engine.getCostPerBatch();
    }

    @Test
    public void testFillNegativeFuelLevel() {
        engine.fill(FuelType.WOOD, -10);
        try {
            engine.start();
            fail("Engine should not start with zero fuel");
        } catch (IllegalStateException e) {
            // do nothing
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testFillWithDifferentFuelType() {
        // 先填充木頭
        engine.fill(FuelType.WOOD, 50);
        // 嘗試切換到煤炭，應該拋出異常
        engine.fill(FuelType.COAL, 50);
    }

    @Test
    public void testFillWithSameFuelType() {
        // 先填充煤炭
        engine.fill(FuelType.COAL, 50);
        // 再次填充相同類型的燃料，應該成功
        engine.fill(FuelType.COAL, 30);
        // 驗證引擎可以正常啟動
        engine.start();
        assertTrue(engine.isRunning());
    }

}