package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import javax.swing.text.View;
import java.util.ArrayList;

public class BallScene extends ScreenAdapter {

    float width;
    float height;

    SpriteBatch batch;
    Texture background;
    Sprite backgroundSprite;
    OrthographicCamera camera;
    Viewport viewport;
    Sprite ball;
    Vector2 ballVelocity;
    Texture up;
    Texture down;
    //tap indicator
    Texture tapIndicator;
    Texture gameOver;
    ShapeRenderer shapeRenderer;



    //tap animation
    Animation<TextureRegion> tap;
    float animationTime;

    //ArrayList of flyingobjects
    ArrayList<FlyingObject> flyingObjects;

    //draw time of a tap on the screen. Is used to indicate a tap. not using it right now.
    //float tapDrawTime;
    //private static final float TAP_DRAW_TIME_MAX=1.0f;

    //touch position
    Vector3 touchPosition = new Vector3();

    //flying object
    FlyingObject fly;

    //enum of different states in the game
    enum GameState {
        INIT, ACTION, GAME_OVER
    }

    // intialize gamestate
    GameState gameState = GameState.INIT;


    //Pillar list
    ArrayList<Pillar> pillars;

    float spawnFreq = 1f;
    float spawnCounter = 0;

    //Arraylist pickups
    ArrayList<PickUp> pickups;
    float spawnFreq2 = 3f;
    float spawnCounter2 = 0;

    //score of the game
    //score
    private int score;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    //exitbutton
    Sprite exitButton;

    Ball game;

    public BallScene(Ball game) {//constructor
        this.game = game;
        batch = game.batch;
        camera = game.camera;
        viewport = game.viewport;
        width = game.screenWidth;
        height = game.screenHeight;
        game.resize(game.screenWidth, game.screenHeight); //updates the viewport.


        //shaperenderer
        shapeRenderer = new ShapeRenderer();

        // create new background sprite
        background = new Texture("background.jpg");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setPosition(0, 0);

        //create sprites
        down = new Texture("pillarDown.png");
        up = new Texture("pillarUp.png");


        //create ball sprite
        ball = new Sprite(new Texture("ball.png"));
        ball.setSize(30, 30);
        ball.setOrigin(ball.getWidth() / 2, ball.getHeight() / 2);
        ball.setPosition(width/2 - (ball.getWidth() / 2), height/2 - (ball.getHeight() / 2));

        //ball velocity
        ballVelocity = new Vector2(0, 0);

        //create tapindicator texture
        tapIndicator = new Texture("tap.png");

        //create gameover texture
        gameOver = new Texture("test.png");

        //arraylist of flyingobjects
        flyingObjects = new ArrayList<FlyingObject>();



        //animation of tap
        // create an animation with 3 pictures
        tap = new Animation(0.05f, new TextureRegion(new Texture("tap.png")),
                new TextureRegion(new Texture("tapTick.png")),
                new TextureRegion(new Texture("tap.png")));
        //loops through the 2 pictures
        tap.setPlayMode(Animation.PlayMode.LOOP);

        flyingObjects = new ArrayList<FlyingObject>();
        addObjects();

        pillars = new ArrayList<Pillar>();
        addPillars();

        //arraylist of pickups
        pickups = new ArrayList<PickUp>();
        addPickUps();

        //score of the game
        score = 0;
        yourScoreName = "Score: 0";
        yourBitmapFontName = new BitmapFont();



        //exitbutton
        exitButton = new Sprite(new Texture("exit.png"));
        exitButton.setSize(75,75);
        exitButton.setOrigin(exitButton.getWidth() / 2, exitButton.getHeight() / 2);
        exitButton.setPosition(400, 50);




    }

    @Override
    public void render(float delta) {

        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //fpsLogger.log();
        float deltaTime = Gdx.graphics.getDeltaTime();

        updateScene(deltaTime);
        drawScene();


        animationTime += deltaTime / 3;


    }

