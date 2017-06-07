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
    MyGame game;

    //Constructor
    public EndGameScreen(MyGame game){
        this.game = game;
    }

    public void create() {
        //Create label to display game over message.
//        final Label message = new Label("       Nice Try \nYou ran out of turns.", skin);
//        message.setPosition(Gdx.graphics.getWidth() /2-350f, Gdx.graphics.getHeight()/2 +400f);
//        message.setSize(600,200);
//        message.setFontScale(8);
//        message.setColor(Color.RED);
        int getBestLevel = new MyGdxGame(game).getLevel();
        String bestLevel = "You got to Level "+getBestLevel;
        final Label displayInformation = new Label(bestLevel, skin);
        displayInformation.setPosition(Gdx.graphics.getWidth() /2-550, Gdx.graphics.getHeight()/2 +150f);
        displayInformation.setSize(300,100);
        displayInformation.setFontScale(8);
        displayInformation.setColor(Color.RED);

        //Create button to exit the game.
        final TextButton confirmButton = new TextButton("Confirm", skin, "default");
        confirmButton.setColor(Color.WHITE);
        confirmButton.setWidth(500);
        confirmButton.setHeight(300);
        confirmButton.getLabel().setFontScale(5);
        confirmButton.setPosition(Gdx.graphics.getWidth() /2-300f, Gdx.graphics.getHeight()/2 -300);
        confirmButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Change to menuScreen
                game.setScreen(MyGame.menuScreen);

            }
        });
        //Add label and button to the game.
        //stage.addActor(message);
        stage.addActor(displayInformation);
        stage.addActor(confirmButton);
        Gdx.input.setInputProcessor(stage);

    }

    public void render(float f) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        Gdx.input.setInputProcessor(stage);
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
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        stage = new Stage();
        create();
    }
    @Override
    public void hide() {
    }
}
