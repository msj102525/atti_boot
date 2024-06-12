package org.ict.atti_boot.board.jpa.repository;

import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    @Query("SELECT b FROM BoardEntity b ORDER BY " +
           "CASE WHEN b.importance = 2 THEN 0 ELSE 1 END, " +
           "CASE WHEN b.importance = 2 THEN b.boardNum END DESC, " +
           "b.boardNum DESC")
    Page<BoardEntity> findAllOrdered(Pageable pageable);
}
