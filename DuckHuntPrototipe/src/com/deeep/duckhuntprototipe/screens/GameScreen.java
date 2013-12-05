package com.deeep.duckhuntprototipe.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.deeep.duckhuntprototipe.classes.Assets;
import com.deeep.duckhuntprototipe.classes.World;
import com.deeep.duckhuntprototipe.classes.World.WorldListener;
import com.deeep.duckhuntprototipe.classes.WorldRenderer;

public class GameScreen implements Screen {

	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;

	Game game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batcher;
	World world;
	WorldListener worldListener;
	WorldRenderer renderer;
	String round;

	public GameScreen(Game game, int gameMode) {
		this.game = game;

		round = "1";
		guiCam = new OrthographicCamera(480, 320);
		guiCam.position.set(480 / 2, 320 / 2, 0);
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		worldListener = new WorldListener() {

			@Override
			public void reload() {
				Assets.reloading.play();
			}

			@Override
			public void shoot() {
				Assets.shoot.play();
			}

			@Override
			public void ducks() {
				Assets.ducks.play();
			}

		};
		world = new World(worldListener, gameMode);
		renderer = new WorldRenderer(batcher, world);
		Assets.startRound.play();
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady(deltaTime);
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		}
	}

	private void updateRunning(float deltaTime) {
		if (state == GAME_RUNNING) {
			if (Gdx.input.justTouched()) {
				// si quisiera usar algo del UI, est� este touchPoint
				guiCam.unproject(touchPoint.set(Gdx.input.getX(),
						Gdx.input.getY(), 0));
				Assets.playSound(Assets.shoot);
			}
		}
		// ApplicationType appType = Gdx.app.getType();

		/*
		 * Input code
		 */

		world.update(deltaTime);
	}

	private void updateReady(float deltaTime) {
		if (Gdx.input.justTouched())
			state = GAME_RUNNING;

		world.update(deltaTime);
	}

	public void draw(float deltaTime) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		renderer.render();

		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.enableBlending();
		batcher.begin();

		drawUI();

		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		}

		batcher.end();
	}

	private void drawUI() {
		batcher.draw(
				Assets.uiShot,
				40,
				20,
				Assets.uiShot.getRegionWidth() + Assets.uiShot.getRegionWidth()
						/ 2,
				Assets.uiShot.getRegionHeight()
						+ Assets.uiShot.getRegionHeight() / 2);
		batcher.draw(
				Assets.uiDucks,
				480 / 2 - Assets.uiDucks.getRegionWidth() / 2 - 30,
				20,
				Assets.uiDucks.getRegionWidth() * 2
						- Assets.uiDucks.getRegionWidth() / 2,
				Assets.uiDucks.getRegionHeight()
						+ Assets.uiDucks.getRegionHeight() / 2);
		batcher.draw(
				Assets.uiScore,
				480 - 100,
				20,
				Assets.uiScore.getRegionWidth()
						+ Assets.uiScore.getRegionWidth() / 2,
				Assets.uiScore.getRegionHeight()
						+ Assets.uiScore.getRegionHeight() / 2);
		Assets.font.draw(batcher, "R = " + round, 0, 0);
	}

	private void presentRunning() {
		// USER INTERFACE DRAW
		// batcher.draw(Assets.pause, 480 - 32, 32, 32, 32);
		// Assets.font.draw(batcher, scoreString, 16, 480 - 20);
	}

	private void presentReady() {
		batcher.draw(
				Assets.presentRound,
				480 / 2 - Assets.presentRound.getRegionWidth(),
				320 / 2 + 30,
				Assets.presentRound.getRegionWidth()
						+ Assets.presentRound.getRegionWidth(),
				Assets.presentRound.getRegionHeight()
						+ Assets.presentRound.getRegionHeight());
		Assets.font.setScale(0.5f, 0.5f);
		Assets.font.draw(batcher, "Round",
				480 / 2 - Assets.presentRound.getRegionWidth() / 2 - 10,
				Gdx.graphics.getHeight() / 2 + 64);
		Assets.font.draw(batcher, round,
				480 / 2 - Assets.font.getSpaceWidth() + 4,
				Gdx.graphics.getHeight() / 2 + 45);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}