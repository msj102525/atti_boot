package org.ict.atti_boot.oneword.jpa.repository;

import com.querydsl.core.types.ExpressionUtils;
import org.ict.atti_boot.oneword.jpa.entity.OnewordSubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnewordSubjectRepository extends JpaRepository<OnewordSubjectEntity, Integer> {
    //jpa 가 제공하는 기본 메서드를 사용하려면 필요함

//    // 전체 건수 조회 메서드
//    long count();

    @Query(value="select owsj_num, owsj_subject, owsj_writer from onewordsubject order by owsj_num desc", nativeQuery=true)
    List<OnewordSubjectVo> findSelectListAll();

    //@Query + JPQL 사용 (엔티티와 프로퍼티 사용)
    @Query(value = "SELECT b FROM OnewordSubjectEntity b WHERE b.owsjSubject LIKE %:keyword%",
            countQuery = "SELECT COUNT(b) FRoM OnewordSubjectEntity b WHERE b.owsjSubject LIKE %:keyword%")
    Page<OnewordSubjectEntity> findSearchSearchOwsjSubject(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    ///////////////////////////////////////////////////////
    //// count
    ///////////////////////////////////////////////////////
    //@Query + Native Query 사용 (테이블명과 컬럼명 사용)
    @Query(value="select count(*) from ONEWORDSUBJECT b where b.owsj_subject like %:keyword%", nativeQuery=true)
    Long countSearchSearchOwsjSubject(@Param("keyword") String keyword);

}