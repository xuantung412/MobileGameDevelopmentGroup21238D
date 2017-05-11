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

public class LevelSelectScreen implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    MyGame game; // Note it's "MyGdxGame" not "Game"
    // constructor to keep a reference to the main Game class

    public LevelSelectScreen(MyGame game){
        this.game = game;
    }

    public void create() {
        final Label title = new Label("Level Selection", skin);
        title.setPosition(Gdx.graphics.getWidth() /2-30f, Gdx.graphics.getHeight()/2 +150f);
        title.setSize(300,100);
        title.setFontScale(3);
        title.setColor(Color.RED);

        //Button Level 1;
        final TextButton level1Button = new TextButton("Level 1\n Easy", skin, "default");
        level1Button.setColor(Color.ROYAL);
        level1Button.setWidth(100);
        level1Button.setHeight(100);
        level1Button.setPosition(Gdx.graphics.getWidth() /2-390f, Gdx.graphics.getHeight()/2 +50f);
        level1Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(1);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 2;
        final TextButton level2Button = new TextButton("Level 2\n Easy", skin, "default");
        level2Button.setColor(Color.ROYAL);
        level2Button.setWidth(100);
        level2Button.setHeight(100);
        level2Button.setPosition(Gdx.graphics.getWidth() /2-280f, Gdx.graphics.getHeight()/2 +50f);
        level2Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(2);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 3;
        final TextButton level3Button = new TextButton("Level 3\n Easy", skin, "default");
        level3Button.setColor(Color.ROYAL);
        level3Button.setWidth(100);
        level3Button.setHeight(100);
        level3Button.setPosition(Gdx.graphics.getWidth() /2-170f, Gdx.graphics.getHeight()/2 +50f);
        level3Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(3);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 4;
        final TextButton level4Button = new TextButton("Level 4\n Easy", skin, "default");
        level4Button.setColor(Color.ROYAL);
        level4Button.setWidth(100);
        level4Button.setHeight(100);
        level4Button.setPosition(Gdx.graphics.getWidth() /2-60f, Gdx.graphics.getHeight()/2 +50f);
        level4Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(4);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 5;
        final TextButton level5Button = new TextButton("Level 5\n Easy", skin, "default");
        level5Button.setColor(Color.ROYAL);
        level5Button.setWidth(100);
        level5Button.setHeight(100);
        level5Button.setPosition(Gdx.graphics.getWidth() /2+50, Gdx.graphics.getHeight()/2 +50f);
        level5Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(5);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 6;
        final TextButton level6Button = new TextButton("Level 6\n Normal", skin, "default");
        level6Button.setColor(Color.ROYAL);
        level6Button.setWidth(100);
        level6Button.setHeight(100);
        level6Button.setPosition(Gdx.graphics.getWidth() /2+160f, Gdx.graphics.getHeight()/2 +50f);
        level6Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(6);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 7;
        final TextButton level7Button = new TextButton("Level 7\n Normal", skin, "default");
        level7Button.setColor(Color.ROYAL);
        level7Button.setWidth(100);
        level7Button.setHeight(100);
        level7Button.setPosition(Gdx.graphics.getWidth() /2 + 270, Gdx.graphics.getHeight()/2 +50f);
        level7Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(7);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 8;
        final TextButton level8Button = new TextButton("Level 8\n Normal", skin, "default");
        level8Button.setColor(Color.ROYAL);
        level8Button.setWidth(100);
        level8Button.setHeight(100);
        level8Button.setPosition(Gdx.graphics.getWidth() /2 + -390f, Gdx.graphics.getHeight()/2 -80);
        level8Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(8);
                game.setScreen(MyGame.mainGame);
            }
        });

        //Button Level 9;
        final TextButton level9Button = new TextButton("Level 9\n Normal", skin, "default");
        level9Button.setColor(Color.ROYAL);
        level9Button.setWidth(100);
        level9Button.setHeight(100);
        level9Button.setPosition(Gdx.graphics.getWidth() /2 + -280f, Gdx.graphics.getHeight()/2 -80);
        level9Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(9);
                game.setScreen(MyGame.mainGame);
            }
        });

        //Button Level 10;
        final TextButton level10Button = new TextButton("Level 10\n Normal", skin, "default");
        level10Button.setColor(Color.ROYAL);
        level10Button.setWidth(100);
        level10Button.setHeight(100);
        level10Button.setPosition(Gdx.graphics.getWidth() /2 + -170, Gdx.graphics.getHeight()/2 -80);
        level10Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(10);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 11;
        final TextButton level11Button = new TextButton("Level 11\n Hard", skin, "default");
        level11Button.setColor(Color.ROYAL);
        level11Button.setWidth(100);
        level11Button.setHeight(100);
        level11Button.setPosition(Gdx.graphics.getWidth() /2 + -60, Gdx.graphics.getHeight()/2 -80);
        level11Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(11);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 12;
        final TextButton level12Button = new TextButton("Level 12\n Hard", skin, "default");
        level12Button.setColor(Color.ROYAL);
        level12Button.setWidth(100);
        level12Button.setHeight(100);
        level12Button.setPosition(Gdx.graphics.getWidth() /2 +50, Gdx.graphics.getHeight()/2 -80);
        level12Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(12);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 13;
        final TextButton level13Button = new TextButton("Level 13\n Hard", skin, "default");
        level13Button.setColor(Color.ROYAL);
        level13Button.setWidth(100);
        level13Button.setHeight(100);
        level13Button.setPosition(Gdx.graphics.getWidth() /2 +160, Gdx.graphics.getHeight()/2 -80);
        level13Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(13);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 14;
        final TextButton level14Button = new TextButton("Level 14\n Hard", skin, "default");
        level14Button.setColor(Color.ROYAL);
        level14Button.setWidth(100);
        level14Button.setHeight(100);
        level14Button.setPosition(Gdx.graphics.getWidth() /2 +270, Gdx.graphics.getHeight()/2 -80);
        level14Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(14);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 15;
        final TextButton level15Button = new TextButton("Level 15\n Hard", skin, "default");
        level15Button.setColor(Color.ROYAL);
        level15Button.setWidth(100);
        level15Button.setHeight(100);
        level15Button.setPosition(Gdx.graphics.getWidth() /2 + -390f, Gdx.graphics.getHeight()/2 -210f);
        level15Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(15);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 16;
        final TextButton level16Button = new TextButton("Level 16\n Hard", skin, "default");
        level16Button.setColor(Color.ROYAL);
        level16Button.setWidth(100);
        level16Button.setHeight(100);
        level16Button.setPosition(Gdx.graphics.getWidth() /2 + -280, Gdx.graphics.getHeight()/2 -210f);
        level16Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(16);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 17;
        final TextButton level17Button = new TextButton("Level 17\n Hard", skin, "default");
        level17Button.setColor(Color.ROYAL);
        level17Button.setWidth(100);
        level17Button.setHeight(100);
        level17Button.setPosition(Gdx.graphics.getWidth() /2 + -170, Gdx.graphics.getHeight()/2 -210f);
        level17Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(17);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 18;
        final TextButton level18Button = new TextButton("Level 18\n Expert", skin, "default");
        level18Button.setColor(Color.ROYAL);
        level18Button.setWidth(100);
        level18Button.setHeight(100);
        level18Button.setPosition(Gdx.graphics.getWidth() /2 + -60, Gdx.graphics.getHeight()/2 -210f);
        level18Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(18);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 19;
        final TextButton level19Button = new TextButton("Level 19\n Expert", skin, "default");
        level19Button.setColor(Color.ROYAL);
        level19Button.setWidth(100);
        level19Button.setHeight(100);
        level19Button.setPosition(Gdx.graphics.getWidth() /2 + 50, Gdx.graphics.getHeight()/2 -210f);
        level19Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(19);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Button Level 20;
        final TextButton level20Button = new TextButton("Level 20\n Pro-Player", skin, "default");
        level20Button.setColor(Color.ROYAL);
        level20Button.setWidth(100);
        level20Button.setHeight(100);
        level20Button.setPosition(Gdx.graphics.getWidth() /2 + 160, Gdx.graphics.getHeight()/2 -210f);
        level20Button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                MyGame.mainGame = new MyGdxGame(game);
                MyGame.mainGame.setLevel(20);
                game.setScreen(MyGame.mainGame);
            }
        });
        //Create button to exit the game.
        final TextButton exitButton = new TextButton("Back", skin, "default");
        exitButton.setColor(Color.WHITE);
        exitButton.setWidth(100);
        exitButton.setHeight(100);
        exitButton.setPosition(Gdx.graphics.getWidth() /2 + 270, Gdx.graphics.getHeight()/2 -210f);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(MyGame.menuScreen);

            }
        });
        stage.addActor(level1Button);
        stage.addActor(level2Button);
        stage.addActor(level3Button);
        stage.addActor(level4Button);
        stage.addActor(level5Button);
        stage.addActor(level6Button);
        stage.addActor(level7Button);
        stage.addActor(level8Button);
        stage.addActor(level9Button);
        stage.addActor(level10Button);
        stage.addActor(level11Button);
        stage.addActor(level12Button);
        stage.addActor(level13Button);
        stage.addActor(level14Button);
        stage.addActor(level15Button);
        stage.addActor(level16Button);
        stage.addActor(level17Button);
        stage.addActor(level18Button);
        stage.addActor(level19Button);
        stage.addActor(level20Button);
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
        Gdx.app.log("Screen: ","Level selection screen showed.");
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        stage = new Stage();
        create();
    }
    @Override
    public void hide() {
        Gdx.app.log("","LevelSelecScreen close.");
    }
}
