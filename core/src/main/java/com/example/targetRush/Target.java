package com.example.targetRush;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;

public class Target {
    private float x;       // Center X
    private float y;       // Center Y
    private float radius;
    private Circle circle;

    public Target(float radius) {
        this.radius = radius;
        this.circle = new Circle(0, 0, radius); // Starts at 0,0 until repositioned
    }

    public void reposition(float screenWidth, float screenHeight) {
        // Keeps the circle completely inside the screen boundaries
        this.x = MathUtils.random(radius, screenWidth - radius);
        this.y = MathUtils.random(radius, screenHeight - radius);

        // Crucial: Update the mathematical hit-box coordinates!
        circle.set(x, y, radius);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.circle(x, y, radius);
    }

    public Circle getCircle(){
        return circle;
    }

    public void dispose() {}
}
