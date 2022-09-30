package com.javarush.games.spaceinvaders;

import com.javarush.engine.cell.*;
import com.javarush.games.spaceinvaders.gameobjects.Bullet;
import com.javarush.games.spaceinvaders.gameobjects.EnemyFleet;
import com.javarush.games.spaceinvaders.gameobjects.PlayerShip;
import com.javarush.games.spaceinvaders.gameobjects.Star;

import java.util.ArrayList;
import java.util.List;

public class SpaceInvadersGame extends Game {
    private int score;
    private static final int PLAYER_BULLETS_MAX = 1;
    private List<Bullet> playerBullets;
    private int animationsCount;
    private boolean isGameStopped;
    private PlayerShip playerShip;
    private List<Bullet> enemyBullets;
    public static final int COMPLEXITY = 5;

    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private List<Star> stars;
    private EnemyFleet enemyFleet;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void onTurn(int step) {
        moveSpaceObjects();
        check();
        Bullet bullet = enemyFleet.fire(this);
        if (bullet != null) {
            enemyBullets.add(bullet);
        }
        setScore(score);
        drawScene();

    }

    private void createGame() {
        score=0;
        enemyFleet = new EnemyFleet();
        createStars();
        enemyBullets = new ArrayList<>();
        playerShip = new PlayerShip();
        isGameStopped = false;
        animationsCount = 0;
        playerBullets = new ArrayList<>();
        drawScene();
        setTurnTimer(40);
    }

    private void drawScene() {
        drawField();
        for (Bullet d : playerBullets) {
            d.draw(this);
        }
        playerShip.draw(this);
        for (Bullet a : enemyBullets) {
            a.draw(this);
        }
        enemyFleet.draw(this);
    }

    private void drawField() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                setCellValueEx(x, y, Color.LIGHTBLUE, "");
            }
        }

        for (Star star : stars) {
            star.draw(this);
        }
    }

    private void createStars() {
        stars = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            stars.add(new Star(x, y));
        }
    }

    private void moveSpaceObjects() {
        enemyFleet.move();
        for (Bullet a : enemyBullets) {
            a.move();
        }
        playerShip.move();
        for (Bullet d : playerBullets) {
            d.move();
        }
    }

    private void removeDeadBullets() {
        for (Bullet d : new ArrayList<>(enemyBullets)) {
            if (!d.isAlive || d.y >= HEIGHT - 1) {
                enemyBullets.remove(d);
            }
        }
        for (Bullet i : new ArrayList<>(playerBullets)) {
            if (!i.isAlive || (i.y + i.height) < 0) {
                playerBullets.remove(i);
            }
        }
    }

    private void check() {
        playerShip.verifyHit(enemyBullets);
        score += enemyFleet.verifyHit(playerBullets);
        enemyFleet.verifyHit(playerBullets);
        enemyFleet.deleteHiddenShips();
        removeDeadBullets();
        if (!playerShip.isAlive) {
            stopGameWithDelay();
        }
        if (enemyFleet.getBottomBorder()>=playerShip.y){
            playerShip.kill();
        }

        if(enemyFleet.getShipsCount()==0){
            playerShip.win();
            stopGameWithDelay();
        }

    }

    private void stopGame(boolean isWin) {
        isGameStopped = true;
        stopTurnTimer();
        if (isWin) {
            showMessageDialog(Color.SILVER, "Ты самый храбрый воин!", Color.GREEN, 30);
        } else {
            showMessageDialog(Color.SILVER, "Все получится, попробуй еще раз=)", Color.RED, 30);
        }
    }

    private void stopGameWithDelay() {
        animationsCount++;
        if (animationsCount >= 10) {
            stopGame(playerShip.isAlive);
        }
    }

    @Override
    public void onKeyPress(Key key) {
        if (isGameStopped == true && key == Key.SPACE) {
            createGame();
            return;
        }
        if (Key.LEFT == key) {
            playerShip.setDirection(Direction.LEFT);
        }

        if (Key.RIGHT == key) {
            playerShip.setDirection(Direction.RIGHT);
        }
        if (key == Key.SPACE) {
            Bullet s = playerShip.fire();
            if (s != null && playerBullets.size() < PLAYER_BULLETS_MAX) {
                playerBullets.add(s);
            }
        }
    }

        @Override
        public void onKeyReleased(Key key) {
            if (Key.LEFT == key && playerShip.getDirection() == Direction.LEFT) {
                playerShip.setDirection(Direction.UP);
            }
            if (Key.RIGHT == key && playerShip.getDirection() == Direction.RIGHT) {
                playerShip.setDirection(Direction.UP);
            }
        }

        @Override
        public void setCellValueEx(int x, int y, Color color, String value) {
            if (x > WIDTH - 1 || x < 0 || y < 0 || y > HEIGHT - 1) {
                return;
            }
            super.setCellValueEx(x, y, color, value);
        }
    }

