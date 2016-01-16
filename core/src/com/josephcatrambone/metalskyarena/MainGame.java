package com.josephcatrambone.metalskyarena;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.josephcatrambone.metalskyarena.scenes.GameOverScene;
import com.josephcatrambone.metalskyarena.scenes.PlayScene;
import com.josephcatrambone.metalskyarena.scenes.Scene;
import com.josephcatrambone.metalskyarena.scenes.TitleScene;

import java.util.Random;
import java.util.Stack;

public class MainGame extends ApplicationAdapter {
	public enum GameState {TITLE, PLAY, GAME_OVER, NUM_STATES};
	public static final Random random;
	public static final World world;
	public static final AssetManager assetManager;
	public static final Stack<Scene> scenes;

	static {
		random = new Random();
		world = new World(new Vector2(0, 0), true);
		assetManager = new AssetManager();
		scenes = new Stack<Scene>();
	}
	
	@Override
	public void create () {
		loadAllAssets();

		switchState(GameState.PLAY);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		scenes.peek().update(dt);
		scenes.peek().render(dt);
	}

	public void loadAllAssets() {
		assetManager.load("missing.png", Texture.class);
		assetManager.load("player.png", Texture.class);
		assetManager.load(GameOverScene.GAME_OVER_BG, Texture.class);
		assetManager.finishLoading();
	}

	public static void switchState(GameState newState) {
		Scene newScene = null;
		switch(newState) {
			case TITLE:
				newScene = new TitleScene();
				break;
			case PLAY:
				newScene = new PlayScene();
				break;
			case GAME_OVER:
				newScene = new GameOverScene();
				break;
		}
		if(!scenes.empty()) {
			scenes.pop().dispose();
		}
		newScene.create();
		scenes.push(newScene);
	}
}
