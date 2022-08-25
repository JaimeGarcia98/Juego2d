/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 * @author jaime
 */
public class GameLevelScreen implements Screen{
  MyGdxGame game;
  boolean sonido = true;
  private Stage stage;
  String mapa;
  int numPers = 0;
	
    public GameLevelScreen (MyGdxGame game, boolean soni, int numero) {
        this.game = game;
	this.sonido = soni;
        this.numPers = numero;
    }

    @Override
    public void show() {

        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();
        
        TextButton playButton = new TextButton("FACIL", game.getSkin());
        playButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mapa = "Nivel1.tmx";
                game.setScreen(new MainScreen(game, sonido, mapa, numPers));
            }
        });
        TextButton soundButton = new TextButton("MEDIO", game.getSkin());
        soundButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mapa = "Nivel2.tmx";
                game.setScreen(new MainScreen(game, sonido, mapa, numPers));
            }
        });
        TextButton exitButton = new TextButton("AVANZADO", game.getSkin());
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mapa = "Nivel3.tmx";
                game.setScreen(new MainScreen(game, sonido, mapa, numPers));
            }
        });

        table.row().height(200);
        table.row().height(50);
        table.add(playButton).center().width(500).pad(5f);
        table.row().height(50);
        table.add(soundButton).center().width(500).pad(5f);
        table.row().height(50);
        table.add(exitButton).center().width(500).pad(5f);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
        //stage.setViewport(new FitViewport(width, height));
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
