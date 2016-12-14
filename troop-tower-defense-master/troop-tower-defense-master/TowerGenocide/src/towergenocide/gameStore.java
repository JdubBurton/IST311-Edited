/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towergenocide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.*;

/**
 *
 * @author Tyler
 */
public class gameStore {
   

    public static int shopWidth = 4;
    public static int buttonSize = 52;
    public static int cellSpace = 2;
    public static int awayFromRoom = 30;
    public Rectangle buttonHealth;
    public Rectangle buttonCoins;
    public static int iconSize = 30;
    public static int iconSpace = 3;
    public static int iconTextY = 20;
    public static int[] buttonID = {Value.turret1,Value.turret2,Value.troop,Value.trash};
   
    public static boolean troopSpawn =false;
    public static int itemIn = 4;
    public static int heldID = -1;
    public static int realID =-1;
    public static int[] towerCost = {20, 10, 5, 0};
    
    
    boolean holdsItem = false;
    
    public Rectangle[] button = new Rectangle[shopWidth];
    public gameStore(){
        define();
    }
    
     public void mouseClick(int mouseButton){
        if (mouseButton == 1){
           
            for(int i=0; i < button.length; i++){
                
                if(button[i].contains(gameScreen.mse)){
                    if(buttonID[i] != Value.airAir){
                    if(buttonID[i] == Value.trash){ //deletes item 
                        holdsItem = false;
                        
                    
                    } else {
                        heldID = buttonID[i];
                        realID = i;
                        
                        holdsItem = true;
                        
                    }
                }
                    if(button[2].contains(gameScreen.mse)){
                        troopSpawn=true;
                    
                }
                    
                }
                troopSpawn =false;
            }
             if(holdsItem == true){
                    if(gameScreen.money >= towerCost[realID]){
                        for(int y=0; y <gameScreen.room.block.length; y++){
                            for(int x=0; x <gameScreen.room.block[0].length; x++){
                                if(gameScreen.room.block[y][x].contains(gameScreen.mse)){
                                   if(gameScreen.room.block[y][x].groundID != Value.groundRoad && gameScreen.room.block[y][x].airID == 10){
                                        gameScreen.room.block[y][x].airID = heldID;
                                        gameScreen.money -= towerCost[realID];
                                        troopSpawn = true;
                                        
                                    }
                                }
                            }
                        }
                    }
                }
          }
        
    }
  

    
    public void define(){
        for(int i=0; i <button.length; i++){
            button[i] = new Rectangle((gameScreen.myWidth/2) - ((shopWidth*(buttonSize + cellSpace))/2)+ ((buttonSize+ cellSpace)*i), (gameScreen.room.block[gameScreen.room.worldHeight-1][0].y) +gameScreen.room.blockSize + awayFromRoom, buttonSize, buttonSize);
           
        }
        
        buttonHealth = new Rectangle(gameScreen.room.block[0][0].x-1, button[0].y, iconSize , iconSize);
        buttonCoins = new Rectangle(gameScreen.room.block[0][0].x-1, button[0].y + button[0].height-iconSize, iconSize, iconSize);
    
    }
    
    public void draw (Graphics g){
        
        for(int i=0; i < button.length; i++){
            if(button[i].contains(gameScreen.mse)){
                g.setColor(new Color(255,255,255,140));
                g.fillRect(button[i].x, button[i].y,button[i].width, button[i].height);
               
            }
            
            g.drawImage(gameScreen.tileset_res[0],button[i].x, button[i].y,button[i].width, button[i].height, null );
          //  g.drawImage(Screen.tileset_turret1[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn ,button[i].width -(itemIn*2), button[i].height - (itemIn*2), null );
          //  g.drawImage(Screen.tileset_turret2[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn ,button[i].width -(itemIn*2), button[i].height - (itemIn*2), null );
          //  g.drawImage(Screen.tileset_trashCan[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn ,button[i].width -(itemIn*2), button[i].height - (itemIn*2), null );
       if(buttonID[i] != Value.airAir) {
           g.drawImage(gameScreen.tileset_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn ,button[i].width -(itemIn*2), button[i].height - (itemIn*2), null );
       }
        
        if(towerCost[i] >0){
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Stencil", Font.PLAIN, 16));
            g.drawString("$"+towerCost[i] + "", button[i].x +itemIn + 12, button[i].y +itemIn+63);
        }
       
       
        }
        
        g.drawImage(gameScreen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(gameScreen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
        g.setFont(new Font("Stencil", Font.PLAIN, 16));
        g.setColor(new Color(255,255,255));
        g.drawString("" + gameScreen.health, buttonHealth.x + buttonHealth.width +iconSpace , buttonHealth.y + iconTextY);
        g.drawString("" + gameScreen.money, buttonCoins.x + buttonCoins.width +iconSpace , buttonCoins.y + iconTextY);    
    
        if(holdsItem == true && heldID != 8){
            g.drawImage(gameScreen.tileset_air[heldID], gameScreen.mse.x -((button[0].width - (itemIn*2) )/2)+itemIn, gameScreen.mse.y -((button[0].width - (itemIn*2) )/2) +itemIn, button[0].width -(itemIn*2), button[0].height - (itemIn*2), null);
            //g.drawImage(Screen.tileset_turret2[heldID], Screen.mse.x -((button[0].width - (itemIn*2) )/2)+itemIn, Screen.mse.y -((button[0].width - (itemIn*2) )/2) +itemIn, button[0].width -(itemIn*2), button[0].height - (itemIn*2), null);
        }
    }
}



