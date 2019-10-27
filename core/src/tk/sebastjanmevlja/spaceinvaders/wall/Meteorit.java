package tk.sebastjanmevlja.spaceinvaders.wall;

import com.badlogic.gdx.graphics.g2d.Sprite;

import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Main;


public class Meteorit extends Sprite {


    private boolean status;
    private int lives = 9;
    private float height;
    private float width;


    Meteorit(float x, float y) {
        super(AssetManager.wallLargeTexture); //sprite superclass
        this.height = GameInfo.HEIGHT / 8;
        this.width = GameInfo.WIDTH / 4;
        setBounds(x, y, width, height);
        this.status = true;
    }

    public void update() {
        if (status) {
            if (lives < 1) {
                status = false;
                Main.sound.playRockSound();
            }
        }
        if (lives == 6 && getTexture().equals(AssetManager.wallLargeTexture)) {
            setTexture(AssetManager.wallMediumTexture);
            setPosition(getX() + getWidth() * 0.3f / 2, getY() + getHeight() * 0.3f / 2);
            setSize(getWidth() * 0.7f, getHeight() * 0.7f);
            Main.sound.playRockSound();
        } else if (lives == 3 && getTexture().equals(AssetManager.wallMediumTexture)) {
            setTexture(AssetManager.wallSmallTexture);
            setPosition(getX() + getWidth() * 0.3f / 2, getY() + getHeight() * 0.3f / 2);
            setSize(getWidth() * 0.7f, getHeight() * 0.7f);
            Main.sound.playRockSound();
        }
    }

    public void draw(Main main) {
        if (status) {
            main.getBatch().draw(this, getX(), getY(), getWidth(), getHeight());
        }
    }


    public boolean getStatus() {
        return status;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


}
