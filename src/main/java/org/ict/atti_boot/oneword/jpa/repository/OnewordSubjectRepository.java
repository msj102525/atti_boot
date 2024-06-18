package org.ict.atti_boot.oneword.jpa.repository;

import com.querydsl.core.types.ExpressionUtils;
import org.ict.atti_boot.oneword.jpa.entity.OnewordSubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnewordSubjectRepository extends JpaRepository<OnewordSubjectEntity, Integer> {
    //jpa 가 제공하는 기본 메서드를 사용하려면 필요함

    //@Query + JPQL 사용 (엔티티와 프로퍼티 사용)
    @Query(value = "SELECT b FROM OnewordSubjectEntity b WHERE b.owsjSubject LIKE %:keyword%",
            countQuery = "SELECT COUNT(b) FRoM OnewordSubjectEntity b WHERE b.owsjSubject LIKE %:keyword%")
    Page<OnewordSubjectEntity> findSearchTitle(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    // 전체 건수 조회 메서드
    long count();

}