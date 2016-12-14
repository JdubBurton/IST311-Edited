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
public class gameBlock extends Rectangle {

    public int groundID;
    public int airID;
    public Rectangle turretRange;
    public int towerRangeSize = 180;
    public int shotEnemy = -1;
    public boolean shooting = false;
    public int loseTime = 100, loseFrame = 0;

    public gameBlock(int x, int y, int width, int height, int groundID, int airID) {
        setBounds(x, y, width, height);
        turretRange = new Rectangle(x - (towerRangeSize/10),y - (towerRangeSize/2),width + (towerRangeSize),height + (towerRangeSize));
        this.groundID = groundID;
        this.airID = airID;

    }

    public void draw(Graphics g) {
        if (groundID == Value.groundGrass) {
            g.drawImage(gameScreen.tileset_sky[groundID], x, y, width, height, null);
        } else if (groundID == Value.groundRoad) {

            g.drawImage(gameScreen.tileset_road[groundID], x, y, width, height, null);

        }
        if (airID == 3) {
            g.drawImage(gameScreen.tileset_base[airID], x, y, width, height, null);

        }

        if (airID == Value.turret1) {
            g.drawImage(gameScreen.tileset_air[airID], x, y, width, height, null);

        }
        if (airID == Value.turret2) {
            g.drawImage(gameScreen.tileset_air[airID], x, y, width, height, null);

        }

    }

    public void physic() {

        if (shotEnemy != -1 && turretRange.contains(gameScreen.enemies[shotEnemy])) {
            shooting = true;
        } else {
            shooting = false;
        }

        if (shooting == false) {
            if (airID == Value.turret1 || airID == Value.turret2) {
                for (int i = 0; i < gameScreen.enemies.length; i++) {
                    if (gameScreen.enemies[i].inGame) {
                        if (turretRange.contains(gameScreen.enemies[i])) {
                            shooting = true;
                            shotEnemy = i;

                        }
                   }
                }
            }
        

            if (shooting == true) {
                if (loseFrame >= loseTime) {
                    gameScreen.enemies[shotEnemy].losingHealth(2);
                    // System.out.println(gameScreen.enemies[shotEnemy].enemyHealth);
                    loseFrame = 0;
                    // System.out.println(loseFrame);
                } else {
                    loseFrame += 1;
                }

                if (gameScreen.enemies[shotEnemy].isDead) {
                    //getMoney(Screen.enemies[shotEnemy].enemyID);
                    //gameScreen.enemies[shotEnemy].inGame=false;
                   // System.out.println(gameScreen.enemies[shotEnemy] + " is dead");
                    shooting = false;
                    //shotEnemy = -1;
                }
                shotEnemy=-1;
            }

        }
    }

    public void fight(Graphics g) {
        // if(Screen.isDebug){
        if (airID == Value.turret1|| airID == Value.turret2){
            //turretRange = new Rectangle(x - (towerRangeSize / 30), y - (towerRangeSize / 2), width + (towerRangeSize), height + (towerRangeSize));
            turretRange = new Rectangle(x - (towerRangeSize ), y - (towerRangeSize / 2), width + (towerRangeSize), height + (towerRangeSize));
            g.drawRect(turretRange.x, turretRange.y, turretRange.width, turretRange.height);
        }

        if (airID == Value.turret2) {

             g.drawRect(turretRange.x , turretRange.y , turretRange.width , turretRange.height );
        }

       //  if(shooting == true){
       //      g.setColor(new Color(255,150,0));
       //      g.drawLine(x+(width/2), y+(width/2), gameScreen.enemies[shotEnemy].x +(gameScreen.enemies[shotEnemy].width/2),gameScreen.enemies[shotEnemy].y + (gameScreen.enemies[shotEnemy].height/2));
       //  }
         
    }
}
