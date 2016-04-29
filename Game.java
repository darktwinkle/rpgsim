package rpg_simulator;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;

public class Game{

	private Map map;
	private Player player;
	private SoundPlayer audio;
	private Graphical graphics;
	private ArrayList<Enemy> enemies=new ArrayList<Enemy>();
	private ArrayList<Enemy> killed=new ArrayList<Enemy>();
	private ArrayList<Chest> chests=new ArrayList<Chest>();
	private long time = System.currentTimeMillis(), time2 = time, time3 = time;
	private boolean bossSpawned= false;
	static boolean gameOver = false, musicOn;
	private int enemyCount;
	static double speed = 5;

	public Game(int scaler, int width, int height, int trees, int enemyCount, int chestCount, 
			int screenWidth, boolean musicOn, boolean gameMode) throws IOException {

		Game.musicOn = musicOn;
		this.enemyCount = enemyCount;
		if (gameMode) { width*= 4; height*=4;}
		
		//Olioiden luonti
		
		audio = new SoundPlayer();
		map = new Map(width*scaler,height*scaler,chestCount, trees);			
		player = new Player(map, 1.5, 1.5);		
		for (int x=0;x<map.getWidth();x++) {
			for (int y=0;y<map.getHeight();y++) {
				if (map.data[x][y] == Map.chestSquare) {
					Chest chest = new Chest(player,x,y);
					chests.add(chest);
				}	
			}
		}	
		int randX = 0;
		int randY = 0;
		int randType = 0;
		for (int i=0;i<=enemyCount-1;i++) {
			do {
				randX = ThreadLocalRandom.current().nextInt(2, map.getWidth()-2);
				randY = ThreadLocalRandom.current().nextInt(2, map.getHeight()-2);
				randType = ThreadLocalRandom.current().nextInt(1, 101);
			}
			while (map.blocked(randX-0.3, randY-0.3)||map.blocked(randX+0.3, randY-0.3)||
					map.blocked(randX-0.3, randY+0.3)||map.blocked(randX+0.3, randY+0.3));
			Enemy enemy = new Enemy(map,player,randX,randY,randType);
			enemies.add(enemy);
        }
		graphics = new Graphical(map, player, enemies, chests, gameMode, screenWidth, width*32, height*32, scaler);
		graphics.drawTitle();
		gameLoop();
	}

	public void gameLoop() throws IOException {
		// Jatkuva looppi joka pyörittää peliä
		boolean gameRunning = false;
		
		while (!gameRunning)
			if(time + 3000 <= System.currentTimeMillis()) {
				graphics.drawTitle();
				gameRunning = true;
			}
		
		while(gameRunning) {
		graphics.drawGraphics();
			// Pelilogiikkaan meno
		if (!gameOver) update();
		}
	}	
	
	public void update() throws IOException {
		
		audio.musicPlayer();
		player.setHealth(10000);
		player.movement(chests);
			
		//Viholliseten liikkuminen ja jos pelaaja lähettä niin taistelun aloittaminen
		
		if (enemies.size() == enemyCount/4 && !bossSpawned) {
			enemies.add(new Enemy(map,player,map.getWidth()-2,map.getHeight()-2,0));
			bossSpawned = true;
		}
		
		for (Enemy enemy : enemies) {
			enemy.move(enemy.getRandomX()  * speed * enemy.getSpeed(), enemy.getRandomY()  * speed * enemy.getSpeed()); 
			if (Math.abs(player.getX() - enemy.getX()) < 0.8 && Math.abs(player.getY() - enemy.getY()) < 0.8) {
				fight(enemy);
			}
		// Kuolleiden vihollisten poisto pelistä
        }
		for (Enemy enemy : killed) {
			enemies.remove(enemy);
		}
		if (time3 + 1000 <= System.currentTimeMillis()) {
			player.setPlayerMovement(1000);
			time3 = System.currentTimeMillis();
		}
	}
	
	public void fight(Enemy enemy) throws IOException {
		// Taistelu
		player.setPlayerMovement(25);
		player.heroFight();
		enemy.setMovement(0);
		
		if(time2 + 200 <= System.currentTimeMillis()) {
			player.setHealth(player.getHealth()-(int)(enemy.getDamage()*player.getShield()));
			enemy.setHealth(enemy.getHealth()-player.getWeaponDamage());
			time2 = System.currentTimeMillis();
		}
		if (enemy.getHealth() <= 0) {
			killed.add(enemy);
			player.setPlayerMovement(1000);
		} 
		if (player.getHealth() <= 0) {
			gameOver = true;
			player.heroDead();
		}
		audio.fightSound();
	}
	


	public static void main(String s[]) throws IOException { 
		System.setProperty("sun.java2d.opengl","true");
		Settings askUser = new Settings();
		if(askUser.settings()) {
			new Game(askUser.scalerMultiplier, askUser.mapWidth, askUser.mapHeight, askUser.treeCount, askUser.enemyCount, 
					askUser.chestCount, askUser.screenWidth, askUser.musicOn, askUser.adventureMode);
		}
	}
}
