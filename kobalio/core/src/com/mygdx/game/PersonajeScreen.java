/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

/**
 *
 * @author jaime
 */
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.GameLevelScreen;
import com.mygdx.game.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PersonajeScreen implements Screen {
  MyGdxGame game;
  boolean sonido = true;
  private Stage stage;
  int numeroPer = 0;
	
    public PersonajeScreen(MyGdxGame game, boolean soni) {
        this.game = game;
	this.sonido = soni;
    }

    @Override
    public void show() {

        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();
        
        Label title = new Label("Elige un personaje", game.getSkin());
        title.setFontScale(1.5f);
        TextButton playButton = new TextButton("LINK", game.getSkin());
        playButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numeroPer = 0;
                game.setScreen(new GameLevelScreen(game, sonido, numeroPer));
            }
        });
        TextButton soundButton = new TextButton("MURCIEGALO", game.getSkin());
        soundButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numeroPer = 1;
                game.setScreen(new GameLevelScreen(game, sonido, numeroPer));
            }
        });
        TextButton exitButton = new TextButton("CACAROL", game.getSkin());
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                numeroPer = 2;
                game.setScreen(new GameLevelScreen(game, sonido, numeroPer));
            }
        });
        table.row().height(200);
        table.add(title).center().pad(30f);
        table.row().height(40);
        table.add(playButton).center().width(500).pad(5f);
        table.row().height(40);
        table.add(soundButton).center().width(500).pad(5f);
        table.row().height(40);
        table.add(exitButton).center().width(500).pad(5f);
        table.row().height(40);     

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
