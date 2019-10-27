package tk.sebastjanmevlja.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;

import tk.sebastjanmevlja.spaceinvaders.enemy.EnemyConfig;
import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Main;
import tk.sebastjanmevlja.spaceinvaders.player.PlayerConfig;


public class Level3Screen implements Screen {

    private Main main;
    private boolean paused = false;

    public Level3Screen(Main main) {
        this.main = main;
    }

    @Override
    public void show() { //create, setup method
        PlayerConfig.resetPlayerBullets();
        EnemyConfig.prepareForLevel3();
        Main.level = 3;
    }

    private void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            main.changeScreen(Screens.MENUSCREEN);
        }
        if (PlayerConfig.getPlayer().getLives() <= 0) {
            main.changeScreen(Screens.ENDSCREEN);
        }
        PlayerConfig.update(dt);
        EnemyConfig.update(dt);
    }

    @Override
    public void render(float delta) {   //draw, loop called every frame
        if (!paused) {
            update(delta);


            Batch gameBatch = main.getBatch();

            gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
            //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
            gameBatch.draw(AssetManager.backgroundTexture, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
            gameBatch.draw(AssetManager.starsTexture, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
            PlayerConfig.draw(main);
            EnemyConfig.draw(main);


            // Draw the score and remaining lives
            AssetManager.font.draw(gameBatch, "Score: " + Main.getScore(), GameInfo.WIDTH * 0.81f, GameInfo.HEIGHT - GameInfo.HEIGHT / 40);
            AssetManager.font.draw(gameBatch, "Lives:", GameInfo.WIDTH / 30, GameInfo.HEIGHT - GameInfo.HEIGHT / 40);

            switch (PlayerConfig.getPlayer().getLives()) {
                case 3:
                    gameBatch.draw(PlayerConfig.getPlayer(), GameInfo.WIDTH * 0.18f, GameInfo.HEIGHT * 0.955f, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    gameBatch.draw(PlayerConfig.getPlayer(), GameInfo.WIDTH * 0.18f + GameInfo.WIDTH / 15, GameInfo.HEIGHT * 0.955f, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    gameBatch.draw(PlayerConfig.getPlayer(), GameInfo.WIDTH * 0.18f + GameInfo.WIDTH / 15 * 2, GameInfo.HEIGHT * 0.955f, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    break;
                case 2:
                    gameBatch.draw(PlayerConfig.getPlayer(), GameInfo.WIDTH * 0.18f, GameInfo.HEIGHT * 0.955f, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    gameBatch.draw(PlayerConfig.getPlayer(), GameInfo.WIDTH * 0.18f + GameInfo.WIDTH / 15, GameInfo.HEIGHT * 0.955f, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    break;
                case 1:
                    gameBatch.draw(PlayerConfig.getPlayer(), GameInfo.WIDTH * 0.18f, GameInfo.HEIGHT * 0.955f, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    break;
            }

            AssetManager.font.draw(gameBatch, "Bullets:", GameInfo.WIDTH / 30, GameInfo.HEIGHT - GameInfo.HEIGHT / 20);
            switch (PlayerConfig.getPlayer().getBulletsActive()) {
                case 3:
                    gameBatch.draw(PlayerConfig.getPlayer().getBullets()[0], GameInfo.WIDTH * 0.2f, GameInfo.HEIGHT - GameInfo.HEIGHT / 14, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    gameBatch.draw(PlayerConfig.getPlayer().getBullets()[0], GameInfo.WIDTH * 0.2f + GameInfo.WIDTH / 30, GameInfo.HEIGHT - GameInfo.HEIGHT / 14, GameInfo.WIDTH / 25, GameInfo.HEIGHT / 40);
                    gameBatch.draw(PlayerConfig.getPlayer().getBullets()[0], GameInfo.WIDTH * 0.2f + GameInfo.WIDTH / 15, GameInfo.HEIGHT - GameInfo.HEIGHT / 14, GameInfo.WIDTH / 25, GameInfo.HEIGHT / 40);
                    break;
                case 2:
                    gameBatch.draw(PlayerConfig.getPlayer().getBullets()[0], GameInfo.WIDTH * 0.2f, GameInfo.HEIGHT - GameInfo.HEIGHT / 14, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    gameBatch.draw(PlayerConfig.getPlayer().getBullets()[0], GameInfo.WIDTH * 0.2f + GameInfo.WIDTH / 30, GameInfo.HEIGHT - GameInfo.HEIGHT / 14, GameInfo.WIDTH / 25, GameInfo.HEIGHT / 40);
                    break;
                case 1:
                    gameBatch.draw(PlayerConfig.getPlayer().getBullets()[0], GameInfo.WIDTH * 0.2f, GameInfo.HEIGHT - GameInfo.HEIGHT / 14, GameInfo.WIDTH / 20, GameInfo.HEIGHT / 40);
                    break;
            }
            AssetManager.font.draw(gameBatch, "Level:  " + Main.getLevel(), GameInfo.WIDTH * 0.81f, GameInfo.HEIGHT - GameInfo.HEIGHT / 20);


            gameBatch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        //it will resize our screen to given with and height
    }

    @Override
    public void pause() {
        //called when main is paused
        paused = true;
    }

    @Override
    public void resume() {
        //continue
        paused = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        //when we terminate our app or if we change menu, actvity
        //dispose resources
    }


}
