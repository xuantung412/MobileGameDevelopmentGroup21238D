package com.mygdx.game;

/**
 * Created by Nguyen on 10/4/17.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;


public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ParticleEffect particleEffect;

    MyGdxGame game; // Note it's "MyGdxGame" not "Game"
    // constructor to keep a reference to the main Game class
    public GameScreen(MyGdxGame game){

        this.game = game;
    }

    public void create() {

    }

    public void render(float f) {

    }

    @Override
    public void dispose() { }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void show() {
        Gdx.app.log("GameScreen: ", "show called ");
    }
    @Override
    public void hide() {
    }
}
