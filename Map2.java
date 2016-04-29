package rpg_simulator;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

public class Map2 {
	
	private Image tree, grass;
	private static final int emptySquare = 0;
	private static final int fullSquare = 1;
	static final int chestSquare = 2;
	private int width, height;
	public static final int squareSize = 32;
	int[][] data;
	BufferedImage mapImg;
	

	public Map2(int width, int height, int chests, int trees) {
		
		this.height = height;
		this.width = width;
		data = new int[width][height];
		mapImg = new BufferedImage(width*32, height*32, BufferedImage.TYPE_INT_RGB);
		
		// Puiden sijoitus kartalle
		for (int i=0;i<trees;i++) {
			data[ThreadLocalRandom.current().nextInt(1, width)][ThreadLocalRandom.current().nextInt(1, height)] = fullSquare;
		}
		
		// Arkkujen sijoitus kartalle
		for (int i=0;i<chests;i++) {
			int x = ThreadLocalRandom.current().nextInt(2, width-2);
			int y = ThreadLocalRandom.current().nextInt(2, height-2);
			while (data[x+1][y] == chestSquare || data[x][y+1] == chestSquare|| data[x-1][y] == chestSquare || data[x][y-1] == chestSquare ||
					data[x+1][y-1] == chestSquare || data[x+1][y+1] == chestSquare|| data[x-1][y-1] == chestSquare || data[x-1][y+1] == chestSquare) {
				 x = ThreadLocalRandom.current().nextInt(2, width-2);
				 y = ThreadLocalRandom.current().nextInt(2, height-2);
			}
			data[x][y] = chestSquare;
		}
		
		// Ääriviivojen piirto
		for (int i=0;i<height;i++) {
			data[0][i] = fullSquare;
			data[width-1][i] = fullSquare;
		}
		
		for (int i=0;i<width;i++) {
			data[i][0] = fullSquare;
			data[i][height-1] = fullSquare;
		}
		
		// Tyhjä aloituspaikka pelaajalle
		data[1][1]= emptySquare; data[1][2]= emptySquare; data[2][1]= emptySquare; data[2][2]= emptySquare;
		// Kuvien lataus
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/tree.png");
			tree = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Unable to load tree image!");
		}
		
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource("rpg_simulator/res/grass.png");
			grass = ImageIO.read(url);
		} catch (IOException e) {
			System.err.println("Unable to load grass image!");
		}
		makeMap();
		
	}
	
	public void makeMap() {
		Graphics g =  this.mapImg.getGraphics();
		for (int x=0;x<width;x++) {
			for (int y=0;y<height;y++) {
				if (data[x][y] == fullSquare) {
					g.drawImage(tree,x*squareSize,y*squareSize,null);
				}
				if (data[x][y] == emptySquare) {
					g.drawImage(grass,x*squareSize,y*squareSize,null);
				}	
			}
		}
	}

	public void draw(Graphics2D g2d, double playerX, double playerY) {
		// Karttaelementtien piirto
		g2d.drawImage(this.mapImg,(int)-playerX*32,(int)-playerY*32,null);
	}
	public boolean blocked(double x, double y) {
		//Onko ruutu vapaa?
		return data[(int) x][(int) y] == fullSquare || data[(int) x][(int) y] == chestSquare;
	}
	
	public boolean chest(double x, double y) {
		//Onko ruutu arkku?
		return data[(int) x][(int) y] == chestSquare;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	

}
