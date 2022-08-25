package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Enemigo extends Image {
    TextureRegion ini, salt;
    Animation walk;
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;

    final float GRAVITY = -3.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 1.9f;

    public Enemigo() {
        final float width = 24;
        final float height = 24;
        this.setSize(1, height / width);

        Texture enemigoTexture = new Texture("ene.png");
        TextureRegion[][] grid = TextureRegion.split(enemigoTexture, (int) width, (int) height);
        
        ini = grid[0][0];
        salt = grid[0][0];
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
   @Override
    public void act(float delta) {
        time = time + delta;

            if (canJump) {
                yVelocity = yVelocity + MAX_VELOCITY * 3;
            }
            canJump = false;

        yVelocity = (float) (yVelocity + GRAVITY * 0.5);

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x, y + yChange) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }

        this.setPosition(x + xChange, y + yChange);
    }
      private boolean canMoveTo(float startX, float startY) {
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