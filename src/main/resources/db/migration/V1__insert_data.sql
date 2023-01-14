insert into main_category (id, name)
values (1, '수입');
insert into main_category (id, name, weekly_limit)
values (2, '식비', 130000);
insert into main_category (id, name)
values (3, '생활비');
insert into main_category (id, name)
values (4, '교통비');
insert into main_category (id, name)
values (5, '의료비');
insert into main_category (id, name)
values (6, '문화비');
insert into main_category (id, name)
values (7, '교육비');
insert into main_category (id, name)
values (8, '경조사비');
insert into main_category (id, name)
values (9, '보험비');
insert into main_category (id, name)
values (10, '용돈');
insert into main_category (id, name)
values (11, '저축');
insert into main_category (id, name)
values (12, '기타');

# type: 1-수입, 2-지출, 3-저축
insert into category (id, name, main_category_id, type)
values (1, '장보기', 2, 2);
insert into category (id, name, main_category_id, type)
values (2, '외식', 2, 2);
insert into category (id, name, main_category_id, type)
values (3, '배달', 2, 2);
insert into category (id, name, main_category_id, type)
values (4, '식비-기타', 2, 2);
insert into category (id, name, main_category_id, type)
values (5, '관리비', 3, 2);
insert into category (id, name, main_category_id, type)
values (6, '대출', 3, 2);
insert into category (id, name, main_category_id, type)
values (7, '통신비', 3, 2);
insert into category (id, name, main_category_id, type)
values (8, '생활비-기타', 3, 2);
insert into category (id, name, main_category_id, type)
values (9, '대중교통', 4, 2);
insert into category (id, name, main_category_id, type)
values (10, '주유비', 4, 2);
insert into category (id, name, main_category_id, type)
values (11, '톨게이트비', 4, 2);
insert into category (id, name, main_category_id, type)
values (12, '주차비', 4, 2);
insert into category (id, name, main_category_id, type)
values (13, '자동차 정비', 4, 2);
insert into category (id, name, main_category_id, type)
values (14, '교통비-기타', 4, 2);
insert into category (id, name, main_category_id, type)
values (15, '병원', 5, 2);
insert into category (id, name, main_category_id, type)
values (16, '의료비-기타', 5, 2);
insert into category (id, name, main_category_id, type)
values (17, '영화', 6, 2);
insert into category (id, name, main_category_id, type)
values (18, '쇼핑', 6, 2);
insert into category (id, name, main_category_id, type)
values (19, '구독', 6, 2);
insert into category (id, name, main_category_id, type)
values (20, '책', 6, 2);
insert into category (id, name, main_category_id, type)
values (21, '문화비-기타', 6, 2);
insert into category (id, name, main_category_id, type)
values (22, '교육비', 7, 2);
insert into category (id, name, main_category_id, type)
values (23, '학원', 7, 2);
insert into category (id, name, main_category_id, type)
values (24, '책 구입비', 7, 2);
insert into category (id, name, main_category_id, type)
values (25, '교육비-기타', 7, 2);
insert into category (id, name, main_category_id, type)
values (26, '경조사비', 8, 2);
insert into category (id, name, main_category_id, type)
values (27, '보험료', 9, 2);
insert into category (id, name, main_category_id, type)
values (28, '보험료-기타', 9, 2);
insert into category (id, name, main_category_id, type)
values (29, '용돈', 10, 2);
insert into category (id, name, main_category_id, type)
values (30, '용돈-기타', 10, 2);
insert into category (id, name, main_category_id, type)
values (31, '적금', 11, 3);
insert into category (id, name, main_category_id, type)
values (32, '저축', 11, 3);
insert into category (id, name, main_category_id, type)
values (33, '연금', 11, 3);
insert into category (id, name, main_category_id, type)
values (34, '투자', 11, 3);
insert into category (id, name, main_category_id, type)
values (35, '저축-기타', 11, 3);
insert into category (id, name, main_category_id, type)
values (36, '기타', 12, 2);
insert into category (id, name, main_category_id, type)
values (37, '월급', 1, 1);
insert into category (id, name, main_category_id, type)
values (38, '보너스', 1, 1);
insert into category (id, name, main_category_id, type)
values (39, '이월', 1, 1);
insert into category (id, name, main_category_id, type)
values (40, '수입-기타', 1, 1);

# type: 1-계좌(현금), 2-카드, 3-기타
insert into account_card (id, amount, name, type)
values (1, 0, '생활비', 1);
insert into account_card (id, amount, name, type)
values (2, 0, '비상금', 1);
insert into account_card (id, amount, name, type)
values (3, 0, '생필품', 1);
insert into account_card (id, amount, name, type)
values (4, 0, '남은돈', 1);
insert into account_card (id, amount, name, type)
values (5, 0, '현금', 1);
insert into account_card (id, amount, name, type)
values (6, 0, '현금-기타', 1);
insert into account_card (id, amount, name, type)
values (7, 0, '롯데쿠팡카드', 2);
insert into account_card (id, amount, name, type)
values (8, 0, '신한더모아', 2);
insert into account_card (id, amount, name, type)
values (9, 0, '현대스마일', 2);
insert into account_card (id, amount, name, type)
values (10, 0, '전주돼지', 2);
insert into account_card (id, amount, name, type)
values (11, 0, '우리카드', 2);
insert into account_card (id, amount, name, type)
values (12, 0, '카드-기타', 2);
insert into account_card (id, amount, name, type)
values (13, 0, '기타', 3);


