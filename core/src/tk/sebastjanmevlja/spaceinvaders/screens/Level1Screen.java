package tk.sebastjanmevlja.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import tk.sebastjanmevlja.spaceinvaders.enemy.EnemyConfig;
import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Main;
import tk.sebastjanmevlja.spaceinvaders.player.Player;
import tk.sebastjanmevlja.spaceinvaders.player.PlayerConfig;
import tk.sebastjanmevlja.spaceinvaders.wall.MeteoritConfig;

public class Level1Screen implements Screen {

    private Main main;
    private boolean paused = false;
    private boolean firstBoot = true;


    public Level1Screen(Main main) {
        this.main = main;
    }

    @Override
    public void show() { //create, setup method
        Main.score = 0;
        Main.level = 1;
        Gdx.input.setInputProcessor(null); //cleara gumbe od menuja
        PlayerConfig.setPlayer(new Player());
        PlayerConfig.makeBullets();
        EnemyConfig.make();
        MeteoritConfig.make();

    }

    private void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            main.changeScreen(Screens.MENUSCREEN);
        }
        if (PlayerConfig.getPlayer().getLives() <= 0) {
            main.changeScreen(Screens.ENDSCREEN);
        }
        if (EnemyConfig.getEnemiesAlive() <= 0) {
            main.changeScreen(Screens.LEVEL2SCREEN);
        } else {
            PlayerConfig.update(dt);
            EnemyConfig.update(dt);
            MeteoritConfig.update();
        }
        if (firstBoot) {
            firstBoot = false;
            paused = true;
        }
    }


    @Override
    public void render(float delta) {   //draw, loop called every frame
        if (!paused) {
            update(delta);

        }

        Batch gameBatch = main.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the screen
        gameBatch.draw(AssetManager.backgroundTexture, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        gameBatch.draw(AssetManager.starsTexture, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        PlayerConfig.draw(main);
        EnemyConfig.draw(main);
        MeteoritConfig.draw(main);


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
        if (paused) {
            if (Gdx.input.isTouched(0)) {
                paused = false;
            }
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(105, 105, 105, 0.8f));
            shapeRenderer.rect(0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.line(0, GameInfo.HEIGHT * 0.2f, GameInfo.WIDTH, GameInfo.HEIGHT * 0.2f);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            shapeRenderer.end();


            Gdx.gl.glLineWidth(GameInfo.HEIGHT * 0.5f);
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.line(0, GameInfo.HEIGHT * 0.2f, GameInfo.WIDTH, GameInfo.HEIGHT * 0.2f);
            shapeRenderer.line(GameInfo.WIDTH / 2, 0, GameInfo.WIDTH / 2, GameInfo.HEIGHT * 0.2f);
            shapeRenderer.end();


            gameBatch.begin();
            AssetManager.font.setColor(Color.BLACK);
            AssetManager.font.draw(gameBatch, "Control your spaceship by", GameInfo.WIDTH * 0.15f, GameInfo.HEIGHT * 0.8f);
            AssetManager.font.draw(gameBatch, "touching different parts of the screen.", GameInfo.WIDTH * 0.15f, GameInfo.HEIGHT * 0.75f);

            AssetManager.font.draw(gameBatch, "Touch here", GameInfo.WIDTH * 0.1f, GameInfo.HEIGHT * 0.14f);
            AssetManager.font.draw(gameBatch, "to move left.", GameInfo.WIDTH * 0.1f, GameInfo.HEIGHT * 0.1f);
            AssetManager.font.draw(gameBatch, "Touch here", GameInfo.WIDTH * 0.65f, GameInfo.HEIGHT * 0.14f);
            AssetManager.font.draw(gameBatch, "to move right.", GameInfo.WIDTH * 0.65f, GameInfo.HEIGHT * 0.1f);
            AssetManager.font.setColor(Color.WHITE);
            gameBatch.end();
        }
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        //when we terminate our app or if we change menu, actvity
        //dispose resources
        MeteoritConfig.dispose();
        PlayerConfig.dispose();
        EnemyConfig.dispose();
    }


}
