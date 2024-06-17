INSERT INTO USERS (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user001', 'password123', 'John Doe', 'Johnny', 'john.doe@example.com', '555-1234',
    TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'D', 'M', 'http://example.com/profile/user001',
    SYSTIMESTAMP, 'standard', 'token001'
);

INSERT INTO USERS (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user002', 'password456', 'Jane Smith', 'Janie', 'jane.smith@example.com', '555-5678',
    TO_DATE('1985-05-15', 'YYYY-MM-DD'), 'D', 'F', 'http://example.com/profile/user002',
    SYSTIMESTAMP, 'standard', 'token002'
);

INSERT INTO USERS (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user003', 'password789', 'Robert Brown', 'Rob', 'robert.brown@example.com', '555-9876',
    TO_DATE('1975-10-25', 'YYYY-MM-DD'), 'D', 'M', 'http://example.com/profile/user003',
    SYSTIMESTAMP, 'standard', 'token003'
);

INSERT INTO USERS (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user004', 'passwordabc', 'Emily White', 'Em', 'emily.white@example.com', '555-6543',
    TO_DATE('1992-08-13', 'YYYY-MM-DD'), 'D', 'F', 'http://example.com/profile/user004',
    SYSTIMESTAMP, 'standard', 'token004'
);

INSERT INTO USERS (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user005', 'passwordxyz', 'Michael Green', 'Mike', 'michael.green@example.com', '555-3210',
    TO_DATE('1980-03-30', 'YYYY-MM-DD'), 'D', 'M', 'http://example.com/profile/user005',
    SYSTIMESTAMP, 'standard', 'token005'
);

-- 더미 데이터 삽입을 위한 기존 USER_ID
INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user001', '555-4321', '우리는 최고의 의료 서비스를 제공합니다.', '서울시 강남구 역삼동 123-45', '강남서울병원'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user002', '555-8765', '친절하고 따뜻한 의료 서비스를 약속합니다.', '부산시 해운대구 우동 456-78', '해운대성모병원'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user003', '555-5432', '모든 환자에게 최선을 다합니다.', '대구시 수성구 범어동 789-01', '대구중앙병원'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user004', '555-6789', '최신 의료 장비와 우수한 의료진을 보유하고 있습니다.', '인천시 남동구 구월동 234-56', '인천베스트병원'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user005', '555-3210', '모든 치료에 최선을 다하겠습니다.', '광주시 서구 치평동 123-45', '광주사랑병원'
);

-- 서울대학교 졸업
INSERT INTO Education VALUES('서울대학교 졸업', 'user001');
-- 하버드대학교 석사
INSERT INTO Education VALUES('하버드대학교 석사', 'user001');
-- 뉴욕대학교 박사
INSERT INTO Education VALUES('뉴욕대학교 박사', 'user001');
-- 서울고등학교 졸업
INSERT INTO Education VALUES('서울고등학교 졸업', 'user001');
-- 서울대학교 의과대학 졸업
INSERT INTO Education VALUES('서울대학교 의과대학 졸업', 'user001');

-- 고려대학교 졸업
INSERT INTO Education VALUES('고려대학교 졸업', 'user002');
-- 옥스퍼드대학교 석사
INSERT INTO Education VALUES('옥스퍼드대학교 석사', 'user002');
-- 서울여자대학교 졸업
INSERT INTO Education VALUES('서울여자대학교 졸업', 'user002');
-- 인하대학교 의과대학 졸업
INSERT INTO Education VALUES('인하대학교 의과대학 졸업', 'user002');
-- 연세대학교 박사
INSERT INTO Education VALUES('연세대학교 박사', 'user002');

-- 연세대학교 졸업
INSERT INTO Education VALUES('연세대학교 졸업', 'user003');
-- 토론토대학교 석사
INSERT INTO Education VALUES('토론토대학교 석사', 'user003');
-- 대구고등학교 졸업
INSERT INTO Education VALUES('대구고등학교 졸업', 'user003');
-- 부산대학교 의과대학 졸업
INSERT INTO Education VALUES('부산대학교 의과대학 졸업', 'user003');
-- 프린스턴대학교 박사
INSERT INTO Education VALUES('프린스턴대학교 박사', 'user003');

