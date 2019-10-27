package tk.sebastjanmevlja.spaceinvaders.player;

import com.badlogic.gdx.Gdx;

import tk.sebastjanmevlja.spaceinvaders.bullet.Bullet;
import tk.sebastjanmevlja.spaceinvaders.bullet.Heading;
import tk.sebastjanmevlja.spaceinvaders.bullet.Type;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Direction;
import tk.sebastjanmevlja.spaceinvaders.main.Main;

import static tk.sebastjanmevlja.spaceinvaders.main.Main.*;


public class PlayerConfig {

    private static Player player; //instanca objekta Player
    private static boolean shootsAlreadyFired = false; //prepreci da bi naenkrta iztrelili vec metkov


    public static void update(float dt) {
        checkInput();
        player.update(dt);
        updateBullets(dt);
    }

    public static void draw(Main main) {
        player.draw(main);
        drawBullets(main);
    }

    public static void makeBullets() {
        for (int i = 0; i < player.getBullets().length; i++) {
            player.getBullets()[i] = new Bullet(Type.PLAYER);
        }
        player.setBulletsActive(player.getBullets().length); //shrani kolko bulletov je active

    }

    private static void updateBullets(float dt) {
        for (int i = 0; i < player.getBullets().length; i++) {
            player.getBullets()[i].update(dt);
        }
    }

    private static void drawBullets(Main main) {
        for (int i = 0; i < player.getBullets().length; i++) {
            player.getBullets()[i].draw(main);
        }
    }

    private static void checkInput() {
        int screenTouchedSt = 0;
        for (int i = 0; i < 3; i++) { // 3 is max number of touch points
            if (Gdx.input.isTouched(i)) { //je touch?
                screenTouchedSt++;
                if (Gdx.input.getY(i) > GameInfo.HEIGHT - GameInfo.HEIGHT / 5) { //spodnji del screena
                    if (Gdx.input.getX(i) > GameInfo.WIDTH / 2 && !(Gdx.input.getX(i) < GameInfo.WIDTH / 2)) {

                        PlayerConfig.player.setMovementState(Direction.RIGHT);
                        //desna polvica screena
                    } else if (Gdx.input.getX(i) < GameInfo.WIDTH / 2 && !(Gdx.input.getX(i) > GameInfo.WIDTH / 2)) {
                        PlayerConfig.player.setMovementState(Direction.LEFT);
                        //leva polovica screena
                    }

                } else { //zgornji del screena
                    // Shots fired
                    if (!shootsAlreadyFired) {
                        for (int j = 0; j < player.getBullets().length; j++) {
                            if (!shootsAlreadyFired) {
                                if (player.getBullets()[j].shoot(player.getX() + (player.getWidth() / 2) - (player.getBullets()[j].getWidth() / 2), player.getY() + player.getHeight(), Heading.UP)) {
                                    shootsAlreadyFired = true;
                                    player.setBulletsActive(player.getBulletsActive() - 1);
                                    sound.playShootSound();
                                }
                            }
                        }
                    }
                }
            }
        }
        if (screenTouchedSt == 0) { //0 prtov na screenu
            PlayerConfig.player.setMovementState(Direction.STOPPED);
            shootsAlreadyFired = false;
        }
    }


    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        PlayerConfig.player = player;
    }


    private static void disposeBullets() {
        for (int i = 0; i < player.getBullets().length; i++) {
            player.getBullets()[i] = null;
        }
        player.setBulletsNull();
    }

    public static void dispose() {
        if (player != null) {
            disposeBullets();
            player = null;
        }

    }

    public static void resetPlayerBullets() {
        for (Bullet bullet : player.getBullets()) {
            bullet.setInactive();
        }
        player.setBulletsActive(player.getBullets().length); //shrani kolko bulletov je active
    }


}
