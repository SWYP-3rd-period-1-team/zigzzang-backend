package com.swig.zigzzang.calender.dto.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class MyCalenderDTO {

    private Long calenderId; // 캘린더 번호

    private SleepScheduleDTO sleepScheduleInfo; // 수면 스케줄 정보

    private List<ScheduleDTO> scheduleInfoList; //  스케쥴 목록

    private List<SupplementDTO> supplementInfoList; // 영양제 목록

    private WaterIntakeDTO waterIntakeInfo; // 물 섭취 정보
    


}
