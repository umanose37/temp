package com.cst.peach.model;

import java.util.Date;

public class DateModel {

    private static String startUpDts;
    private static Date runDts;

    public static String getStartUpDts() {
        return startUpDts;
    }

    public static void setStartUpDts(String startUpDts) {
        DateModel.startUpDts = startUpDts;
    }

    public static Date getRunDts() {
        return runDts;
    }

    public static void setRunDts(Date runDts) {
        DateModel.runDts = runDts;
    }
}
