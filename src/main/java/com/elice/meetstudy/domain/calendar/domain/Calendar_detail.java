package com.elice.meetstudy.domain.calendar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Calendar_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @NotNull
    private String title;

    private String content;

    @NotNull
    @JoinColumn(name = "start_day")
    private String startDay;

    @NotNull
    @JoinColumn(name = "end_day")
    private String endDay;

    @NotNull
    @JoinColumn(name = "start_time")
    private String startDime;

    @NotNull
    @JoinColumn(name = "end_time")
    private String endDime;

    @NotNull
    @ColumnDefault("false")
    private boolean isHoliday;

    //메모 내용 빠진 일정 추가하기 위함
//    public Calendar_detail(Calendar calendar, String title, String start_day,
//        String end_day, String start_time, String end_time, boolean isHoliday) {
//        this.calendar = calendar;
//        this.title = title;
//        this.start_day = start_day;
//        this.end_day = end_day;
//        this.start_time = start_time;
//        this.end_time = end_time;
//        this.isHoliday = isHoliday;
//    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }
}
