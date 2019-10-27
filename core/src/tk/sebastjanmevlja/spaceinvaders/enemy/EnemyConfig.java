package tk.sebastjanmevlja.spaceinvaders.enemy;

import java.util.Random;

import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Direction;
import tk.sebastjanmevlja.spaceinvaders.main.Main;


public class EnemyConfig {
    private static Random generator = new Random();
    private static Enemy[] enemies;
    private static int enemiesAlive;
    private static long flyDownTIme; //timer za flyDown
    private static Direction enemyMoving = Direction.RIGHT;
    private static int previousEnemyX = 0; //za racunanje odmika
    private static long enemyShowTimer = System.currentTimeMillis();
    private static long dropDownAndReverseTimer = 0;


    public static void make() {
        enemies = new Enemy[24];
        int numEnemies = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 6; column++) {
                enemies[numEnemies] = new Enemy(row, column);
                numEnemies++;
            }
        }
        enemiesAlive = enemies.length;
        if (Main.getLevel() == 2) {
            flyDownTIme = System.currentTimeMillis();
        }
    }

    public static void update(float dt) {
        for (int i = enemies.length - 1; i >= 0; i--) { //da prej skocijo oni spodi
            enemies[i].update(dt);
        }
        if (Main.getLevel() == 3) {
            if (System.currentTimeMillis() - enemyShowTimer > 1000) {
                showEnemies();
                enemyShowTimer = System.currentTimeMillis();
            }
        }

    }

    public static void draw(Main main) {
        for (Enemy enemy : enemies) {
            enemy.draw(main);
        }
    }

    static void dropDownAndReverse() {
        // Move all the enemies down and change direction
        if (enemyMoving == Direction.LEFT) {
            enemyMoving = Direction.RIGHT;
        } else {
            enemyMoving = Direction.LEFT;
        }
        if (Main.getLevel() == 2) {
            for (Enemy enemy : enemies) {
                //ce je level1 - ne potujejo dol enemy
                if (!enemy.isFlyDown()) {
                    enemy.setY(enemy.getY() - enemy.getHeight());
                } else {
                    enemy.setOldY(enemy.getOldY() - enemy.getHeight());
                }

            }
        }
    }

    private static void showEnemies() {
        int randEnemy = generator.nextInt(enemies.length);
        if (!enemies[randEnemy].getStatus()) {
            int x = generator.nextInt((int) (((GameInfo.WIDTH - enemies[0].getWidth()) - enemies[0].getWidth()) + enemies[0].getWidth()));
            //preveri odmik
            if (Math.abs(previousEnemyX - x) > enemies[0].getWidth()) { //preveri ce sta zadosti narazen
                previousEnemyX = x;
                enemies[randEnemy].setX(x);
                enemies[randEnemy].setY(GameInfo.HEIGHT - enemies[0].getHeight());
                enemies[randEnemy].setStatusTrue();
            }
        }
    }

    public static void prepareForLevel2() {
        int numEnemies = 0;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 6; column++) {
                enemies[numEnemies] = new Enemy(row, column);
                numEnemies++;
            }
        }
        enemiesAlive = enemies.length;
        flyDownTIme = System.currentTimeMillis();
    }

    public static void prepareForLevel3() {
        enemies = new Enemy[60];
        int numEnemies = 0;
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 10; column++) {
                enemies[numEnemies] = new Enemy(row, column);
                enemies[numEnemies].setStatusFalse();
                numEnemies++;
            }
        }
        enemiesAlive = enemies.length;
    }


    public static Enemy[] getEnemies() {
        return enemies;
    }

    public static int getEnemiesAlive() {
        return enemiesAlive;
    }

    public static void setEnemiesAlive(int enemiesAlive) {
        EnemyConfig.enemiesAlive = enemiesAlive;
    }

    static long getFlyDownTIme() {
        return flyDownTIme;
    }

    static void setFlyDownTIme(long flyDownTIme) {
        EnemyConfig.flyDownTIme = flyDownTIme;
    }

    static Direction getEnemyMoving() {
        return enemyMoving;
    }

    public static long getDropDownAndReverseTimer() {
        return dropDownAndReverseTimer;
    }

    public static void setDropDownAndReverseTimer(long dropDownAndReverseTimer) {
        EnemyConfig.dropDownAndReverseTimer = dropDownAndReverseTimer;
    }

    public static void dispose() {
        if (enemies != null) {
            for (int i = 0; i < enemies.length; i++) {
                enemies[i].dispose();
                enemies[i] = null;
            }
            enemies = null;
        }

    }


}
