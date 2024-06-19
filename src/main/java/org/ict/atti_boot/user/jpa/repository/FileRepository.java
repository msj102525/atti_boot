package org.ict.atti_boot.user.jpa.repository;

import org.ict.atti_boot.user.jpa.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
