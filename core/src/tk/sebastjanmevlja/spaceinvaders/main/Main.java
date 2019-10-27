package tk.sebastjanmevlja.spaceinvaders.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tk.sebastjanmevlja.spaceinvaders.helpers.AssetManager;
import tk.sebastjanmevlja.spaceinvaders.helpers.Sound;
import tk.sebastjanmevlja.spaceinvaders.screens.AboutScreen;
import tk.sebastjanmevlja.spaceinvaders.screens.EndScreen;
import tk.sebastjanmevlja.spaceinvaders.screens.Level1Screen;
import tk.sebastjanmevlja.spaceinvaders.screens.Level2Screen;
import tk.sebastjanmevlja.spaceinvaders.screens.Level3Screen;
import tk.sebastjanmevlja.spaceinvaders.screens.LoadingScreen;
import tk.sebastjanmevlja.spaceinvaders.screens.MenuScreen;
import tk.sebastjanmevlja.spaceinvaders.screens.PreferencesScreen;
import tk.sebastjanmevlja.spaceinvaders.screens.Screens;


public class Main extends Game {

    public static int level = 1;
    public static int score = 0;
    public static AssetManager assetManager = new AssetManager();
    public static Sound sound;
    public static Main main;


    private SpriteBatch batch;
    private LoadingScreen loadingScreen;
    private PreferencesScreen preferencesScreen;
    private MenuScreen menuScreen;
    private Level1Screen level1Screen;
    private Level2Screen level2Screen;
    private Level3Screen level3Screen;
    private AboutScreen aboutScreen;
    private EndScreen endScreen;


    @Override
    public void create() {
        batch = new SpriteBatch();
        main = this;
        changeScreen(Screens.LOADINGSCREEN);
        sound = new Sound();
        Gdx.input.setCatchBackKey(true); //back key doesnt the app close
    }


    public void changeScreen(Screens screen) {
        switch (screen) {
            case LOADINGSCREEN:
                if (loadingScreen == null)
                    loadingScreen = new LoadingScreen(this);
                setScreen(loadingScreen);
                break;
            case MENUSCREEN:
                if (menuScreen == null)
                    menuScreen = new MenuScreen(this);
                setScreen(menuScreen);
                break;
            case PREFERENCESSCREEN:
                if (preferencesScreen == null)
                    preferencesScreen = new PreferencesScreen(this);
                setScreen(preferencesScreen);
                break;
            case LEVEL1SCREEN:
                if (level1Screen == null)
                    level1Screen = new Level1Screen(this);
                setScreen(level1Screen);
                break;
            case LEVEL2SCREEN:
                if (level2Screen == null)
                    level2Screen = new Level2Screen(this);
                setScreen(level2Screen);
                break;
            case LEVEL3SCREEN:
                if (level3Screen == null)
                    level3Screen = new Level3Screen(this);
                setScreen(level3Screen);
                break;
            case ENDSCREEN:
                if (endScreen == null)
                    endScreen = new EndScreen(this);
                setScreen(endScreen);
                break;
            case ABOUTSCREEN:
                if (aboutScreen == null)
                    aboutScreen = new AboutScreen(this);
                setScreen(aboutScreen);
                break;
        }
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

        batch.dispose();
        assetManager.dispose();
        aboutScreen.dispose();
        endScreen.dispose();
        level1Screen.dispose();
        level2Screen.dispose();
        level3Screen.dispose();
        menuScreen.dispose();
        loadingScreen.dispose();
        preferencesScreen.dispose();
    }

    public static int getLevel() {
        return level;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Main.score = score;
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }

}
