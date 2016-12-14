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
public class Enemy extends Rectangle {

    public int enemySize = 52;
    public int enemyID = Value.enemyGreen;
    public boolean inGame = false;
    public int xC, yC;
    public int enemyWalk = 0;
    public int right = 2;
    public int direction = right;
    public int enemyHealth;
    public int healthSpace = 3;
    public int healthHeight = 6;
    public static boolean isDead = false;
    public static int value = 5;

    //int x = 900;
    public Enemy() {

    }

    public void spawnEnemy(int enemyID) {
        for (int y = 0; y < gameScreen.room.block.length; y++) {
            if (gameScreen.room.block[y][0].groundID == Value.groundRoad) {
                setBounds(gameScreen.room.block[y][0].x, gameScreen.room.block[y][0].y, enemySize, enemySize);
               // x = 830;
               // xC = 0;
               xC = 0;
                yC = y;
            }

        }

        this.enemyID = enemyID;
        this.enemyHealth = enemySize;
        inGame = true;
    }

    public void deleteEnemy() {
        inGame = false;
        enemyWalk = 0;
        //enemyHealth = 52;
    }

    public void loseHealth() {
        gameScreen.health -= 10;
    }

    public int walkFrame = 0, walkSpeed = 40;

    public void physic() {
        if (walkFrame >= walkSpeed) {
            if (direction == right) {
                x += 1;
            }

            enemyWalk += 1;

            if (enemyWalk == gameScreen.room.blockSize) {
                if (direction == right) {
                    xC += 1;

                }

                   if (gameScreen.room.block[yC][xC].airID == Value.playerTower){
                   deleteEnemy();
                  loseHealth();
                 }
                enemyWalk = 0;
            }

            //  if (gameScreen.room.block[yC][xC].airID == Value.enemyAir){
            //      deleteEnemy();
            //      System.out.println("danger close");
            //  }
            walkFrame = 0;
        } else {
            walkFrame += 1;
        }
    }

    public void losingHealth(int damage) {
        
        enemyHealth -= damage;
        

        checkDeath();
    }

    public void checkDeath() {
        if (enemyHealth == 0) {
            deleteEnemy();
            //inGame = false;
            //isDead = true;
            isDead();
            gameScreen.money += value;

        } else {
           // isDead = false;
        }
    }

    public boolean isDead() {
        if (inGame == true) {
            return true;
        } else {
            //inGame=false;
            return false;
        }
    }

    public void draw(Graphics g) {
        if (inGame == true) {
            g.drawImage(gameScreen.tileset_enemy[enemyID], x, y, width, height, null);

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
