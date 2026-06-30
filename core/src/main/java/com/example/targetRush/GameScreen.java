package com.example.targetRush;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private Main game;

    private static final int VWIDTH = 800;
    private static final int VHEIGHT = 480;

    // Camera and Viewport to fix the click coordinate mismatch
    private OrthographicCamera camera;
    private Viewport viewport;

    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private SpriteBatch batch;

    private Target target;

    private int score = 0;
    private float totalTime = 0;
    private float spawnTimer = 0;
    private boolean isPaused = false;

    public GameScreen(Main game){
        this.game = game;
    }

    @Override
    public void show() {
        // 1. Set up the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VWIDTH, VHEIGHT, camera);
        viewport.apply(true); // Centers the camera

        font  = new BitmapFont();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Initialize target with a radius of 35 to make it a bit easier to hit
        target = new Target(35);
        target.reposition(VWIDTH, VHEIGHT);

        score = 0;
        totalTime = 0;
        spawnTimer = 0;
        isPaused = false;
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(0, 0, 0, 1);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        target.draw(shapeRenderer);
        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Score: " + score, 20, VHEIGHT - 20);
        font.draw(batch, "Total Time: " + (int)totalTime + "s", 20, VHEIGHT - 40);

        if (isPaused) {
            font.draw(batch, "GAME PAUSED - Press P to Resume", VWIDTH / 2 - 120, VHEIGHT / 2);
        }
        batch.end();
    }

    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            isPaused = !isPaused;
        }

        if (!isPaused) {
            totalTime += dt;
            spawnTimer += dt;

            if (spawnTimer >= 2.0f) {
                spawnTimer = 0;
                target.reposition(VWIDTH, VHEIGHT);
            }

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                viewport.unproject(touchPoint);
                if (target.getCircle().contains(touchPoint.x, touchPoint.y)) {
                    score++;
                    spawnTimer = 0;
                    target.reposition(VWIDTH, VHEIGHT);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport when the game window changes size
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        target.dispose();
    }
}
