
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

	@Override
	public void create() {




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
		// The position of the body will be at the bottom of the screen half way along
		groundBodyDef.position.set(new Vector2(0, 10));
        topBodyDef.position.set(new Vector2(0, 310));
        leftBodyDef.position.set(new Vector2(10, 160));
        rightBodyDef.position.set(new Vector2(470, 160));
		// Add the body to the world
		Body groundBody = world.createBody(groundBodyDef);
        Body topBody = world.createBody(topBodyDef);
        Body leftBody = world.createBody(leftBodyDef);
        Body rightBody = world.createBody(rightBodyDef);
		// Select a shape for the fixture of the ground
		// Its a polygon that will be defined as a box
		PolygonShape groundBox = new PolygonShape();
        PolygonShape topBox = new PolygonShape();
        PolygonShape leftBox = new PolygonShape();
        PolygonShape rightBox = new PolygonShape();
		// set the size of the box to fill the camera viewport width
		// and hence the width of the screen
		groundBox.setAsBox((camera.viewportWidth) * 2, 10.0f);
        topBox.setAsBox((camera.viewportWidth) * 2, 10.0f);
        leftBox.setAsBox(10.0f, (camera.viewportHeight) * 2);
        rightBox.setAsBox(10.0f, (camera.viewportHeight) * 2);
		// Add the fixture to the ground body
		groundBody.createFixture(groundBox, 0.0f);
        topBody.createFixture(topBox, 0.0f);
        leftBody.createFixture(leftBox, 0.0f);
        rightBody.createFixture(rightBox, 0.0f);
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

		//TODO need to move this into a function later
		if(body.getLinearVelocity().x<=2 && body.getLinearVelocity().y<=2) {
			//Gdx.app.log("spinning ", "is true");
			//Gdx.app.log("spinning ", (String.valueOf(body.getAngle())));
			body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() + (0.0174533f*2));

			//TODO swap press right with touch
			//Harris this line will make the circle shoot in the direction it is facing
			// if (touch){
			if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				body.applyLinearImpulse(moveVelocity, body.getMassData().center, true);
			}
		}

        //TODO fix this, also need to do a rotation update, will need to convert radians and degrees
		sprite.setPosition(body.getPosition().x, body.getPosition().y);
    //    sprite.setRotation();

/*		Gdx.app.log("velocity ", (String.valueOf(body.getLinearVelocity())));
		Gdx.app.log("velocity ", (String.valueOf(body.getLinearVelocity().x)));
		Gdx.app.log("velocity ", (String.valueOf(body.getLinearVelocity().y)));*/


		//Gdx.app.log("velocity ", "plz work");





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

	public boolean touchDown(int screenX, int screenY, int touch){
        if(touch != Input.Buttons.LEFT){
            Gdx.app.log("tester2", "fuck libgdx");
            return false;
        }
        Gdx.app.log("tester", "fuck libgdx");
        return true;

    }

}
