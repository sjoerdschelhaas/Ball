package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Menu extends ScreenAdapter {

    Ball game;
    Batch batch;
    Camera camera;
    Viewport viewport;
    Stage stage;
    Image screenBg;
    Image title;
    Table table;
    Table options;
    Skin skin;
    Image playButton;
    Image optionsButton;
    Image exitButton;
    Image backButton;


    public Menu(Ball game){ //constructor

        this.game = game;
        stage = new Stage();
        stage.setViewport(game.viewport);
        Gdx.input.setInputProcessor(stage);

        screenBg = new Image(new Texture("background2.png"));
        title = new Image(new Texture("TextGetReady.png"));
        table = new Table().debug();
        playButton = new Image(new Texture("play.png"));
        playButton.setSize(75,75);
        table.add(playButton);
        table.row();
        optionsButton = new Image(new Texture("play.png")); //zelfde foto om te kiekn of code werkt
        optionsButton.setSize(75,75);
        table.add(optionsButton);
        table.row();
        exitButton = new Image(new Texture("play.png"));
        exitButton.setSize(75,75);
        table.add(exitButton);
        table.setPosition(400, -200);

        options = new Table().debug();
        backButton = new Image(new Texture("play.png"));
        backButton.setSize(75,75);
        options.add(backButton).colspan(2).padTop(20);

        stage.addActor(screenBg);
        stage.addActor(title);
        stage.addActor(table);
        stage.addActor(options);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Ball game = new Ball();
                game.setScreen(new BallScene(game));
            }
            });

        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                showMenu(false);
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
            Gdx.app.exit();
            }
        });


        }

    private void showMenu(boolean flag){
        MoveToAction actionMove1 = Actions.action(MoveToAction.class);//out
        actionMove1.setPosition(400, -200);
        actionMove1.setDuration(1);
        actionMove1.setInterpolation(Interpolation.swingIn);
        MoveToAction actionMove2 = Actions.action(MoveToAction.class);//in
        actionMove2.setPosition(400, 190);
        actionMove2.setDuration(1.5f);
        actionMove2.setInterpolation(Interpolation.swing);
        if(flag){
            table.addAction(actionMove2);
            options.addAction(actionMove1);
        } else{
            options.addAction(actionMove2);
            table.addAction(actionMove1);
        }
    }
    
}
