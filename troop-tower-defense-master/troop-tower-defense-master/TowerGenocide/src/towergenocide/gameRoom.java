/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towergenocide;


import java.awt.*;

/**
 *
 * @author Tyler
 */
public class gameRoom {
    public int worldWidth = 12;
    public int worldHeight = 8;
    public int blockSize = 52;
    public gameBlock[][] block;
    
    public gameRoom() {
        define();
        
    }
    
    public void define(){
        block = new gameBlock[worldHeight][worldWidth];
        for(int y=0; y < block.length; y++){
            for(int x=0; x < block[0].length; x++){
                block[y][x] = new gameBlock((gameScreen.myWidth/2)-((worldWidth*blockSize)/2)+x * blockSize, y * blockSize, blockSize, blockSize,Value.groundGrass, Value.airAir );
                
            }
        }
        
    }
    
    public void physic(){
        for(int y = 0; y < block.length; y++){
            for(int x=0; x < block[0].length; x++){
                block[y][x].physic();
            }
        }
    }
    
    public void draw(Graphics g){
         for(int y=0; y < block.length; y++){
            for(int x=0; x < block[0].length; x++){
                block[y][x].draw(g);
            }
        }
    
    
     for(int y=0; y < block.length; y++){
            for(int x=0; x < block[0].length; x++){
                block[y][x].fight(g);
            }
        }
    }
}

