package tk.sebastjanmevlja.spaceinvaders.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import tk.sebastjanmevlja.spaceinvaders.bullet.Bullet;
import tk.sebastjanmevlja.spaceinvaders.bullet.Heading;
import tk.sebastjanmevlja.spaceinvaders.bullet.Type;
import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Direction;
import tk.sebastjanmevlja.spaceinvaders.main.Main;
import tk.sebastjanmevlja.spaceinvaders.player.PlayerConfig;

import static tk.sebastjanmevlja.spaceinvaders.main.Main.*;


public class Enemy extends Sprite {

    private Random generator = new Random();

    private float speedX = GameInfo.WIDTH / 30;

    private float speedY;

    private boolean status = true;

    private Bullet bullet;

    private Texture enemy1; //slika 1
    private Texture enemy2; //slika 2

    private Animation<Texture> enemyAnimation; // Must declare frame type (TextureRegion) - animacija

    private Animation<TextureRegion> explosionAnimation;

    // A variable for tracking elapsed time for the animation
    private float stateTime;
    private float stateTime2;

    private boolean explosion = false;
    private long explosionStartTime;
    //private TextureRegion explosionImage;
    private boolean flyDown = false;
    private float oldY;


    Enemy(int row, int column) {
        super(AssetManager.enemyTexture1a);
        //this.explosionImage = new TextureRegion(AssetManager.enemyTexture, 358, 632, 96, 55);


        switch (row) {
            case 0:
                this.enemy1 = AssetManager.enemyTexture1a;
                this.enemy2 = AssetManager.enemyTexture1b;
                break;
            case 1:
                this.enemy1 = AssetManager.enemyTexture1a;
                this.enemy2 = AssetManager.enemyTexture1b;
                break;
            case 2:
                this.enemy1 = AssetManager.enemyTexture2a;
                this.enemy2 = AssetManager.enemyTexture2b;
                break;
            case 3:
                this.enemy1 = AssetManager.enemyTexture2a;
                this.enemy2 = AssetManager.enemyTexture2b;
                break;
            case 4:
                this.enemy1 = AssetManager.enemyTexture1a;
                this.enemy2 = AssetManager.enemyTexture1b;
                break;
            case 5:
                this.enemy1 = AssetManager.enemyTexture1a;
                this.enemy2 = AssetManager.enemyTexture1b;
                break;
        }
        Texture[] enemyFrames = new Texture[2];

        //slike shranimo v frame
        enemyFrames[0] = this.enemy1;
        enemyFrames[1] = this.enemy2;


        //naredimo animacijo ki preklaplja med frami vasko sekundo
        this.enemyAnimation = new Animation<Texture>(1, enemyFrames);

        float width = GameInfo.WIDTH / 10;
        float height = GameInfo.HEIGHT / 15;

        float paddingWidth = width / 2;
        float enemyX = column * (width + paddingWidth);

        float paddingHeight = height / 3;
        float enemyY;
        if (row == 0)
            enemyY = GameInfo.HEIGHT - paddingHeight - 2 * height;
        else
            enemyY = GameInfo.HEIGHT - paddingHeight - 2 * height - (row * (height + paddingHeight));

        this.setBounds(enemyX, enemyY, width, height);

        this.speedY = (float) (speedX * (Math.random() * (6 - 2) + 1) + 2);//random speed

        this.bullet = new Bullet(Type.ENEMY);
        TextureAtlas atlas = AssetManager.explosionAtlas;
        this.explosionAnimation = new Animation<TextureRegion>(0.15f, atlas.findRegions("explosion"));
    }

    public void setInvisible() {
        sound.playExplosionSound();
        status = false;
        explosion = true;
        explosionStartTime = System.currentTimeMillis();
    }

    public boolean getStatus() {
        return status;
    }

