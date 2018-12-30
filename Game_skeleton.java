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

class Game_skeleton
{
  SpriteBatch batch;
  Texture background;
  Sprite backgroundSprite;
  FPSLogger fpsLogger;
  OrthographicCamera camera;
  Viewport viewport;

  //enum of different states in the game
	 enum GameState
	{
		INIT, ACTION, GAME_OVER
	}

  // intialize gamestate
  GameState gameState = GameState.INIT;


  @Override
  	public void create() {
  		batch = new SpriteBatch();
  		fpsLogger = new FPSLogger();

  		// creates new camera
  		camera = new OrthographicCamera();
  		camera.position.set(400,240,0); //position of camera, usually middle of screen
  		viewport = new FillViewport(800, 480, camera); // viewport aka virtual display

  		// create new background sprite
  		background = new Texture("background.jpg");
  		backgroundSprite=new Sprite(background);
  		backgroundSprite.setPosition(0,0);

  		//create sprites
      //Sprite sprite = new Sprite(new Texture(picture.png))
      // sprite.setSize(int,int)
      // sprite.setPosition(int,int)
  		//create ball sprite

  	}

    @Override
  public void render ()
  {
  Gdx.gl.glClearColor(1, 0, 0, 1);
  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  fpsLogger.log();
  updateScene();
  drawScene();
  }

  public void updateScene()
  {

// if screen is toched, gamestate = init.
    if(Gdx.input.justTouched())
    {
      if(gameState == GameState.INIT)
      {
        gameState = GameState.ACTION;
        return;
      }
      if(gameState == GameState.GAME_OVER)
      {
        gameState = GameState.INIT;
        resetScene(); //method that resets game when game over
        return;
      }
      // creates touch position
      touchPosition.set(Gdx.input.getX(),Gdx.input.getY(),0);
      //converts coordinates to camera resolution
      camera.unproject(touchPosition);
    }
    if(gameState == GameState.INIT || gameState ==
    GameState.GAME_OVER)
    {
      return;
    }
  }

  public void drawScene()
  {
    // always update camera in beginning of drawScene.
    camera.update();
    batch.setProjectionMatrix(camera.combined);
    // always have a batch.begin()
    batch.begin();
    batch.disableBlending();

    //draws background
    backgroundSprite.draw(batch);
    batch.enableBlending();
    //draw sprite
    //sprite.draw(batch);

    // if gamestate is init, screen is motionless until screen is pressed. see until touched above
    if(gameState == GameState.INIT)
    {
      //draws a tap indicator texture on the screen at coordinates int,int
      batch.draw(tapIndicator,int width/2,int height/2);
    }

    // end batch.
    batch.end();
  }

  public void resetScene()
  {
    //TODO reset the game. IE set velocity of some sprite to 0 or set position to center of screen
    // TODO set gamestate to init
  }

  @Override
	public void dispose ()
  {
		batch.dispose();
		background.dispose();
	}

}
