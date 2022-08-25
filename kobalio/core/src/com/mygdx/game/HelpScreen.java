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

public class HelpScreen implements Screen {
  MyGdxGame game;
  boolean sonido = true;
  private Stage stage;
	
    public HelpScreen(MyGdxGame game, boolean soni) {
        this.game = game;
	this.sonido = soni;
    }

    @Override
    public void show() {

        stage = new Stage();

        Table table = new Table(game.getSkin());
        table.setFillParent(true);
        table.center();

        Label title = new Label("Controles e Informacion", game.getSkin());
        title.setFontScale(2f);
        Label aboutLabel = new Label("El juego consiste en llegar al castillo que se \n"
                + "encuentra al final de cada pantalla\n"
                + "Para manejar al personaje principal, que en este caso \n"
                + "cuenta con 3 personajes distintos, los controles son: \n"
                + "Para moverse en horizontal utilizaremos las flechas del teclado \n"
                + "Para saltar utilizaremos la flecha que apunta hacia arriba \n"
                + "Los personajes tienen distintas caracteristicas: \n"
                + "- Link: Tiene cualidades balanceadas \n"
                + "- Murciegalo: Este personaje tiene un salto mas grande de lo normal, incluso \n"
                + "parece que vuela...\n"
                + "- Cacarol : Este personaje corre mucho mas que los demas personajes aunque\n"
                + " su apariencia engane...", game.getSkin());
        TextButton exitButton = new TextButton("VOLVER", game.getSkin());
        exitButton.addListener(new ClickListener() {
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionMenu(game, sonido));
            }
        });

        table.row().height(100);
        table.add(title).center().pad(35f);
        table.row().height(40);
        table.add(aboutLabel).center().pad(100f);
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