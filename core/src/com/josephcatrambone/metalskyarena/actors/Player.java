package com.josephcatrambone.metalskyarena.actors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.Stack;

/**
 * Created by Jo on 12/23/2015.
 */
public class Player extends Pawn {

	public static final String PLAYER_USER_DATA = "player";
	public float walkSpeed = 6.5f;
	public float temperature = 0;
	public float maxTemperature = 10f;

	public Player(int x, int y) {
		create(x, y, 8, 8, 1.0f, "player.png");

		TextureRegion[] frames = new TextureRegion[]{
				new TextureRegion(this.spriteSheet, 0, 0, 16, 16),
				new TextureRegion(this.spriteSheet, 16, 0, 16, 16)};
		animations[State.IDLE.ordinal()][Direction.DOWN.ordinal()] = new Animation(0.1f, frames);

		// Always use first fixture for labelling contact data.
		this.getBody().getFixtureList().get(0).setUserData(PLAYER_USER_DATA);
		//this.getBody().setUserData(PLAYER_USER_DATA);
	}

	@Override
	public void act(float deltaTime) {
		super.act(deltaTime);

		if(this.state == State.MOVING) {
			float dx = 0;
			float dy = 0;
			if (this.direction == Direction.RIGHT) { dx = this.walkSpeed; dy = 0; }
			if (this.direction == Direction.UP) { dx = 0; dy = this.walkSpeed; }
			if (this.direction == Direction.LEFT) { dx = -this.walkSpeed; dy = 0;	}
			if (this.direction == Direction.DOWN) { dx = 0; dy = -this.walkSpeed; }
			this.getBody().setLinearVelocity(dx, dy);
		} else {
			this.getBody().setLinearVelocity(0, 0);
		}
	}

	public void heat(float amount) {
		temperature += amount;
		if(temperature > maxTemperature) {
			temperature = maxTemperature;
			kill();
		}
	}

	public void cool(float amount) {
		temperature -= (temperature/2)*amount;
		if(temperature < 0) { temperature = 0;  }
	}

	public void kill() {

	}

	@Override
	public void draw(Batch spriteBatch, float alpha) {
		// TODO: Better linear interpolation of colors using real color theory.
		float ratio = temperature/maxTemperature + 0.1f;
		ratio = Math.max(0.0f, Math.min(1.0f, ratio)); // Clamp.
		float invRatio = 1.0f - ratio;
		spriteBatch.setColor(ratio, invRatio, invRatio, 1.0f);
		super.draw(spriteBatch, alpha);
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public InputListener getInputListener() {
		// Might be lazy programming to have an input handler here.  Find something better.
		final Player ref = this;

		// TODO: Use a key map.
		return new InputListener() {
			private Stack<Pawn.Direction> directionStack = new Stack<Pawn.Direction>(); // TODO: Figure out the cocksucking language level dogshit that's preventing diamong ops.

			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Input.Keys.D) { directionStack.push(Direction.RIGHT); }
				if(keycode == Input.Keys.W) { directionStack.push(Direction.UP); }
				if(keycode == Input.Keys.A) { directionStack.push(Direction.LEFT); }
				if(keycode == Input.Keys.S) { directionStack.push(Direction.DOWN); }

				if(!directionStack.empty()) {
					ref.direction = directionStack.peek();
					ref.state = State.MOVING;
				} else {
					ref.state = State.IDLE;
				}
				return true;
			}

			public boolean keyUp(InputEvent event, int keycode) {
				if(keycode == Input.Keys.D) { directionStack.remove(Direction.RIGHT); }
				if(keycode == Input.Keys.W) { directionStack.remove(Direction.UP); }
				if(keycode == Input.Keys.A) { directionStack.remove(Direction.LEFT); }
				if(keycode == Input.Keys.S) { directionStack.remove(Direction.DOWN); }
				// Keep looking the way we were if there are no keys.
				if(!directionStack.empty()) {
					ref.direction = directionStack.peek();
					ref.state = State.MOVING;
				} else {
					ref.state = State.IDLE;
				}
				return true;
			}
		};
	}
}
