package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.Font;
import java.util.Random;

public class MyGdxGame extends Game implements ApplicationListener,Screen {
	// Create a new box2d world with gravity down in the y direction
	// set default sleep true for bodies that are not moving
	World world = new World(new Vector2(0, 0), true);

	// The built in renderer of box2d for debugging
	Box2DDebugRenderer debugRenderer;
	// Box2d operates in a floating point world so use
	// an orthographic camera to draw on the screen
	OrthographicCamera camera;
	// set the time step for the physics simulation to 1/60 second
	static final float BOX_STEP=1/60f;
	// These are internal simulation parameters see the documentation
	static final int BOX_VELOCITY_ITERATIONS=6;
	static final int BOX_POSITION_ITERATIONS=2;

	Body body;
	//Animation goalAnim;



	SpriteBatch batch;


	Sprite sprite;
	Sprite goalSprite;
	Sprite backgroundSprite;
	Sprite backgroundSprite2;

	Texture img;
	static int level=1;
	BitmapFont font;

	public static Texture backgroundTexture;
	public static Texture backgroundTexture2;

	Body[] wallBodies = new Body[20];
	int wallBodiesCount = 0;
	int turnsRemaining;

	Body moveableWall1;
	Body moveableWall2;
	Body moveableWall3;
	Body moveableWall4;
	Body moveableWall5;
	Body moveableWall6;
	Body moveableWall7;
	boolean rotateWall1And2 = false;
	boolean displayOption = false;
	MyGame game;
	private Stage stage;
	private Stage stageForLabel;
	Music music;

	ParticleSystem par;
	boolean wasTouched;

	public static int reachedLevel;
	public boolean createdMenu;

	public MyGdxGame(MyGame game){
		this.game = game;
		stage = new Stage();
		stageForLabel = new Stage();
		par = new ParticleSystem();
		par.init();
	}


	public void addWall(float xPos, float yPos, float width, float height){
		BodyDef wallBodyDef = new BodyDef();
		wallBodyDef.position.set(new Vector2(xPos, yPos));
		Body wallBody = world.createBody(wallBodyDef);

		PolygonShape wallBox = new PolygonShape();
		wallBox.setAsBox(width, height);
		wallBody.createFixture(wallBox, 1.0f);
		wallBodies[wallBodiesCount] = wallBody;
		wallBodiesCount++;
	}
	public Body addMoveableWall(float xPos, float yPos, float width, float height){
		BodyDef wallBodyDef = new BodyDef();
		wallBodyDef.position.set(new Vector2(xPos, yPos));
		Body wallBody = world.createBody(wallBodyDef);
		PolygonShape wallBox = new PolygonShape();
		wallBox.setAsBox(width, height);
		wallBody.createFixture(wallBox, 1.0f);
		wallBodies[wallBodiesCount] = wallBody;
		wallBodiesCount++;
		return wallBody;

	}

