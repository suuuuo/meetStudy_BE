package com.elice.meetstudy.domain.calendar.holiday.service;

import com.elice.meetstudy.domain.calendar.holiday.domain.Holiday;
import com.elice.meetstudy.domain.calendar.holiday.repository.HolidayRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Value("${secret.key}") String serviceKey;

    // 연도, 달로 공휴일 리스트 뽑아서 전달 (캘린더에서 공휴일 일정 자동 등록하기 위함)
    @Transactional
    public List<Holiday> Holiday(String year, String month){
        int Month = Integer.parseInt(month);
        if(Month < 10) month = "0" + month;
        String date = year + month; // ex: 202405

        return holidayRepository.findByDateStartingWith(date);
    }

    //공휴일 db 저장 전체 로직
  @Transactional
  public void getHolidays() throws IOException {
    LocalDate now = LocalDate.now();
    int year = now.getYear();

    for(int i = year; i < year+3; i++){ // 올해 +2년 치 가져오기
          for (int j = 1; j <= 12; j++){
              String jsonString = getJsonResponse(i, j);
              JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
              JsonElement itemsElement = jsonObject.getAsJsonObject("response").getAsJsonObject("body").get("items");

              if (itemsElement != null && itemsElement.isJsonObject()) {
                  JsonObject itemsObject = itemsElement.getAsJsonObject();
                  if (itemsObject.has("item")) {
                      JsonElement itemElement = itemsObject.get("item");
                      itemElementProcess(itemElement);
                  }
              }
          }
      }
  }

  //url 조합
    @Transactional
    public URL getUrl(String year, String month)
        throws UnsupportedEncodingException, MalformedURLException {
        StringBuilder urlBuilder =
            new StringBuilder(
                "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo");

        urlBuilder.append( "?" + URLEncoder.encode("solYear", "UTF-8")
            + "=" + URLEncoder.encode(year, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8")
            + "=" + URLEncoder.encode(month, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8")
            + "=" +serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=json");

        return new URL(urlBuilder.toString());
    }

    //요청 -> 응답 반환
    @Transactional
    public String getJsonResponse(int year, int month) throws IOException {
        String monthString = (month < 10) ? "0" + month : Integer.toString(month);
        URL url = getUrl(Integer.toString(year), monthString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            return response.toString();
        } finally {
            conn.disconnect();
        }
    }

    // 배열인지 객체인지 확인하고 처리
    public void itemElementProcess(JsonElement itemElement) {
        if (itemElement.isJsonArray()) { //배열
            JsonArray itemArray = itemElement.getAsJsonArray();
            for (JsonElement item : itemArray) {
                processHoliday(item.getAsJsonObject());
            }
        } else if (itemElement.isJsonObject()) { //객체
            processHoliday(itemElement.getAsJsonObject());
        }
    }

    //공휴일 이름, 날짜 db에 추가
    public void processHoliday(JsonObject itemObject){
        String dateName = itemObject.get("dateName").getAsString();
        String locdate = itemObject.get("locdate").getAsString();
        addHoliday(locdate, dateName); //db에 추가함
    }

    @Transactional
    public void addHoliday(String date, String name){
        boolean isExist = holidayRepository.existsByDate(date); //공휴일이 이미 존재하는지 확인
        if (isExist) {
        } else{
            Holiday holiday = new Holiday(date, name);
            holidayRepository.save(holiday);
        }
    }
}