    public void update(float dt) {
        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time for animation
        if (status) {
            if (getLevel() < 3) {
                if (getLevel() == 2) {
                    if (flyDown) {
                        setY(getY() - speedY * dt);
                    } else if (System.currentTimeMillis() - EnemyConfig.getFlyDownTIme() >= 4000 && generator.nextInt(40) == 1) {
                        EnemyConfig.setFlyDownTIme(System.currentTimeMillis());
                        doFlyDown();
                    }
                }
                if (EnemyConfig.getEnemyMoving() == Direction.LEFT) {
                    setX(getX() - speedX * dt);
                } else if (EnemyConfig.getEnemyMoving() == Direction.RIGHT) {
                    setX(getX() + speedX * dt);
                }
            } else if (getLevel() == 3) {
                setY(getY() - speedY * dt);
            }
            // Does he want to take a shot?
            if (takeAim(PlayerConfig.getPlayer().getX(), PlayerConfig.getPlayer().getWidth())) {
                // If so try and spawn a playerBullet
                bullet.shoot(getX() + getWidth() / 2 - bullet.getWidth() / 2, getY(), Heading.DOWN);
            }
            collision();
        }
        bullet.update(dt);
        explosionCheck();
    }

    public void draw(Main game) {
        if (status) {
            Texture currentFrame = this.enemyAnimation.getKeyFrame(this.stateTime, true);
            game.getBatch().draw(currentFrame, getX(), getY(), getWidth(), getHeight()); //narisemo trenutni frame
        } else if (explosion) {
            TextureRegion currentFrame = this.explosionAnimation.getKeyFrame(this.stateTime2, true);
            game.getBatch().draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        }
        bullet.draw(game);
    }


    private boolean takeAim(float playerX, float playerWidth) {

        int randomNumber;

        // If near the player
        if ((playerX + playerWidth > getX() && playerX + playerWidth < getX() + getWidth()) ||
                (playerX > getX() && playerX < getX() + getWidth())) {

            // chance to shoot
            if (getLevel() == 3) {
                randomNumber = generator.nextInt(250);
            } else {
                randomNumber = generator.nextInt(500);
            }
            if (randomNumber == 0) {
                return true;
            }

        }

        // If firing randomly (not near the player)
        if (getLevel() == 3) {
            randomNumber = generator.nextInt(1000);
        } else {
            randomNumber = generator.nextInt(2000);
        }
        return randomNumber == 0;

    }


    private void explosionCheck() {
        if (explosion) {
            stateTime2 += Gdx.graphics.getDeltaTime();
            if (System.currentTimeMillis() - explosionStartTime > 2000) {
                explosion = false;
                explosionStartTime = 0;
            }
        }
    }

    private void doFlyDown() {
        oldY = getY();
        flyDown = true;
    }

    private void collision() {
        //collision bottom canvas border
        if (getLevel() == 3) {
            if (getY() + getHeight() < 0) {
                status = false;
                EnemyConfig.setEnemiesAlive(EnemyConfig.getEnemiesAlive() - 1);
            }
        }
        //collision z player
        if (getBoundingRectangle().overlaps(PlayerConfig.getPlayer().getBoundingRectangle())) {
            PlayerConfig.getPlayer().setLives(PlayerConfig.getPlayer().getLives() - 1);
            setInvisible();
            EnemyConfig.setEnemiesAlive(EnemyConfig.getEnemiesAlive() - 1);
            PlayerConfig.getPlayer().setHit();
        }

        // If that move caused them to bump the screen change bumped to true
        if (getLevel() < 3) {
            if (getX() + getWidth() >= GameInfo.WIDTH || getX() <= 0) {
                if (System.currentTimeMillis() - EnemyConfig.getDropDownAndReverseTimer() > 1000) {
                    EnemyConfig.dropDownAndReverse();
                    EnemyConfig.setDropDownAndReverseTimer(System.currentTimeMillis());
                }
                //pocaka da se usi premaknejo pered spet preveri
                // Move all the enemies down and change direction
                //1 sec delay

            }
        }
        if (getLevel() == 2){
            if (flyDown) {
                if (getY() + getHeight() < 0) {
                    setY(GameInfo.HEIGHT);
                }
                else if (getY() > oldY && getY() < oldY + getHeight() / 2) {
                    flyDown = false;
                    setY(oldY);
                }

            } else if (getY() + getHeight() < 0) {
                PlayerConfig.getPlayer().setLives(0);
            }
        }


    }

    void setStatusTrue() {
        this.status = true;
    }

    void setStatusFalse() {
        this.status = false;
    }

    boolean isFlyDown() {
        return flyDown;
    }

    float getOldY() {
        return oldY;
    }

    void setOldY(float oldY) {
        this.oldY = oldY;
    }

    void dispose() {
        bullet = null;
    }
}
