package org.ict.atti_boot.board.jpa.repository;

import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    @Query("SELECT b FROM BoardEntity b ORDER BY " +"b.boardNum DESC")
    Page<BoardEntity> findAllOrdered(Pageable pageable);

    Page<BoardEntity> findAllByBoardWriter(String boardWriter, Pageable pageable);

    Page<BoardEntity> findAllByBoardTitle(String boardTitle, Pageable pageable);

    Page<BoardEntity> findAllByBoardDateBetween(LocalDateTime beginDate, LocalDateTime endDate, Pageable pageable);


}
