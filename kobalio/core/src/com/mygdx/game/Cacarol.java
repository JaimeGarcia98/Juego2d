package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Cacarol extends Image {
    TextureRegion stand, jump;
    Animation walk;
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    TiledMapTileLayer layer;
    private Music salto;
    final float GRAVITY = -2.5f;
    float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;
    float MAX_VEL2 = 13f;
    int contador = 0;
    int contador2 = 0;
    public Cacarol() {
        final float width = 38;
        final float height = 24;
        this.setSize(1, height/ width);
        Texture koalaTexture = new Texture("cacarol.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);
        stand = grid[0][0];
        jump = grid[0][0];
        walk = new Animation(0.15f, grid[0][0], grid[0][1], grid[0][2],grid[0][3], grid[0][4], grid[0][5]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        salto = Gdx.audio.newMusic(Gdx.files.internal("jump.wav"));
    }

    public float getMAX_VEL2() {
        return MAX_VEL2;
    }

    public void setMAX_VEL2(float MAX_VEL2) {
        this.MAX_VEL2 = MAX_VEL2;
    }

    public float getMAX_VELOCITY() {
        return MAX_VELOCITY;
    }

    public void setMAX_VELOCITY(float MAX_VELOCITY) {
        this.MAX_VELOCITY = MAX_VELOCITY;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int getContador2() {
        return contador2;
    }

    public void setContador2(int contador2) {
        this.contador2 = contador2;
    }

    @Override
    public void act(float delta) {
        time = time + delta;

        boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched) {
            if (canJump) {
                salto.play();
                yVelocity = yVelocity + MAX_VELOCITY * 4;
                if(contador > 0){
                    MAX_VELOCITY = 10f;
                }
                contador = 0;
            }
            canJump = false;
        }

        boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftTouched) {
            xVelocity = -1 * MAX_VEL2;
            if(time > 5){
                MAX_VEL2 = 13f;
            }
            isFacingRight = false;
        }

        boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightTouched) {
            xVelocity = MAX_VEL2;
            if(time > 5){
                MAX_VEL2 = 13f;
            }
            isFacingRight = true;
        }
        
       /* boolean spaceBar = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            final float width = 45;
            final float height = 39;
            Texture koalaTextureEspada = new Texture("espada.png");
            TextureRegion[][] grid2 = TextureRegion.split(koalaTextureEspada, (int) width, (int) height);
            walk = new Animation(0.15f, grid2[0][0], grid2[0][1],grid2[0][2]);
            walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        }*/
        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;

        if (canMoveTo(x + xChange, y, false) == false) {
            xVelocity = xChange = 0;
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