    public void updateScene(float delta) {

        // if screen is touched and if gamestate = init, change gamestate to action
        if (Gdx.input.justTouched()) {
            if (gameState == GameState.INIT) {
                gameState = GameState.ACTION;
                return;
            }
            if (gameState == GameState.GAME_OVER) {
                gameState = GameState.INIT;
                score = 0;
                resetScene();
                return;
            }
            // creates touch position. sets position to justtouched coordinates
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            //converts coordinates to camera resolution
            camera.unproject(touchPosition);


        }

        // if gamestate == action, go to code below. Hij returnt als gamestate niet action is.
        if (gameState == GameState.INIT || gameState ==
                GameState.GAME_OVER) {
            return;
        }

        //position of ball
        float accelX = Gdx.input.getAccelerometerX();
        float accelY = Gdx.input.getAccelerometerY();
        ballVelocity.set(accelX, accelY);
        ball.setPosition(ball.getX() + ballVelocity.y, ball.getY() - ballVelocity.x);

        if (ball.getBoundingRectangle().x > 800 - 30) {
            ball.setX(800 - 30);
        }
        if (ball.getBoundingRectangle().x < 0) {
            ball.setX(0);
        }

        if (ball.getBoundingRectangle().y > 480 - 45) {
            ball.setY(480 - 45);
        }
        if (ball.getBoundingRectangle().y < 15) {
            ball.setY(15);
        }


        //removes objects from flyingbjects when len > 800.
        FlyingObject toRemove2 = null;
        for (FlyingObject f : flyingObjects) {
            f.update();
            if (new Vector2(f.sprite.getX(), f.sprite.getY()).len() > 800) {
                toRemove2 = f;
            }
        }
        if (toRemove2 != null) {
            fly = null;
            flyingObjects.remove(toRemove2);
            addObjects();
        }

        Pillar toRemove = null;
        for (Pillar p : pillars) {
            p.update();
            if (p.sprite.getX() < -40){
                toRemove = p;
            }

        }
        pillars.remove(toRemove);

        PickUp toRemove3 = null;
        for(PickUp P : pickups) {
            P.update();
            if(P.sprite.getX() < -40){
                toRemove3 = P;
            } else if(ball.getBoundingRectangle().overlaps(P.sprite.getBoundingRectangle())){
                toRemove3 = P;
                //increment score
                score += 10;
                yourScoreName = "score: " + score;
            }
        }
        pickups.remove(toRemove3);

        //update rectangle of ball. if ball and pillar overlap, game is over.
        for (Pillar p : pillars) {
            if (ball.getBoundingRectangle().overlaps(p.sprite.getBoundingRectangle())) {
                gameState = GameState.GAME_OVER;
            }

        }

        for(FlyingObject f : flyingObjects){
            if(ball.getBoundingRectangle().overlaps(f.sprite.getBoundingRectangle())){
                gameState = GameState.GAME_OVER;
            }
        }

        //spawns pillars each second
        spawnCounter += delta;
        if(spawnCounter >= spawnFreq){
            addPillars();
            spawnCounter = 0;
        }
        spawnCounter2 += delta;
        if(spawnCounter2 >= spawnFreq2){
            addPickUps();
            spawnCounter2 = 0;
        }


        if(touchPosition.x > (exitButton.getBoundingRectangle().x) &&
                (touchPosition.x < (exitButton.getBoundingRectangle().x + 75)) &&
                (touchPosition.y > exitButton.getBoundingRectangle().y) &&
                (touchPosition.y < exitButton.getBoundingRectangle().y + 75))
        {

            game.setScreen(new MenuScene(game));
        }
        System.out.println(touchPosition.x);

    }

    public void drawScene() {
        // altijd doen in begin van draw
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.disableBlending();
        backgroundSprite.draw(batch);
        batch.enableBlending();
        //draw ball
        ball.draw(batch);

        //draw pillars
        //draws the pillars


		/*for(Sprite s: pillars)
		{
			s.draw(batch);
		}*/

        //draw the objects
        for (FlyingObject f : flyingObjects) {
            f.draw(batch);
        }

        for (Pillar p : pillars) {
            p.draw(batch);
        }

        for(PickUp P : pickups) {
                P.draw(batch);
        }


        //draws tap indicator. wil dit veranderen naar een tap-animation.

        if (gameState == GameState.INIT) //screen will be motionless until screen is touched.
        // when touched, gamestate will change to action.
        {
            batch.draw(tap.getKeyFrame(animationTime), 400, 200);
            //batch.draw(tapIndicator, 400,240);
        }

        if (gameState == GameState.GAME_OVER) {
            batch.draw(gameOver, 400 - 206, 240 - 80);
        }

        //draws score
        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        yourBitmapFontName.draw(batch, yourScoreName, 25, 455);

        //draw button
        exitButton.draw(batch);
        batch.end();


		/*shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(ball.getBoundingRectangle().x ,ball.getBoundingRectangle().y,ball.getBoundingRectangle().width,ball.getBoundingRectangle().height);
			shapeRenderer.rect(pillarUp.getBoundingRectangle().x ,pillarUp.getBoundingRectangle().y,pillarUp.getBoundingRectangle().width,pillarUp.getBoundingRectangle().height);
			shapeRenderer.rect(pillarDown.getBoundingRectangle().x ,pillarDown.getBoundingRectangle().y,pillarDown.getBoundingRectangle().width,pillarDown.getBoundingRectangle().height);

		shapeRenderer.end(); */
    }


    public void addObjects() {
        fly = new FlyingObject("rocket.png");
        flyingObjects.add(fly);
        System.out.println("added rocket");
    }

    public void addPillars() {
        pillars.add(new Pillar("pillarDown.png",
                new Vector2(-4f, 0),
                new Vector2(900, MathUtils.random(0,75))));

        pillars.add(new Pillar("pillarDown.png",
                new Vector2(-4f, 0),
                new Vector2(900, 70 + 177 + MathUtils.random(50))));
        System.out.println("added pillar, size is : " + pillars.size());

    }

    public void addPickUps() {
        pickups.add(new PickUp("starGold.png",
                new Vector2(-5f,0),
                new Vector2(900,MathUtils.random(0,480))));
        System.out.println("added star");
    }

    private void resetScene() {

        //pillars.clear();
        gameState = GameState.INIT;
        // set ball position at center of screen.
        ball.setPosition(width/2 - 50, height/2);
        pillars.clear();
        flyingObjects.clear();
        score = 0;
    }


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
    }

    @Override
    public void dispose() {
        super.dispose();
        background.dispose();
    }
}