	public void displayButton(){
		Skin skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
		TextButton restartButton = new TextButton("Reset", skin, "default");
		restartButton.setColor(Color.ORANGE);
		restartButton.setWidth(100);
		restartButton.setHeight(100);
		restartButton.getLabel().setFontScale(2);
		restartButton.setPosition(Gdx.graphics.getWidth() /2-1200f, Gdx.graphics.getHeight()/2+450f);
		restartButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				for (int i=0; i<wallBodies.length; i++){
					if (wallBodies[i]!=null) {
						world.destroyBody(wallBodies[i]);
					}
				}
				world.destroyBody(body);
				music.stop();
				create();
			}
		});
		TextButton musicButton = new TextButton("Music", skin, "default");
		musicButton.setColor(Color.ORANGE);
		musicButton.setWidth(100);
		musicButton.setHeight(100);
		musicButton.getLabel().setFontScale(2);
		musicButton.setPosition(Gdx.graphics.getWidth() /2-1200f, Gdx.graphics.getHeight()/2+350f);
		musicButton.addListener(new ClickListener(){

			public void clicked(InputEvent event, float x, float y){
				if(music.isPlaying() == true) {
					music.stop();
				}
				else{
					music.play();
				}


			}
		});
		TextButton nextLevelButton = new TextButton("Next", skin, "default");
		nextLevelButton.setColor(Color.ORANGE);
		nextLevelButton.setWidth(100);
		nextLevelButton.setHeight(100);
		nextLevelButton.getLabel().setFontScale(2);
		nextLevelButton.setPosition(Gdx.graphics.getWidth() /2-1200f, Gdx.graphics.getHeight()/2+250f);
		nextLevelButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				for (int i=0; i<wallBodies.length; i++) {
					if (wallBodies[i] != null) {
						world.destroyBody(wallBodies[i]);
					}
				}
				world.destroyBody(body);
				level ++;
				music.stop();
				create();


			}
		});
		TextButton backButton = new TextButton("Exit", skin, "default");
		backButton.setColor(Color.ORANGE);
		backButton.setWidth(100);
		backButton.setHeight(100);
		backButton.getLabel().setFontScale(2);
		backButton.setPosition(Gdx.graphics.getWidth() /2-1200f, Gdx.graphics.getHeight()/2+150);
		backButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				turnsRemaining =0;
				music.stop();
			}
		});

		stage.addActor(restartButton);
		stage.addActor(nextLevelButton);
		stage.addActor(backButton);
		stage.addActor(musicButton);


	}

	public void createMenuButton(){

	}

	@Override
	public void create() {

		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setVolume(0.99f);
		music.setLooping(true);
		music.play();

		backgroundTexture = new Texture("background1.png");
		backgroundTexture2 = new Texture("background2.png");

		backgroundSprite =new Sprite(backgroundTexture);
		backgroundSprite2 =new Sprite(backgroundTexture2);

		//Create option
		Skin skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
		final TextButton menuButton = new TextButton("Menu", skin, "default");
		createdMenu = false;
		menuButton.setColor(Color.RED);
		menuButton.setWidth(100);
		menuButton.setHeight(100);
		menuButton.getLabel().setFontScale(2);
		//TODO this is only here so i can find the menu button location in future
		menuButton.setPosition(Gdx.graphics.getWidth() /2-1200f, Gdx.graphics.getHeight()/2+550);
		menuButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){

				if(stage.getActors().size == 1) {
					displayButton();
				}
				else{
					while (stage.getActors().size > 1){
						if(stage.getActors().get(0).equals(menuButton)) {
							stage.getActors().removeIndex(1);
						}
						else{
							stage.getActors().removeIndex(0);

						}
					}
				}
			}
		});
		stage.addActor(menuButton);
		font = new BitmapFont(Gdx.files.internal("uidata/default.fnt"));
		for (int i=0; i<wallBodies.length; i++){
			wallBodies[i]=null;
		}
		// Use a camera to map from box2d to screen co-ordinates
		camera = new OrthographicCamera();
		// Screen resolution
		camera.viewportHeight = 320;
		camera.viewportWidth = 480;
		// Select which part of the world you want the camera to see
		camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f);
		// Update the changed camera settings!
		camera.update();


		// Define the ground on which the ball will bounce
		// By default a body is static
		//BodyDef goalBodyDef = new BodyDef();

		// The position of the body will be at the bottom of the screen half way along
		//goalBodyDef.position.set(new Vector2(420,50));

		// Add the body to the world
		//Body goalBody = world.createBody(goalBodyDef);


		// Select a shape for the fixture of the ground
		// Its a polygon that will be defined as a box
		PolygonShape goalBox = new PolygonShape();

		// set the size of the box to fill the camera viewport width
		// and hence the width of the screen
		//goalBox.setAsBox(20.0f, 20.0f);

		// Add the fixture to the ground body
		//goalBody.createFixture(goalBox, 1f);

		//TODO i think we want to make the goal box a sensor
		//this means it won't collide and will just tell us when it's hit not bounce off it
		//	FixtureDef goalFixture = new FixtureDef();
		//	goalFixture.isSensor= true;

		//

		//TODO use this thing to make the basic walls now
		//side walls
		addWall(0,0,(camera.viewportWidth), 1f);
		addWall(0,camera.viewportHeight,(camera.viewportWidth), 1f);
		addWall(0,0,0, (camera.viewportHeight));
		addWall(camera.viewportWidth+1,0,1f, (camera.viewportHeight));
		Random ranNum = new Random();

		if (level==1) {
			turnsRemaining=20;
			addWall(270, 250, 40, 60);
			addWall(300, 55, 70, 40);
			wallBodiesCount=0;
			reachedLevel =1;

		}

		else if(level==2){
			turnsRemaining=20;
			addWall(150, 120, 10, 110);
			addWall(270, 200, 10, 110);
			addWall(350, 120, 10, 110);
			if(reachedLevel <2) {
				reachedLevel = 2;
			}

		}

		else if (level==3) {
			turnsRemaining=20;
			addWall(60, 110, 10, 30);
			addWall(140, 100, 30, 50);
			addWall(250, 160, 50, 80);
			addWall(400, 250, 20, 30);
			wallBodiesCount=0;
			if(reachedLevel <3) {
				reachedLevel = 3;
			}
		}
		else if(level ==4 ) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(ranNum.nextInt(10)+210, 200, ranNum.nextInt(3)+5, 110);
			moveableWall2  = addMoveableWall(270, 260, 10, 50);
			addWall(ranNum.nextInt(10)+350, 120, ranNum.nextInt(3)+5, 110);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <4) {
				reachedLevel = 4;
			}
		}

		else if (level==5) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(ranNum.nextInt(10)+210, 200, ranNum.nextInt(3)+5, 140);
			moveableWall2  = addMoveableWall(270, 260, 10, 50);
			addWall(ranNum.nextInt(10)+350, 120, ranNum.nextInt(3)+5, 150);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <5) {
				reachedLevel = 5;
			}
		}



		else if(level == 6){
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			moveableWall2  = addMoveableWall(350, 260, 10, 50);
			moveableWall3 = addMoveableWall(250, 160, 5, 75);
			addWall(250, 278, 90, 30);
			addWall(250, 42, 90, 30);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <6) {
				reachedLevel = 6;
			}
		}

		else if(level == 7){
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			moveableWall2  = addMoveableWall(350, 260, 10, 50);
			moveableWall3 = addMoveableWall(250, 160, 5, 75);
			addWall(250, 278, 90, 30);
			addWall(250, 42, 90, 30);
			wallBodiesCount=0;
			turnsRemaining = 10;
			if(reachedLevel <7) {
				reachedLevel = 7;
			}
		}

		else if(level == 8){
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			moveableWall2  = addMoveableWall(350, 260, 10, 50);
			moveableWall3 = addMoveableWall(250, 160, 5, 75);
			addWall(250, 278, 90, 30);
			addWall(250, 42, 90, 30);
			wallBodiesCount=0;
			turnsRemaining = 10;
			if(reachedLevel <8) {
				reachedLevel = 8;
			}
		}

		else if(level == 9){
			moveableWall3 = addMoveableWall(175, 70, 5, 60);
			moveableWall4 = addMoveableWall(175, 200, 5, 60);
			moveableWall5 = addMoveableWall(300, 70, 5, 60);
			moveableWall6 = addMoveableWall(300, 200, 5, 60);
			addWall(250, 289, 100, 20);
			addWall(380, 61, 15, 50);
			addWall(380, 175, 15, 30);
			addWall(380, 274, 15, 35);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <9) {
				reachedLevel = 9;
			}
		}

		else if(level == 10){
			moveableWall3 = addMoveableWall(170, 70, 5, 60);
			moveableWall4 = addMoveableWall(170, 200, 5, 60);
			moveableWall5 = addMoveableWall(300, 70, 5, 60);
			moveableWall6 = addMoveableWall(300, 200, 5, 60);
			addWall(250, 289, 100, 20);
			moveableWall1 = addMoveableWall(235, 60, 3, 50);
			moveableWall2  = addMoveableWall(360, 260, 3, 50);
			if(reachedLevel <10) {
				reachedLevel = 10;
			}
			wallBodiesCount=0;
			turnsRemaining = 25;
		}

		else if(level == 11) {
			moveableWall3=null;
			moveableWall4 =null;
			moveableWall5 =null;
			moveableWall6 =null;

			addWall(150, 111, 8, 100);
			addWall(150, 274, 8, 35);
			addWall(200, 209, 8, 100);
			addWall(200, 46, 8, 35);
			addWall(250, 126, 8, 115);
			addWall(250, 289, 8, 20);
			addWall(300, 229, 8, 80);
			addWall(300, 66, 8, 55);
			addWall(350, 150, 8, 139);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <11) {
				reachedLevel = 11;
			}
		}

		else if(level == 12) {
			moveableWall3=null;
			moveableWall4 =null;
			moveableWall5 =null;
			moveableWall6 =null;

			addWall(150, 111, 8, 100);
			addWall(150, 274, 8, 35);
			addWall(200, 209, 8, 100);
			addWall(200, 46, 8, 35);
			addWall(250, 126, 8, 115);
			addWall(250, 289, 8, 20);
			addWall(300, 229, 8, 80);
			addWall(300, 66, 8, 55);
			addWall(350, 150, 8, 139);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <12) {
				reachedLevel = 12;
			}
		}

		else if(level == 13) {
			moveableWall3=null;
			moveableWall4 =null;
			moveableWall5 =null;
			moveableWall6 =null;

			addWall(150, 111, 8, 100);
			addWall(150, 274, 8, 35);
			addWall(200, 209, 8, 100);
			addWall(200, 46, 8, 35);
			addWall(250, 126, 8, 115);
			addWall(250, 289, 8, 20);
			addWall(300, 229, 8, 80);
			addWall(300, 66, 8, 55);
			addWall(350, 150, 8, 139);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <13) {
				reachedLevel = 13;
			}
		}

		else if( level == 14){
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			moveableWall2  = addMoveableWall(350, 260, 10, 50);
			moveableWall3 = addMoveableWall(250, 160, 5, 75);
			addWall(250, 278, 90, 30);
			addWall(250, 42, 90, 30);
			wallBodiesCount=0;
			turnsRemaining = 20;
			if(reachedLevel <14) {
				reachedLevel = 14;
			}
		}


