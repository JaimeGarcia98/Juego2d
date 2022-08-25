package com.mygdx.game;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

public class OptionMenu implements Screen {
  MyGdxGame game;
  boolean sonido = true;
  private Stage stage;
	
    public OptionMenu(MyGdxGame game, boolean soni) {
        this.game = game;
	this.sonido = soni;
    }

    @Override
    public void show() {

        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        Label title = new Label("Castillos Y Mazmorras v1.0", game.getSkin());
        title.setFontScale(1.5f);

        TextButton playButton = new TextButton("JUGAR", game.getSkin());
        playButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PersonajeScreen(game, sonido));
            }
        });
        TextButton soundButton = new TextButton("SONIDO", game.getSkin());
        soundButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SoundScreen(game, sonido));
            }
        });
        TextButton helpButton = new TextButton("AYUDA", game.getSkin());
        helpButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new HelpScreen(game, sonido));
            }
        });
        TextButton exitButton = new TextButton("SALIR", game.getSkin());
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                System.exit(0);
            }
        });

        table.row().height(200);
        table.add(title).center().pad(30f);
        table.row().height(40);
        table.add(playButton).center().width(500).pad(5f);
        table.row().height(40);
        table.add(soundButton).center().width(500).pad(5f);
        table.row().height(40);
        table.add(helpButton).center().width(500).pad(5f);
        table.row().height(40);
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

