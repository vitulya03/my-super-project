package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private int score;
    private int countClosedTiles=SIDE*SIDE;
    private boolean isGameStopped;
    private int countFlags;
    private static final String FLAG="\uD83D\uDEA9";
    private static final String MINE="\uD83D\uDCA3";
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;

    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    @Override
    public void onMouseLeftClick(int x, int y) {

        if(isGameStopped) {
            restart();
            return;
        }

        openTile(x, y);
    }
    @Override
     public void onMouseRightClick(int x, int y){
        markTile(x,y);
    }


    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x,y, "");
            }
        }
        countMineNeighbors();
        countFlags = countMinesOnField;

    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }
    private void countMineNeighbors(){
        for(int i=0;i<SIDE;i++){
            for(int j=0;j<SIDE;j++){
               GameObject sa =gameField[i] [j];
               if(!sa.isMine){
                   for(GameObject gameObject:getNeighbors(sa)){
                       if(gameObject.isMine){
                           sa.countMineNeighbors++;
                       }
                   }
               }
            }
        }
    }
    private void openTile(int x, int y) {
        GameObject gameObject = gameField[y][x];
        if(isGameStopped == true || gameObject.isFlag || gameObject.isOpen ){
            return;
        }
        gameObject.isOpen = true;

        setCellColor(x, y, Color.GREEN);

        if (gameObject.isMine) {
            setCellValue(gameObject.x, gameObject.y, MINE);
        } else if (gameObject.countMineNeighbors == 0) {
            setCellValue(gameObject.x, gameObject.y, "");
            List<GameObject> neighbors = getNeighbors(gameObject);
            for (GameObject neighbor : neighbors) {
                if (!neighbor.isOpen) {
                    openTile(neighbor.x, neighbor.y);
                }
            }
        } else {
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }
        if(gameObject.isMine){
            setCellValueEx(gameObject.x, gameObject.y, Color.RED, MINE);
            gameOver();
        }
        if(gameObject.isOpen){
            countClosedTiles--;
        }
        if(countMinesOnField==countClosedTiles && (!gameObject.isOpen==gameObject.isMine)){
            win();
        }
        if(gameObject.isOpen && !gameObject.isMine){
            score=score+5;
        }
        setScore(score);

    }
    private void markTile(int x, int y){
        GameObject gameObject =gameField[y][x];
        if(gameObject.isOpen==true){
            return;
        }
        else if(countFlags==0 && gameObject.isFlag==false){
            return;
        }
        if(!gameObject.isFlag){
            countFlags--;
            gameObject.isFlag=true;

           setCellValue(gameObject.x, gameObject.y,FLAG);
            setCellColor(gameObject.x, gameObject.y,Color.YELLOW);

        }
        else{
            countFlags++;
                    gameObject.isFlag=false;
                setCellValue(gameObject.x, gameObject.y,"");
                setCellColor(gameObject.x, gameObject.y,Color.ORANGE);
        }

    }
    private  void gameOver(){
        isGameStopped=true;
        showMessageDialog(Color.RED, "Game Over", Color.SILVER, 20);
    }
    private void win(){
        isGameStopped=true;
        showMessageDialog(Color.AQUA, "YOUUUUUU WINNNNN!!!!!!", Color.PINK,20);
    }
    private void restart(){
            countClosedTiles = SIDE * SIDE;
            score = 0;
            setScore(score);
            countMinesOnField = 0;
            isGameStopped = false;
            createGame();
    }

}