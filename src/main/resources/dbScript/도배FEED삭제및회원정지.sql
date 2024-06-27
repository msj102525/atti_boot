CREATE OR REPLACE PROCEDURE sp_feed_count IS
    v_user_id    VARCHAR2(50 BYTE);
    v_feed_num   NUMBER;
    v_user_count NUMBER;
    v_last_user  VARCHAR2(50 BYTE) := NULL;

CURSOR feed_cursor IS
SELECT
    user_id,
    feed_num,
    COUNT(*) OVER (PARTITION BY user_id) AS user_count
FROM
    feed
WHERE
    feed_date BETWEEN SYSDATE - INTERVAL '10' MINUTE AND SYSDATE;

BEGIN
OPEN feed_cursor;

LOOP
FETCH feed_cursor INTO v_user_id, v_feed_num, v_user_count;
        EXIT WHEN feed_cursor%NOTFOUND;

        dbms_output.put_line('USER_ID: ' || v_user_id || ', USER_COUNT: ' || v_user_count || ', FEED_NUM: ' || v_feed_num);

        IF v_user_count > 3 THEN
            IF v_last_user IS NULL OR v_last_user != v_user_id THEN
                dbms_output.put_line('USER_ID: ' || v_user_id || ', FEED_NUM: ' || v_feed_num);

INSERT INTO SUSPENSION (USER_ID, SUSPENSION_NO, SUSPENSION_TITLE, SUSPENSION_CONTENT)
VALUES (v_user_id, SUSPENSION_SEQ.NEXTVAL, '도배로 인한 정지', '10분내에 3개 초과의 글 작성 도배행위로 인해 정지 조취를 취함');

v_last_user := v_user_id;
END IF;

DELETE FROM FEED WHERE FEED_NUM = v_feed_num;
END IF;
END LOOP;

CLOSE feed_cursor;

EXCEPTION
    WHEN OTHERS THEN
        dbms_output.put_line('Error occurred: ' || sqlerrm);
END sp_feed_count;
/

EXECUTE sp_feed_count;
----------------------------------

DECLARE
X NUMBER;
BEGIN
    SYS.DBMS_JOB.SUBMIT(
        job => X,
        what => 'sp_feed_count;',
        next_date => SYSDATE,
        interval => 'SYSDATE + 1/24/6',
        no_parse => FALSE
    );

    DBMS_OUTPUT.PUT_LINE('Job Number is : ' || TO_CHAR(X));
COMMIT;
END;
/

select * from USER_JOBS;
