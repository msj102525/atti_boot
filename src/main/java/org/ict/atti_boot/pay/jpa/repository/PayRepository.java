package org.ict.atti_boot.pay.jpa.repository;

import org.ict.atti_boot.pay.jpa.entity.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<PayEntity, String> {

}
