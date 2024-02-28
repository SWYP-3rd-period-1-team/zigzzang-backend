package com.swig.zigzzang.survey.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swig.zigzzang.survey.domain.Survey;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record SurveySaveResponse(
        Long id,
        String userid,
        String targetBodyPart,
        String diagnosisPart,
        String presentedSymptom,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        LocalDateTime savedAt
) {
    public static SurveySaveResponse of(Survey survey) {
        return SurveySaveResponse.builder()
                .id(survey.getSurveyId())
                .userid(survey.getMember().getUserId())
                .diagnosisPart(survey.getDiagnosisPart())
                .presentedSymptom(survey.getPresentedSymptom())
                .targetBodyPart(survey.getTargetBody())
                .savedAt(survey.createdDate)
                .build();
    }
}