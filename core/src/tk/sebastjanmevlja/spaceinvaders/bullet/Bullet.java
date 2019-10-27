package tk.sebastjanmevlja.spaceinvaders.bullet;


import com.badlogic.gdx.graphics.g2d.Sprite;

import tk.sebastjanmevlja.spaceinvaders.enemy.EnemyConfig;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.main.Main;
import tk.sebastjanmevlja.spaceinvaders.player.PlayerConfig;
import tk.sebastjanmevlja.spaceinvaders.wall.MeteoritConfig;


public class Bullet extends Sprite {

    //which way its shooting

    private Heading heading = Heading.NOWHERE;
    private float speed;
    private boolean status;
    private Type type;
    private int damage = 1;


    public Bullet(Type type) {
        super(AssetManager.enemyBulletTexture); //sprite superclass
        if (type.equals(Type.ENEMY)) {
            setTexture(AssetManager.enemyBulletTexture);
        } else {
            setTexture(AssetManager.playerBulletTexture);
        }

        float width = GameInfo.WIDTH / 20f;
        float height = GameInfo.HEIGHT / 25f;
        this.speed = GameInfo.HEIGHT / 4;
        this.status = false;
        this.type = type;

        setBounds(getX(), getY(), width, height);
    }

    public void setInactive() {
        status = false;
    }

    private float getImpactPointY() {
        if (heading == Heading.DOWN) {
            return getY();
        } else {
            return getY() + getHeight();
        }
    }


    public boolean shoot(float startX, float startY, Heading direction) {
        if (!status) {
            setBounds(startX, startY, getWidth(), getHeight());
            heading = direction;
            status = true;
            return true;
        }

        // Bullet already active
        return false;
    }

    public void update(float dt) {
        if (status) {
            collision();
            // Just move up or down
            if (heading == Heading.UP) {
                setY(getY() + speed * dt);
            } else {
                setY(getY() - speed * dt);
            }
        }
    }

    public void draw(Main main) {
        if (status) {
            main.getBatch().draw(this, getX(), getY(), getWidth(), getHeight());
        }
    }

    private void collision() {
        if (type == Type.ENEMY) {
            enemyBulletCollision();
        } else if (type == Type.PLAYER) {
            playerBulletCollision();
        }
    }


    private void enemyBulletCollision() {

        //collisono enemybullet hit player
        if (getBoundingRectangle().overlaps(PlayerConfig.getPlayer().getBoundingRectangle())) {
            setInactive();
            PlayerConfig.getPlayer().setLives(PlayerConfig.getPlayer().getLives() - damage);
            PlayerConfig.getPlayer().setHit();
        }


        // Has an enemies playerBullet hit the bottom of the screen
        if (getImpactPointY() < 0) {
            setInactive();
        }


        //collisono enemybullet hit wall
        if (Main.getLevel() == 1) { //level 2, 3 nimajo wallow
            for (int i = 0; i < MeteoritConfig.getMeteorits().length; i++) {
                if (MeteoritConfig.getMeteorits()[i].getStatus()) {
                    if (getBoundingRectangle().overlaps(MeteoritConfig.getMeteorits()[i].getBoundingRectangle())) {
                        status = false;
                        MeteoritConfig.getMeteorits()[i].setLives(MeteoritConfig.getMeteorits()[i].getLives() - this.damage);
                    }
                }
            }
        }


    }

    private void playerBulletCollision() {
        // Has the player's playerBullet hit the top of the screen
        if (getImpactPointY() > GameInfo.HEIGHT) {
            setInactive();
            PlayerConfig.getPlayer().setBulletsActive(PlayerConfig.getPlayer().getBulletsActive() + 1);

        }

        //collisono playerbullet hit enemy
        for (int i = 0; i < EnemyConfig.getEnemies().length; i++) {
            if (EnemyConfig.getEnemies()[i].getStatus() && getBoundingRectangle().overlaps(EnemyConfig.getEnemies()[i].getBoundingRectangle())) {
                EnemyConfig.getEnemies()[i].setInvisible();
                setInactive();
                Main.setScore(Main.getScore() + 1);
                EnemyConfig.setEnemiesAlive(EnemyConfig.getEnemiesAlive() - 1);
                PlayerConfig.getPlayer().setBulletsActive(PlayerConfig.getPlayer().getBulletsActive() + 1);
                break; //da zadene samo en enemy - ce je an sredini bi zadeu oba
            }
        }

        //collisono playerbullet hit walls
        if (Main.getLevel() == 1) { //level 2, 3 nimajo wallow
            for (int i = 0; i < MeteoritConfig.getMeteorits().length; i++) {
                if (MeteoritConfig.getMeteorits()[i].getStatus()) {
                    if (getBoundingRectangle().overlaps(MeteoritConfig.getMeteorits()[i].getBoundingRectangle())) {
                        MeteoritConfig.getMeteorits()[i].setLives(MeteoritConfig.getMeteorits()[i].getLives() - this.damage);
                        status = false;
                        PlayerConfig.getPlayer().setBulletsActive(PlayerConfig.getPlayer().getBulletsActive() + 1);
                        break; //da zadene samo en wall - ce je an sredini bi zadeu oba
                    }
                }
            }
        }


    }


}
