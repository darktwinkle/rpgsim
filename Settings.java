package rpg_simulator;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;  
  
public class Settings extends JFrame{  
	
	boolean musicOn = true;
	boolean ready = false;
	boolean adventureMode = false;
	int treeCount, enemyCount, chestCount, screenWidth, mapWidth, mapHeight, scalerMultiplier;

  
	public boolean settings() {
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = dim.width;
		
		Frame frame = new Frame("RPG Simulator Settings");		
		JButton startButton = new JButton("Start Simulation");
		
		String[] resolutions = {"Small \"Square\" (860*700)","Small Wide (1200*700)", "Medium Square (900*900)",
								"Medium Wide (1600*900", "Large Square (1200*1200)","Large Wide (2000*1200)"};
		
		String[] gameModes = {"Normal", "Adventure","Mega Map"};
		
		JComboBox<String> resolution = new JComboBox<>(resolutions);
		resolution.setSelectedIndex(2);
		
		JComboBox<String> gameMode = new JComboBox<>(gameModes);
		gameMode.setSelectedIndex(0);
		
		JSlider scaler = new JSlider(1, 3, 1);   
		scaler.setMajorTickSpacing(1);
		scaler.setPaintTicks(true);  
		scaler.setPaintLabels(true);
		scaler.setSnapToTicks(true);
	
		JSlider trees = new JSlider(0, 300, 100);  
		trees.setMinorTickSpacing(10);  
		trees.setMajorTickSpacing(100);
		trees.setPaintTicks(true);  
		trees.setPaintLabels(true);
		
		JSlider enemies = new JSlider(0, 50, 10);  
		enemies.setMinorTickSpacing(5);  
		enemies.setMajorTickSpacing(10);
		enemies.setPaintTicks(true);  
		enemies.setPaintLabels(true);
		
		JSlider chests = new JSlider(0, 100, 10);  
		chests.setMinorTickSpacing(10);  
		chests.setMajorTickSpacing(20);
		chests.setPaintTicks(true);  
		chests.setPaintLabels(true);
		
		JToggleButton musicToggle = new JToggleButton("On");
		
		ActionListener toggleMusic = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		           if (musicToggle.isSelected()){
		        	   musicToggle.setText("Off");
		            } else {
		            	musicToggle.setText("On");
		            }
		         musicOn = !musicToggle.isSelected();
		      }
		    };
		    
		    gameMode.addItemListener(new ItemListener() {
		        public void itemStateChanged(ItemEvent arg0) {
		           if (gameMode.getSelectedIndex() != 0) {
		        	   trees.setMaximum(2000);
		        	   trees.setValue(1000);
		        	   trees.setMajorTickSpacing(500);
		        	   trees.setMinorTickSpacing(100);
		        	   trees.setLabelTable(trees.createStandardLabels(500));
		        	   enemies.setMaximum(500);
		        	   enemies.setValue(50);
		        	   enemies.setMajorTickSpacing(100);
		        	   enemies.setMinorTickSpacing(25);
		        	   enemies.setLabelTable(enemies.createStandardLabels(100));
		        	   chests.setMaximum(1000);
		        	   chests.setValue(100);
		        	   chests.setMajorTickSpacing(200);
		        	   chests.setMinorTickSpacing(100);
		        	   chests.setLabelTable(chests.createStandardLabels(200));
		           } else {
		        	   trees.setMaximum(300);
		        	   trees.setValue(100);
		        	   trees.setMajorTickSpacing(100);
		        	   trees.setMinorTickSpacing(10);
		        	   trees.setLabelTable(trees.createStandardLabels(100));
		        	   enemies.setMaximum(50);
		        	   enemies.setValue(10);
		        	   enemies.setMajorTickSpacing(10);
		        	   enemies.setMinorTickSpacing(5);
		        	   enemies.setLabelTable(enemies.createStandardLabels(10));
		        	   chests.setMaximum(100);
		        	   chests.setValue(10);
		        	   chests.setMajorTickSpacing(20);
		        	   chests.setMinorTickSpacing(10);
		        	   chests.setLabelTable(chests.createStandardLabels(20));
		           }
		        }
		    });
		
		    
			ActionListener startGame = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			    	 frame.dispose();
			    	 treeCount =trees.getValue();
			    	 enemyCount = enemies.getValue();
			    	 chestCount = chests.getValue();
			    	 scalerMultiplier = scaler.getValue();
			    	 switch (resolution.getSelectedIndex()) {
			    	 case 0: 
			    		 mapWidth = 27; 
			    		 mapHeight = 20;
			    		 break;
			    	 case 1:
			    		 mapWidth = 38; 
			    		 mapHeight = 20;
			    		 break;
			    	 case 2:
			    		 mapWidth = 28; 
			    		 mapHeight = 28;
			    		 break;
			    	 case 3:
			    		 mapWidth = 50; 
			    		 mapHeight = 28;
			    		 break;
			    	 case 4:
			    		 mapWidth = 38; 
			    		 mapHeight = 38;
			    		 break;
			    	 case 5:
			    		 mapWidth = 62; 
			    		 mapHeight = 38;
			    		 break;
			    	default:
			    		 mapWidth = 28; 
			    		 mapHeight = 28;
			    	 }
			    	 switch (gameMode.getSelectedIndex()) {
			    	 case 0: 
			    		 scalerMultiplier = 1;
			    		 adventureMode = false;
			    		 break;
			    	 case 1:
			    		 scalerMultiplier = 2;
			    		 adventureMode = true;
			    		 break;
			    	 case 2:
			    		 scalerMultiplier = 3;
			    		 adventureMode = false;
			    		 break;
			    	 default:
			    		 scalerMultiplier = 1;
			    	 }
			    	 ready = true;
			      }
			    };
		    
		musicToggle.addActionListener(toggleMusic);
		startButton.addActionListener(startGame);	    
		
		JPanel panel=new JPanel();
		
		panel.add(new JLabel("Select map size."));
	    panel.add(resolution);
	    panel.add(new JLabel("Select game mode."));
	    panel.add(gameMode);
		panel.add(new JLabel("Set the amount of trees on the map."));
		panel.add(trees);
		panel.add(new JLabel("Set the amount of enemies on the map."));
		panel.add(enemies);
		panel.add(new JLabel("Set the amount of chests on the map."));
		panel.add(chests);
		panel.add(new JLabel("Turn audio on or off."));
		panel.add(musicToggle);
		panel.add(startButton);
		
		frame.add(panel);
		frame.pack();
		frame.setSize(270, 475);
		frame.setLocation(screenWidth/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	
		while (!ready)
			try { Thread.sleep(1);} catch(InterruptedException e) {}
			
		return true;
	}

}  