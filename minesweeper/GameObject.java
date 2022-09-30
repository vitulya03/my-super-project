package com.javarush.games.minesweeper;

public class GameObject {
    public boolean  isFlag;
    public boolean isOpen;
    public int countMineNeighbors;
    public boolean isMine;
    public int x;
    public int y;


    public GameObject(int x,int y,boolean isMine){
        this.x=x;
        this.y=y;
        this.isMine=isMine;
    }

}
