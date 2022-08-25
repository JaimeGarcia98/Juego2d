/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.OptionMenu;

/**
 *
 * @author jaime
 */
public class SoundScreen implements Screen {
  MyGdxGame game;
  boolean sonido = true;
  private Stage stage;
    public SoundScreen(MyGdxGame gam, boolean soni) {
        this.game = gam;
        this.sonido = soni;
    }

    public void show() {

        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        Label title = new Label("SONIDO OPCIONES", game.getSkin());
        title.setFontScale(2.5f);

        TextButton onButton = new TextButton("ON", game.getSkin());
        onButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sonido = true;
                game.setScreen(new OptionMenu(game, sonido));
            }
        });
        TextButton offButton = new TextButton("OFF", game.getSkin());
        offButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sonido = false;
                game.setScreen(new OptionMenu(game, sonido));
            }
        });

        table.row().height(200);
        table.add(title).center().pad(35f);
        table.row().height(75);
        table.add(onButton).center().width(500).pad(5f);
        table.row().height(75);
        table.row().height(75);
        table.add(offButton).center().width(500).pad(5f);
        table.row().height(75);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
        stage.draw();
	}

	public void resize(int width, int height) {
        //stage.setViewport(new FitViewport(width, height));
	}

	public void hide() {
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}
}