-- 부산대학교 졸업
INSERT INTO Education VALUES('부산대학교 졸업', 'user004');
-- 스탠포드대학교 석사
INSERT INTO Education VALUES('스탠포드대학교 석사', 'user004');
-- 경북대학교 박사
INSERT INTO Education VALUES('경북대학교 박사', 'user004');
-- 부산여자고등학교 졸업
INSERT INTO Education VALUES('부산여자고등학교 졸업', 'user004');
-- 서울대학교 의과대학 졸업
INSERT INTO Education VALUES('서울대학교 의과대학 졸업', 'user004');

-- 이화여자대학교 졸업
INSERT INTO Education VALUES('이화여자대학교 졸업', 'user005');
-- 캘리포니아대학교 버클리 석사
INSERT INTO Education VALUES('캘리포니아대학교 버클리 석사', 'user005');
-- 광주고등학교 졸업
INSERT INTO Education VALUES('광주고등학교 졸업', 'user005');
-- 조지타운대학교 박사
INSERT INTO Education VALUES('조지타운대학교 박사', 'user005');
-- 서울대학교 의과대학 졸업
INSERT INTO Education VALUES('서울대학교 의과대학 졸업', 'user005');


-- user001에 대한 경력 데이터
INSERT INTO Career VALUES('일본유학 2년', 'user001');
INSERT INTO Career VALUES('서울대병원 5년', 'user001');
INSERT INTO Career VALUES('삼성서울병원 3년', 'user001');
INSERT INTO Career VALUES('영국유학 1년', 'user001');
INSERT INTO Career VALUES('세브란스병원 4년', 'user001');

-- user002에 대한 경력 데이터
INSERT INTO Career VALUES('미국유학 3년', 'user002');
INSERT INTO Career VALUES('고려대병원 2년', 'user002');
INSERT INTO Career VALUES('서울아산병원 3년', 'user002');
INSERT INTO Career VALUES('독일유학 2년', 'user002');
INSERT INTO Career VALUES('한양대병원 4년', 'user002');

-- user003에 대한 경력 데이터
INSERT INTO Career VALUES('캐나다유학 4년', 'user003');
INSERT INTO Career VALUES('대구병원 3년', 'user003');
INSERT INTO Career VALUES('부산대병원 2년', 'user003');
INSERT INTO Career VALUES('호주유학 1년', 'user003');
INSERT INTO Career VALUES('서울성모병원 5년', 'user003');

-- user004에 대한 경력 데이터
INSERT INTO Career VALUES('영국유학 2년', 'user004');
INSERT INTO Career VALUES('부산대병원 3년', 'user004');
INSERT INTO Career VALUES('서울대병원 4년', 'user004');
INSERT INTO Career VALUES('미국유학 3년', 'user004');
INSERT INTO Career VALUES('연세대병원 2년', 'user004');

-- user005에 대한 경력 데이터
INSERT INTO Career VALUES('프랑스유학 2년', 'user005');
INSERT INTO Career VALUES('이대목동병원 3년', 'user005');
INSERT INTO Career VALUES('광주기독병원 4년', 'user005');
INSERT INTO Career VALUES('일본유학 1년', 'user005');
INSERT INTO Career VALUES('서울삼성병원 3년', 'user005');


-- user001에 대한 태그 데이터
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user001', '결혼/육아');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user001', '대인관계');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user001', '직장');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user001', '이별/이혼');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user001', '자아/성격');

-- user002에 대한 태그 데이터
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user002', '가족');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user002', '정신건강');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user002', '성추행');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user002', '외모');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user002', '학업/고시');

-- user003에 대한 태그 데이터
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user003', '우울');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user003', '스트레스');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user003', '화병');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user003', '공황');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user003', '불면');

-- user004에 대한 태그 데이터
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user004', '자존감');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user004', '강박');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user004', '충동/폭력');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user004', '트라우마');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user004', '조울증');

