package com.inqube.aamarmedic.app;

public class UnCaughtExHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //Handle exceptions here.
        System.out.println(" UNCAUGHT EXCEPTION  "+ex.getMessage());
        System.gc();
        System.exit(2);
    }
}
