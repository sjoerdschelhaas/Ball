package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class OptionScene implements Screen
{

    Ball game;
    SpriteBatch batch;
    Stage stage;
    OrthographicCamera camera;
    Image screenBg;
    Image backButton;

    public OptionScene(Ball aGame)
    {
        game = aGame;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);


        //camera shit
        stage.setViewport(new FillViewport(800,480));
        camera = new OrthographicCamera(); // weet niet of ik dit wel gebruik.

        //background and title
        screenBg = new Image(new Texture("background2.png"));

        //actors
        backButton = new Image(new Texture("exit.png"));
        backButton.setSize(75,75);
        backButton.setPosition(400-backButton.getWidth()/2, 50);

        //Button listeners
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MenuScene(game));
            }
        });

        stage.addActor(screenBg);
        stage.addActor(backButton);
    }

    @Override
    public void show() {
        //dit moet je doen
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.camera.combined);
        stage.act(delta);

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
