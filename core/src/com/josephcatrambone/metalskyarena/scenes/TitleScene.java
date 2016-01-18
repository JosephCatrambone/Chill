package com.josephcatrambone.metalskyarena.scenes;

import com.josephcatrambone.metalskyarena.MainGame;

/**
 * Created by Jo on 1/16/2016.
 */
public class TitleScene extends KeyWaitScene {

	public static final String TITLE_BG = "title.png";

	public TitleScene() {
		super(TITLE_BG, MainGame.GameState.PLAY);
		this.clearBlack = false;
	}
}
