package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PickUp
{
  Sprite sprite;
  Vector2 velocity;

  public PickUp(String spritePath, Vector2 velocity, Vector2 pos)
  {
      this.velocity = velocity;
      sprite = new Sprite(new Texture(spritePath));
      sprite.setPosition(pos.x,pos.y);

  }

  public void update()
  {
      sprite.setPosition(sprite.getX() + velocity.x, sprite.getY() + velocity.y);
  }

  public void draw(SpriteBatch batch)
  {
      sprite.draw(batch);
  }
}
