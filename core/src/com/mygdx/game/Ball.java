package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

public class Ball extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Sprite backgroundSprite;
	FPSLogger fpsLogger;
	OrthographicCamera camera;
	Viewport viewport;
	Sprite ball;
	Vector2 ballVelocity;
	Texture up;
	Texture down;
	Sprite pillarUp;
	Sprite pillarDown;
	ArrayList<Sprite> pillars;


	@Override
	public void create () {
		batch = new SpriteBatch();
		fpsLogger = new FPSLogger();
		// creates new camera
		camera = new OrthographicCamera();
		camera.position.set(400,240,0);
		viewport = new FillViewport(800, 480, camera);

		// create new background sprite
		background = new Texture("background.jpg");
		backgroundSprite=new Sprite(background);
		backgroundSprite.setPosition(0,0);

		//create sprites
		down = new Texture("pillarDown.png");
		up = new Texture("pillarUp.png");

		//ArrayList with sprites
		pillars  = new ArrayList<Sprite>();
		pillarUp = new Sprite(up);
		pillarDown = new Sprite(down);
		pillars.add(pillarUp);

		//create ball sprite
		ball = new Sprite(new Texture("ball.png"));
		ball.setSize(30,30);
		ball.setPosition(400-(ball.getWidth()/2),240-(ball.getHeight()/2));

		//ball velocity
		ballVelocity = new Vector2(0,0);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		fpsLogger.log();
		updateScene();
		drawScene();
	}

	public void updateScene() {
		float accelX = Gdx.input.getAccelerometerX();
		float accelY = Gdx.input.getAccelerometerY();
		ballVelocity.set(accelX,accelY);
		ball.setPosition(ball.getX() + ballVelocity.y,ball.getY()-ballVelocity.x);

		/*if(pillars.get(0).getX() < 100)
		{
			addPillars();
		}*/
		Sprite toRemove = null;
		for(Sprite s : pillars)
		{
			s.setPosition(s.getX()-10, s.getY());
			if(s.getX() < -50)
			{
				toRemove = s;
			}
		}
		if (toRemove != null){
			pillars.remove(toRemove);
			addPillars();

		}



	}

	public void drawScene() {
		// altijd doen in begin van draw
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.disableBlending();
		backgroundSprite.draw(batch);
		batch.enableBlending();
		//draw ball
		ball.draw(batch);

		//draw pillars
		//draws the pillars
		for(Sprite s: pillars)
		{
			s.draw(batch);
		}
		batch.end();
	}

	public void addPillars()
	{
		if(MathUtils.randomBoolean())
		{
			pillarUp.setPosition((float) (800 + Math.random()*100), 0);
			pillars.add(pillarUp);
		} else {
			pillarDown.setPosition((float) (800 + Math.random()*100), 480-pillarDown.getHeight());
			pillars.add(pillarDown);
		}

	}



	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width,height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
