package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ship extends GameObject {
    private boolean loopAnimation=false;
    private int frameIndex;
    private List<int[][]> frames;
    public boolean isAlive=true;
    public Ship(double x, double y) {
        super(x, y);
    }

    public void setStaticView(int[][] viewFrame) {
        setMatrix(viewFrame);
        frames=new ArrayList<>();
        frames.add(viewFrame);
        frameIndex=0;
    }
    public  Bullet fire(){
        return null;
    }
    public void kill(){
        isAlive=false;
    }

    public void nextFrame(){
        frameIndex++;
        if(frameIndex>=frames.size() && loopAnimation==false){
            return;
        }
        else if(frameIndex>=frames.size() && loopAnimation==true){
            frameIndex=0;
        }
            matrix=frames.get(frameIndex);
        }


    @Override
    public void draw(Game game){
        super.draw(game);
        nextFrame();
    }
    public  boolean isVisible(){
        if(!isAlive && frameIndex>=frames.size()){
            return false;
        }
        else {
            return true;
        }
    }
    public void setAnimatedView(boolean isLoopAnimation, int[][]... viewFrames){
        loopAnimation=isLoopAnimation;
    }


}
