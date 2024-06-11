package org.ict.atti_boot.oneword.jpa.repository;

import org.ict.atti_boot.oneword.jpa.entity.OnewordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnewordRepository extends JpaRepository<OnewordEntity, Integer>  {
    //jpa 가 제공하는 기본 메서드를 사용하려면 필요함
}