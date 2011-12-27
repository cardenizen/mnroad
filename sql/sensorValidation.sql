SELECT s.id sensor_id,ln.id lane_id,l.id layer_id,c.id cell_id,c.demolished_date,s.date_removed,nvl(to_char(cell),'All Cells') CELL,nvl(model,'All Models') MODEL,s.seq
--,count(*),min(SEQ),max(SEQ)
FROM sensor S
join mnr.layer l on s.layer_id=l.id   
join mnr.lane ln on l.lane_id=ln.id   
join mnr.cell c on ln.cell_id=c.id   
where c.demolished_date is not null and s.date_removed is null
--and cell=53 and model='TD'
--group by cube (c.demolished_date,cell,model)
order by s.cell,s.model


SELECT * FROM mnr.tc_values_2006 where cell=1 and seq < 3 and day between '30-JUN-2006' and '31-JUL-2006'
order by cell,seq,day,hour,minute


select * from (
select cell,to_char(day,'yyyy') year,seq,count(*) num_readings, to_char(min(day),'yyyy-mm-dd') from_day, to_char(max(day),'yyyy-mm-dd') to_day
from mnr.tc_values_all
group by cell,to_char(day,'yyyy'),seq
)
where
from_day not like '201%'
and (from_day not like '%01-01'
or to_day not like '%12-31')
order by cell, year

select count(*) from mnr.tc_values_2004 where cell=26 and day between '1-MAY-2004' and '15-MAY-2004'

SELECT ICP.CELL, TO_CHAR(ICP.DEAD_FROM_DATE, 'dd-MON-yyyy') DEAD_FROM, TO_CHAR(ICP.DEAD_TO_DATE, 'dd-MON-yyyy') DEAD_TO, TO_CHAR(TC.DAY, 'dd-MON-yyyy') READING_DATE
,TC.CELL
,TC.SEQ
,TC.HOUR
,TC.QHR
,TC.MINUTE
,TC.VALUE
FROM
(
SELECT
CELL, FROMD + 1 DEAD_FROM_DATE, TOD - 1 DEAD_TO_DATE
FROM
(
   SELECT
   CELL,
   TO_DATE FROMD,
   LEAD
   (
      FROM_DATE, 1, NULL
   )
   OVER (PARTITION BY CELL ORDER BY CELL, FROM_DATE) TOD
   FROM MNR.CELLS
)
WHERE FROMD IS NOT NULL
AND TOD IS NOT NULL
AND FROMD+1 < TOD
) ICP
JOIN
MNR.TC_VALUES_ALL TC
ON TC.CELL=ICP.CELL AND TC.DAY BETWEEN ICP.DEAD_FROM_DATE AND ICP.DEAD_TO_DATE
ORDER BY TC.CELL,TC.SEQ,TC.DAY

-- Unique cell numbers from sensor_id
select cell, count(*) from 
(
select case
when length(sensor_id)=6 then lpad(substr(sensor_id,1,1),3,'0')
when length(sensor_id)=7 or (length(sensor_id)=8 and (sensor_id like '%SCG%' or sensor_id like '%TDR%')) then lpad(substr(sensor_id,1,2),3,'0')
when length(sensor_id)=8 and not (sensor_id like '%SCG%' or sensor_id like '%TDR%') then substr(sensor_id,1,3)
end cell
from mnr.sensor
) 
group by cell
order by cell
;

-- Unique models from sensor_id
select nvl(model,'_All_') model, count(*) from 
(
select case
when length(sensor_id)=6 then rpad(substr(sensor_id,2,2),3,' ')
when length(sensor_id)=7 then rpad(substr(sensor_id,3,2),3,' ')
when length(sensor_id)=8 then rpad(substr(sensor_id,4,2),3,' ')
else '??'
end model
from mnr.sensor
) 
group by cube(model)
order by model
;

