package com.onetwo.mongddang.domain.game.coinLog.controller;

import com.onetwo.mongddang.common.responseDto.ResponseDto;
import com.onetwo.mongddang.common.annotation.ChildRequired;
import com.onetwo.mongddang.domain.game.coinLog.service.CoinLogService;
import com.onetwo.mongddang.domain.user.jwt.JwtExtratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
@Tag(name = "Coin API", description = "코인 api")
public class CoinLogController {

    private final CoinLogService coinLogService;
    private final JwtExtratService jwtExtratService;

    /**
     * 보유 코인을 반환
     *
     * @return ResponseCoin
     */
    @GetMapping("/coin")
    @ChildRequired
    @Tag(name = "Coin API", description = "코인 api")
    @Operation(summary = "보유 코인 조회 api", description = "로그인 된 유저가 보유한 코인을 조회합니다.")
    public ResponseEntity<ResponseDto> getRemainCoin(HttpServletRequest request) {
        log.info("GET /api/game/coin");

        // 유저의 id를 jwt 에서 추출
        Long userId = jwtExtratService.jwtFindId(request);

        // 빌더 패턴을 사용하여 ResponseCoin 객체 생성
        ResponseDto responseDto = coinLogService.responseGetCoinCount(userId);

        log.info("responseGetCoinCount responseDto: {}", responseDto);
        return ResponseEntity.ok(responseDto);
    }

}
