package com.josephcatrambone.metalskyarena.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.josephcatrambone.metalskyarena.Level;
import com.josephcatrambone.metalskyarena.actors.Player;

/**
 * Created by Jo on 1/12/2016.
 */
public class RegionContactListener implements ContactListener {

	public boolean playerCooling;

	@Override
	public void beginContact(Contact contact) {
		if(playerCoolerCollision(contact)) {
			playerCooling = true;
			System.out.println("All cool!");
		}
	}

	@Override
	public void endContact(Contact contact) {
		if(playerCoolerCollision(contact)) {
			playerCooling = false;
			System.out.println("Warming up!");
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

	private boolean playerCoolerCollision(Contact contact) {
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();

		String ad = (String)fa.getUserData();
		String bd = (String)fb.getUserData();
		if(ad != null && bd != null) {
			if(ad.equals(Player.PLAYER_USER_DATA) && bd.equals(Level.COOL_TYPE)) {
				return true;
			} else if(ad.equals(Level.COOL_TYPE) && bd.equals(Player.PLAYER_USER_DATA)) {
				return true;
			}
		}

		return false;
	}
}
