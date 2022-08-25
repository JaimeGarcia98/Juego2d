package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Ene2 extends Image {
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

    public Ene2() {
        final float width = 55;
        final float height = 52;
        this.setSize(1, height/ width);
        Texture koalaTexture = new Texture("ene2.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);
        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][0], grid[0][1]);
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

            if (segundavuelta == true) {
                xVelocity = MAX_VELOCITY;
                isFacingRight = true;
            }
        } else if (isFacingRight == true) {
            xVelocity = MAX_VELOCITY;
            isFacingRight = true;
            segundavuelta = false;
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

        this.setPosition(x + xChange, y + yChange);//Para que se mueva y se fije la posicion constantemente

        xVelocity = xVelocity * DAMPING;//Derrapar
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