-- Generate sensor_id where it is null
select 'update mnr.sensor set sensor_id = '''||to_char(c.cell_number)||sm.model||lpad(to_char(s.seq),3,'0')||''' where id = '||s.ID||';'
FROM MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
where s.sensor_id is null
order by c.cell_number, sm.model, s.seq

-- Fix SCG and TDR models
SELECT 
'update mnr.sensor set sensor_id = '''||c.cell_number||sm.model||lpad(to_char(s.seq),3,'0')||''' where id = '||s.ID||';' 
FROM
MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
where sensor_id like '%SCG%' or sensor_id like '%TDR%'

-- Reading Counts by cell,model,seq
select cell, substr(table_name,1,2) model, lpad(to_char(seq),3,'0') seq, min(from_day) from_day, max(to_day) TO_Day, sum(num_readings) nvalues from sensor_counts
group by table_name,cell,seq
order by table_name,cell,seq

--Find sensor collecting data before installation or after removal
SELECT * FROM 
(
select to_char(cell) cell, substr(table_name,1,2) model, lpad(to_char(seq),3,'0') seq, min(from_day) from_day, max(to_day) TO_Day, sum(num_readings) nvalues from sensor_counts
group by table_name,cell,seq
) sc
JOIN MNR.SENSOR S ON s.sensor_id=sc.cell||sc.model||sc.seq
where to_day > date_removed or from_day < date_installed
order by sc.cell,sc.model,s.seq


SELECT unique lpad(to_char(c.cell_number),3,'0')
cell
FROM MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
where s.sensor_id is null
order by cell

********************************************************************

The data has reached a point where we can start checking for sensors reporting no data and data being collected from unknown sensors.  

First a couple of data fixes:

-- Generate sensor_id from CELL, SENSOR_MODEL, and SENSOR where it is null
SELECT 'UPDATE MNR.SENSOR SET SENSOR_ID = '''||TO_CHAR(C.CELL_NUMBER)||SM.MODEL||LPAD(TO_CHAR(S.SEQ),3,'0')||''' WHERE ID = '||S.ID||';'
FROM MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
WHERE S.SENSOR_ID IS NULL
ORDER BY C.CELL_NUMBER, SM.MODEL, S.SEQ

-- Fix SCG and TDR models in the sensor_id column.  Change SCG to SC and TDR to TD 
SELECT 
'UPDATE MNR.SENSOR SET SENSOR_ID = '''||C.CELL_NUMBER||SM.MODEL||LPAD(TO_CHAR(S.SEQ),3,'0')||''' WHERE ID = '||S.ID||';' 
FROM
MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
WHERE SENSOR_ID LIKE '%SCG%' OR SENSOR_ID LIKE '%TDR%'


A query that counts the number of sensor readings runs nightly.  It stores the counts in a table called SENSOR_COUNTS.  Here is a query that summarizes the table:

SELECT TO_CHAR(CELL) CELL, SUBSTR(TABLE_NAME,1,2) MODEL, LPAD(TO_CHAR(SEQ),3,'0') SEQ, MIN(FROM_DAY) FROM_DAY, MAX(TO_DAY) TO_DAY, SUM(NUM_READINGS) NVALUES 
FROM MNR.SENSOR_COUNTS
GROUP BY TABLE_NAME,CELL,SEQ;

It reports 8866 unique cell,model,seq sensors reporting values.  Joining this result to the SENSOR table on concatenate(cell,model,seq) = sensor_id yields 5,914 of the 9130 sensors in the SENSOR table are reporting data.

--Here is a nice crosstab query that summarizes sensors by cell and model using sensor_id
SELECT NVL(CELL,'_ALL CELLS_') CELL,NVL(MODEL,'_ALL MODELS_') MODEL, COUNT(*) FROM 
(
SELECT 
CASE
WHEN LENGTH(SENSOR_ID)=6 THEN LPAD(SUBSTR(SENSOR_ID,1,1),3,'0')
WHEN LENGTH(SENSOR_ID)=7 THEN LPAD(SUBSTR(SENSOR_ID,1,2),3,'0')
WHEN LENGTH(SENSOR_ID)=8 THEN SUBSTR(SENSOR_ID,1,3)
END CELL
,CASE
WHEN LENGTH(SENSOR_ID)=6 THEN SUBSTR(SENSOR_ID,2,2)
WHEN LENGTH(SENSOR_ID)=7 THEN SUBSTR(SENSOR_ID,3,2)
WHEN LENGTH(SENSOR_ID)=8 THEN SUBSTR(SENSOR_ID,4,2)
ELSE '??'
END MODEL
FROM MNR.SENSOR
) 
GROUP BY CUBE(CELL,MODEL)
ORDER BY CELL,MODEL
;

--Here are the 5,914 reporting data:
SELECT * FROM 
(
SELECT TO_CHAR(CELL) CELL
, SUBSTR(TABLE_NAME,1,2) MODEL
, LPAD(TO_CHAR(SEQ),3,'0') SEQ
, MIN(FROM_DAY) FROM_DAY
, MAX(TO_DAY) TO_DAY
, SUM(NUM_READINGS) NVALUES 
FROM MNR.SENSOR_COUNTS
GROUP BY TABLE_NAME,CELL,SEQ
) SC
JOIN MNR.SENSOR S ON S.SENSOR_ID = SC.CELL||SC.MODEL||SC.SEQ
--where to_day > date_removed or from_day < date_installed
ORDER BY SC.CELL,SC.MODEL,S.SEQ

--Here are the 3,125 sensors not reporting data (according to SENSOR_COUNTS)
SELECT C.CELL_NUMBER CELL, SM.MODEL, LPAD(TO_CHAR(S.SEQ),3,'0') SEQ 
FROM MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
MINUS
SELECT CELL, SUBSTR(TABLE_NAME,1,2) MODEL, LPAD(TO_CHAR(SEQ),3,'0') SEQ 
FROM SENSOR_COUNTS
GROUP BY TABLE_NAME,CELL,SEQ
ORDER BY CELL,MODEL,SEQ

--Here are the 209 sensors where the cell number in the sensor_id column does not match the cell number derived from the layer/lane/cel relation.
SELECT 
  TO_CHAR(C.CELL_NUMBER) CELL
, SM.MODEL
, LPAD(TO_CHAR(S.SEQ),3,'0') SEQ
, S.SENSOR_ID
FROM MNR.CELL C
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
WHERE TO_CHAR(C.CELL_NUMBER)||SM.MODEL||LPAD(TO_CHAR(SEQ),3,'0') <> S.SENSOR_ID
ORDER BY CELL,MODEL,SEQ

--Of the 5,914 sensors reporting data here are the 1,350 reporting data before the sensor was installed or after the sensor was removed.
SELECT * FROM 
(
SELECT TO_CHAR(CELL) CELL, SUBSTR(TABLE_NAME,1,2) MODEL, LPAD(TO_CHAR(SEQ),3,'0') SEQ, MIN(FROM_DAY) FROM_DAY, MAX(TO_DAY) TO_DAY, SUM(NUM_READINGS) NVALUES 
FROM MNR.SENSOR_COUNTS
GROUP BY TABLE_NAME,CELL,SEQ
) SC
JOIN MNR.SENSOR S ON S.SENSOR_ID = SC.CELL||SC.MODEL||SC.SEQ
WHERE TO_DAY > DATE_REMOVED OR FROM_DAY < DATE_INSTALLED
ORDER BY SC.CELL,SC.MODEL,S.SEQ


SELECT SC.CELL
,SC.MODEL
,SC.SEQ
,SC.FROM_DAY
,S.DATE_INSTALLED
,S.DATE_REMOVED
,SC.TO_DAY
,SC.NVALUES
,S.DESCRIPTION
,S.LAYER_ID
,S.LAST_UPDATED_BY
 FROM 
(
SELECT TO_CHAR(CELL) CELL
, SUBSTR(TABLE_NAME,1,2) MODEL
, LPAD(TO_CHAR(SEQ),3,'0') SEQ
, MIN(FROM_DAY) FROM_DAY
, MAX(TO_DAY) TO_DAY
, SUM(NUM_READINGS) NVALUES 
FROM MNR.SENSOR_COUNTS
GROUP BY TABLE_NAME,CELL,SEQ
) SC
JOIN MNR.SENSOR S ON S.SENSOR_ID = SC.CELL||SC.MODEL||SC.SEQ
WHERE TO_DAY > DATE_REMOVED OR FROM_DAY+33 < DATE_INSTALLED
ORDER BY SC.CELL,SC.MODEL,S.SEQ

SELECT 
CCC.CELL_OVER CELL
,SUBSTR(CCC.CLASS,24) TYPE
,CCC.CELL_UNDER
,SUBSTR(CCC.CLASS_UNDER,24) 
,CASE
WHEN CCC.FROM_STATION_UNDER > CCC.SS THEN CCC.FROM_STATION_UNDER
ELSE CCC.SS
END FROM_STATION_UNDER
,S.STATION_FT STATION
,CCC.TO_STATION_UNDER
,CCC.FROM_DATE_UNDER
,CCC.TO_DATE_UNDER
,LN.NAME LANE
,M.BASIC_MATERIAL 
,CCC.FROM_DATE
,SM.MODEL
,S.ID
,S.SEQ
,S.OFFSET_FT OFFSET
,S.SENSOR_DEPTH_IN DEPTH
,S.DATE_INSTALLED
,S.DATE_REMOVED
FROM 
(
SELECT 
CC.CELL_ID ID_UNDER
,(SELECT CELL_NUMBER FROM MNR.CELL WHERE ID = CC.CELL_ID) CELL_UNDER
,(SELECT CLASS FROM MNR.CELL WHERE ID = CC.CELL_ID) CLASS_UNDER
, C.START_STATION SS
,(SELECT START_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_STATION_UNDER
,(SELECT END_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_STATION_UNDER
,(SELECT CONSTRUCTION_ENDED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_DATE_UNDER
,(SELECT DEMOLISHED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_DATE_UNDER
,C.CONSTRUCTION_ENDED_DATE FROM_DATE
,C.ID ID_OVER
,C.CELL_NUMBER CELL_OVER
,C.CLASS
FROM MNR.CELL C JOIN MNR.CELL_CELL CC ON CC.CELL_COVERS_ID=C.ID
WHERE C.CELL_NUMBER=914
) CCC 
JOIN MNR.LANE LN ON LN.CELL_ID=CCC.ID_UNDER
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
WHERE SM.MODEL='TC' 
  AND S.DATE_INSTALLED > FROM_DATE-63
  AND S.STATION_FT BETWEEN CCC.FROM_STATION_UNDER AND CCC.TO_STATION_UNDER
ORDER BY STATION,OFFSET,DEPTH;


SELECT * FROM (
SELECT
C.CELL_NUMBER CELL,SM.MODEL,S.SEQ,S.STATION_FT STATION,S.OFFSET_FT OFFSET,S.DATE_INSTALLED,S.DATE_REMOVED
FROM MNR.SENSOR S JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID=SM.ID JOIN MNR.LAYER L ON S.LAYER_ID=L.ID JOIN MNR.LANE LN ON L.LANE_ID=LN.ID JOIN MNR.CELL C ON LN.CELL_ID=C.ID JOIN MNR.MATERIAL M ON M.ID = L.MATERIAL_ID
UNION ALL
SELECT CELL, MODEL, SEQ, STATION, OFFSET, DATE_INSTALLED, DATE_REMOVED FROM (
    SELECT
    CCC.CELL_OVER CELL,SUBSTR(CCC.CLASS,24) TYPE,CCC.CELL_UNDER,SUBSTR(CCC.CLASS_UNDER,24)
    ,CCC.FROM_STATION_UNDER,CCC.TO_STATION_UNDER,CCC.FROM_DATE_UNDER,CCC.TO_DATE_UNDER
    ,LN.NAME LANE,M.BASIC_MATERIAL,CCC.FROM_DATE
    ,SM.MODEL,S.ID,S.SEQ,S.STATION_FT STATION,S.OFFSET_FT OFFSET,S.SENSOR_DEPTH_IN DEPTH,S.DATE_INSTALLED,S.DATE_REMOVED
    FROM
    (
    SELECT
    CC.CELL_ID ID_UNDER
    ,(SELECT CELL_NUMBER FROM MNR.CELL WHERE ID = CC.CELL_ID) CELL_UNDER
    ,(SELECT CLASS FROM MNR.CELL WHERE ID = CC.CELL_ID) CLASS_UNDER
    ,(SELECT START_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_STATION_UNDER
    ,(SELECT END_STATION FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_STATION_UNDER
    ,(SELECT CONSTRUCTION_ENDED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) FROM_DATE_UNDER
    ,(SELECT DEMOLISHED_DATE FROM MNR.CELL WHERE ID = CC.CELL_ID) TO_DATE_UNDER
    ,C.CONSTRUCTION_ENDED_DATE FROM_DATE,C.ID ID_OVER,C.CELL_NUMBER CELL_OVER,C.CLASS
    FROM MNR.CELL C JOIN MNR.CELL_CELL CC ON CC.CELL_COVERS_ID=C.ID
    ) CCC
    JOIN MNR.LANE LN ON LN.CELL_ID=CCC.ID_UNDER
    JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
    JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
    JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
    JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
    WHERE DATE_INSTALLED > FROM_DATE-63
)
)
WHERE CELL=14
ORDER BY CELL, MODEL,SEQ
