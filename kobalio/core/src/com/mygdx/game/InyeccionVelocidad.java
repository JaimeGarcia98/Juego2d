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
public class InyeccionVelocidad extends Image{
    TextureRegion ini, salt;
    Animation walk;
    float time = 1;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -3.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 1.9f;

    public TextureRegion getIni() {
        return ini;
    }

    public void setIni(TextureRegion ini) {
        this.ini = ini;
    }

    public TextureRegion getSalt() {
        return salt;
    }

    public void setSalt(TextureRegion salt) {
        this.salt = salt;
    }

    public Animation getWalk() {
        return walk;
    }

    public void setWalk(Animation walk) {
        this.walk = walk;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public boolean isCanJump() {
        return canJump;
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public boolean isIsFacingRight() {
        return isFacingRight;
    }

    public void setIsFacingRight(boolean isFacingRight) {
        this.isFacingRight = isFacingRight;
    }

    public TiledMapTileLayer getLayer() {
        return layer;
    }

    public void setLayer(TiledMapTileLayer layer) {
        this.layer = layer;
    }

    
    public InyeccionVelocidad() {
        final float width = 16;
        final float height = 16;
        this.setSize(1, height / width);

        Texture enemigoTexture = new Texture("Injector.png");
        TextureRegion[][] grid = TextureRegion.split(enemigoTexture, (int) width, (int) height);
        
        ini = grid[0][0];
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = ini;

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }
}
