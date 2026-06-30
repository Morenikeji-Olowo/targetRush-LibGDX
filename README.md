# Target Rush — LibGDX

A fast-paced reflex game built with LibGDX where targets appear randomly 
on screen and the player races against time to click as many as possible.

## Features

- **Random target spawning** — a new target appears at a random position 
  every 2 seconds
- **Click detection** — uses LibGDX input handling to register successful 
  hits and award points
- **Live score and timer display** — current score and elapsed time 
  rendered with BitmapFont
- **Pause/resume** — game state can be paused mid-session

## Tech Stack

Java · LibGDX · Gradle

## Architecture
core/src/com/example/targetrush/
├── Target.java        → handles position, drawing, and click hit-testing
└── GameScreen.java      → spawn timer, score tracking, time tracking, rendering
## How to Run

```bash
git clone https://github.com/Morenikeji-Olowo/targetRush-LibGDX.git
cd targetRush-LibGDX
./gradlew lwjgl3:run
```

## Controls

- Mouse click — hit the target
- P — pause
