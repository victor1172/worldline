package com.worldline;

import com.worldline.interview.Engine;
import com.worldline.interview.FuelType;
import com.worldline.interview.InternalCombustionEngine;
import com.worldline.interview.SteamEngine;
import com.worldline.interview.WidgetMachine;

public class WorldLine {
    public static void main(String[] args) {
        // Use InterfaceEngine and WidgetMachine
        Engine petrolEngine = new InternalCombustionEngine(FuelType.PETROL);
        petrolEngine.fill(FuelType.PETROL,50);
        WidgetMachine wMachine = new WidgetMachine(petrolEngine);
        int costPetrol = wMachine.produceWidgets(24);
        System.out.println("Cost: " + costPetrol);
        
        // Use InterfaceEngine and WidgetMachine
        Engine steamEngine = new SteamEngine(FuelType.WOOD);
        steamEngine.fill(FuelType.WOOD,80);
        WidgetMachine sMachine = new WidgetMachine(steamEngine);
        int costSteam = sMachine.produceWidgets(5);
        System.out.println("Cost: " + costSteam);

        // You can add your main logic here
        // For example: initialize objects, call methods, etc.
    }
}