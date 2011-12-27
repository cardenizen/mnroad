ALTER TABLE CELL ADD CELL_COVERS_ID NUMBER(19);
ALTER TABLE CELL ADD CELL_COVERED_BY_ID number(19);
ALTER TABLE CELL
ADD CONSTRAINT FK_COVERS_CELL
FOREIGN KEY (CELL_COVERS_ID)
REFERENCES CELL(ID);
ALTER TABLE CELL
ADD CONSTRAINT FK_COVERED_BY_CELL
FOREIGN KEY (CELL_COVERED_BY_ID)
REFERENCES CELL(ID);
--
DROP TABLE CELL_CELL PURGE;
CREATE TABLE CELL_CELL
(
   CELL_COVERED_BY_ID           NUMBER(19),
   CELL_ID                      NUMBER(19),
   CELL_COVERS_ID               NUMBER(19)
)
TABLESPACE USERS NOLOGGING
   PCTFREE 10
   INITRANS 1
   MAXTRANS 255
  STORAGE (
   INITIAL 65536
   MINEXTENTS 1
   MAXEXTENTS 2147483645
 )
   NOCACHE
;
ALTER TABLE CELL_CELL
ADD CONSTRAINT FK_CELL_COVERED_BY
FOREIGN KEY (CELL_COVERED_BY_ID)
REFERENCES CELL(ID);
ALTER TABLE CELL_CELL
ADD CONSTRAINT FK_CELL_CELL
FOREIGN KEY (CELL_ID)
REFERENCES CELL(ID);
ALTER TABLE CELL_CELL
ADD CONSTRAINT FK_CELL_COVERS
FOREIGN KEY (CELL_COVERS_ID)
REFERENCES CELL(ID);


--truncate table cell_cell;
-- cell 1
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,80,15849);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (80,15849,null);
-- cell 2
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,107,17047);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (107,17047,null);
-- cell 3
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,13430,17075);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (13430,17075,null);
-- 4 covers old 4
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,163,13449);
-- old 4 covered by 4
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (163,13449,null);
-- cell 5
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,184,15865);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (184,15865,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,184,15866);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (184,15866,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,184,15867);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (184,15867,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,184,15868);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (184,15868,null);
-- cells 93(15228), 94(15229), 95(15230)
-- cells 60(15797), 61(15798), 62(15799), 63(15800)
-- 60/15797 (115327.5 - 115560.0) covers     93 (115327.5 - 115635.0) from 115327.5 to 115560.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,15228,15797);
-- 93/15228 (115327.5 - 115635.0) covered by 60 (115327.5 - 115560.0) from 115327.5 to 115560.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (15228,15797,null);
-- 61/15798 (115560.0 - 115785.0) covers     93 (115327.5 - 115635.0) from 115560.0 to 115635.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,15228,15798);
-- 93/15228 (115327.5 - 115635.0) covered by 61 (115560.0 - 115785.0 from 115560.0 to 115635.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (15228,15798,null);
-- 61/15798 (115560.0 - 115785.0) covers     94 (115635.0 - 115935.0) from 115635.0 to 115785.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,15229,15798);
-- 94/15229 (115635.0 - 115935.0) covered by 61 (115560.0 - 115785.0 from 115635.0 to 115785.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (15229,15798,null);
-- 62/15799 (115785.0 - 116010.0) covers     94 (115635.0 - 115935.0) from 115785.0 to 115935.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,15229,15799);
-- 94/15229 (115635.0 - 115935.0) covered by 62 (115785.0 - 116010.0)from 115635.0 to 115785.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (15229,15799,null);
-- 62/15799 (115785.0 - 116010.0) covers     95 (115935.0 - 116234.0) from 115935.0 to 116010.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,15230,15799);
-- 95/15230 (115935.0 - 116234.0) covered by 62 (115785.0 - 116010.0) from 115935.0 to 116010.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (15230,15799,null);
-- 63/15800 (116010.0 - 116234.0) covers     95 (115935.0 - 116234.0) from 116010.0 to 116234.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,15230,15800);
-- 95/15230 (115935.0 - 116234.0) covered by 63 (116010.0 - 116234.0) from 116010.0 to 116234.0
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (15230,15800,null);
-- cell 14
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15668);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15668,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15669);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15669,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15670);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15670,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15671);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15671,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15672);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15672,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15673);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15673,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15674);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15674,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15675);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15675,null);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,373,15676);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (373,15676,null);
-- cell 15
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,396,15856);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (396,15856,null);
--LVR North
-- cell 33
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,927,16368);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (927,16368,null);
-- cell 34
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,954,16379);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (954,16379,null);
-- cell 35
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,981,16390);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (981,16390,null);
-- cell 39
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,1043,16273);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (1043,16273,null);
--LVR South
-- cell 24
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,634,16120);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (634,16120,null);
-- cell 24
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,634,16120);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (634,16120,null);
-- cell 31
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,863,16253);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (863,16253,null);
-- cell 32
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (null,897,16130);
insert into cell_cell (cell_covered_by_id, cell_id, cell_covers_id) values (897,16130,null);



