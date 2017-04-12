
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Timer;

import java.awt.Color;
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
	BitmapFont font;
    Texture img;

	@Override
	public void create() {

<<<<<<< HEAD


=======
		font = new BitmapFont(Gdx.files.internal("uidata/default.fnt"));
>>>>>>> origin/master

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
		BodyDef groundBodyDef =new BodyDef();
        BodyDef topBodyDef = new BodyDef();
        BodyDef leftBodyDef = new BodyDef();
        BodyDef rightBodyDef = new BodyDef();
		BodyDef obstacleBodyDef = new BodyDef();
		// The position of the body will be at the bottom of the screen half way along
		groundBodyDef.position.set(new Vector2(0, 10));
        topBodyDef.position.set(new Vector2(0, 310));
        leftBodyDef.position.set(new Vector2(10, 160));
        rightBodyDef.position.set(new Vector2(470, 160));
		obstacleBodyDef.position.set(new Vector2(240,160));
		// Add the body to the world
		Body groundBody = world.createBody(groundBodyDef);
        Body topBody = world.createBody(topBodyDef);
        Body leftBody = world.createBody(leftBodyDef);
        Body rightBody = world.createBody(rightBodyDef);
		Body obstacleBody = world.createBody(obstacleBodyDef);
		// Select a shape for the fixture of the ground
		// Its a polygon that will be defined as a box
		PolygonShape groundBox = new PolygonShape();
        PolygonShape topBox = new PolygonShape();
        PolygonShape leftBox = new PolygonShape();
        PolygonShape rightBox = new PolygonShape();
		PolygonShape obstacleBox = new PolygonShape();
		// set the size of the box to fill the camera viewport width
		// and hence the width of the screen
		groundBox.setAsBox((camera.viewportWidth) * 2, 10.0f);
        topBox.setAsBox((camera.viewportWidth) * 2, 10.0f);
        leftBox.setAsBox(10.0f, (camera.viewportHeight) * 2);
        rightBox.setAsBox(10.0f, (camera.viewportHeight) * 2);
		obstacleBox.setAsBox(10.0f, 10.0f);
		// Add the fixture to the ground body
		groundBody.createFixture(groundBox, 0.0f);
        topBody.createFixture(topBox, 0.0f);
        leftBody.createFixture(leftBox, 0.0f);
        rightBody.createFixture(rightBox, 0.0f);
		obstacleBody.createFixture(obstacleBox, 1.0f);
		// Create the body for the ball
		BodyDef bodyDef = new BodyDef();

		// Make the ball a dynamic body so that it can be accelerated
		// and can collide with the ground and bounce
		bodyDef.type = BodyType.DynamicBody;

		// Start the ball positioned near the top of the viewport
		bodyDef.position.set(camera.viewportWidth / 2, camera.viewportHeight / 1.1f);

		// add the ball to the world
		body = world.createBody(bodyDef);

		//TOM'S CHANGES IN HERE !!!!!
		body.setFixedRotation(true);
		body.setLinearDamping(1f);



		// Give the ball a circular shape
		CircleShape dynamicCircle = new CircleShape();

		// Set the size of the ball shape
		dynamicCircle.setRadius(5f);

        // Create a new fixture
		FixtureDef fixtureDef = new FixtureDef();
		FixtureDef fixtureDef2 = new FixtureDef();
		// Give it the circle shape
		fixtureDef.shape = dynamicCircle;

		// Give it a density so the ball will have mass
		fixtureDef.density = 1.0f;

		// Ignore friction
		fixtureDef.friction = 0.0f;

		// Enable bounce
		fixtureDef.restitution = 0.95f;

		// add the fixture to the ball body
		body.createFixture(fixtureDef);


        //TODO make this appear and then have it's position match the physics body all the time
        batch = new SpriteBatch();
        img = new Texture("player.png");
        sprite = new Sprite(img);

        sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                Gdx.graphics.getHeight() / 2);



		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.position.set(0, 0);
		bd.type = BodyType.DynamicBody;

		// 2. Create a FixtureDef, as usual.
		FixtureDef fd = new FixtureDef();
		fd.density = 1;
		fd.friction = 0.5f;
		fd.restitution = 0.3f;

		// 3. Create a Body, as usual.
		Body player;
		player = world.createBody(bd);

		bd.position.set(camera.viewportWidth / 2, camera.viewportHeight / 1.1f);
		sprite.setPosition(camera.viewportWidth / 2, camera.viewportHeight / 1.1f);

		// create a new debug renderer
		debugRenderer = new Box2DDebugRenderer();

	}
	@Override
	public void dispose() {
	}


	boolean moving = false;
	int turnsRemaining = 9999;


	@Override
	public void render() {
		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// call the debug renderer
		debugRenderer.render(world, camera.combined);
		// Update the simulation
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);



		//This code sets the direction to the way the circle is facing
		// and sets the speed it travels in that direction to moveSpeed
		int moveSpeed = 10000;
		double xDirectionD = (Math.cos(body.getAngle()));
		float xDirection = (float) xDirectionD;
		double yDirectionD = (Math.sin(body.getAngle()));
		float yDirection = (float) yDirectionD;

		Vector2 moveVelocity = new Vector2(xDirection * moveSpeed, yDirection * moveSpeed);



		//TODO may need to change to inertia or something if an issue with bouncing off walls occurs
		if(Math.abs(body.getLinearVelocity().x)<=2 && Math.abs(body.getLinearVelocity().y)<=2) {
			moving=false;
		}

		if (!moving){
			body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() + (0.0174533f*2));
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
		font.draw(batch,turn,Gdx.graphics.getWidth()/2 +650f,Gdx.graphics.getHeight()/2+470f );
		batch.end();
		//TODO build a couple levels so it transitions appropriately from one to another
		//TODO polish




			//TODO fix this, also need to do a rotation update, will need to convert radians and degrees
			// 1 degree = 0.0174533 radians
		sprite.setPosition(body.getPosition().x, body.getPosition().y);






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
