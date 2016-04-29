package rpg_simulator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Enemy {
	
	private double x, y;
	private Image image, enemyBasic, enemyArmed, enemyArmored, enemyMage, enemyBoss;
	private Map map;
	private Player player;
    private double speed = 0.003, size = 0.3;
    private long time = System.currentTimeMillis();
    private long time2 = System.currentTimeMillis();
    private int health = 100, dir = 1, dir2 = 1, damage = 3, movement;
    
    //Vihollis konstruktori
	public Enemy(Map map, Player player, double x, double y, int type) throws IOException {
		this.map = map;
		this.player = player;
		this.x = x;
		this.y = y;
		
		enemyBasic = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/enemybasic.png"));
		enemyArmed = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/enemyarmed.png"));
		enemyArmored = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/enemyarmored.png"));
		enemyMage = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/enemymage.png"));
		enemyBoss = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/enemyboss.png"));
		
		if (type <= 40) {
			image = enemyBasic; 
			damage = 3; 
			movement = ThreadLocalRandom.current().nextInt(50, 150);
		}
		if (type > 40 && type <= 70) {
			image = enemyArmed; 
			damage = 6; 
			movement = ThreadLocalRandom.current().nextInt(150, 300);
		}
		if (type > 70 && type <= 90) {
			image = enemyArmored; 
			damage = 9; 
			movement = ThreadLocalRandom.current().nextInt(300, 450);
		}
		if (type > 90) {
			image = enemyMage; 
			damage = 12; 
			movement = ThreadLocalRandom.current().nextInt(450, 600);
		}
		if (type == 0) {
			image = enemyBoss; 
			damage = 20; 
			movement = ThreadLocalRandom.current().nextInt(600, 750);
		}
	}
	
    public double getRandomX() {
        // Satunnainen X suunta
        double dirX = 0;
        if(time2 + movement <= System.currentTimeMillis()) {
            dir2 =  ThreadLocalRandom.current().nextInt(1, 3); 
            time2 = System.currentTimeMillis();
        }
		if (dir2 == 1) dirX -= 1;
		if (dir2 == 2) dirX += 1;

		if (Math.abs(player.getX()- x) < 5) {
			if (x < player.getX()) dirX++;
			if (x > player.getX()) dirX--;
		}
		return dirX;
    }
        
	public double getRandomY() {
		//Satunnainen Y suunta
	    double dirY = 0;
	    if(time + (movement) <= System.currentTimeMillis()) {
	    	dir = ThreadLocalRandom.current().nextInt(1, 3);
	    	time = System.currentTimeMillis();
	    }
		if (dir == 1) dirY -= 1;
		if (dir == 2) dirY += 1;
		
		if (Math.abs(player.getY()- y) < 5) {
			if (y < player.getY()) dirY++;
			if (y > player.getY()) dirY--;
		}
		return dirY;
	}

	public boolean move(double dirX, double dirY) {
		//Vihollisen liike		
		double newX = x + dirX;
		double newY = y + dirY;
		if (validSquare(newX, newY)) {
			x = newX;
			y = newY;
			return true;
		}
			
		return false;
	}
        public double getSpeed() {
            return speed;
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
		//Grafiikan piirrustus
		int xPos = (int) (Map.squareSize * x);
		int yPos = (int) (Map.squareSize * y);
		
		g2d.drawImage(image, (int) (xPos - 16), (int) (yPos - 16), null);
		g2d.setColor(Color.red);
		g2d.setFont(new Font("default", Font.BOLD, 12));
		g2d.drawString(health+"", (int)xPos-11, (int)yPos-23);
	}
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public void setMovement(int movement) {
		this.movement = movement;
	}
	public int getDamage() {
		return damage;
	}
	
}
