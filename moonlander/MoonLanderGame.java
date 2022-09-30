package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    private int score;
    private boolean isGameStopped;
    private GameObject platform;
    private boolean isUpPressed;//Давай добавим поля, которые будут ответственны за состояние клавиш, благодаря которым мы перемещаем ракету по экрану. Эти поля будут принимать только два значения: true, если клавиша нажата, иначе false.
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private GameObject landscape;
    private Rocket rocket;
    public static final int  WIDTH=64;
    public static final int  HEIGHT=64;
    @Override
    public void  initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        showGrid(false);
    }
    private void createGame(){//В нем мы окрасим все ячейки игрового поля в один цвет
        score=1000;
        createGameObjects();
        drawScene();
        setTurnTimer(50);//Чтобы задать частоту работы этого метода, мы будем использовать метод setTurnTimer. Он принимает на вход параметр — длительность каждого шага в миллисекундах
        isUpPressed=false;
        isLeftPressed=false;
        isRightPressed=false;
        isGameStopped=false;
    }
    private void drawScene(){//Он будет отвечать за создание игры.
        for(int x=0; x<WIDTH;x++){
            for(int y=0; y<HEIGHT;y++){
                setCellColor(x, y, Color.AQUAMARINE);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }
    private void createGameObjects(){
        rocket=new Rocket(WIDTH/2,0);
        landscape=new GameObject(0, 25, ShapeMatrix.LANDSCAPE);//ландшафт
        platform=new GameObject(23, MoonLanderGame.HEIGHT - 1, ShapeMatrix.PLATFORM);
    }
    @Override
   public void onTurn(int game){
        if(score>0){
            score=score-1;
        }
      rocket.move(isUpPressed,isLeftPressed,isRightPressed);
        check();
        setScore(score);
      drawScene();
    }

@Override
 public  void setCellColor(int x, int y, Color color){
       if(x> WIDTH-1 || y> HEIGHT-1 || x<0 || y<0){
           return;
       }
       super.setCellColor(x,y,Color.LIME);
   }
   @Override
    public  void  onKeyPress(Key key){//вызывается при нажатии любой клавиши
        if(key==Key.UP){
            isUpPressed=true;
        }
        else if(key==Key.LEFT){
            isLeftPressed=true;
            isRightPressed=false;
        }
        else if (key==Key.RIGHT){
            isRightPressed=true;
            isLeftPressed=false;
       }
        if(key==Key.SPACE && isGameStopped==true){
            createGame();
            return;
        }
   }
   @Override
    public void onKeyReleased(Key released){//при отпускании
        if(released==Key.UP){
            isUpPressed=false;
        }
        else if(released==Key.LEFT){
            isLeftPressed=false;
        }
        else if(released==Key.RIGHT){
            isRightPressed=false;
        }

   }
   private  void check(){
       if(rocket.isStopped() && rocket.isCollision(platform)){
           win();
       }
else if(rocket.isCollision(landscape)){
    gameOver();
}
   }
private void win(){
rocket.land();
isGameStopped=true;
    showMessageDialog(Color.WHITE, "Congratulations, you are the Winner!!!", Color.BLUEVIOLET, 50);
    stopTurnTimer();
}
private void gameOver(){
rocket.crash();
isGameStopped=true;
    showMessageDialog(Color.WHITE, "Sorry,you lose", Color.BLUEVIOLET, 50);
    stopTurnTimer();
    score=0;
}




}
