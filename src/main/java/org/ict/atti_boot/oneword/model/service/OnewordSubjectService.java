package org.ict.atti_boot.oneword.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.jpa.entity.OnewordSubjectEntity;
import org.ict.atti_boot.oneword.jpa.repository.OnewordSubjectRepository;
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
public class OnewordSubjectService {
    private final OnewordSubjectRepository onewordSubjectRepository;

    public ArrayList<OnewordSubjectDto> selectList(Pageable pageable) {
        Page<OnewordSubjectEntity> pages = onewordSubjectRepository.findAll(pageable);
        ArrayList<OnewordSubjectDto> list = new ArrayList<>();

        for (OnewordSubjectEntity entity : pages) {
            OnewordSubjectDto onewordSubjectDto = entity.toDto();
            list.add(onewordSubjectDto);
        }
        return list;
    }

    public long getCountOwsjSubjectList() {
        return onewordSubjectRepository.count();
    }

    //// 상세 조회 처리용
    public OnewordSubjectDto selectOnewordSubjectDetail(Integer owsjNum) {
        Optional<OnewordSubjectEntity> optionalOnewordSubjectEntity = onewordSubjectRepository.findById(owsjNum);
        return optionalOnewordSubjectEntity.get().toDto();

        //Optional<OnewordSubjectEntity> optionalOnewordSubjectEntity = onewordSubjectRepository.findById(owsjNum);
        //OnewordSubjectEntity onewordSubjectEntity  = optionalOnewordSubjectEntity.get();

        // 게시글 조회수 1증가 처리
        //onewordSubjectEntity.setOnewordSubjectReadcount(onewordSubjectEntity.getOnewordSubjectReadcount() + 1);
        //onewordSubjectRepository.save(onewordSubjectEntity); //존재하는 글에 save 하면 수정됨
        //return onewordSubjectEntity.toDto();
    }

    @Transactional
    public void insertOnewordSubject(OnewordSubjectDto onewordSubjectDto) {
        //save(entity) : Entity
        //jpa 제공, insert 문과 update문 처리용 메서드
        onewordSubjectRepository.save(onewordSubjectDto.toEntity());
    }

    @Transactional
    public void updateOnewordSubject(OnewordSubjectDto onewordSubjectDto) {
        onewordSubjectRepository.save(onewordSubjectDto.toEntity());
    }

    @Transactional
    public void deleteOnewordSubject(Integer owsjNum) {
        onewordSubjectRepository.deleteById(owsjNum);
    }

    /// 제목 검색 목록 조회
    public ArrayList<OnewordSubjectDto> selectSearchOwsjSubject(String keyword, Pageable pageable) {
        Page<OnewordSubjectEntity> pages = onewordSubjectRepository.findSearchSearchOwsjSubject(keyword, pageable);
        ArrayList<OnewordSubjectDto> list = new ArrayList<>();

        for (OnewordSubjectEntity entity : pages) {
            OnewordSubjectDto onewordSubjectDto = entity.toDto();
            list.add(onewordSubjectDto);
        }
        return list;
    }

    //// 제목 검색에 대한 목록 갯수
    public long getCountSearchOwsjSubject(String keyword) {
        return onewordSubjectRepository.countSearchSearchOwsjSubject(keyword);
    }

}
