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
