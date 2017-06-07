package com.mygdx.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public  class MyGame extends Game implements ApplicationListener {
    /**
     * This class is used to hold all the screens
     */

    public static MenuScreen menuScreen;    //Menu screen
    public static MyGdxGame mainGame;   //Screen for the game.
    public static LevelSelectScreen levelSelectScreen;  //Level Selection screen
    public static EndGameScreen endGameScreen;  //Screen for game over.


    @Override
    public void create() {
        mainGame = new MyGdxGame(this);
        menuScreen = new MenuScreen(this);
        levelSelectScreen = new LevelSelectScreen(this);
        endGameScreen = new EndGameScreen(this);
        // Change screens to the menu
        setScreen(menuScreen);
    }

    @Override
    public void dispose() {super.dispose();}



    @Override
    public void resize(int width, int height) { super.resize(width, height);}

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
