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

}
