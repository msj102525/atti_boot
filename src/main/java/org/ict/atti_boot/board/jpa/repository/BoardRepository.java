package org.ict.atti_boot.board.jpa.repository;

import org.ict.atti_boot.board.jpa.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    @Query("SELECT b FROM BoardEntity b ORDER BY " +"b.boardNum DESC")
    Page<BoardEntity> findAllOrdered(Pageable pageable);

    List<BoardEntity> findByBoardTitleContaining(String keyword);

    List<BoardEntity> findByBoardWriterContaining(String keyword);

    List<BoardEntity> findByBoardDateBetween(Date beginDate, Date endDate);


}
