package com.onetwo.mongddang.domain.user.repository;

import com.onetwo.mongddang.domain.user.model.CtoP;
import com.onetwo.mongddang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CtoPRepository extends JpaRepository<CtoP, Long> {

    // 보호자와 어린이가 함께 엮인 데이터를 조회합니다. 그저 존재여부를 찾기 위함임.
    @Query("SELECT c FROM CtoP c WHERE c.child = :child AND c.protector = :protector")
    List<CtoP> findByChildAndProtector(@Param("protector") User protector, @Param("child") User child);

    // 보호자 id를 통해, 연결된 어린이 정보들을 리스트 형식으로 조회합니다.
    @Query("SELECT c.child FROM CtoP c WHERE c.protector.id = :protectorId")
    List<User> findChildByProtectorId(@Param("protectorId") Long protectorId);

    // 어린이 id를 통해, 연결된 보호자 정보들을 리스트 형식으로 조회합니다.
    @Query("SELECT c.protector FROM CtoP c WHERE c.child.id = :childId")
    List<User> findProtectorByChildId(@Param("childId") Long childId);

    List<CtoP> findByChild(User child);
}
