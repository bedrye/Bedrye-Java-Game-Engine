package com.bedrye.bjge.GameEngine.Util;

public class TimeCounter {
    private static final float startTime = System.nanoTime();

    public static float getStartTime() {
        return startTime;
    }
    public static float getRunTimeNanoSec(){
        return System.nanoTime()-startTime;

    }
    public static float getRunTimeSec(){
        return (float)(getRunTimeNanoSec()/1E10);

    }


}
