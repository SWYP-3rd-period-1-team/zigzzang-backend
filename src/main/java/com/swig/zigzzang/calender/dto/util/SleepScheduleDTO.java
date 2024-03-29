package com.swig.zigzzang.calender.dto.util;

import com.swig.zigzzang.calender.domain.SleepSchedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SleepScheduleDTO {

    @Schema(description = "수면 스케쥴 번호", nullable = false, example = "1")
    Long sleepScheduleId; // id

    @Schema(description = " 수면 기간", nullable = false, example = "23000700(=> 23:00~07:00)")
    String sleepPeriod;

    @Schema(description = "총 수면 시간", nullable = false, example = "2024-03-10")
    Long sleepTime;

    @Schema(description = "캘린더 날짜", nullable = false, example = "2024-03-10")
    String calenderDate;

    @Schema(description = "수면 스케줄 달성도", nullable = false, example = "true")
    Boolean sleepScheduleAchievement;

    // entity -> dto
    public static SleepScheduleDTO of(SleepSchedule sleepSchedule) {
        return SleepScheduleDTO.builder()
                .sleepScheduleId(sleepSchedule.getSleepScheduleId())
                .sleepPeriod(String.valueOf(sleepSchedule.getPeriod()))
                .sleepTime(sleepSchedule.getTime())
                .calenderDate(sleepSchedule.getCalenderDate())
                .sleepScheduleAchievement(sleepSchedule.getAchieveArray())
                .build();

    }

}

