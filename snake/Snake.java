package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;
    private static final String HEAD_SIGN = "\\uD83D\\uDC7E";
    private static final String BODY_SIGN = "u+1f7e1";
    private List<GameObject> snakeParts = new ArrayList<>();

    public Snake(int x, int y) {
        GameObject a1 = new GameObject(x, y);
        GameObject a2 = new GameObject(x + 1, y);
        GameObject a3 = new GameObject(x + 2, y);
        snakeParts.add(a1);
        snakeParts.add(a2);
        snakeParts.add(a3);
    }

    public void setDirection(Direction direction) {
if(this.direction==Direction.LEFT && snakeParts.get(0).x==snakeParts.get(1).x){
    return;
}
        if(this.direction==Direction.RIGHT && snakeParts.get(0).x==snakeParts.get(1).x){
            return;
        }
        if(this.direction==Direction.UP && snakeParts.get(0).y==snakeParts.get(1).y){
            return;
        }
        if(this.direction==Direction.DOWN && snakeParts.get(0).y==snakeParts.get(1).y){
            return;
        }
        if (direction == Direction.UP && this.direction == Direction.DOWN) {
            return;
        } else if (direction == Direction.DOWN && this.direction == Direction.UP) {
            return;
        } else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) {
            return;
        } else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) {
            return;

        }
        this.direction = direction;
    }


    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;

            if (isAlive == false) {
                game.setCellValueEx(part.x, part.y, Color.AQUAMARINE, sign, Color.RED, 75);
            } else {
                game.setCellValueEx(part.x, part.y, Color.AQUAMARINE, sign, Color.YELLOW, 75);
            }
        }

    }

    public void move(Apple apple) {
        GameObject s = createNewHead();
        if (s.y >= SnakeGame.HEIGHT || s.y < 0 || s.x >= SnakeGame.WIDTH || s.x < 0) {
            isAlive = false;
            return;
        }

        if (checkCollision(s) ){
            isAlive=false;
            return;
        }

            snakeParts.add(0, s);
        
        if (s.y == apple.y && s.x == apple.x) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
    }

    public GameObject createNewHead() {
        GameObject a = snakeParts.get(0);
        if (direction == Direction.LEFT) {
            return new GameObject(a.x - 1, a.y);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(a.x + 1, a.y);
        } else if (direction == Direction.UP) {
            return new GameObject(a.x, a.y - 1);
        } else {
            return new GameObject(a.x, a.y + 1);
        }

    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject a : snakeParts) {
            if (a.x == gameObject.x && a.y == gameObject.y) {
                return true;
            }
        }
                return false;
     
    }
    public int getLength(){
       return snakeParts.size();
    }

}
