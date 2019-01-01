package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class Ball extends Game //this is the main game class
{

    public OrthographicCamera camera;
    FillViewport viewport;
    //Viewport viewport;
    FPSLogger fpsLogger;
    SpriteBatch batch;
    public static final int screenWidth=800;
    public static final int screenHeight=480;


    public Ball()
    {
        fpsLogger = new FPSLogger();
        camera = new OrthographicCamera();
        camera.position.set(screenWidth/2, screenHeight/2, 0);
        viewport = new FillViewport(screenWidth, screenHeight, camera);

    }

    @Override
    public void create ()
    {
       // camera = new OrthographicCamera();
       // camera.position.set(screenWidth/2,screenHeight/2,0);
       // viewport = new FillViewport(screenWidth,screenHeight,camera);

        batch = new SpriteBatch();
        setScreen(new MenuScene(this));

    }



    /** Clears the screen with a white color */
    /*private void clearWhite() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }*/

    @Override
    public void render () {
        super.render();

    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose()
    {
       batch.dispose();
       //screen.dispose();
    }

}
