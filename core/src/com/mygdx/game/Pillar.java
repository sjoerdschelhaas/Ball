package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Pillar {

    Sprite sprite;
    Vector2 velocity;

    public Pillar(String spritePath, Vector2 vel,Vector2 sPos){
        sprite = new Sprite(new Texture(spritePath));
        sprite.setSize(sprite.getWidth()*0.6f,sprite.getHeight()*0.6f);
        velocity = vel;
        sprite.setPosition(sPos.x,sPos.y);
    }

    public void update(){
        sprite.setPosition(sprite.getX()+ velocity.x,sprite.getY()+ velocity.y);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }




}
