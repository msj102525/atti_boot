package org.ict.atti_boot.oneword.jpa.repository;

import org.ict.atti_boot.oneword.jpa.entity.OnewordEntity;
import org.ict.atti_boot.oneword.jpa.entity.OnewordSubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnewordRepository extends JpaRepository<OnewordEntity, Integer>  {
    //jpa 가 제공하는 기본 메서드를 사용하려면 필요함

    //@Query + JPQL 사용 (엔티티와 프로퍼티 사용)
    @Query(value = "SELECT b FROM OnewordEntity b WHERE b.owsjNum = :keyword",
            countQuery = "SELECT COUNT(b) FRoM OnewordEntity b WHERE b.owsjNum = :keyword")
    Page<OnewordEntity> findSearchSearchOneword(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

}