/*		else if(level == 4) {
			addWall(150, 120, 5, 110);
			addWall(200, 200, 5, 110);
			addWall(250, 120, 5, 110);
			addWall(350, 120, 5, 110);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}*/





/*		Random ranNum = new Random();
		if(level < 3) {
			//addWall(170, 80, 10, 80);


			addWall(ranNum.nextInt(10)+150, 120, ranNum.nextInt(5)+10, 110);
			addWall(ranNum.nextInt(20)+270, 200, ranNum.nextInt(5)+10, 110);
			if(ranNum.nextBoolean() == true){
				addWall(ranNum.nextInt(20)+350, 120, ranNum.nextInt(5)+10, 110);
			}
			wallBodiesCount=0;
			if(level ==1) {
				turnsRemaining = 20;
			}
			else{
				turnsRemaining = 15;
			}
		}
		else if(level == 3) {
			//addWall(170, 80, 10, 80);

			addWall(ranNum.nextInt(10)+150, 120, ranNum.nextInt(3)+5, 110);
			addWall(ranNum.nextInt(10)+200, 200, ranNum.nextInt(3)+5, 110);
			addWall(ranNum.nextInt(10)+250, 120, ranNum.nextInt(3)+5, 110);

			if(new Random().nextBoolean() == false){
				addWall(ranNum.nextInt(10)+350, 120, ranNum.nextInt(3)+5, 110);
			}
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 4) {
			//addWall(170, 80, 10, 80);
			wallBodiesCount=0;
			turnsRemaining = 20;
			addWall(ranNum.nextInt(10)+150, 120, ranNum.nextInt(3)+5, 110);
			addWall(ranNum.nextInt(10)+200, 200, ranNum.nextInt(3)+5, 110);
			addWall(250, 120, ranNum.nextInt(3)+5, 110);
			addWall(306, 220, 50, 10);

			if(new Random().nextBoolean() == false){
				addWall(410, 110, 60, 10);
			}
		}

		else if(level == 5) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(ranNum.nextInt(10)+210, 200, ranNum.nextInt(3)+5, 110);
			moveableWall2  = addMoveableWall(270, 260, 10, 50);
			addWall(ranNum.nextInt(10)+350, 120, ranNum.nextInt(3)+5, 110);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 6) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(ranNum.nextInt(10)+210, 200, ranNum.nextInt(5)+10, 110);
			moveableWall2  = addMoveableWall(270, 260, 10, 50);
			addWall(ranNum.nextInt(10)+320, 120, ranNum.nextInt(5)+10, 110);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 7) {
			addWall(150, 260, 15, 50);
			addWall(150, 60, 15, 50);
			addWall(350, 260, 15, 50);
			addWall(350, 60 ,15, 50);
			moveableWall3 = addMoveableWall(250, 160, 5, 70);
			wallBodiesCount=0;
			turnsRemaining = 20;
			//moveableWall2  = addMoveableWall(270, 260, 10, 50);
		}
		else if(level == 8) {
			addWall(150, 180, 10, 130);
			addWall(225, 140, 10, 130);
			addWall(300, 180, 10, 130);
			addWall(375, 140 ,10, 130);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 9) {
			addWall(150, 180, 10, 130);
			addWall(200, 140, 10, 130);
			addWall(250, 180, 10, 130);
			addWall(300, 140 ,10, 130);
			addWall(350, 180, 10, 130);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 10) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(200, 140, 10, 130);
			moveableWall2  = addMoveableWall(250, 260, 10, 50);
			addWall(300, 180 ,10, 130);
			addWall(350, 140, 10, 130);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 11) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(200, 140, 10, 130);
			moveableWall2  = addMoveableWall(250, 260, 10, 50);
			addWall(300, 180 ,10, 130);
			addWall(350, 140, 10, 130);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 12) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			moveableWall2  = addMoveableWall(350, 260, 10, 50);
			moveableWall3 = addMoveableWall(250, 160, 5, 75);
			addWall(250, 278, 90, 30);
			addWall(250, 42, 90, 30);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}
		else if(level == 13) {
			moveableWall3 = addMoveableWall(175, 70, 5, 60);
			moveableWall4 = addMoveableWall(175, 200, 5, 60);
			moveableWall5 = addMoveableWall(300, 70, 5, 60);
			moveableWall6 = addMoveableWall(300, 200, 5, 60);
			addWall(250, 289, 100, 20);
			addWall(380, 61, 15, 50);
			addWall(380, 175, 15, 30);
			addWall(380, 274, 15, 35);
			wallBodiesCount=0;
			turnsRemaining = 20;

		}
		else if(level == 14) {
			addWall(150, 111, 8, 100);
			addWall(150, 274, 8, 35);
			addWall(200, 209, 8, 100);
			addWall(200, 46, 8, 35);
			addWall(250, 126, 8, 115);
			addWall(250, 289, 8, 20);
			addWall(300, 229, 8, 80);
			addWall(300, 66, 8, 55);
			addWall(350, 150, 8, 139);
			wallBodiesCount=0;
			turnsRemaining = 20;
		}*/
		//goalBox.setAsBox(20.0f, 20.0f);
		//goalBody.createFixture(goalBox, 1.0f);
		//this means it won't collide and will just tell us when it's hit not bounce off it
		//	FixtureDef goalFixture = new FixtureDef();
		//	goalFixture.isSensor= true;


		// Create the body for the ball
		BodyDef bodyDef = new BodyDef();

		// Make the ball a dynamic body so that it can be accelerated
		// and can collide with the ground and bounce
		bodyDef.type = BodyType.DynamicBody;

		// Start the ball positioned near the top of the viewport
		bodyDef.position.set(camera.viewportWidth / 5, camera.viewportHeight / 2f);

		// add the ball to the world
		body = world.createBody(bodyDef);

		body.setFixedRotation(true);
		//slows the player down
		body.setLinearDamping(.8f);

		// Give the ball a circular shape
		CircleShape dynamicCircle = new CircleShape();

		// Set the size of the ball shape
		dynamicCircle.setRadius(5f);
		if(level == 7){
			dynamicCircle.setRadius(8f);
		}

		// Create a new fixture
		FixtureDef fixtureDef = new FixtureDef();
		// Give it the circle shape
		fixtureDef.shape = dynamicCircle;

		// Give it a density so the ball will have mass
		fixtureDef.density = 0.5f;

		// Ignore friction
		fixtureDef.friction = 0.0f;

		// Enable bounce
		fixtureDef.restitution = 1f;

		// add the fixture to the ball body
		body.createFixture(fixtureDef);

		//TODO make this appear and then have it's position match the physics body all the time
		batch = new SpriteBatch();
		img = new Texture("player.png");
		Texture goalImg = new Texture("rotate_1.png");
		sprite = new Sprite(img);
		goalSprite = new Sprite(goalImg);//goalSprite.

		//sprite.setPosition(camera.viewportWidth / 2, camera.viewportHeight / 1.1f);
		// create a new debug renderer
		debugRenderer = new Box2DDebugRenderer();

	}
	@Override
	public void dispose() {
		par.dispose();
	}


	boolean moving = false;


	@Override
	public void render() {


		//Makes background colour
		Gdx.gl.glClearColor(0/255f, 0/255f, 128/255f, 1);
		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// call the debug renderer
		debugRenderer.render(world, camera.combined);
		// Update the simulation
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
		//Move the wall
		if(moveableWall1 != null && moveableWall2 != null) {
			if (moveableWall1.getPosition().y > 260 || moveableWall1.getPosition().y < 60) {
				if (rotateWall1And2 == true) {
					rotateWall1And2 = false;
				} else {
					rotateWall1And2 = true;
				}
			}
			int wall1And2Speed = 0;
			if( level == 4){
				wall1And2Speed = 2;
			}
			else if(level == 5 ){
				wall1And2Speed = 3;
			}
			else if(level == 8 ){
				wall1And2Speed = 5;
			}
			else if(level == 10 ){
				wall1And2Speed = 4;

			}

			if (rotateWall1And2 == false) {
				moveableWall1.setTransform(moveableWall1.getPosition().x, wall1And2Speed + moveableWall1.getPosition().y, moveableWall1.getAngle());
			} else {
				moveableWall1.setTransform(moveableWall1.getPosition().x, -wall1And2Speed + moveableWall1.getPosition().y, moveableWall1.getAngle());

			}

			if (moveableWall2 != null) {
				if (rotateWall1And2 == false) {
					moveableWall2.setTransform(moveableWall2.getPosition().x, -wall1And2Speed + moveableWall2.getPosition().y, moveableWall2.getAngle());
				} else {
					moveableWall2.setTransform(moveableWall2.getPosition().x, +wall1And2Speed + moveableWall2.getPosition().y, moveableWall2.getAngle());
				}
			}
		}


		if( moveableWall3 != null){
			float rotateSpeed =0.04f;
			if(level == 7){
				rotateSpeed = 0.06f;
			}
			moveableWall3.setTransform(moveableWall3.getPosition().x,moveableWall3.getPosition().y, moveableWall3.getAngle()-rotateSpeed);
		}
		if( moveableWall4 != null){
			float rotateSpeed = 0.04f;
			if(level == 8){
				rotateSpeed = 0.03f;
			}
			moveableWall4.setTransform(moveableWall4.getPosition().x,moveableWall4.getPosition().y, moveableWall4.getAngle()-rotateSpeed);
		}
		if( moveableWall5 != null){
			float rotateSpeed = 0.04f;
			if(level == 8){
				rotateSpeed = 0.03f;
			}
			moveableWall5.setTransform(moveableWall5.getPosition().x,moveableWall5.getPosition().y, moveableWall5.getAngle()-rotateSpeed);
		}
		if( moveableWall6 != null){
			float rotateSpeed = 0.04f;
			if(level == 8){
				rotateSpeed = -0.04f;
			}
			moveableWall6.setTransform(moveableWall6.getPosition().x,moveableWall6.getPosition().y, moveableWall6.getAngle()-rotateSpeed);
		}
		if( moveableWall7 != null){
			float rotateSpeed = 0.04f;
			if(level == 8){
				rotateSpeed = -0.04f;
			}
			moveableWall7.setTransform(moveableWall7.getPosition().x,moveableWall7.getPosition().y, moveableWall7.getAngle()-rotateSpeed);
		}


		//TODO fix this, also need to do a rotation update, will need to convert radians and degrees
		//TODO so it moves in the same direction as the body and stuff
		//one of their scales is in gdx graphics the other in camera or something
		//they don't have same position
		// 1 degree = 0.0174533 radians
		float deltatime = Gdx.graphics.getDeltaTime();
		//par.update(deltatime);
		//par.render(batch);

		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();

		if(Gdx.input.isTouched() && !wasTouched){
			int i = par.spawn(ParticleSystem.Type.SMOKE);
			par.position[i].set(x,y);
			i = par.spawn(ParticleSystem.Type.SMOKE);
			par.position[i].set(x,y);
			i = par.spawn(ParticleSystem.Type.EXPLOSION);
			par.position[i].set(x,y);
			i = par.spawn(ParticleSystem.Type.SMOKE);
			par.position[i].set(x,y);
		}
		par.update(deltatime);
		wasTouched = Gdx.input.isTouched();
		batch.begin();
		par.render(batch);
		goalSprite.setRotation(57.298f);
		//TODO goal sprite draw
		batch.draw(goalSprite, 2030,150, 150f, 150f);//body.getPosition().x - sprite.getWidth()  / 2, body.getPosition().y - sprite.getHeight()/2);
		//if(level < 13) {
		//backgroundSprite.draw(batch, 0.6f);
		//}
		//else {
		//backgroundSprite2.draw(batch, 0.6f);
		//}

		//TODO map goal sprite to goal box, i think this can be done in create and not in every frame, maybe
		//need to change the size of it as well as the location
		//batch.draw(goalSprite, camera.viewportWidth/2, camera.viewportHeight/2);
		//Gdx.app.log("positioning sprite x", String.valueOf(sprite.get));
/*		Gdx.app.log("positioning sprite X",  String.valueOf(sprite.getRegionX()));
		Gdx.app.log("positioning sprite y",  String.valueOf(sprite.getRegionY()));
		Gdx.app.log("positioning body x",  String.valueOf(body.getPosition().x));
		Gdx.app.log("positioning body y",  String.valueOf(body.getPosition().y));*/

		batch.end();

		//This code sets the direction to the way the circle is facing
		// and sets the speed it travels in that direction to moveSpeed
		int moveSpeed = 50000;
		double xDirectionD = (Math.cos(body.getAngle()));
		float xDirection = (float) xDirectionD;
		double yDirectionD = (Math.sin(body.getAngle()));
		float yDirection = (float) yDirectionD;

		Vector2 moveVelocity = new Vector2(xDirection * moveSpeed, yDirection * moveSpeed);

		int rotationThreshold = 5;


		//TODO may need to change to inertia or something if an issue with bouncing off walls occurs
		if(Math.abs(body.getLinearVelocity().x)<=rotationThreshold && Math.abs(body.getLinearVelocity().y)<=rotationThreshold) {
			moving=false;
		}

		if (!moving){
			body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() + (0.0174533f*4));
		}

		//Only for level 11. Ball moveback
		if(level == 11){
			body.setTransform(body.getPosition().x -0.07f, body.getPosition().y, body.getAngle());

		}

		//Only for level 12. Ball moveback South-West
		if(level == 12){
			body.setTransform(body.getPosition().x -0.08f, body.getPosition().y-0.04f, body.getAngle());
		}
		//Only for level 13. Ball moveback random direction
		if(level == 13){
			Random random = new Random();
			int ranNum = random.nextInt(3)+1;
			if(ranNum == 1) {
				body.setTransform(body.getPosition().x - 0.5f, body.getPosition().y, body.getAngle());
			}
			if(ranNum == 2) {
				body.setTransform(body.getPosition().x + 0.5f, body.getPosition().y, body.getAngle());
			}
			if(ranNum == 3) {
				body.setTransform(body.getPosition().x , body.getPosition().y- 0.5f, body.getAngle());
			}
			if(ranNum == 4) {
				body.setTransform(body.getPosition().x , body.getPosition().y+ 0.5f, body.getAngle());
			}
		}

		//Only for level 14. Ball moveback random direction base on turn remaining.
		if(level == 14){
			Random random = new Random();
			int ranNum = random.nextInt(3)+1;
			if(ranNum == 1) {
				body.setTransform(body.getPosition().x - 0.75f+ turnsRemaining*0.03f, body.getPosition().y, body.getAngle());
			}
			if(ranNum == 2) {
				body.setTransform(body.getPosition().x + 0.4f- turnsRemaining*0.02f, body.getPosition().y, body.getAngle());
			}
			if(ranNum == 3) {
				body.setTransform(body.getPosition().x , body.getPosition().y- 0.75f+ turnsRemaining*0.03f, body.getAngle());
			}
			if(ranNum == 4) {
				body.setTransform(body.getPosition().x , body.getPosition().y+ 0.4f- turnsRemaining*0.02f, body.getAngle());
			}
		}

		if((Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !moving || (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !moving)) && turnsRemaining>0) {
			Gdx.app.log("right worked",  Float.toString(moveVelocity.x));
			Gdx.app.log("right worked",  Float.toString(moveVelocity.y));

			body.applyLinearImpulse(moveVelocity, body.getMassData().center, true);
			moving=true;
			turnsRemaining--;
		}

		//Press left to go to next level
		//Press left to go to next level
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			Gdx.app.log("went into create thing",  "i love uni");
			//	world.dispose();
			//TODO put the walls that you make each level into an array and delete them in here aswell
			//when running create do so based on a level variable
			//badabing badaboom
			//that should work i think
			// XD
			for (int i=0; i<wallBodies.length; i++){
				if (wallBodies[i]!=null) {
					world.destroyBody(wallBodies[i]);
				}
			}
			world.destroyBody(body);
			//if you uncomment this it will load level 2
			level++;

			create();
		}


		//press up to restart or run out of lives
		if(Gdx.input.isKeyPressed(Input.Keys.UP) || (turnsRemaining==0 && !moving)){
			Gdx.app.log("went into create thing",  "i love uni");
			//	world.dispose();
			//TODO put the walls that you make each level into an array and delete them in here aswell
			//when running create do so based on a level variable
			//badabing badaboom
			//that should work i think
			// XD
			for (int i=0; i<wallBodies.length; i++){
				if (wallBodies[i]!=null) {
					world.destroyBody(wallBodies[i]);
				}
			}
			world.destroyBody(body);
			//if you uncomment this it will load level 2
			//	level++;
			music.stop();
			create();
		}


		//Check postion of body and goal. If body is reach a goal, move to next level.
		if((body.getPosition().x < 431 && body.getPosition().x >400) && (body.getPosition().y >14 && body.getPosition().y < 75)){
			Gdx.app.log("Move to next level","");

			for (int i=0; i<wallBodies.length; i++){
				if (wallBodies[i]!=null) {
					world.destroyBody(wallBodies[i]);
				}
			}
			world.destroyBody(body);
			//Increase level by 1
			level++;
			create();
		}


		if (turnsRemaining==0){
			game.setScreen(MyGame.endGameScreen);

		}

		//TODO add box with collision for goal
		//TODO add sprite for both player and goal and map them appropriately

		String turn;
		turn ="Turns Remaining: " +turnsRemaining;
		Skin skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
		Label turnRemainingLabel = new Label(turn, skin);
		turnRemainingLabel.setPosition(Gdx.graphics.getWidth() /2-1150f, Gdx.graphics.getHeight()/2 +580f);
		turnRemainingLabel.setSize(200,200);
		turnRemainingLabel.setFontScale(3);
		turnRemainingLabel.setColor(Color.RED);
		String currentLevel;
		currentLevel = "Current Level: "+ level;
		Label levelLabel = new Label(currentLevel, skin);
		levelLabel.setPosition(Gdx.graphics.getWidth() /2+800, Gdx.graphics.getHeight()/2 +580f);
		levelLabel.setSize(200,200);
		levelLabel.setFontScale(3);
		levelLabel.setColor(Color.RED);
		stageForLabel.addActor(turnRemainingLabel);
		stageForLabel.addActor(levelLabel);
		Gdx.input.setInputProcessor(stage);
		stageForLabel.draw();
		stage.draw();
		//TODO build a couple levels so it transitions appropriately from one to another
		//TODO polish

	}


	@Override
	public void show() {
		create();
	}

	@Override
	public void render(float delta) {
		this.render();
	}

	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	//Getter
	public int getLevel(){
		return this.level;
	}
	public void setLevel(int level){
		this.level = level;
	}
}