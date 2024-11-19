package com.onetwo.mongddang.domain.game.coinLog.service;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.domain.game.coinLog.application.CoinLogUtils;
import com.onetwo.mongddang.domain.game.coinLog.dto.ResponseCoin;
import com.onetwo.mongddang.domain.game.coinLog.repository.CoinLogRepository;
import com.onetwo.mongddang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoinLogService {

    private final CoinLogRepository coinLogRepository;
    private final UserRepository userRepository;
    private final CoinLogUtils coinLogUtils;

    /**
     * 보유 코인 응답 객체를 반환합니다.
     *
     * @param id 코인을 조회할 유저의 id
     * @return ResponseCoin
     */
    public ResponseDto responseGetCoinCount(Long id) {
        log.info("responseGetCoinCount id: {}", id);

        ResponseCoin data = ResponseCoin.builder()
                .coin(coinLogUtils.getCoinCount(id))
                .build();

        ResponseDto response = ResponseDto.builder()
                .message("코인 조회에 성공했습니다.")
                .data(data)
                .build();

        // 현재 coin 값 반환
        return response;
    }
}
