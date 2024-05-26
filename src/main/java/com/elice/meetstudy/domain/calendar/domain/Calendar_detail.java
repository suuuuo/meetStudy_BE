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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
    private String startTime;

    @NotNull
    @JoinColumn(name = "end_time")
    private String endTime;

    @NotNull
    @ColumnDefault("false")
    private boolean isHoliday;

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

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
