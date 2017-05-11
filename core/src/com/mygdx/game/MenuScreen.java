package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    MyGame game; // Note it's "MyGdxGame" not "Game"
    // constructor to keep a reference to the main Game class

    public MenuScreen(MyGame game){
        this.game = game;
    }

    public void create() {
        //Gdx.input.setOnscreenKeyboardVisible(true);
        final Label title = new Label("Welcome to the Game", skin);
        title.setPosition(Gdx.graphics.getWidth() /2-30f, Gdx.graphics.getHeight()/2 +150f);
        title.setSize(300,100);
        title.setFontScale(3);
        title.setColor(Color.RED);


        final TextButton newGameButton = new TextButton("New Game", skin, "default");
        newGameButton.setColor(Color.WHITE);
        newGameButton.setWidth(300);
        newGameButton.setHeight(100);
        newGameButton.setPosition(Gdx.graphics.getWidth() /2-110f, Gdx.graphics.getHeight()/2 +50f);
        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                game.setScreen(MyGame.levelSelectScreen);
            }
        });

        //Create button to exit the game.
        final TextButton exitButton = new TextButton("Exit", skin, "default");
        exitButton.setColor(Color.WHITE);
        exitButton.setWidth(300);
        exitButton.setHeight(100);
        exitButton.setPosition(Gdx.graphics.getWidth() /2-110f, Gdx.graphics.getHeight()/2 -120f);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();

            }
        });
        stage.addActor(newGameButton);
        stage.addActor(exitButton);
        stage.addActor(title);
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
