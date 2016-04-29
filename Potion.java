package rpg_simulator;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Potion {

	private String name;
    private int heal;
    private final int dropChance = ThreadLocalRandom.current().nextInt(1, 101);
    HashMap <Integer, String> potions = new HashMap<Integer, String>() {{put(5, "Apple"); put(15, "Beer"); put(60, "Life Elixir");}};
        
	public Potion() {
        if (dropChance <= 55) {
            name = potions.get(5); 
            heal = 5; 
            }
		if (dropChance > 55 && dropChance <= 85) {
            name = potions.get(15);
            heal = 15; 
                    }
		if (dropChance > 85 && dropChance <= 100) {
            name = potions.get(60);
            heal = 60; }    
        }

	public String getName() {
		return name;
	}
	public int getHeal() {
		return heal;
	}
}
