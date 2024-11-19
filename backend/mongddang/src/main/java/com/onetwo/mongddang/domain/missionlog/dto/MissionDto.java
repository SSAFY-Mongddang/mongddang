package com.onetwo.mongddang.domain.missionlog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class MissionDto {


    public enum Status {
        rewardable, not_rewardable, already_rewarded
    }

    public enum Mission {
        breakfast, lunch, dinner, first_medication, second_medication, third_medication, exercise;

        public static final Map<Mission, String> missionDescriptions = new HashMap<>();

        static {
            missionDescriptions.put(breakfast, "아침 먹기");
            missionDescriptions.put(lunch, "점심 먹기");
            missionDescriptions.put(dinner, "저녁 먹기");
            missionDescriptions.put(first_medication, "약 맞기 1회");
            missionDescriptions.put(second_medication, "약 맞기 2회");
            missionDescriptions.put(third_medication, "약 맞기 3회");
            missionDescriptions.put(exercise, "운동하기");
        }

        public String getDescription() {
            return missionDescriptions.get(this);
        }


    }

}
