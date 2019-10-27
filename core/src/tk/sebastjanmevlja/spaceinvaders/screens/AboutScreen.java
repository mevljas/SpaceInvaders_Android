package tk.sebastjanmevlja.spaceinvaders.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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


public class AboutScreen implements Screen {

    private Main main;
    private Stage stage;


    public AboutScreen(final Main main) {
        this.main = main;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        Skin skin = Main.assetManager.manager.get("skin/glassy-ui.json");

        // Create a table that fills the screen. Everything else will go inside
        // this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);


        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                AboutScreen.this.main.changeScreen(Screens.MENUSCREEN);

            }
        });

        Label titleLabel = new Label("About", skin, "big");
        titleLabel.setFontScale(GameInfo.WIDTH / 300);
        Label versionLable = new Label("Version: 1.1.0", skin, "big");
        Label authorLable = new Label("Author: Sebastjan Mevlja", skin, "big");


        table.defaults().width(Value.percentWidth(.90F, table));
        table.defaults().height(Value.percentHeight(.10F, table));

        table.add(titleLabel).center().width(Value.percentWidth(.40F, table));
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(new Image(AssetManager.logoTexture)).width(Value.percentWidth(.40F, table)).height(Value.percentWidth(.40F, table)).center();
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(versionLable).width(Value.percentWidth(.90F, table));
        table.row();
        table.add(authorLable).width(Value.percentWidth(.90F, table));
        table.row().padTop(Value.percentHeight(.1F, table));
        table.add(backButton).center().width(Value.percentWidth(.50F, table));
    }

    @Override
    public void show() {
        //stage.clear();
        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
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
    public void resize(int width, int height) {
    }

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
