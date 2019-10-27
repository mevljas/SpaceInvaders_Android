package tk.sebastjanmevlja.spaceinvaders;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import tk.sebastjanmevlja.spaceinvaders.main.Main;

public class AndroidLauncher extends AndroidApplication {
	public static String versionName;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useWakelock = true; //screen stays on
		initialize(new Main(), config);
	}
}
