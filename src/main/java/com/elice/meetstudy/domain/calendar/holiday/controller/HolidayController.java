package com.elice.meetstudy.domain.calendar.holiday.controller;

import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import com.elice.meetstudy.domain.calendar.holiday.service.HolidayService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.time.LocalDate;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.IOException;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Autowired private HolidayService holidayService;

    @GetMapping("")
    public String getHoliday() throws IOException {
        LocalDate now = LocalDate.now();
        int year = now.getYear();

        for (int i = year; i <= year + 1; i++) { //내년까지 가져옴
            for (int j = 1; j <= 12; j++) {

                String month = Integer.toString(j);
                if(j < 10) month = "0"+month;

                StringBuilder urlBuilder = new StringBuilder(
            "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo");

        urlBuilder.append(
            "?" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(i),
                "UTF-8"));
        urlBuilder.append(
            "&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month,
                "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8")
            + "=u68Vcu5y2ZXWBQ%2Fvmi%2BjltzqsE1hJSxVg5Vdm3XL8V9DdzUTdK4OZdjMBaXguu2XD7cK1fMuAYitTOhuTgrycA%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=json");

        URL url = new URL(urlBuilder.toString());
        System.out.println(url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String jsonString = response.toString();
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        JsonObject responseBody = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
        JsonElement itemsElement = responseBody.get("items");

        if (itemsElement != null && itemsElement.isJsonObject()) {
            JsonObject itemsObject = itemsElement.getAsJsonObject();
            if (itemsObject.has("item")) {
                JsonElement itemElement = itemsObject.get("item");

                if (itemElement.isJsonArray()) {
                    JsonArray itemArray = itemElement.getAsJsonArray();
                    for (JsonElement item : itemArray) {
                        processHoliday(item.getAsJsonObject());
                    }
                } else if (itemElement.isJsonObject()) {
                    processHoliday(itemElement.getAsJsonObject());
                }
            }
        }
            }
        }
    return null;
    }

    private void processHoliday(JsonObject itemObject){
        String dateName = itemObject.get("dateName").getAsString();
        //String isHoliday = itemObject.get("isHoliday").getAsString();
        String locdate = itemObject.get("locdate").getAsString();

        System.out.println("Date Name: " + dateName);
        System.out.println("Loc Date: " + locdate);
        System.out.println();

        holidayService.addHoliday(locdate, dateName);
    }
}


