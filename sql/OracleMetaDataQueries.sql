http://www.alberton.info/oracle_meta_info.html
--Find a referential constraint (foreign key) for a table,schema

SELECT TABLE_NAME, COLUMN_ID "ORDINAL_POSITION", COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE
    FROM ALL_TAB_COLUMNS
WHERE OWNER='MNR' AND TABLE_NAME LIKE  'DISTRESS_%' AND COLUMN_ID < 6
ORDER BY TABLE_NAME,COLUMN_ID

SELECT  DBMS_METADATA.GET_DEPENDENT_DDL('REF_CONSTRAINT','TRANSVERSE_JOINT','MPS') from dual
ALTER TABLE "MPS"."TRANSVERSE_JOINT" ADD CONSTRAINT "FKF9F17D58D52A83EC" FOREIGN KEY ("LAYER_ID")
  REFERENCES "MPS"."LAYER" ("ID") ENABLE
  
SELECT  DBMS_METADATA.GET_DEPENDENT_DDL('REF_CONSTRAINT','PROFILE_BASE','NIMBLE') from dual

SELECT  DBMS_METADATA.GET_DEPENDENT_DDL('REF_CONSTRAINT','PERSON','NIMBLE') from dual
ALTER TABLE "NIMBLE"."PERSON" ADD CONSTRAINT "FK8E4887756D69CCD" FOREIGN KEY ("FEDERATION_PROVIDER_ID")
  REFERENCES "NIMBLE"."FEDERATION_PROVIDER" ("ID") ENABLE
ALTER TABLE "NIMBLE"."PERSON" ADD CONSTRAINT "FK8E4887756F1935BF" FOREIGN KEY ("PROFILE_ID")
  REFERENCES "NIMBLE"."PROFILE_BASE" ("ID") ENABLE

-- Find all MM_values tables where MM is a sensor model  
select * from user_tables where table_name like '___VALUES'  


select model,dynamic_static,count(*) from sensor 
group by model,dynamic_static
order by model

SELECT tablespace_name
FROM dba_data_files
order by tablespace_name;

select v.table_name, m.model, m.model_desc, v.tablespace_name, m.model_number, m.DATABASE_UNITS, m.FILE_UNITS, m.MIN_POSSIBLE_VALUE, m.MAX_POSSIBLE_VALUE, m.MIN_EXPECTED_VALUE, m.MAX_EXPECTED_VALUE, m.MIN_SAMPLES_PER_PERIOD, m.MIN_SAMPLE_PERIOD, m.MAX_SAMPLES_PER_PERIOD, m.MAX_SAMPLE_PERIOD, m.OFFLINE_IND, m.ONLINE_IND, m.DECIMAL_PLACES, m.MIN_OP_TEMP, m.MAX_OP_TEMP, m.CALIB_EQUATION from models m, (SELECT table_name, tablespace_name
FROM user_tables where table_name like '%_VALUES') v where m.data_table = v.table_name
order by v.table_name;
select
cname,coltype,width,scale,precision,nulls
from col
where tname = upper('distress_pcc_faults')


SELECT OBJECT_NAME name
			FROM USER_OBJECTS
			WHERE OBJECT_TYPE='SEQUENCE'

SELECT *
  FROM user_constraints
 WHERE table_name = 'DISTRESS_PCC_FAULTS'

SELECT *
  FROM user_indexes
where UNIQUENESS = 'UNIQUE'
ORDER BY TABLE_NAME

SELECT *
FROM USER_IND_COLUMNS
WHERE upper(TABLE_NAME) like 'MAT_%'
ORDER BY INDEX_NAME, COLUMN_POSITION


SELECT *
FROM USER_IND_COLUMNS
WHERE TABLE_NAME='DISTRESS_PCC_FAULTS'
ORDER BY INDEX_NAME, COLUMN_POSITION

SELECT C.CONSTRAINT_NAME fk_name, RC.TABLE_NAME primary_table, RC.COLUMN_NAME primary_col1, CC.TABLE_NAME foreign_table, CC.COLUMN_NAME foreign_col1
			FROM USER_CONSTRAINTS C, USER_CONS_COLUMNS CC, USER_CONS_COLUMNS RC
			WHERE C.CONSTRAINT_NAME = CC.CONSTRAINT_NAME
			AND C.R_CONSTRAINT_NAME = RC.CONSTRAINT_NAME
			AND C.CONSTRAINT_TYPE = 'R'
			AND CC.POSITION=1
			AND RC.POSITION=1

SELECT OBJECT_NAME name, case when OBJECT_TYPE='VIEW' then 1 else 0 end IsView, nvl(NUM_ROWS,0) rowcnt
			FROM USER_OBJECTS O, USER_TABLES T
			WHERE O.OBJECT_TYPE IN ('TABLE', 'VIEW')
			AND O.OBJECT_NAME = T.TABLE_NAME(+)
			AND O.OBJECT_NAME NOT LIKE 'Bin$%'

SELECT *
  FROM user_indexes
where UNIQUENESS = 'UNIQUE'
ORDER BY TABLE_NAME

-- Find unique index name and columns for a table
select  
        t6.name INDEX_NAME,
           t4.pos# COLUMN_POSITION,
           t5.property c9,
           t3.name COLUMN_NAME
from       sys.user$ t1,
           sys.obj$ t2,
           sys.col$ t3,
           sys.icol$ t4,
           sys.ind$ t5,
           sys.obj$ t6
where           t1.name = upper('mnr')
 and       t1.user# = t2.owner#
 and       t2.name = upper('distress_pcc_faults')
 and       t2.status != 0
 and       t2.obj# = t3.obj#
 and       t3.obj# = t4.bo#
 and       t3.col# = t4.col#
 and       t4.obj# = t5.obj#
 and       t4.obj# = t6.obj#
