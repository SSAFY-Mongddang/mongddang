package com.onetwo.mongddang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
//converter 사용시 @CreatedDate 사용 위해 어노테이션 추가
@EnableJpaAuditing
@EnableScheduling // 스케줄러 활성화
public class MongddangApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongddangApplication.class, args);
	}

}
