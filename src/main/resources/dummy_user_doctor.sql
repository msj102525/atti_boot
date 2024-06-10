INSERT INTO users (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user1', 'password1', '홍길동', '길동이', 'user1@example.com', '010-1234-5678',
    TO_DATE('1990-01-01', 'YYYY-MM-DD'), 'D', 'M', '/profile/user1.png',
    SYSTIMESTAMP, 'EMAIL', 'access_token_1'
);

INSERT INTO users (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user3', 'password3', '김철수', '철수', 'user3@example.com', '010-3456-7890',
    TO_DATE('1985-03-03', 'YYYY-MM-DD'), 'D', 'M', '/profile/user3.png',
    SYSTIMESTAMP, 'EMAIL', 'access_token_3'
);


INSERT INTO users (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user5', 'password5', '최영수', '영수', 'user5@example.com', '010-5678-9012',
    TO_DATE('1979-05-05', 'YYYY-MM-DD'), 'D', 'M', '/profile/user5.png',
    SYSTIMESTAMP, 'EMAIL', 'access_token_5'
);

INSERT INTO users (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user7', 'password7', '한지민', '지민', 'user7@example.com', '010-7890-1234',
    TO_DATE('1991-07-07', 'YYYY-MM-DD'), 'D', 'F', '/profile/user7.png',
    SYSTIMESTAMP, 'EMAIL', 'access_token_7'
);


INSERT INTO users (
    USER_ID, PASSWORD, USER_NAME, NICK_NAME, EMAIL, PHONE, BIRTH_DATE,
    USER_TYPE, GENDER, PROFILE_URL, LOGIN_TIME, LOGIN_TYPE, SNS_ACCESS_TOKEN
) VALUES (
    'user9', 'password9', '김태희', '태희', 'user9@example.com', '010-9012-3456',
    TO_DATE('1986-09-09', 'YYYY-MM-DD'), 'D', 'F', '/profile/user9.png',
    SYSTIMESTAMP, 'EMAIL', 'access_token_9'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user1', '02-1234-5678', '허준 박사는 한의학의 아버지로 알려져 있습니다.', '서울시 종로구 세종대로 1길 1', '허준 한의원'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user3', '031-2345-6789', '김철수 박사는 소아과 전문의로 유명합니다.', '경기도 성남시 분당구 대왕판교로 2길 2', '철수 소아과'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user5', '02-3456-7890', '최영수 박사는 정형외과 전문의로 오랜 경험을 가지고 있습니다.', '서울시 강남구 강남대로 3길 3', '영수 정형외과'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user7', '02-4567-8901', '한지민 박사는 피부과 전문의로 다양한 피부 질환을 치료합니다.', '서울시 서초구 서초대로 4길 4', '지민 피부과'
);

INSERT INTO DOCTOR (
    USER_ID, HOSPITAL_PHONE, INTRODUCE, HOSPITAL_ADDRESS, HOSPITAL_NAME
) VALUES (
    'user9', '02-5678-9012', '김태희 박사는 내과 전문의로 환자들에게 친절한 진료를 제공합니다.', '서울시 송파구 송파대로 5길 5', '태희 내과'
);
commit;