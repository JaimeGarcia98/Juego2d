package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.OptionMenu;

public class MyGdxGame extends Game {
    private Skin skin;
    
        @Override
	public void create() {
		// Use LibGDX's default Arial font.
		this.setScreen(new OptionMenu(this, true));
	}
        public void render() {
		super.render(); // important!
	}

	public void dispose() {
	}
        public Skin getSkin() {
        if (skin == null)
            skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        return skin;
    }

}
