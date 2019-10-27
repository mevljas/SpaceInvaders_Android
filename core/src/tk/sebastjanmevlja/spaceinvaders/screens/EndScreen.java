package tk.sebastjanmevlja.spaceinvaders.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Main;

public class EndScreen implements Screen{

    private Main main;
    private Stage stage;


    public EndScreen(final Main main){
        this.main = main;
        Main.sound.playGameOverSound();

        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Skin skin = Main.assetManager.manager.get("skin/glassy-ui.json");

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        Label titleLabel = new Label("Game over", skin, "big");
        Label textLabel = new Label("Better luck next time!", skin, "big");
        titleLabel.setFontScale(GameInfo.WIDTH / 500f);
        Label scoreLabel = new Label("Score: " + Main.getScore(), skin, "big");

        // return to main screen button
        final TextButton retryButton = new TextButton("Retry", skin);
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.sound.changeMusicState();
                EndScreen.this.main.changeScreen(Screens.LEVEL1SCREEN);

            }
        });

        final TextButton backButton = new TextButton("Menu", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.sound.changeMusicState();
                EndScreen.this.main.changeScreen(Screens.MENUSCREEN);

            }
        });



        table.defaults().width(Value.percentWidth(.100F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).center().width(Value.percentWidth(.60F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(textLabel).center().width(Value.percentWidth(.60F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(scoreLabel).center().width(Value.percentWidth(.25F, table));
        table.row().padTop(Value.percentWidth(.2F, table));
        table.add(retryButton).center().width(Value.percentWidth(.50F, table));
        table.row().padTop(Value.percentWidth(.1F, table));
        table.add(backButton).center().width(Value.percentWidth(.50F, table));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            Main.sound.changeMusicState();
            main.changeScreen(Screens.MENUSCREEN);
        }

        Batch gameBatch = main.getBatch();

        gameBatch.begin(); //kdr zacenmo rendirat klicemo begin
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameBatch.draw(AssetManager.loadingBackgroundTexture, 0, 0, GameInfo.WIDTH, GameInfo.HEIGHT);
        gameBatch.end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
