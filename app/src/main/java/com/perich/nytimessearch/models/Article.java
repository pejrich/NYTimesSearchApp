package com.perich.nytimessearch.models;

import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Article implements Serializable {
    // These are the accepted "document_type" fields. Some fields like
    // "topic" have no real content and should not be accepted.
    final static String ACCEPTED_TYPES = "article blogpost";
    public String headline;
    public String snippet;
    public String image_url;
    public String url;
    public String source;
    public Date pub_date;

    public Article() {

    }

    public Article(JSONObject json) {
        try {
            String doc_type  = json.getString("document_type");
            if (validDocumentType(doc_type)) {
                this.headline    = Html.fromHtml(json.getJSONObject("headline").getString("main")).toString();
                this.snippet     = Html.fromHtml(json.getString("snippet")).toString();
                this.image_url   = NYTUrl(json.getJSONArray("multimedia"));
                this.url         = json.getString("web_url");
                this.source      = json.getString("source");
                this.pub_date    = dateFromString(json.getString("pub_date"));
            }
        } catch (JSONException e) {}
    }

    public static ArrayList<Article> fromJSONArray(JSONArray articleArray) {
        ArrayList <Article >results = new ArrayList<>();
        for (int i = 0; i < articleArray.length(); i++) {
            try {
                JSONObject jsonArticle = (JSONObject) articleArray.get(i);
                if (!Article.validDocumentType(jsonArticle.getString("document_type"))) {
                    // Some return types, such as "topic", have no real value to end user
                    // and should be skipped
                    Log.i("logger", "Skipped invalid artile type: " + jsonArticle.getString("document_type"));
                    continue;
                }
                results.add(new Article(jsonArticle));
            } catch (JSONException e) {}

        }
        return results;
    }

    public static boolean validDocumentType(String docType) {
        return ACCEPTED_TYPES.toLowerCase().contains(docType.toLowerCase());
    }

    public static Date dateFromString(String dateString) {
//        Converts date with format yyyy-mm-ddThh:mm:ssZ
//        e.g. "2016-03-04T00:00:00Z"
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            date = format.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {e.printStackTrace();}
        return date;
    }

    public String pubDateString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(pub_date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int year = cal.get(Calendar.YEAR);
        String monthName = DateFormatSymbols.getInstance().getMonths()[month];
        return monthName + " " + day + ", " + year;
    }

    public String NYTUrl(JSONArray mmArray) {
        String url = "https://blogs.qub.ac.uk/library/files/2015/11/nytimes-logo.jpg";
        try {
            if (mmArray.length() > 0) {
                url = "http://www.nytimes.com/" + mmArray.getJSONObject(0).getString("url");
            }
        } catch (JSONException e) {}
        return url;
    }

}
