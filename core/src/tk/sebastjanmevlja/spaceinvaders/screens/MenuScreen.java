package tk.sebastjanmevlja.spaceinvaders.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.main.Main;

public class MenuScreen implements Screen {

    private Main main;
    private Stage stage;
    private Skin skin;

    public MenuScreen(Main main) {
        this.main = main;
        /// create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        skin = Main.assetManager.manager.get("skin/glassy-ui.json");

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        table.defaults().width(Value.percentWidth(.65F, table));
        table.defaults().height(Value.percentHeight(.15F, table));


        //create buttons
        TextButton newGame = new TextButton("New Game", skin);
        TextButton preferences = new TextButton("Preferences", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton about = new TextButton("About", skin);

        //add buttons to table
        table.add(newGame);
        table.row().pad(80, 0, 0, 0);
        table.add(preferences);
        table.row().pad(80, 0, 0, 0);
        table.add(about);
        table.row().pad(80, 0, 0, 0);
        table.add(exit);

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });

        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.changeScreen(Screens.LEVEL1SCREEN);
            }
        });

        preferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.changeScreen(Screens.PREFERENCESSCREEN);
            }
        });

        about.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                main.changeScreen(Screens.ABOUTSCREEN);
            }
        });

    }

    @Override
    public void render(float delta) {
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
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

}