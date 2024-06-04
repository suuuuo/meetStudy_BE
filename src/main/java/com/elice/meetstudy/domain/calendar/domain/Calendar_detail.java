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

    @NotNull
    @JoinColumn(name = "start_day")
    private String startDay;

    @NotNull
    @JoinColumn(name = "end_day")
    private String endDay;

    @NotNull
    @ColumnDefault("00:00:00")
    @JoinColumn(name = "start_time")
    private String startTime;

    @NotNull
    @ColumnDefault("23:59:59")
    @JoinColumn(name = "end_time")
    private String endTime;

    @NotNull
    @ColumnDefault("false")
    private boolean isHoliday;

    @PrePersist
    protected void setToday() {
        if (this.startDay == null) {
            LocalDate now = LocalDate.now();
            String date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            this.startDay = date;
        }

        if (this.endDay == null) {
            LocalDate now = LocalDate.now();
            String date1 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            this.endDay = date1;
        }
    }

    @Builder
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
