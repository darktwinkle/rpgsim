package rpg_simulator;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Graphical extends Canvas{
	
	private Map map;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Chest> chests;
	private BufferStrategy buffer;
	private Image titleImg, healthBar, armorBar, weaponBar, gameOverImg;
	private int gameWidth, gameHeight, frameWidth, frameHeight;
	private boolean  adventureMode;
	private double scaler;
	
    public Graphical(Map map, Player player, ArrayList<Enemy> enemies, ArrayList<Chest> chests, boolean adventureMode, int screenWidth, int winWidth, int winHeight, double scaler) throws IOException {
    	
    	this.map = map;
    	this.player = player;
    	this.enemies = enemies;
    	this.chests = chests;
    	this.adventureMode = adventureMode;
    	this.scaler = scaler;
		frameWidth = winWidth;
		frameHeight = winHeight;
    	
    	if(adventureMode) {frameWidth = winWidth/4; frameHeight = winHeight/4;} 
    	
		titleImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/title.png"));
		healthBar = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/healthbar.png"));
		weaponBar = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/weaponbar4.png"));
		armorBar = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/armorbar.png"));
		gameOverImg = ImageIO.read(Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/gameover.png"));
	
		gameWidth = map.getWidth()*32; gameHeight = map.getHeight()*32;
    
		// Simulaattori-ikkunan teko
		Frame frame = new Frame("RPG Simulator 2017");
		frame.setLayout(null);
		frame.add(this);
		frame.setBounds(0,0,frameWidth,frameHeight+20);
		frame.setSize(frameWidth,frameHeight+20);
		frame.setLocation(screenWidth/2-frame.getSize().width/2,20);
		frame.setResizable(false);	
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
		frame.createBufferStrategy(2);
		
		// Asetus ikkunan teko
		Frame optionFrame = new Frame("In-game Options");
		JSlider speedSlider = new JSlider(1, 300, 15);
		JPanel optionPanel=new JPanel();
		JToggleButton musicToggle = new JToggleButton();
		if(Game.musicOn) {
			musicToggle.setText("Pause");
			musicToggle.setSelected(false);
		} else {
			musicToggle.setText("Play");
			musicToggle.setSelected(true);
		}
		optionPanel.add(new JLabel("Set Speed"));
		optionPanel.add(speedSlider);
		optionPanel.add(new JLabel("Music Control"));
		optionPanel.add(musicToggle);
		optionFrame.add(optionPanel);
		optionFrame.pack();
		optionFrame.setSize(180, 130);
		optionFrame.setLocation(frame.getX()+frame.getWidth(),20);
		optionFrame.setVisible(true);
		musicToggle.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
	         if (musicToggle.isSelected()){
	        	 musicToggle.setText("Play");
	      	 } else {
	            musicToggle.setText("Pause");
	         }
	         Game.musicOn = !musicToggle.isSelected();
	      }
	    });
	    speedSlider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent ce){
	            Game.speed = speedSlider.getValue()/3;
	        }
	    });
		optionFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				optionFrame.dispose();
			}
		});		
		//Olioiden luonti	
		buffer = frame.getBufferStrategy();	
    }
    
    public void drawTitle() {
    	// Alussa näkyvän otsikon piirto (tapahtuu kerran)
    		Graphics2D g2d = (Graphics2D) buffer.getDrawGraphics();
    		g2d.translate(0,20);
    		if (this.adventureMode) g2d.scale(1/this.scaler/4, 1/this.scaler/4);
    		else g2d.scale(1/this.scaler, 1/this.scaler);
    		map.draw(g2d);
    		for (Chest chest : chests)  chest.draw(g2d); 
    		g2d.drawImage(titleImg,gameWidth/2-450,100,null);
    		g2d.dispose();
    		buffer.show();
    	}
    
	public void drawGraphics() {
	// Pelin kuvien jatkuva piirto	
		Graphics2D g2d = (Graphics2D) buffer.getDrawGraphics();
		Graphics2D g2 = (Graphics2D) buffer.getDrawGraphics();
		if (!this.adventureMode) {
			g2d.translate(0,20);
			g2d.scale(1/this.scaler, 1/this.scaler);
		}
		else {
			double cameraX = frameWidth/4, cameraXX = cameraX;
			double cameraY = frameHeight/4, cameraYY = cameraY;
			while (player.getX()*32 - cameraX< 0 ) cameraX-=5;			
			while (player.getY()*32 - cameraY< 0 ) cameraY-=5;
			while (player.getX()*32 + cameraXX > gameWidth ) {
				cameraXX-=7;
				cameraX+=5;
			}
			while (player.getY()*32  + cameraYY > gameHeight ) {
				cameraYY -=4;
				cameraY+=5;
			}
			g2d.scale(this.scaler, this.scaler);
			g2d.translate(-player.getX()*32+cameraX, -player.getY()*32+cameraY);
		}
		map.draw(g2d);
		player.draw(g2d);
		for (Chest chest : chests)  chest.draw(g2d);
		for (Enemy enemy : enemies) enemy.draw(g2d);
		g2.scale(1, 1);
		g2.translate(0, 0);
        g2.setFont(new Font("Arial", Font.PLAIN, 18));
        g2.setColor(Color.white);
		g2.drawImage(healthBar,5,frameHeight-50,null);
		g2.drawString(player.getHealth()+"/100", 85, frameHeight-20);
		g2.drawImage(armorBar,frameWidth-205,frameHeight-50,null);
		g2.setFont(new Font("Arial", Font.BOLD, 12));
		g2.drawString(player.getArmorName()+" Armor", frameWidth-185, frameHeight-23);
		g2.setFont(new Font("Arial", Font.BOLD, 18));
		g2.drawImage(weaponBar,frameWidth/2-225,frameHeight-50,null);
		g2.setColor(player.weaponColor());
		g2.drawString(player.getWeaponName()+" (+"+player.getWeaponDamage() +")",frameWidth/2-85, frameHeight-28);
		if (Game.gameOver) g2.drawImage(gameOverImg,frameWidth/2-450,100,null);
		g2d.dispose();
		g2.dispose();
		buffer.show();
	}	
}
