package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

import java.util.List;

public class RocketFire extends  GameObject{//еализовать реактивную тягу ракеты. На этом этапе создадим класс RocketFire, который будет отвечать за отрисовку и анимирование огня выхлопа
    private List<int[][]> frames;
    private int frameIndex;
    private boolean isVisible;
    public RocketFire(List<int[][]> frameList){
        super( 0, 0, frameList.get(0));
        frames=frameList;
        frameIndex=0;
        isVisible=false;
    }
    private void nextFrame(){
        frameIndex=frameIndex+1;
        if(frameIndex>=frames.size()){
            frameIndex=0;
        }
        matrix=frames.get(frameIndex);
    }
    @Override
    public  void draw(Game game){
        if(isVisible == false){
            return;
        }
        nextFrame();
        super.draw( game);
    }
    public void show(){
        isVisible=true;
    }
    public void hide(){
        isVisible=false;
    }




}
