/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towergenocide;

/**
 *
 * @author Tyler
 */
import java.awt.*;

/**
 *
 * @author Tyler
 */
public class Troops extends Rectangle {

    public int troopSize = 52;
    public int troopID = Value.enemyAir;
    public boolean inGame = false;
    public int xC, yC;
    public int troopWalk = 0;
    public int right = 2;
    public int direction = right;
    public int enemyHealth = 50;
    public int healthSpace = 3;
    public int healthHeight = 6;
    public static boolean isDead = false;
    public static int value = 5;

    public Troops() {

    }

    public void spawnTroops(int troopID) {
        for (int y = 0; y < gameScreen.room.block.length; y++) {
            if (gameScreen.room.block[y][0].groundID == Value.groundRoad) {
                setBounds(gameScreen.room.block[y][0].x, gameScreen.room.block[y][0].y, troopSize, troopSize);
                xC = 0;
                yC = y;
            }

        }

        this.troopID = troopID;
        inGame = true;
    }

    public void deleteTroop() {
        inGame = false;

    }

    public void loseHealth() {
        gameScreen.health += 1;
        //gameScreen.enemyHealth -=10;
        gameScreen.money += 5;
    }

    public int walkFrame = 0, walkSpeed = 40;

    public void physicT() {
        if (walkFrame >= walkSpeed) {
            if (direction == right) {
                x += 1;
            }

            troopWalk += 1;

            if (troopWalk == gameScreen.room.blockSize) {
                if (direction == right) {
                    xC += 1;

                }

                if (gameScreen.room.block[yC][xC].airID == Value.enemyTower) {
                    deleteTroop();
                    loseHealth();

                }
                //if (gameScreen.room.block[yC][xC].airID == Value.enemyGreen) {
                  //if ( Value.enemyAir == Value.enemyGreen){  
                  //  deleteTroop();
                    //loseHealth();
                 //   System.out.println("enemy vs enemy triggered");

               // }
                troopWalk = 0;

            }

            walkFrame = 0;
        } else {
            walkFrame += 1;
        }
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(gameScreen.tileset_troops[troopID], x, y, width, height, null);

            //health bar 
            g.setColor(new Color(200, 70, 20));
            g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

            g.setColor(new Color(250, 30, 200));
            g.fillRect(x, y - (healthSpace + healthHeight), enemyHealth, healthHeight);

            g.setColor(new Color(0, 0, 0));
            g.drawRect(x, y - (healthSpace + healthHeight), enemyHealth - 1, healthHeight - 1);
        }

    }

}
