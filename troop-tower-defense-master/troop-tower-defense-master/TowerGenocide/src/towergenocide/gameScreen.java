/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towergenocide;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.*;
import java.io.*;


/**
 *
 * @author Tyler
 */
public class gameScreen extends JPanel implements Runnable, MouseListener, MouseMotionListener  {
    
   public Thread thread = new Thread(this);
   
   public static Image[] tileset_sky = new Image[100];
   public static Image[] tileset_air = new Image[100];
   public static Image[] tileset_road = new Image[100];
   public static Image[] tileset_troops = new Image[100];
   public static Image[] tileset_enemy = new Image[100];
   public static Image[] tileset_res = new Image[100];
   public static Image[] tileset_base = new Image[100];
   public static Image[] tileset_enemyBase = new Image[100];
   
   public static int myWidth, myHeight;
   public static int money =10, health = 100;
   
   
   public static boolean isFirst = true;
   
   
   public static Point mse = new Point(0,0);
   
   public static gameRoom room;
   public static Save save;
   public static gameStore store;
   
   public static Enemy[] enemies = new Enemy[100];
   public static Troops[] troops = new Troops[100];
   
    public gameScreen(GameWindow game) {
        
        addMouseMotionListener(this);
        addMouseListener(this);
        
        thread.start();
        
    }
    
    public void define() {
        room = new gameRoom();
        save = new Save();
        store = new gameStore();
        
      
        
        for(int i=0; i < tileset_sky.length; i++){
            tileset_sky[i] = new ImageIcon("src\\pics\\skytile.png").getImage();
            tileset_sky[i] = createImage(new FilteredImageSource(tileset_sky[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
             
        }
        
         for(int i=0; i < tileset_air.length; i++){
            tileset_air[i] = new ImageIcon("src\\pics\\tileset_air.png").getImage();
            tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
            
        }
          for(int i=0; i < tileset_road.length; i++){
            tileset_road[i] = new ImageIcon("src\\pics\\stoneTile_1.png").getImage();
            tileset_road[i] = createImage(new FilteredImageSource(tileset_road[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
            
        }
         // tileset_enemyBase[7] = new ImageIcon("src\\pics\\enemyBase.png").getImage();
          tileset_base[3] = new ImageIcon("src\\pics\\base.jpg").getImage();
          tileset_res[0] = new ImageIcon("src\\pics\\cell.png").getImage(); 
          tileset_res[1] = new ImageIcon("src\\pics\\health.png").getImage();
          tileset_res[2] = new ImageIcon("src\\pics\\cash.png").getImage();
          tileset_enemy[0] = new ImageIcon("src\\pics\\troopWalk.gif").getImage();
          tileset_troops[4] = new ImageIcon("src\\pics\\enemyWalk.gif").getImage();
          tileset_air[5] = new ImageIcon("src\\pics\\turret1.png").getImage();
          tileset_air[6] = new ImageIcon("src\\pics\\turret1Fire.gif").getImage();
          tileset_air[7] = new ImageIcon("src\\pics\\trash.png").getImage();
          tileset_air[8] = new ImageIcon("src\\pics\\Soldier1_default.png").getImage();
          
          
         try {
            save.loadSave(new File("src\\pics\\map.level"));
         }
         catch(Exception e){
             e.printStackTrace();
         }
         
          for(int i=0; i<enemies.length; i++){
            enemies[i] = new Enemy();
            
            
        }
          for (int i=0; i <troops.length; i++){
              troops[i] = new Troops();
          }
         
         
    }
    
   @Override
    public void paintComponent(Graphics g){
        
        
        
        if(isFirst){
            myWidth = getWidth();
            myHeight = getHeight();
            
            define();
            isFirst = false;
        }
        
        g.setColor(new Color(30,100,30));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(0,0,230)); 
        //drawing the left line border
        g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y +room.blockSize); 
        //drawing the right line border
        g.drawLine(room.block[0][room.worldWidth-1].x +room.blockSize, 0, room.block[0][room.worldWidth-1].x + room.blockSize, room.block[room.worldHeight-1][0].y +room.blockSize );
        // draw botton line border
        g.drawLine(room.block[0][0].x, room.block[room.worldHeight-1][0].y +room.blockSize,room.block[0][room.worldWidth-1].x +room.blockSize , room.block[room.worldHeight-1][0].y +room.blockSize);
      
        
        room.draw(g); //draw the room
        store.draw(g); // draw the store
        
        
        for (int i=0; i <enemies.length; i++){
            if(enemies[i].inGame){
                enemies[i].draw(g);
            }
            
            
        }
         for (int i=0; i <troops.length; i++){
            if(troops[i].inGame){
                troops[i].draw(g);
            }
         }
        
        // Display Game Over
        if ( health < 1)
        {
            thread.stop();
            g.setColor(new Color(240,20,20));
            g.fillRect(0,0,myWidth, myHeight);
            g.setColor(new Color(255, 255, 255));
            g.setFont(new Font("Stencil", Font.BOLD, 50));
            
            g.drawString("Game Over! You have been destroyed by", 100, 100);
            g.drawString("the " + GameWindow.faction + " powers", 100, 200);
            revalidate();
        }
    }
    
    
    public int spawnTime = 3200, spawnFrame =0;
    public void enemySpawner(){
        if(spawnFrame >= spawnTime){
            for (int i=0; i < enemies.length; i++){
                if(!enemies[i].inGame){
                    enemies[i].spawnEnemy(Value.enemyGreen);
                    //enemies[i].spawnEnemy(i);
                    
                    spawnTime -= 20;
                    
                    break;
                }
                
            }
            
            spawnFrame =0; 
        }
        else {
            spawnFrame +=1;
        }
        
    }
     public void troopSpawner(){
        if(spawnFrame >= spawnTime){
            for (int i=0; i < troops.length; i++){
                if(!troops[i].inGame){
                    troops[i].spawnTroops(Value.enemyAir);
                    
                    break;
                }
                
            }
            
            spawnFrame =0; 
        }
        else {
            spawnFrame +=1;
        }
        
    }
     
    
    
   @Override
    public void run (){
        while(true){
            if(!isFirst && health > 0){
                room.physic();
                enemySpawner();
                for(int i=0; i < enemies.length;i++){
                    if(enemies[i].inGame){
                         enemies[i].physic();
                         
                    }
                }
                
             /*
                if (gameStore.troopSpawn == true){
                troopSpawner();
                for (int i=0; i<troops.length; i++){
                    if(troops[i].inGame){
                         troops[i].physicT();
                    }
                }
            }
                */
                
            }
            repaint();
            
            try {
                Thread.sleep(1);
            }
            catch (Exception e) {
                
            }
        }
        
    }
    
      @Override
    public void mouseClicked(MouseEvent e) {
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gameScreen.store.mouseClick(e.getButton());
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
     
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
     @Override
    public void mouseDragged(MouseEvent e) { 
      gameScreen.mse = new Point((e.getX())+((GameWindow.size.width - gameScreen.myWidth)/100), (e.getY())+ ((GameWindow.size.height - (gameScreen.myHeight))+(GameWindow.size.width - gameScreen.myWidth)/7));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      gameScreen.mse = new Point((e.getX())+((GameWindow.size.width - gameScreen.myWidth)/100), (e.getY())+ ((GameWindow.size.height - (gameScreen.myHeight))+(GameWindow.size.width - gameScreen.myWidth)/7));
    }
    
}