-- user005에 대한 태그 데이터
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user005', '결혼/육아');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user005', '대인관계');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user005', '직장');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user005', '가족');
INSERT INTO DOCTOR_TAG (USER_ID, TAG) VALUES ('user005', '자아/성격');


-- 리뷰 데이터 삽입
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (1, TO_DATE('2023-01-15', 'YYYY-MM-DD'), 4.5, '매우 친절하고 전문적이었습니다.', 'user001', 'user01');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (2, TO_DATE('2023-02-20', 'YYYY-MM-DD'), 3.8, '진료 대기 시간이 길었어요.', 'user001', 'user02');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (3, TO_DATE('2023-03-05', 'YYYY-MM-DD'), 4.2, '의사 선생님이 자세히 설명해 주셨어요.', 'user001', 'user03');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (4, TO_DATE('2023-03-10', 'YYYY-MM-DD'), 4.7, '시설이 깨끗하고 최신식입니다.', 'user001', 'user04');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (5, TO_DATE('2023-04-20', 'YYYY-MM-DD'), 4.0, '병원이 접근성이 좋습니다.', 'user002', 'user05');

INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (6, TO_DATE('2023-01-18', 'YYYY-MM-DD'), 3.5, '진료가 친절했지만 조금 급하게 끝났어요.', 'user002', 'user07');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (7, TO_DATE('2023-02-25', 'YYYY-MM-DD'), 4.9, '모든 것이 완벽했습니다.', 'user002', 'user08');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (8, TO_DATE('2023-03-15', 'YYYY-MM-DD'), 4.3, '진단이 명확하고 자세했어요.', 'user002', 'user09');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (9, TO_DATE('2023-04-05', 'YYYY-MM-DD'), 3.6, '조금 더 친절했으면 좋겠어요.', 'user002', 'user10');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (10, TO_DATE('2023-05-10', 'YYYY-MM-DD'), 4.8, '정말 만족스러웠습니다.', 'user003', 'user01');

INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (11, TO_DATE('2023-01-25', 'YYYY-MM-DD'), 4.1, '친절한 설명 감사합니다.', 'user003', 'user02');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (12, TO_DATE('2023-02-05', 'YYYY-MM-DD'), 4.5, '편안하게 진료받았습니다.', 'user003', 'user03');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (13, TO_DATE('2023-03-22', 'YYYY-MM-DD'), 3.9, '설명이 조금 부족했어요.', 'user003', 'user04');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (14, TO_DATE('2023-04-18', 'YYYY-MM-DD'), 4.2, '전반적으로 만족스러웠습니다.', 'user003', 'user05');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (15, TO_DATE('2023-05-25', 'YYYY-MM-DD'), 4.7, '적극 추천합니다.', 'user004', 'user07');

INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (16, TO_DATE('2023-01-10', 'YYYY-MM-DD'), 3.8, '조금 더 친절했으면 합니다.', 'user004', 'user08');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (17, TO_DATE('2023-02-14', 'YYYY-MM-DD'), 4.4, '상담이 정말 유익했어요.', 'user004', 'user09');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (18, TO_DATE('2023-03-30', 'YYYY-MM-DD'), 3.7, '진료실 환경이 좋았습니다.', 'user004', 'user10');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (19, TO_DATE('2023-04-10', 'YYYY-MM-DD'), 4.6, '굉장히 만족스러운 진료였습니다.', 'user005', 'user01');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (20, TO_DATE('2023-05-15', 'YYYY-MM-DD'), 4.3, '친절하고 편안하게 설명해 주셨어요.', 'user005', 'user02');

INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (21, TO_DATE('2023-01-20', 'YYYY-MM-DD'), 4.0, '전체적으로 만족스러웠습니다.', 'user005', 'user03');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (22, TO_DATE('2023-02-25', 'YYYY-MM-DD'), 4.1, '다음에도 방문할 생각입니다.', 'user005', 'user04');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (23, TO_DATE('2023-03-14', 'YYYY-MM-DD'), 3.9, '진료가 너무 짧았습니다.', 'user005', 'user05');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (24, TO_DATE('2023-04-19', 'YYYY-MM-DD'), 4.5, '자세한 설명이 좋았습니다.', 'user005', 'user07');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID) VALUES (25, TO_DATE('2023-05-22', 'YYYY-MM-DD'), 4.8, '아주 만족스러웠습니다.', 'user005', 'user08');
-- 추가 리뷰 데이터

-- user001에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (26, TO_DATE('2023-06-01', 'YYYY-MM-DD'), 4.5, '친절하고 세심한 진료였습니다.', 'user001', 'user09');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (27, TO_DATE('2023-06-10', 'YYYY-MM-DD'), 3.7, '기다리는 시간이 좀 길었어요.', 'user001', 'user08');

-- user002에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (28, TO_DATE('2023-06-15', 'YYYY-MM-DD'), 4.8, '상담이 매우 유익했습니다.', 'user002', 'user03');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (29, TO_DATE('2023-06-20', 'YYYY-MM-DD'), 4.0, '진료가 명확하고 좋았습니다.', 'user002', 'user07');

-- user003에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (30, TO_DATE('2023-06-25', 'YYYY-MM-DD'), 4.9, '매우 만족스러운 진료였습니다.', 'user003', 'user04');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (31, TO_DATE('2023-06-30', 'YYYY-MM-DD'), 4.2, '진료실 분위기가 편안했습니다.', 'user003', 'user10');

-- user004에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (32, TO_DATE('2023-07-05', 'YYYY-MM-DD'), 4.6, '설명이 친절하고 자세했습니다.', 'user004', 'user02');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (33, TO_DATE('2023-07-10', 'YYYY-MM-DD'), 3.8, '진료 시간이 좀 짧았습니다.', 'user004', 'user01');

-- user005에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (34, TO_DATE('2023-07-15', 'YYYY-MM-DD'), 4.3, '친절하게 상담해주셔서 감사합니다.', 'user005', 'user05');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (35, TO_DATE('2023-07-20', 'YYYY-MM-DD'), 4.7, '매우 만족스러운 경험이었습니다.', 'user005', 'user08');

-- user001에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (36, TO_DATE('2023-07-25', 'YYYY-MM-DD'), 4.1, '진료가 매우 친절했습니다.', 'user001', 'user07');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (37, TO_DATE('2023-07-30', 'YYYY-MM-DD'), 3.9, '대기 시간이 길었어요.', 'user001', 'user10');

-- user002에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (38, TO_DATE('2023-08-01', 'YYYY-MM-DD'), 4.4, '전반적으로 만족했습니다.', 'user002', 'user01');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (39, TO_DATE('2023-08-05', 'YYYY-MM-DD'), 4.0, '설명이 부족했어요.', 'user002', 'user05');

-- user003에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (40, TO_DATE('2023-08-10', 'YYYY-MM-DD'), 4.7, '친절하고 만족스러웠습니다.', 'user003', 'user09');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (41, TO_DATE('2023-08-15', 'YYYY-MM-DD'), 4.2, '진료 시간이 적당했습니다.', 'user003', 'user02');

-- user004에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (42, TO_DATE('2023-08-20', 'YYYY-MM-DD'), 4.5, '매우 친절하셨어요.', 'user004', 'user03');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (43, TO_DATE('2023-08-25', 'YYYY-MM-DD'), 3.6, '진료 대기 시간이 길었습니다.', 'user004', 'user08');

-- user005에 대한 추가 리뷰
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (44, TO_DATE('2023-08-30', 'YYYY-MM-DD'), 4.8, '진료가 매우 만족스러웠습니다.', 'user005', 'user10');
INSERT INTO DOCTOR_REVIEW (REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID)
VALUES (45, TO_DATE('2023-09-01', 'YYYY-MM-DD'), 4.1, '정말 유익한 상담이었습니다.', 'user005', 'user07');

COMMIT;


