package rpg_simulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Player {
	
	private static double x;
	private static double y;
	private Image heroRight, heroLeft, heroFight, heroDead, hero;
	private Map map;
	private double size = 0.3;
	private int playerMovement = 1000, dir = 2, health = 100;
	private long time = System.currentTimeMillis();
	private boolean left, right, up, down;
	private Weapon weapon;
	private Armor armor;
        
	public Player(Map map, double x, double y) throws IOException {
		
		heroRight = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/player.png"));
		heroLeft = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/player2.png"));
		heroFight = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/playerFight.png"));
		heroDead = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/playerDead.png"));
		
        armor = new Armor();        
		weapon = new Weapon();
		this.map = map;
		Player.x = x;
		Player.y = y;      
	}
	
	public void movement(ArrayList<Chest> chests) {
		//Pelaajan liikkuminen
		if(time + playerMovement-(Game.speed*15) <= System.currentTimeMillis()) {
			if (dir == 1 || dir == 2)  dir = ThreadLocalRandom.current().nextInt(3, 5); 
			else if (dir == 3 || dir == 4)  dir = ThreadLocalRandom.current().nextInt(1, 3); 
		    time = System.currentTimeMillis();
		}
		double dirX = 0;
		double dirY = 0;
		
		if (dir == 1) left = true;
		if (dir == 2) right = true;
		if (dir == 3) up = true;
		if (dir == 4) down = true;
		
		for (int i = 0; i<2; i++) {
			if (left) dirX -= 1;
			if (right) dirX += 1;
			if (up) dirY -= 1;
			if (down) dirY += 1;
			
			if ((dirX != 0) || (dirY != 0)) {
				dirX = dirX  * Game.speed * 0.003;
				dirY = dirY  * Game.speed * 0.003;
				for (Chest chest : chests) {
					if (chest.openChest(dirX + x, dirY + y)){
						chest.chestOpened();
					}
				}
				if (!move(dirX, dirY)) {
					dir = ThreadLocalRandom.current().nextInt(1, 5); 
				}
			}
		}
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public boolean move(double dirX, double dirY) {
		//Pelaajan liike
		double newX = x + dirX;
		double newY = y + dirY;
		if (validSquare(newX, newY)) {
			if (x<newX) hero = heroRight;
			else hero = heroLeft;
			x = newX;
			y = newY;
			return true;
		}
		return false;
	}
		
	public boolean validSquare(double newX, double newY) {
		// Törmäyksen tarkastus
		if (map.blocked(newX - size, newY - size)) {
			return false;
		}
		if (map.blocked(newX + size, newY - size)) {
			return false;
		}
		if (map.blocked(newX - size, newY + size)) {
			return false;
		}
		if (map.blocked(newX + size, newY + size)) {
			return false;
		}
		return true;
	}
		
	public void draw(Graphics2D g2d) {
		// Pelaaja grafiikan piirrustus
		int xPos = (int) (Map.squareSize * x);
		int yPos = (int) (Map.squareSize * y);
		g2d.drawImage(hero, (int) (xPos - 16), (int) (yPos - 16), null);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health < 0) this.health = 0;
		else this.health = health;
	}
	
	public void setPlayerMovement(int playerMovement) {
		this.playerMovement = playerMovement;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void heroDead() {
		hero = heroDead;
	}
	
	public void heroFight() {
		hero = heroFight;
	}
	
	public int getWeaponDamage() {
		return weapon.getDamage();
	}
	
	public String getWeaponName() {
		return weapon.getName();
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Color weaponColor() {
		return weapon.getWeaponColor();
	}
        public double getShield() {
		return armor.getShield();
	}
  
	public String getArmorName() {
		return armor.getName();
	}

}
