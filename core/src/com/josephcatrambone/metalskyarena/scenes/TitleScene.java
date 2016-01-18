package com.josephcatrambone.metalskyarena.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.josephcatrambone.metalskyarena.MainGame;

/**
 * Created by Jo on 1/16/2016.
 */
public class TitleScene extends Scene {

	public static final String TITLE_BG = "title.png";
	SpriteBatch batch;
	Camera camera;
	Texture bg;

	@Override
	public void create() {
		bg = MainGame.assetManager.get(TITLE_BG);
		camera = new OrthographicCamera(bg.getWidth(), bg.getHeight());
		batch = new SpriteBatch();
		camera.update();
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(bg, 0, 0, bg.getWidth()*4, bg.getHeight()*4);
		batch.end();
	}

	@Override
	public void update(float deltaTime) {
		if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
			MainGame.switchState(MainGame.GameState.PLAY);
		}
	}
}
