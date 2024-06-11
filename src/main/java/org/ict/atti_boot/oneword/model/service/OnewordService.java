package org.ict.atti_boot.oneword.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.atti_boot.oneword.jpa.repository.OnewordRepository;
import org.ict.atti_boot.oneword.model.dto.OnewordDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OnewordService {
    private final OnewordRepository onewordRepository;

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
    public int deleteOneword(int owNum) {     //int 리턴 형태를 만든다면 아래와 같이 작성함
        try {
            //jpa 제공 메서드
            onewordRepository.deleteById(owNum);
            return 1;
        }catch (Exception e){
            log.error(e.getMessage());
            return 0;
        }
    }

}
