package com.mygdx.game;

/**
 * Created by Nguyen on 10/4/17.
 */


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MenuScreen implements Screen {
    MyGdxGame game; // Note it's "MyGdxGame" not "Game"
    // constructor to keep a reference to the main Game class
    public MenuScreen(MyGdxGame game){
        this.game = game;
    }

    public void create() {
        Gdx.app.log("MenuScreen: ","menuScreen create");
    }

    public void render(float f) {
        Gdx.app.log("MenuScreen: ","menuScreen render");
        Gdx.app.log("MenuScreen: ","About to call gameScreen");
        game.setScreen(MyGdxGame.gameScreen);
        Gdx.app.log("MenuScreen: ","gameScreen started");
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
        Gdx.app.log("MenuScreen: ","menuScreen show called");
        create();
    }
    @Override
    public void hide() {
        Gdx.app.log("MenuScreen: ","menuScreen hide called");
    }
}