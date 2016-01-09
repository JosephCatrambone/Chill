package com.josephcatrambone.metalskyarena.actors;

import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Jo on 12/23/2015.
 */
public class Player extends Pawn {

	public Player(int x, int y) {
		super(x, y);
	}

	public InputListener getInputListener() {
		return new InputListener() {

		};
	}

}
