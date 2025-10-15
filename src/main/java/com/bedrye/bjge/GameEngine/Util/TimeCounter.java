package com.bedrye.bjge.GameEngine.Util;

public class TimeCounter {
    private static final double startTime = System.nanoTime();

    public static double getStartTime() {
        return startTime;
    }
    public static double getRunTimeNanoSec(){
        return System.nanoTime()-startTime;

    }
    public static double getRunTimeMiliSec(){
        return (getRunTimeNanoSec()/1E6);

    }
    public static double getRunTimeSec(){
        return (getRunTimeNanoSec()/1E10);

    }


}
