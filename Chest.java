package rpg_simulator;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Chest {
	
	private int x;
	private int y;
	private Image chestSprite, chestSprite2;
	private Player player;
	private boolean closed = true;
	//private Armor armor;
	private int randomLoot = ThreadLocalRandom.current().nextInt(1, 3);
	
	public Chest(Player player, int x, int y) {
		this.player = player;
		this.x = x;
		this.y = y;
		
		// Arkkukuvien lataus
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/chest1.png");
			chestSprite = ImageIO.read(url);
		} catch (IOException e) {}
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/chest2.png");
			chestSprite2 = ImageIO.read(url);
		} catch (IOException e) {}

	}
	//Kuvan vaihto jos arkku avattu
	public void chestOpened() {
		chestSprite = chestSprite2;
		if (closed) {
			closed = false;
			if (randomLoot ==1) {
                            Potion potion = new Potion();
				player.setHealth(player.getHealth()+potion.getHeal());
				if (player.getHealth() > 100) player.setHealth(100);
			} else {
				player.setWeapon(new Weapon());
			}
		}
		
	}
		// Arkkukuvan piirto
	public void draw(Graphics2D g2d) {
		g2d.drawImage(chestSprite,x*32,y*32,null);
	}
	
	public boolean openChest(double playerX, double playerY) {
		if ((int)(playerX -0.3) == (int)x && (int)(playerY - 0.3) == (int)y) {
			return true;
		}
		if ((int)(playerX + 0.3) == (int)x && (int)(playerY - 0.3) == (int)y) {
			return true;
		}
		if ((int)(playerX - 0.3) == (int)x && (int)(playerY + 0.3) == (int)y) {
			return true;
		}
		if ((int)(playerX + 0.3) == (int)x && (int)(playerY + 0.3) == (int)y) {
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
}
