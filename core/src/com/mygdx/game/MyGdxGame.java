
package com.mygdx.game;



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.touchDown;

public class MyGdxGame implements ApplicationListener {
	// Create a new box2d world with gravity down in the y direction
	//     set default sleep true for bodies that are not moving
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

	SpriteBatch batch;
	Sprite sprite;
	Texture img;
	BitmapFont font;
	Body moveableWall1;
	Body moveableWall2;
	Body moveableWall3;
	boolean rotateWall1And2 = false;

	public void addWall(float xPos, float yPos, float width, float height){
		BodyDef wallBodyDef = new BodyDef();
		wallBodyDef.position.set(new Vector2(xPos, yPos));
		Body wallBody = world.createBody(wallBodyDef);
		PolygonShape wallBox = new PolygonShape();
		wallBox.setAsBox(width, height);
		wallBody.createFixture(wallBox, 1.0f);
	}
	public Body addMoveableWall(float xPos, float yPos, float width, float height){
		BodyDef wallBodyDef = new BodyDef();
		wallBodyDef.position.set(new Vector2(xPos, yPos));
		Body wallBody = world.createBody(wallBodyDef);
		PolygonShape wallBox = new PolygonShape();
		wallBox.setAsBox(width, height);
		wallBody.createFixture(wallBox, 1.0f);
		return wallBody;
	}

