package org.ict.atti_boot.oneword.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.jpa.repository.OnewordSubjectRepository;
import org.ict.atti_boot.oneword.model.dto.OnewordSubjectDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OnewordSubjectService {
    private final OnewordSubjectRepository onewordSubjectRepository;

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
    public int deleteOnewordSubject(int owsjNum) {     //int 리턴 형태를 만든다면 아래와 같이 작성함
        try {
            //jpa 제공 메서드
            onewordSubjectRepository.deleteById(owsjNum);
            return 1;
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

}
