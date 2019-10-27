package tk.sebastjanmevlja.spaceinvaders.wall;

import tk.sebastjanmevlja.spaceinvaders.helpers.GameInfo;
import tk.sebastjanmevlja.spaceinvaders.main.Main;

public class MeteoritConfig {
    private static Meteorit[] meteorits;


    public static void make() {
        meteorits = new Meteorit[3];
        for (int i = 0; i < meteorits.length; i++) {
            meteorits[i] = new Meteorit((GameInfo.WIDTH / 3f * i) + GameInfo.WIDTH / 20, GameInfo.HEIGHT / 4f);
        }
    }

    public static void update() {
        for (Meteorit meteorit : meteorits) {
            meteorit.update();
        }
    }

    public static void draw(Main main) {
        for (Meteorit meteorit : meteorits) {
            meteorit.draw(main);
        }
    }


    public static Meteorit[] getMeteorits() {
        return meteorits;
    }


    public static void dispose() {
        if (meteorits != null) {
            for (int i = 0; i < meteorits.length; i++) {
                meteorits[i] = null;
            }
            meteorits = null;
        }
    }
}



