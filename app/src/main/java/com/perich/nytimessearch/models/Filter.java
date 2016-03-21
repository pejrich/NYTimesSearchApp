package com.perich.nytimessearch.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by perich on 3/21/16.
 */
public class Filter implements Serializable {
    public Date startDate;
    public boolean oldest;
    public ArrayList<String> newsDesk;

    public Filter() {
        this.oldest = false;
        this.startDate = null;
        this.newsDesk = new ArrayList<>();
    }

    public String newsDeskString() {
        if (newsDesk.size() == 0) {
            return "";
        } else {
            String vals = "";
            for (String s : newsDesk) {
                vals = vals + "\"" + s + "\"";
            }
            return "news_desk:(" + vals + ")";
        }
    }

    public String yyyymmdd() {
        if (startDate == null) {
            return "";
        }
        String year = String.valueOf(startDate.getYear());
        int month = startDate.getMonth() + 1;
        int day = startDate.getDate();
        return year + String.format("%02d", month) + String.format("%02d", day);
    }

    public String displayDate() {
        if (startDate == null) {
            return "";
        }
        String year = String.valueOf(startDate.getYear());
        int month = startDate.getMonth() + 1;
        int day = startDate.getDate();
        return String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + year;
    }
}