	@Override
	public void create() {
		level =0;
		font = new BitmapFont(Gdx.files.internal("uidata/default.fnt"));

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
/*		BodyDef groundBodyDef =new BodyDef();
        BodyDef topBodyDef = new BodyDef();
        BodyDef leftBodyDef = new BodyDef();
        BodyDef rightBodyDef = new BodyDef();
		BodyDef obstacleBodyDef = new BodyDef();
		BodyDef wall = new BodyDef();
		BodyDef wall2 = new BodyDef();*/
		BodyDef goalBodyDef = new BodyDef();

		// The position of the body will be at the bottom of the screen half way along
/*		groundBodyDef.position.set(new Vector2(0, 1));
        topBodyDef.position.set(new Vector2(0, 319));
        leftBodyDef.position.set(new Vector2(1, 160));
        rightBodyDef.position.set(new Vector2(479, 160));
		obstacleBodyDef.position.set(new Vector2(220,200));
		wall.position.set(new Vector2(130,110));
		wall2.position.set(new Vector2(330,100));*/
		goalBodyDef.position.set(new Vector2(420,50));

		// Add the body to the world
/*		Body groundBody = world.createBody(groundBodyDef);
        Body topBody = world.createBody(topBodyDef);
        Body leftBody = world.createBody(leftBodyDef);
        Body rightBody = world.createBody(rightBodyDef);
		Body obstacleBody = world.createBody(obstacleBodyDef);
		Body wallBody = world.createBody(wall);
		Body wallBody2 = world.createBody(wall2);*/
		Body goalBody = world.createBody(goalBodyDef);

		// Select a shape for the fixture of the ground
		// Its a polygon that will be defined as a box
/*		PolygonShape groundBox = new PolygonShape();
        PolygonShape topBox = new PolygonShape();
        PolygonShape leftBox = new PolygonShape();
        PolygonShape rightBox = new PolygonShape();
		PolygonShape obstacleBox = new PolygonShape();
		PolygonShape wallBox = new PolygonShape();*/
		PolygonShape goalBox = new PolygonShape();

		// set the size of the box to fill the camera viewport width
		// and hence the width of the screen
		/*groundBox.setAsBox((camera.viewportWidth) * 2, 10.0f);
        topBox.setAsBox((camera.viewportWidth) * 2, 10.0f);
        leftBox.setAsBox(10.0f, (camera.viewportHeight) * 2);
        rightBox.setAsBox(10.0f, (camera.viewportHeight) * 2);
		obstacleBox.setAsBox(40.0f, 40.0f);
		wallBox.setAsBox(40.0f, 55.0f);*/
		goalBox.setAsBox(20.0f, 20.0f);


		//TODO use this thing to make the basic walls now
		//side walls
		addWall(0,1,(camera.viewportWidth) * 2, 10.0f);
		addWall(0,319,(camera.viewportWidth) * 2, 10.0f);
		addWall(1,160,10.0f, (camera.viewportHeight) * 2);
		addWall(479,160,10.0f, (camera.viewportHeight) * 2);



	/*	addWall(70,80,10,80);
		addWall(70,300,10,80);
		addWall(170,80,10,80);
		addWall(170,300,10,80);
		addWall(250,80,10,80);
		addWall(250,300,10,80);
		addWall(340,80,10,70);
		addWall(340,300,10,70);*/

		//Tung's level
		Random ranNum = new Random();
		level = 8;
		if(level < 3) {
			//addWall(170, 80, 10, 80);

			addWall(ranNum.nextInt(10)+150, 120, ranNum.nextInt(5)+10, 110);
			addWall(ranNum.nextInt(20)+270, 200, ranNum.nextInt(5)+10, 110);
			if(ranNum.nextBoolean() == true){
				addWall(ranNum.nextInt(20)+350, 120, ranNum.nextInt(5)+10, 110);
			}
		}
		else if(level < 4) {
			//addWall(170, 80, 10, 80);

			addWall(ranNum.nextInt(10)+150, 120, ranNum.nextInt(3)+5, 110);
			addWall(ranNum.nextInt(10)+200, 200, ranNum.nextInt(3)+5, 110);
			addWall(ranNum.nextInt(10)+250, 120, ranNum.nextInt(3)+5, 110);

			if(new Random().nextBoolean() == false){
				addWall(ranNum.nextInt(10)+350, 120, ranNum.nextInt(3)+5, 110);
			}
		}
		else if(level < 5) {
			//addWall(170, 80, 10, 80);

			addWall(ranNum.nextInt(10)+150, 120, ranNum.nextInt(3)+5, 110);
			addWall(ranNum.nextInt(10)+200, 200, ranNum.nextInt(3)+5, 110);
			addWall(250, 120, ranNum.nextInt(3)+5, 110);
			addWall(306, 220, 50, 10);

			if(new Random().nextBoolean() == false){
				addWall(410, 110, 60, 10);
			}
		}

		else if(level < 6) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(ranNum.nextInt(10)+210, 200, ranNum.nextInt(3)+5, 110);
			moveableWall2  = addMoveableWall(270, 260, 10, 50);
			addWall(ranNum.nextInt(10)+350, 120, ranNum.nextInt(3)+5, 110);

		}
		else if(level < 7) {
			moveableWall1 = addMoveableWall(150, 60, 10, 50);
			addWall(ranNum.nextInt(10)+210, 200, ranNum.nextInt(5)+10, 110);
			moveableWall2  = addMoveableWall(270, 260, 10, 50);
			addWall(ranNum.nextInt(10)+320, 120, ranNum.nextInt(5)+10, 110);
		}
		else if(level < 8) {
			addWall(150, 260, 15, 50);
			addWall(150, 60, 15, 50);
			addWall(350, 260, 15, 50);
			addWall(350, 60 ,15, 50);
			moveableWall3 = addMoveableWall(250, 160, 5, 70);

			//moveableWall2  = addMoveableWall(270, 260, 10, 50);
		}
		else if(level < 9) {
			addWall(150, 180, 10, 130);
			addWall(225, 140, 10, 130);
			addWall(300, 180, 10, 130);
			addWall(375, 140 ,10, 130);
			//moveableWall3 = addMoveableWall(250, 160, 5, 70);

			//moveableWall2  = addMoveableWall(270, 260, 10, 50);
		}
		//later level probably
		//obstacle walls
/*		addWall(70,80,10,90);
		addWall(130,110,20,50);
		addWall(250,200,50,50);
		addWall(400,300,40,30);*/





		// Add the fixture to the ground body
/*		groundBody.createFixture(groundBox, 0.0f);
        topBody.createFixture(topBox, 0.0f);
        leftBody.createFixture(leftBox, 0.0f);
        rightBody.createFixture(rightBox, 0.0f);
		obstacleBody.createFixture(obstacleBox, 1.0f);
		wallBody.createFixture(wallBox, 1.0f);
		wallBody2.createFixture(wallBox, 1.0f);*/
		goalBody.createFixture(goalBox, 1.0f);



		//TODO i think we want to make the goal box a sensor
		//this means it won't collide and will just tell us when it's hit not bounce off it
		FixtureDef goalFixture = new FixtureDef();
		goalFixture.isSensor= true;



