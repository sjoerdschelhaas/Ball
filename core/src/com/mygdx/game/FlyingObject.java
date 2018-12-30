package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;


public class FlyingObject
{

    Sprite sprite;
    Vector2 randomOffScreen;
    Vector2 randomInScreen;
    Vector2 velocity;

    float speed = 4.0f;

    public FlyingObject(String s)
    {
        sprite = new Sprite(new Texture(s));
        sprite.setSize(54,100);
        sprite.setOrigin(400,240);
        randomOffScreen = new Vector2();
        randomInScreen = new Vector2();
        velocity = new Vector2();

        if(MathUtils.randomBoolean())
        {
            randomOffScreen.set((float) (800 + Math.random()*200), (float) (480 + Math.random()*200));
        } else {
            randomOffScreen.set((float) (0 - Math.random() * 200), (float) (0 - Math.random()*200));
        }

        if(MathUtils.randomBoolean())
        {
            randomInScreen.set((float) (140 + Math.random()*200), (float) (36 + Math.random()*200));
        } else {
            randomInScreen.set((float) (100 + Math.random() * 100), (float) (100 + Math.random()*100));
        }

        velocity = randomInScreen.sub(randomOffScreen);
        velocity.nor();
        velocity.scl(speed);
        //dikke coole rotation machocode
        sprite.setRotation(MathUtils.radiansToDegrees * MathUtils.atan2(velocity.x,velocity.y));

    }

    public void update()
    {
        // move the object towards randomInScreen
        sprite.setPosition(sprite.getX() + velocity.x,sprite.getY() + velocity.y);
    }

    public void draw(SpriteBatch batch){
        sprite.draw(batch);
    }
}
