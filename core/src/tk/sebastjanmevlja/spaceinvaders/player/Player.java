package tk.sebastjanmevlja.spaceinvaders.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

import tk.sebastjanmevlja.spaceinvaders.bullet.Bullet;
import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Direction;
import tk.sebastjanmevlja.spaceinvaders.main.Main;

import static tk.sebastjanmevlja.spaceinvaders.main.Main.*;


public class Player extends Sprite {

    private float playerSpeed;
    private Direction playerMoving = Direction.STOPPED;
    private int lives;
    private int bulletsActive;
    private Bullet[] bullets = new Bullet[3];
    private boolean hit = false;
    private long hitTimer = 0;


    public Player() {
        super(AssetManager.playerTExture); //sprite superclass
        setBounds((GameInfo.WIDTH / 2f) - (getWidth() / 2f), 0, GameInfo.WIDTH / 5f, GameInfo.HEIGHT / 14f);
        this.playerSpeed = GameInfo.WIDTH / 2;
        this.lives = 3;
    }

    void setMovementState(Direction direction) {
        playerMoving = direction;
    }

    //mnozimo z dt - podobno ku fps - da bo na vseh telefonih enaka hitrost
    public void update(float dt) {
        float templPlayerSpeed = playerSpeed * dt;
        if (playerMoving == Direction.LEFT && getX() - templPlayerSpeed > 0) {
            setX(getX() - (templPlayerSpeed));
        }

        if (playerMoving == Direction.RIGHT && getX() + getWidth() + templPlayerSpeed < GameInfo.WIDTH) {
            setX(getX() + templPlayerSpeed);
        }
    }

    public void draw(Main game) {
        game.getBatch().draw(this, getX(), getY(), getWidth(), getHeight());
        if (hit) {
            game.getBatch().setColor(Color.RED);
            game.getBatch().draw(this, getX(), getY(), getWidth(), getHeight());
            game.getBatch().setColor(Color.WHITE);
            if (System.currentTimeMillis() - hitTimer > 1000) {
                hit = false;
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getBulletsActive() {
        return bulletsActive;
    }

    public void setBulletsActive(int bulletsActive) {
        this.bulletsActive = bulletsActive;
    }

    public Bullet[] getBullets() {
        return bullets;
    }

    void setBulletsNull() {
        this.bullets = null;
    }

    public void setHit() {
        this.hit = true;
        hitTimer = System.currentTimeMillis();
        sound.playPlayerHitSound();
    }

}
