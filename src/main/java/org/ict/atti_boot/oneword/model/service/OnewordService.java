package org.ict.atti_boot.oneword.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.jpa.entity.OnewordEntity;
import org.ict.atti_boot.oneword.jpa.entity.OnewordSubjectEntity;
import org.ict.atti_boot.oneword.jpa.repository.OnewordRepository;
import org.ict.atti_boot.oneword.model.dto.OnewordDto;
import org.ict.atti_boot.oneword.model.dto.OnewordSubjectDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OnewordService {
    private final OnewordRepository onewordRepository;

    public ArrayList<OnewordDto> selectList(Pageable pageable) {
        Page<OnewordEntity> pages = onewordRepository.findAll(pageable);
        ArrayList<OnewordDto> list = new ArrayList<>();

        for (OnewordEntity entity : pages) {
            OnewordDto onewordDto = entity.toDto();
            list.add(onewordDto);
        }
        return list;
    }

    /// 오늘 한 줄 주제 번호 검색 목록 조회
    public ArrayList<OnewordDto> selectSearchOneword(String keyword, Pageable pageable) {
        Page<OnewordEntity> pages = onewordRepository.findSearchSearchOneword(keyword, pageable);
        ArrayList<OnewordDto> list = new ArrayList<>();

        for (OnewordEntity entity : pages) {
            OnewordDto onewordDto = entity.toDto();
            list.add(onewordDto);
        }
        return list;
    }

    //// 상세 조회 처리용
    public OnewordDto selectOnewordDetail(Integer owNum) {
        Optional<OnewordEntity> optionalOnewordEntity = onewordRepository.findById(owNum);
        return optionalOnewordEntity.get().toDto();

        //Optional<OnewordEntity> optionalOnewordEntity = onewordRepository.findById(owNum);
        //OnewordEntity onewordEntity  = optionalOnewordEntity.get();

        // 게시글 조회수 1증가 처리
        //onewordEntity.setOnewordReadcount(onewordEntity.getOnewordReadcount() + 1);
        //onewordRepository.save(onewordEntity); //// 존재하는 글에 save 하면 수정됨
        //return onewordEntity.toDto();
    }

    @Transactional
    public void insertOneword(OnewordDto onewordDto) {
        //save(entity) : Entity
        //jpa 제공, insert 문과 update문 처리용 메서드
        onewordRepository.save(onewordDto.toEntity());
    }

    @Transactional
    public void updateOneword(OnewordDto onewordDto) {
        onewordRepository.save(onewordDto.toEntity());
    }

    @Transactional
    public void deleteOneword(int owNum) {
        onewordRepository.deleteById(owNum);
    }

}
