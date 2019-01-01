package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Ball;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BallScene;

public class MenuScene implements Screen {

    private Stage stage;
    private Ball game;
    SpriteBatch batch;

    OrthographicCamera camera;

    //background and title
    Image title;
    Image screenBg;

    //actors
    Image playButton;
    Image optionsButton;
    Image exitButton;

    public MenuScene(Ball aGame)
    {

        game = aGame;

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);


        //camera shit
        stage.setViewport(new FillViewport(800,480));
        camera = new OrthographicCamera(); // weet niet of ik dit wel gebruik.


        //background and title
        screenBg = new Image(new Texture("background2.png"));
        title = new Image(new Texture("TextGetReady.png"));
        title.setPosition(400 - (title.getWidth()) / 2, 340 - (title.getHeight()) / 2);


        //actors
        playButton = new Image(new Texture("play.png"));
        playButton.setSize(75,75);
        playButton.setPosition(400-playButton.getWidth()/2,200);

        optionsButton = new Image(new Texture("option.png"));
        optionsButton.setSize(75,75);
        optionsButton.setPosition(400-optionsButton.getWidth()/2,125);

        exitButton = new Image(new Texture("exit.png"));
        exitButton.setSize(75,75);
        exitButton.setPosition(400-exitButton.getWidth()/2, 50);


        //Button listeners
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new BallScene(game));
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new OptionScene(game));
            }
        });

        //add actors to stage
        stage.addActor(screenBg);
        stage.addActor(title);
        stage.addActor(optionsButton);
        stage.addActor(exitButton);
        stage.addActor(playButton);
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
