
package com.mygdx.game;



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

		// Make the ball a dynamic body so that it can be accellerated
		// and can collide with the ground and bounce
		bodyDef.type = BodyType.DynamicBody;

		// Start the ball positioned near the top of the viewport
		bodyDef.position.set(camera.viewportWidth / 2, camera.viewportHeight / 1.1f);

		// add the ball to the world
		Body body = world.createBody(bodyDef);

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
		// call the debug renerer
		debugRenderer.render(world, camera.combined);
		// Update the simulation
		world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);


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
