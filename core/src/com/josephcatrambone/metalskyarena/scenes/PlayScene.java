package com.josephcatrambone.metalskyarena.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.josephcatrambone.metalskyarena.Level;
import com.josephcatrambone.metalskyarena.MainGame;
import com.josephcatrambone.metalskyarena.actors.Player;
import com.josephcatrambone.metalskyarena.handlers.RegionContactListener;

/**
 * Created by Jo on 12/20/2015.
 */
public class PlayScene extends Scene {
	public final int PIXEL_DISPLAY_WIDTH = 160; // Ten pixels on a side?
	Stage stage;
	Camera camera;
	Level level;
	Player player;

	RegionContactListener regionContactListener;

	Box2DDebugRenderer debugRenderer;

	@Override
	public void create() {
		stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())); // Fit viewport = black bars.
		debugRenderer = new Box2DDebugRenderer();

		regionContactListener = new RegionContactListener();
		MainGame.world.setContactListener(regionContactListener);

		// Setup camera.  Enforce y-up.
		float invAspectRatio = stage.getHeight()/stage.getWidth();
		camera = stage.getCamera();
		((OrthographicCamera)camera).setToOrtho(false, PIXEL_DISPLAY_WIDTH, PIXEL_DISPLAY_WIDTH*invAspectRatio);
		camera.update(true);

		level = new Level("test.tmx");

		player = new Player(level.getPlayerStartX(), level.getPlayerStartY());
		stage.addActor(player);

		// Global input listener if needed.
		stage.addListener(player.getInputListener());

		// TODO: When resuming, restore input processors.
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void dispose() {
		level.dispose();
		stage.dispose();
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		level.drawBG(camera);
		stage.draw();
		//debugRenderer.render(MainGame.world, camera.combined);
		level.drawOverlay(camera);
	}

	@Override
	public void update(float deltaTime) {
		MainGame.world.step(deltaTime, 8, 3);
		stage.act(deltaTime);

		// Update player's heat.
		if(regionContactListener.playerCooling) {
			player.cool(deltaTime);
		} else {
			player.heat(deltaTime);
		}

		// Camera follows player?
		camera.position.set(player.getX(), player.getY(), camera.position.z);
		camera.update();
	}

}