select (select cell_number from cell where id=under_cell_id) cell_over
, (select cell_number from cell where id=over_cell_id) cell_under
, us.FROM_STATION
, us.TO_STATION
from
(SELECT unique cc2.cell_id under_cell_id, cc1.cell_id over_cell_id
, case
 when (select start_station from cell where id=cc2.cell_id) > (select start_station from cell where id=cc1.cell_id)
 then (select start_station from cell where id=cc2.cell_id)
 else (select start_station from cell where id=cc1.cell_id)
 end
 FROM_STATION
, case
 when (select end_station from cell where id=cc1.cell_id)<(select end_station from cell where id=cc2.cell_id)
 then (select end_station from cell where id=cc1.cell_id)
 when (select end_station from cell where id=cc2.cell_id)<(select start_station from cell where id=cc1.cell_id)
 then (select start_station from cell where id=cc1.cell_id)
 else (select end_station from cell where id=cc2.cell_id)
 end
 TO_STATION
FROM cell_cell cc1 join cell_cell cc2 on cc1.cell_id=cc2.cell_covered_by_id) us
order by FROM_STATION
CELL_OVER	CELL_UNDER	FROM_STATION	TO_STATION
33	33	6,350	6,910
34	34	6,910	7,470
35	35	7,470	8,062
39	39	9,690	10,232.5
24	24	15,800	16,367.5
31	31	19,775	20,342.5
32	32	20,342.5	20,900
1	1	110,284.54	110,839.54
2	2	110,839.54	111,399.54
3	3	111,399.54	111,984.54
4	4	111,984.54	112,585
105	5	112,585	112,758
205	5	112,758	112,893
305	5	112,893	113,043
405	5	113,043	113,172.5
60	93	115,327.5	115,560
61	93	115,560	115,635
61	94	115,635	115,785
62	94	115,785	115,935
62	95	115,935	116,010
63	95	116,010	116,234
114	14	118,890	118,989
214	14	118,989	119,013
314	14	119,013	119,151
414	14	119,151	119,181
514	14	119,181	119,217
614	14	119,217	119,325
714	14	119,325	119,343
814	14	119,343	119,367
914	14	119,367	119,445
15	15	119,445	120,017.5

select (select cell_number from cell where id=over_cell_id) cell_under
, us.FROM_STATION
, us.TO_STATION
from
(SELECT unique cc2.cell_id under_cell_id, cc1.cell_id over_cell_id
, case
 when (select start_station from cell where id=cc2.cell_id) > (select start_station from cell where id=cc1.cell_id)
 then (select start_station from cell where id=cc2.cell_id)
 else (select start_station from cell where id=cc1.cell_id)
 end
 FROM_STATION
, case
 when (select end_station from cell where id=cc1.cell_id)<(select end_station from cell where id=cc2.cell_id)
 then (select end_station from cell where id=cc1.cell_id)
 when (select end_station from cell where id=cc2.cell_id)<(select start_station from cell where id=cc1.cell_id)
 then (select start_station from cell where id=cc1.cell_id)
 else (select end_station from cell where id=cc2.cell_id)
 end
 TO_STATION
FROM cell_cell cc1 join cell_cell cc2 on cc1.cell_id=cc2.cell_covered_by_id) us
where (select cell_number from cell where id=under_cell_id)=61
order by FROM_STATION

select (select cell_number from cell where id=under_cell_id) cell_under
, (select cell_number from cell where id=over_cell_id) cell_over
, us.FROM_STATION
, us.TO_STATION
from
(SELECT unique cc1.cell_id under_cell_id, cc2.cell_id over_cell_id
, case
 when (select start_station from cell where id=cc2.cell_id) > (select start_station from cell where id=cc1.cell_id)
 then (select start_station from cell where id=cc2.cell_id)
 else (select start_station from cell where id=cc1.cell_id)
 end
 FROM_STATION
, case
 when (select end_station from cell where id=cc1.cell_id)<(select end_station from cell where id=cc2.cell_id)
 then (select end_station from cell where id=cc1.cell_id)
 when (select end_station from cell where id=cc2.cell_id)<(select start_station from cell where id=cc1.cell_id)
 then (select start_station from cell where id=cc1.cell_id)
 else (select end_station from cell where id=cc2.cell_id)
 end
 TO_STATION
FROM cell_cell cc1 join cell_cell cc2 on cc2.cell_id=cc1.cell_covers_id) us
order by FROM_STATION
CELL_UNDER	CELL_OVER	FROM_STATION	TO_STATION
33	33	6,350	6,910
34	34	6,910	7,470
35	35	7,470	8,062
39	39	9,690	10,232.5
24	24	15,800	16,367.5
31	31	19,775	20,342.5
32	32	20,342.5	20,900
1	1	110,284.54	110,839.54
2	2	110,839.54	111,399.54
3	3	111,399.54	111,984.54
4	4	111,984.54	112,585
5	105	112,585	112,758
5	205	112,758	112,893
5	305	112,893	113,043
5	405	113,043	113,172.5
93	60	115,327.5	115,560
93	61	115,560	115,635
94	61	115,635	115,785
94	62	115,785	115,935
95	62	115,935	116,010
95	63	116,010	116,234
14	114	118,890	118,989
14	214	118,989	119,013
14	314	119,013	119,151
14	414	119,151	119,181
14	514	119,181	119,217
14	614	119,217	119,325
14	714	119,325	119,343
14	814	119,343	119,367
14	914	119,367	119,445
15	15	119,445	120,017.5
