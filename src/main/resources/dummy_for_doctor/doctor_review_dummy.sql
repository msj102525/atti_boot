INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    1, TO_DATE('2024-01-01', 'YYYY-MM-DD'), 5, '의사 선생님이 친절하고 설명이 자세했습니다.', 'user1', 'user01'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    2, TO_DATE('2024-02-15', 'YYYY-MM-DD'), 3, '대기 시간이 좀 길었습니다.', 'user1', 'user02'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    3, TO_DATE('2024-03-10', '2024-03-10'), 4, '진료가 매우 만족스러웠습니다. 다음에도 방문할 예정입니다.', 'user1', 'user03'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    4, TO_DATE('2024-04-20', 'YYYY-MM-DD'), 5, '의사 선생님이 매우 친절하게 설명해 주셨습니다.', 'user1', 'user04'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    5, TO_DATE('2024-05-05', 'YYYY-MM-DD'), 2, '병원의 시설이 조금 오래된 것 같습니다.', 'user1', 'user05'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    6, TO_DATE('2024-06-12', 'YYYY-MM-DD'), 4, '진료 시간도 잘 지켜주셔서 만족스러웠습니다.', 'user1', 'user06'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    7, TO_DATE('2024-07-25', 'YYYY-MM-DD'), 1, '진료가 너무 짧아서 불만족스러웠습니다.', 'user1', 'user07'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    8, TO_DATE('2024-08-15', 'YYYY-MM-DD'), 3, '의사 선생님은 친절했지만 대기 시간이 길었습니다.', 'user1', 'user08'
);

INSERT INTO DOCTOR_REVIEW (
    REVIEW_ID, WRITE_DATE, STAR_POINT, CONTENT, DOCTOR_ID, USER_ID
) VALUES (
    9, TO_DATE('2024-09-30', 'YYYY-MM-DD'), 5, '최고의 진료를 받았습니다. 다음에도 방문할 예정입니다.', 'user1', 'user09'
);
commit;