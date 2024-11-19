package com.onetwo.mongddang.domain.game.coinLog.application;

import com.onetwo.mongddang.domain.game.coinLog.errors.CustomCoinLogErrorCode;
import com.onetwo.mongddang.domain.game.coinLog.model.CoinLog;
import com.onetwo.mongddang.domain.game.coinLog.model.CoinLog.CoinCategory;
import com.onetwo.mongddang.domain.game.coinLog.repository.CoinLogRepository;
import com.onetwo.mongddang.domain.user.error.CustomUserErrorCode;
import com.onetwo.mongddang.domain.user.model.User;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import com.onetwo.mongddang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoinLogUtils {

    private final CoinLogRepository coinLogRepository;
    private final UserRepository userRepository;

    /**
     * 유저의 보유 코인을 조회합니다.
     *
     * @param id 코인을 조회할 유저의 id
     * @return 보유 코인량
     */
    public int getCoinCount(Long id) {
        log.info("getCoinCount id: {}", id);

        // userId에 해당하는 CoinLog 조회
        CoinLog coinLog = coinLogRepository.findTopByChildIdOrderByIdDesc(id).orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 현재 coin 값 반환
        return coinLog.getCoin();
    }

    /**
     * 코인을 지급합니다.
     *
     * @param id       코인을 지급할 유저의 id
     * @param category 코인 지급 카테고리
     * @param amount   지급할 코인량
     * @return 지급된 코인 로그
     * @throws RestApiException amount 가 0 이하인 경우
     */
    @Transactional
    public CoinLog rewardCoin(Long id, CoinCategory category, int amount) {

        // id 에 해당하는 User 조회
        User user = userRepository.findById(id).orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // 새로운 CoinLog 기록 추가
        int remainCoin = getCoinCount(id); // 보유 코인
        CoinLog newCoinLog = CoinLog.builder()
                .child(user) // 유저
                .coin(remainCoin + amount) // 지급량
                .category(category) // 카테고리
                .build();

        // 새로운 CoinLog 저장
        coinLogRepository.save(newCoinLog);

        return newCoinLog;
    }


    /**
     * 코인을 차감합니다.
     *
     * @param id       유저의 id
     * @param category 코인 차감 카테고리
     * @param amount   차감할 코인량
     * @return 차감된 코인 로그
     * @throws RestApiException amount 가 0 이하인 경우
     */
    @Transactional
    public CoinLog deductCoin(Long id, CoinCategory category, int amount) {
        // id 에 해당하는 User 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestApiException(CustomUserErrorCode.USER_NOT_FOUND));

        // userId에 해당하는 CoinLog 조회
        CoinLog coinLog = coinLogRepository.findTopByChildIdOrderByIdDesc(id)
                .orElseThrow(() -> new RestApiException(CustomCoinLogErrorCode.NOT_FOUND_COIN_LOG));

        // 코인이 부족할 경우 예외 처리
        if (coinLog.getCoin() - amount < 0) {
            throw new RestApiException(CustomCoinLogErrorCode.INSUFFICIENT_COIN);
        }

        // 새로운 CoinLog 기록 추가
        CoinLog newCoinLog = CoinLog.builder()
                .child(user) // 유저
                .coin(coinLog.getCoin() - amount) // 차감량
                .category(CoinCategory.mongddang) // 카테고리
                .build();

        // 새로운 CoinLog 저장
        coinLogRepository.save(newCoinLog);

        return newCoinLog;
    }

    /**
     * 코인 지급 및 차감을 처리합니다.
     *
     * @param id           유저의 id
     * @param coinCategory 코인 카테고리
     * @param amount       코인량
     */
    public void classificationCategoryType(Long id, CoinCategory coinCategory, int amount) {
        if (coinCategory.equals(CoinCategory.mongddang)) {
            deductCoin(id, coinCategory, amount); //  차감
        } else {
            rewardCoin(id, coinCategory, amount); //  지급
        }
    }
}
