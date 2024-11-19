package com.onetwo.mongddang.domain.vital.application;

import com.onetwo.mongddang.domain.vital.model.Vital;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VitalUtils {


    // 전체 bloodSugar의 합산
    public int getTotalBloodSugar(List<Vital> vitalList) {
        // 전체 bloodSugar의 합산
        int totalBloodSugar = 0;
        for (Vital vital : vitalList) {
            totalBloodSugar += vital.getBloodSugarLevel();
        }
        return totalBloodSugar;
    }


    // GMI 계산
    public float getGMI(float a1c) {
        // A1C를 GMI로 변환
        return (a1c + 46.7f) / 28.7f;
    }


    // 이번 주의 평균 혈당 계산
    public float getAverageGlucose(List<Vital> vitalList) {
        // 이번 주의 평균 혈당 계산
        return (float) this.getTotalBloodSugar(vitalList) / vitalList.size();
    }


    // 혈당 변동성 계산
    public float getGlucoseVariability(List<Vital> vitalList, float averageBloodSugar) {
        // 혈당 변동성 계산
        float sum = 0;
        for (Vital vital : vitalList) {
            sum += (float) Math.pow(vital.getBloodSugarLevel() - averageBloodSugar, 2);
        }
        return (float) Math.sqrt(sum / vitalList.size());
    }


    // 목표 범위 내 비율
    public float getInTargetRangeRatio(List<Vital> vitalList) {
        // 목표 범위 내 비율
        int inTargetRangeCount = 0;
        for (Vital vital : vitalList) {
            if (vital.getBloodSugarLevel() >= 70 && vital.getBloodSugarLevel() <= 180) {
                inTargetRangeCount++;
            }
        }
        return (float) inTargetRangeCount / vitalList.size() * 100;
    }

}