order by INDEX_NAME,
            COLUMN_POSITION;




select r.description road, offset_ref, l.name lane, count(*) from 
road_section r join cell c on c.road_section_id=r.id join lane l on l.cell_id=c.id
where cell_number not in (64,74)
group by r.description, offset_ref, l.name
order by r.description, offset_ref, l.name

select r.description road, c.cell_number, offset_ref, l.name lane, avg(lyr.thickness), count(*) from 
road_section r join cell c on c.road_section_id=r.id join lane l on l.cell_id=c.id join layer lyr on lyr.lane_id=l.id
where cell_number not in (64,74) and lyr.thickness < 0
group by r.description, c.cell_number, offset_ref, l.name
order by r.description, c.cell_number, offset_ref, l.name

SELECT count(*) FROM sensor where layer_id is null

SELECT cell,count(*) FROM sensor where layer_id is null
group by cell

select s.id sensor_id
,c.cell_number,s.model model_in_sensor_table, sm.model model_in_sensor_model_table, sm.id sensor_model_id, s.seq from sensor s 
join layer l on s.layer_id=l.id 
join lane ln on l.lane_id=ln.id 
join cell c on ln.cell_id=c.id
join sensor_model sm on sm.id=s.sensor_model_id
where s.model <> sm.model

select s.id sensor_id
,c.cell_number,s.model model_in_sensor_table, sm.model model_in_sensor_model_table, sm.id sensor_model_id, s.seq from sensor s 
join layer l on s.layer_id=l.id 
join lane ln on l.lane_id=ln.id 
join cell c on ln.cell_id=c.id
join sensor_model sm on sm.id=s.sensor_model_id
where c.cell_number <> s.cell



select unique * from (
SELECT 
C.ID,
CASE
 WHEN (C.START_STATION) > (SELECT START_STATION FROM CELL WHERE ID=CC.CELL_ID)
 THEN (C.START_STATION)
 ELSE (SELECT START_STATION FROM CELL WHERE ID=CC.CELL_ID)
 END
 FROM_STATION
, CASE
 WHEN (C.END_STATION) < (SELECT END_STATION FROM CELL WHERE ID=CC.CELL_ID)
 THEN (C.END_STATION)
 ELSE (SELECT END_STATION FROM CELL WHERE ID=CC.CELL_ID)
 END
 TO_STATION
, (SELECT CELL_NUMBER FROM CELL WHERE ID=CC.CELL_ID) CELL
, (SELECT TO_CHAR(CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') FROM CELL WHERE ID=CC.CELL_ID) FROM_DATE
, (SELECT TO_CHAR(DEMOLISHED_DATE,'yyyy-mm-dd') FROM CELL WHERE ID=CC.CELL_ID) TO_DATE
, CC.CELL_ID ID_UNDER
, C.CELL_NUMBER CELL_UNDER
, TO_CHAR(CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') CELL_UNDER_FROM_DATE
, TO_CHAR(C.DEMOLISHED_DATE,'yyyy-mm-dd') CELL_UNDER_TO_DATE
FROM CELL C JOIN CELL_CELL CC ON CC.CELL_COVERS_ID = C.ID
union all
SELECT 
C.ID,
CASE
 WHEN (C.START_STATION) > (SELECT START_STATION FROM CELL WHERE ID=CC.CELL_ID)
 THEN (C.START_STATION)
 ELSE (SELECT START_STATION FROM CELL WHERE ID=CC.CELL_ID)
 END
 FROM_STATION
, CASE
 WHEN (C.END_STATION) < (SELECT END_STATION FROM CELL WHERE ID=CC.CELL_ID)
 THEN (C.END_STATION)
 ELSE (SELECT END_STATION FROM CELL WHERE ID=CC.CELL_ID)
 END
 TO_STATION
, (SELECT CELL_NUMBER FROM CELL WHERE ID=CC.CELL_ID) CELL_OVER
, (SELECT TO_CHAR(CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') FROM CELL WHERE ID=CC.CELL_ID) CELL_OVER_FROM_DATE
, (SELECT TO_CHAR(DEMOLISHED_DATE,'yyyy-mm-dd') FROM CELL WHERE ID=CC.CELL_ID) CELL_OVER_TO_DATE
, CC.CELL_ID ID_UNDER
, C.CELL_NUMBER CELL_UNDER
, TO_CHAR(CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') CELL_UNDER_FROM_DATE
, TO_CHAR(C.DEMOLISHED_DATE,'yyyy-mm-dd') CELL_UNDER_TO_DATE
FROM CELL C JOIN CELL_CELL CC ON CC.CELL_COVERED_BY_ID = C.ID
union all
SELECT 
C.ID,
START_STATION FROM_STATION
, END_STATION TO_STATION
, CELL_NUMBER
, TO_CHAR(CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') FROM_DATE
, TO_CHAR(DEMOLISHED_DATE,'yyyy-mm-dd') TO_DATE
, null ID_UNDER
, null CELL_UNDER
, null CELL_UNDER_FROM_DATE
, null CELL_UNDER_TO_DATE
FROM CELL C 
WHERE DEMOLISHED_DATE IS NOT NULL
union all
SELECT 
C.ID,
START_STATION FROM_STATION
, END_STATION TO_STATION
, CELL_NUMBER
, TO_CHAR(CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') FROM_DATE
, TO_CHAR(DEMOLISHED_DATE,'yyyy-mm-dd') TO_DATE
, null ID_UNDER
, null CELL_UNDER
, null CELL_UNDER_FROM_DATE
, null CELL_UNDER_TO_DATE
FROM CELL C 
WHERE DEMOLISHED_DATE IS NULL and id not in (select cell_id from cell_cell)
)
ORDER BY FROM_STATION,FROM_DATE;


