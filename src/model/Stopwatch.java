package model;

import java.time.Duration;
import java.time.LocalTime;
import java.util.TimerTask;

public class Stopwatch extends TimerTask implements Runnable {

    private final Minesweeper minesweeper;

    public Stopwatch(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
    }

    @Override
    public void run() {
        minesweeper.viewNotifier.notifyTimeElapsedChanged(Duration.between(LocalTime.now(), minesweeper.getStartTime()));
    }


//    int seconds;
//    int minutes;
//    int elapsedTime;
//
//    Timer timer = new Timer(1000, new ActionListener(){
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            elapsedTime = 1000;
//            minutes = (elapsedTime/60000) % 60;
//            seconds = (elapsedTime/1000) % 60;
//
//        }
//    });
//
//    Stopwatch(){
//
//    }
//
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//
//    void start(){
//
//    }
//
//    void stop(){
//
//    }
//
//    void reset(){
//
//    }
}
