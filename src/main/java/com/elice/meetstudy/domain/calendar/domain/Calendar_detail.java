package com.elice.meetstudy.domain.calendar.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar_detail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @NotNull
    private String title;

    private String content;

    @JoinColumn(name = "start_day")
    private String startDay;

    @JoinColumn(name = "end_day")
    private String endDay;

    @JoinColumn(name = "start_time")
    private String startTime;

    @JoinColumn(name = "end_time")
    private String endTime;

    @NotNull
    @ColumnDefault("false")
    private boolean isHoliday;


    public Calendar_detail(String title, String content, String startDay, String endDay,
        String startTime, String endTime, boolean isHoliday) {
        this.title = title;
        this.content = content;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isHoliday = isHoliday;
    }

  public void update( String title, String content, String startDay,
      String endDay, String startTime, String endTime) {
    this.title = title;
    this.content = content;
    this.startDay = startDay;
    this.endDay = endDay;
    this.startTime = startTime;
    this.endTime = endTime;
    }
}
