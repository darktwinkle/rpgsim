package rpg_simulator;

import java.util.concurrent.ThreadLocalRandom;


public class Armor {

	private double shield;
	private String name;  
    private String[] types = {"Leather", "Iron", "Diamond"};
    private String[] modifiers = {"Worn", "Common", "Shiny"};
    private int armourRulette = ThreadLocalRandom.current().nextInt(1, 101), modifier, type;
    private int dropChance = ThreadLocalRandom.current().nextInt(1, 101);
        
	public Armor() {         
		if (armourRulette <= 40) modifier = 0;
		if (armourRulette > 40 && armourRulette <= 90) modifier = 1;
		if (armourRulette > 90 && armourRulette <= 100) modifier = 2;	
                
        if (dropChance <= 55) type = 0;
		if (dropChance > 55 && dropChance <= 90) type = 1;
		if (dropChance > 90 && dropChance <= 100) type = 2;
		
        if (type == 0) shield = 0.80;
        if (type == 1) shield = 0.60;
        if (type == 2) shield = 0.40;
                
        switch(modifier){
            case 0: shield += 0.10; break;
            case 2: shield -= 0.10; break;
            default: shield += 0;
        }       
		this.name = modifiers[modifier] + " " + types[type];       
	}

	public double getShield() {
		return shield;
	}

	public String getName() {
		return name;
	}

}
