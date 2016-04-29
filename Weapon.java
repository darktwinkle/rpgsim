package rpg_simulator;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon {

	private int damage;
	private String[] types = {"Sword", "Axe", "Dagger", "Spear"};
	private String type;
	private String name;
	private String[] modifiers = {"Rusty", "Common", "Sharp", "Flaming", "Master"};
	private int dropChance = ThreadLocalRandom.current().nextInt(1, 101), modifier;

	
	public Weapon(int damage) {
		this.damage = damage;

	}
	
	public Weapon() {
		modifier = 1;
		if (dropChance <= 20) modifier = 0;
		if (dropChance > 20 && dropChance <= 50) modifier = 1;
		if (dropChance > 50 && dropChance <= 75) modifier = 2;
		if (dropChance > 75 && dropChance <= 90) modifier = 3;
		if (dropChance > 90) modifier = 4;
		this.type = types[ThreadLocalRandom.current().nextInt(0, 4)];
		this.name = modifiers[modifier] + " " + type;
		modifier++;
		if (type.equals("Sword")) this.damage = 10 * modifier;
		if (type.equals("Axe")) this.damage = 6 * modifier;
		if (type.equals("Dagger")) this.damage = 4 * modifier;
		if (type.equals("Spear")) this.damage = 8 * modifier;
	}

	public int getDamage() {
		return damage;
	}

	public String getName() {
		return name;
	}
	
	public Color getWeaponColor() {	
		if (modifier == 2) return Color.green;
		if (modifier == 3) return Color.blue;
		if (modifier == 4) return Color.magenta;
		if (modifier == 5) return Color.yellow;
		else return Color.white;
	}
}
