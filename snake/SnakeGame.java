package com.javarush.games.snake;


import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    private int score;
    private static final int GOAL=28;
    private boolean isGameStopped;
    private Apple apple;
    private int  turnDelay;
    private Snake snake;
    public static final int WIDTH =15;
    public static final int HEIGHT=15;
    @Override
    public  void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame(){
score=0;
        setScore(score);
        turnDelay=300;
        setTurnTimer(turnDelay);
        Snake sna=new Snake(WIDTH / 2,HEIGHT / 2);
        snake=sna;
        createNewApple();
        isGameStopped=false;
        drawScene();



    }
    private void  drawScene(){
        for(int i=0; i<WIDTH;i++){
            for(int j=0;j<HEIGHT;j++){
                setCellValueEx(i, j, Color.LIME,"");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;
    }

    @Override
    public void onTurn(int step) {
        if(!apple.isAlive){
            score=score+5;
            setScore(score);
            turnDelay=turnDelay-10;
            setTurnTimer(turnDelay);
        }

        snake.move(apple);
        if (!apple.isAlive) {
            createNewApple();
        }
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }

        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        }

    }

        private void gameOver () {
            stopTurnTimer();
            isGameStopped = true;
            showMessageDialog(Color.AQUAMARINE, "Game over!", Color.RED, 50);
        }

        private void win () {
            stopTurnTimer();
            isGameStopped = true;
            showMessageDialog(Color.AQUAMARINE, "You win!", Color.YELLOW, 50);
        }


    }


