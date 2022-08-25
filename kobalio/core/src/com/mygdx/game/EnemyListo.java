/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author jaime
 */
public class EnemyListo extends Image {
    TextureRegion stand, jump;
    Animation walk;
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;
    final float GRAVITY = -9.5f;
    final float MAX_VELOCITY = 1f;
    final float DAMPING = 1f;
    boolean vuelta = true;
    boolean segundavuelta = false;
    Koala koala;
    Murciegalo murci;
    Cacarol cacar;
    int numper = 0;

    public EnemyListo(Koala koal) {
        final float width = 52;
        final float height = 34;
        koala = koal;
        numper = 0;
        this.setSize(1, height/ width);
        Texture koalaTexture = new Texture("rino.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);
        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][0], grid[0][1], grid[0][2], grid[0][3]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    public EnemyListo(Murciegalo murcie) {
        final float width = 52;
        final float height = 34;
        murci = murcie;
        numper = 1;
        this.setSize(1, height/ width);
        Texture koalaTexture = new Texture("rino.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);
        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][0], grid[0][1], grid[0][2], grid[0][3]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    public EnemyListo(Cacarol caca) {
        final float width = 52;
        final float height = 34;
        cacar = caca;
        numper = 2;
        this.setSize(1, height/ width);
        Texture koalaTexture = new Texture("rino.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);
        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][0], grid[0][1], grid[0][2], grid[0][3]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    @Override
    public void act(float delta) {
        time = time + delta;

        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;
        if (canMoveTo(x + xChange, y, false) == false) {//Si se choca
            xVelocity = xChange = 0;//Se queda quieto con el objeto que se ha chocado
            xVelocity = -1 * MAX_VELOCITY;
            isFacingRight = false;
            vuelta = true;
        }
        if(numper == 0){
            if (this.getX() > koala.getX() ) {
                xVelocity = -1 * MAX_VELOCITY;
                segundavuelta = true;
                isFacingRight = false;
                
            }
            if (this.getX() < koala.getX()) {
                xVelocity = 1 * MAX_VELOCITY;
                segundavuelta = false;
                isFacingRight = true;
                
            }
        }
        if(numper == 1){
            if (this.getX() > murci.getX() ) {
                xVelocity = -1 * MAX_VELOCITY;
                segundavuelta = true;
                isFacingRight = false;
                
            }
            if (this.getX() < murci.getX()) {
                xVelocity = 1 * MAX_VELOCITY;
                segundavuelta = false;
                isFacingRight = true;
                
            }
        }
        if(numper == 2){
            if (this.getX() > cacar.getX() ) {
                xVelocity = -1 * MAX_VELOCITY;
                segundavuelta = true;
                isFacingRight = false;
                
            }
            if (this.getX() < cacar.getX()) {
                xVelocity = 1 * MAX_VELOCITY;
                segundavuelta = false;
                isFacingRight = true;
                
            }
        }
        if (vuelta == true && isFacingRight == false) {
            xVelocity = -1 * MAX_VELOCITY;
            isFacingRight = false;
            segundavuelta = true;
        }

        if (canMoveTo(x, y + yChange, yVelocity > 0) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }

        this.setPosition(x + xChange, y + yChange);

        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (yVelocity != 0) {
            frame = jump;
        } else if (xVelocity != 0) {
            frame = (TextureRegion) walk.getKeyFrame(time);
        } else {
            frame = stand;
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }

    private boolean canMoveTo(float startX, float startY, boolean shouldDestroy) {
        float endX = startX + this.getWidth();
        float endY = startY + this.getHeight();

        int x = (int) startX;
        while (x < endX) {
            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
            
        }

        return true;
    }

}