		// Create the body for the ball
		BodyDef bodyDef = new BodyDef();

		// Make the ball a dynamic body so that it can be accelerated
		// and can collide with the ground and bounce
		bodyDef.type = BodyType.DynamicBody;

		// Start the ball positioned near the top of the viewport
		bodyDef.position.set(camera.viewportWidth / 5, camera.viewportHeight / 2f);

		// add the ball to the world
		body = world.createBody(bodyDef);

		//TOM'S CHANGES IN HERE !!!!!
		body.setFixedRotation(true);
		body.setLinearDamping(.5f);



		// Give the ball a circular shape
		CircleShape dynamicCircle = new CircleShape();

		// Set the size of the ball shape
		dynamicCircle.setRadius(5f);

		// Create a new fixture
		FixtureDef fixtureDef = new FixtureDef();
		// Give it the circle shape
		fixtureDef.shape = dynamicCircle;

		// Give it a density so the ball will have mass
		fixtureDef.density = 1.0f;

		// Ignore friction
		fixtureDef.friction = 0.0f;

		// Enable bounce
		fixtureDef.restitution = 1f;

		// add the fixture to the ball body
		body.createFixture(fixtureDef);

		//TODO make this appear and then have it's position match the physics body all the time
		batch = new SpriteBatch();
		img = new Texture("player.png");
		sprite = new Sprite(img);


		//sprite.setPosition(camera.viewportWidth / 2, camera.viewportHeight / 1.1f);


		// create a new debug renderer
		debugRenderer = new Box2DDebugRenderer();

	}
	@Override
	public void dispose() {
	}

	boolean moving = false;
	int turnsRemaining = 9999;
	int level;
	double time = 120;
	@Override
	public void render() {
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
			if( level == 5){
				wall1And2Speed = 2;
			}
			else if(level == 6 ){
				wall1And2Speed = 3;
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
			moveableWall3.setTransform(moveableWall3.getPosition().x,moveableWall3.getPosition().y, moveableWall3.getAngle()-1);
		}

		//TODO fix this, also need to do a rotation update, will need to convert radians and degrees
		//TODO so it moves in the same direction as the body and stuff
		//one of their scales is in gdx graphics the other in camera or something
		//they don't have same position
		// 1 degree = 0.0174533 radians
		batch.begin();
		sprite.setRotation(body.getAngle()*57.298f);
		batch.draw(sprite, body.getPosition().x, body.getPosition().y);
		batch.end();

		//This code sets the direction to the way the circle is facing
		// and sets the speed it travels in that direction to moveSpeed
		int moveSpeed = 30000;
		double xDirectionD = (Math.cos(body.getAngle()));
		float xDirection = (float) xDirectionD;
		double yDirectionD = (Math.sin(body.getAngle()));
		float yDirection = (float) yDirectionD;
		Vector2 moveVelocity = new Vector2(xDirection * moveSpeed, yDirection * moveSpeed);

		//TODO may need to change to inertia or something if an issue with bouncing off walls occurs
		if(Math.abs(body.getLinearVelocity().x)<=5 && Math.abs(body.getLinearVelocity().y)<=5) {
			moving=false;
		}

		if (!moving){
			body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() + (0.0174533f*4));
		}

		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !moving || (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !moving) && turnsRemaining>0) {
			body.applyLinearImpulse(moveVelocity, body.getMassData().center, true);
			moving=true;
			turnsRemaining--;
		}

		if (turnsRemaining==0){
			//TODO implement end game screen
		}

		//TODO add box with collision for goal

		//TODO add sprite for both player and goal and map them appropriately
		//TODO add some more walls so it is like a game
		//TODO TUNG is doing add UI that displays turns remaining
		String turn;
		turn ="Turn Remaining: " +turnsRemaining;
		batch.begin();
		font.draw(batch,turn,Gdx.graphics.getWidth()/2 +650f,Gdx.graphics.getHeight()/2+499f );

		String currentLevel;
		currentLevel = "Current Level: "+ level;
		font.draw(batch,currentLevel,Gdx.graphics.getWidth()/2 -830f,Gdx.graphics.getHeight()/2+499f );
		batch.end();
		//TODO build a couple levels so it transitions appropriately from one to another
		//TODO polish


		//TODO build a couple levels so it transitions appropriately from one to another
		//TODO polish











		//	}

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


}
