package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu {
    // TODO add a menu
    Ball game;
    Batch batch;
    Camera camera;
    Viewport viewport;

    public Menu(Ball game){
        this.game = game;
        batch = game.batch;
        camera = game.camera;
        viewport = game.viewport;
        game.resize(game.screenWidth, game.screenHeight); //updates the viewport.
    }
}
