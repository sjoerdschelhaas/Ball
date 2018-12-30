package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Ball extends Game //this is the main game class
{

    OrthographicCamera camera;
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
        batch = new SpriteBatch();
        setScreen(new BallScene(this));
    }

    @Override
    public void render () {
        fpsLogger.log();
        super.render();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose()
    {
        batch.dispose();
    }

}
