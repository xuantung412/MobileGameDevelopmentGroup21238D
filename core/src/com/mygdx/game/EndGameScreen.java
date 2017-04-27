package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EndGameScreen implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    MyGame game; // Note it's "MyGdxGame" not "Game"
    // constructor to keep a reference to the main Game class

    public EndGameScreen(MyGame game){
        this.game = game;
    }

    public void create() {

        final Label message = new Label("Nice Try \n       You ran out of turns.", skin);
        message.setPosition(Gdx.graphics.getWidth() /2-100f, Gdx.graphics.getHeight()/2 +150f);
        message.setSize(300,100);
        message.setColor(Color.RED);

        int getBestLevel = new MyGdxGame(game).getLevel();
        String bestLevel = "Your best Level: "+getBestLevel;
        final Label displayInformation = new Label(bestLevel, skin);
        displayInformation.setPosition(Gdx.graphics.getWidth() /2-50f, Gdx.graphics.getHeight()/2 +100f);
        displayInformation.setSize(300,100);
        displayInformation.setColor(Color.RED);

        //Create button to exit the game.
        final TextButton confirmButton = new TextButton("Confirm", skin, "default");
        confirmButton.setColor(Color.WHITE);
        confirmButton.setWidth(300);
        confirmButton.setHeight(100);
        confirmButton.setPosition(Gdx.graphics.getWidth() /2-150f, Gdx.graphics.getHeight()/2 -120f);
        confirmButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(MyGame.menuScreen);

            }
        });
        stage.addActor(message);
        stage.addActor(displayInformation);
        stage.addActor(confirmButton);
        Gdx.input.setInputProcessor(stage);

    }

    public void render(float f) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        stage.draw();
        batch.end();

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
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        stage = new Stage();
        create();
    }
    @Override
    public void hide() {
        Gdx.app.log("MenuScreen: ","menuScreen hide called");
    }
}
