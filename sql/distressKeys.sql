Candidate natural keys can be identified using a query of this form:

SELECT some,list,of,columns
FROM MNR.table_name
GROUP BY the,same,list,of,columns
HAVING COUNT(*)>1
;

SELECT 
, count(*)
FROM MNR.
GROUP BY 

HAVING COUNT(*)>1
ORDER BY 

;

A query returning no rows indicates that the columns in the list form a potential, or candidate, key.
It is up to the domain expert (aka you) to say whether the data for that set of columns or some otherset, uniquely identifies the row.

Only a few unique indexes ( the duplicate row enforcement mechanism provided by the DB) exist:
DISTRESS_JPCC_INDEX, DISTRESS_OBSI_DATA_UIDX, DISTRESS_OBSI_SUMMARY_UIDX


DISTRESS_AC
-----------
SELECT cell, lane, day, count(*)
FROM MNR.distress_ac
GROUP BY CELL,LANE, DAY
HAVING COUNT(*)>1
ORDER BY CELL, LANE, DAY
;
-- Is CELL, LANE, DAY the minimum set of columns that will distinguish one row from another?
-- 4 rows returned.
-- 2 appear to be duplicates
select *
from mnr.DISTRESS_AC
where 
(cell=19 and lane = 'Passing')
and 
(to_char(day,'dd-MON-yyyy')='17-AUG-2003' or to_char(day,'dd-MON-yyyy')='18-AUG-2003')

DISTRESS_AGG_SURVEY_SEMI
------------------------
SELECT CELL, LANE, DAY, COUNT(*)
FROM MNR.DISTRESS_AGG_SURVEY_SEMI
GROUP BY CELL,LANE, DAY
HAVING COUNT(*)>1
ORDER BY CELL, LANE, DAY
; 
-- Is CELL, LANE, DAY the minimum set of columns that will distinguish one row from another?
-- 4 rows returned.
-- 2 appear to be duplicates
select *
from mnr.DISTRESS_AGG_SURVEY_SEMI
where 
(cell=32 and lane = 'Outside' and to_char(day,'yyyy-mm-dd')='1999-06-30')
or
(cell=35 and lane = 'Inside' and to_char(day,'yyyy-mm-dd')='1999-04-12')
order by cell, lane, day
;

DISTRESS_ALPS_DATA
------------------
select 
CELL,LANE,DAY,HOUR_MIN_SEC,Y_STA_FT,X_OFFSET_FT,MEASUREMENT_TYPE,RECORD_NO
,count(*)
from
mnr.DISTRESS_ALPS_DATA
group by 
CELL,LANE,DAY,HOUR_MIN_SEC,Y_STA_FT,X_OFFSET_FT,MEASUREMENT_TYPE,RECORD_NO
having count(*)>1
order by 
CELL,LANE,DAY,HOUR_MIN_SEC,Y_STA_FT,X_OFFSET_FT,MEASUREMENT_TYPE,RECORD_NO
;
-- Is CELL,LANE,DAY,HOUR_MIN_SEC,Y_STA_FT,X_OFFSET_FT,MEASUREMENT_TYPE,RECORD_NO the minimum set of columns that will distinguish one row from another?
-- 88 rows returned.  
-- 44 appear to be duplicates.
SELECT * FROM mnr.DISTRESS_ALPS_DATA
where CELL=22
and LANE='Driving'
and to_char(day,'yyyy-mm-dd')='2004-04-19'
and HOUR_MIN_SEC='13:17:43'
and Y_STA_FT=123561.5
and X_OFFSET_FT=-9.5
and MEASUREMENT_TYPE='Cracking'
and RECORD_NO=236
;

DISTRESS_ALPS_RESULTS_RUT
-------------------------
select 
CELL,LANE,WHEELPATH,STATION,DAY,HOUR_MIN_SEC
,count(*)
from
mnr.DISTRESS_ALPS_RESULTS_RUT
group by 
CELL,LANE,WHEELPATH,STATION,DAY,HOUR_MIN_SEC
having count(*)>1
order by 
CELL,LANE,WHEELPATH,STATION,DAY,HOUR_MIN_SEC
;
-- 0 rows returned
-- Is CELL,LANE,WHEELPATH,STATION,DAY,HOUR_MIN_SEC the minimum set of columns that will distinguish one row from another?


DISTRESS_CIRCULR_TEXTR_METER
----------------------------
select 
CELL,LANE,WHEEL_PATH,STATION,OFFSET_FT,DAY,TIME,FIELD_ID,TRIAL
,count(*)
from
mnr.DISTRESS_CIRCULR_TEXTR_METER
group by 
CELL,LANE,WHEEL_PATH,STATION,OFFSET_FT,DAY,TIME,FIELD_ID,TRIAL
having count(*)>1
order by 
CELL,LANE,WHEEL_PATH,STATION,OFFSET_FT,DAY,TIME,FIELD_ID,TRIAL
;
-- 0 rows returned
-- Is CELL,LANE,WHEEL_PATH,STATION,OFFSET_FT,DAY,TIME,FIELD_ID,TRIAL the minimum set of columns that will distinguish one row from another?

DISTRESS_CUPPING
----------------
SELECT CELL,LANE,WHEEL_PATH,STATION,DAY,CRACK_NO,CUPPING_X_DISTANCE_INCH, count(*)
FROM MNR.DISTRESS_CUPPING
GROUP BY CELL,LANE,WHEEL_PATH,STATION,DAY,CRACK_NO,CUPPING_X_DISTANCE_INCH
HAVING COUNT(*)>1
ORDER BY CELL,LANE,WHEEL_PATH,STATION,DAY,CRACK_NO,CUPPING_X_DISTANCE_INCH
;
-- 1 row returned
select *
from mnr.DISTRESS_CUPPING
where 
CELL=17 AND LANE='Driving' AND	WHEEL_PATH='Mid-Lane' AND STATION=120851 AND to_char(day,'dd-MON-yyyy')='06-AUG-0002' AND CRACK_NO='17.2.2' AND CUPPING_X_DISTANCE_INCH=34

DISTRESS_FRICTION_DFT
---------------------
SELECT CELL,LANE,DAY,STATION,OFFSET_FT,WHEELPATH,RUN_NO,SPEED_KPH,count(*)
FROM MNR.DISTRESS_FRICTION_DFT
GROUP BY CELL,LANE,DAY,STATION,OFFSET_FT,WHEELPATH,RUN_NO,SPEED_KPH
HAVING COUNT(*)>1
ORDER BY CELL,LANE,DAY,STATION,OFFSET_FT,WHEELPATH,RUN_NO,SPEED_KPH
;
-- 0 rows returned

DISTRESS_FRICTION_GRIP
----------------------
CELL,LANE,WHEEL_PATH,DAY,TIME,OPERATOR,DISTANCE_FT,CHAINAGE,STATION,OFFSET,RUN,SPEED,LOADSELECT CELL,LANE,WHEEL_PATH,DAY,TIME,DISTANCE_FT,CHAINAGE,STATION,OFFSET,RUN
, count(*)
FROM MNR.DISTRESS_FRICTION_GRIP
GROUP BY 
CELL,LANE,WHEEL_PATH,DAY,TIME,DISTANCE_FT,CHAINAGE,STATION,OFFSET,RUN
HAVING COUNT(*)>1
ORDER BY 
CELL,LANE,WHEEL_PATH,DAY,TIME,DISTANCE_FT,CHAINAGE,STATION,OFFSET,RUN
;
-- 0 rows returned

DISTRESS_FRICTION_TRAILER
-------------------------
SELECT CELL,LANE,DAY,TIME,LATITUDE,LONGITUDE
, count(*)
FROM MNR.DISTRESS_FRICTION_TRAILER
GROUP BY 
CELL,LANE,DAY,TIME,LATITUDE,LONGITUDE
HAVING COUNT(*)>1
ORDER BY 
CELL,LANE,DAY,TIME,LATITUDE,LONGITUDE
;
-- 0 rows returned


DISTRESS_JPCC
-------------
DISTRESS_JPCC_INDEX : DAY,CELL,LANE
SELECT DAY,CELL,LANE
, count(*)
FROM MNR.DISTRESS_JPCC
GROUP BY 
DAY,CELL,LANE
HAVING COUNT(*)>1
ORDER BY 
DAY,CELL,LANE
;
-- 0 rows returned


DISTRESS_LANE_SHOULDER_DROPOFF
------------------------------
SELECT CELL,LANE,DAY,TIME,STATION,OFFSET_FT
, count(*)
FROM MNR.DISTRESS_LANE_SHOULDER_DROPOFF
GROUP BY 
CELL,LANE,DAY,TIME,STATION,OFFSET_FT
HAVING COUNT(*)>1
ORDER BY 
CELL,LANE,DAY,TIME,STATION,OFFSET_FT
;
-- 2 rows selected
-- 1 duplicate
SELECT * FROM MNR.DISTRESS_LANE_SHOULDER_DROPOFF
where CELL=105 AND LANE='Passing' AND TO_CHAR(DAY,'yyyy-mm-dd')='2009-06-15' AND TIME='10:20' AND STATION=112700 AND OFFSET_FT=13

DISTRESS_LIGHTWEIGHT_DEFLECT
------------------------------
SELECT CELL,DAY,STATION,OFFSET_FT,FIELD_TEST_NUMBER,MATERIAL
, count(*)
FROM MNR.DISTRESS_LIGHTWEIGHT_DEFLECT
GROUP BY 
CELL,DAY,STATION,OFFSET_FT,FIELD_TEST_NUMBER,MATERIAL
HAVING COUNT(*)>1
ORDER BY 
CELL,DAY,STATION,OFFSET_FT,FIELD_TEST_NUMBER,MATERIAL
;
-- 89 (of 189) rows selected
Which of these?
S1_MM
S2_MM
S3_MM
S_MM
EVD_MN_M2


DISTRESS_NUCLEAR_DENSITY
------------------------------
SELECT CELL,DAY,STATION,OFFSET_FT,LANE,WHEELPATH
, count(*)
FROM MNR.DISTRESS_NUCLEAR_DENSITY
GROUP BY 
CELL,DAY,STATION,OFFSET_FT,LANE,WHEELPATH
HAVING COUNT(*)>1
ORDER BY 
CELL,DAY,STATION,OFFSET_FT,LANE,WHEELPATH
;
-- 33 rows returns
-- 
SELECT * FROM 
MNR.DISTRESS_NUCLEAR_DENSITY
where CELL=2 and to_char(DAY,'yyyy-mm-dd')='2008-07-18' 
--and STATION=111007 and OFFSET_FT=-9 
and LANE='Driving' and WHEELPATH='LWP'

DISTRESS_OBSI_DATA
------------------
DISTRESS_OBSI_DATA_UIDX : CELL,LANE,DAY,TRIAL,TIME,FREQ_HZ




DISTRESS_OBSI_SUMMARY
------------------------------
DISTRESS_OBSI_SUMMARY_UIDX : CELL,LANE,DAY,TIME,TRIAL


DISTRESS_PCC_FAULTS
------------------------------


DISTRESS_PROCEQ
------------------------------


DISTRESS_RIDE_LISA
------------------------------


DISTRESS_RIDE_PATHWAYS
------------------------------


DISTRESS_RIDE_PAVETECH
------------------------------


DISTRESS_RUTTING_DIPSTICK
------------------------------


DISTRESS_RUTTING_DIPSTICK_PINS
------------------------------


DISTRESS_RUTTING_DIPSTICK_RAW
------------------------------


DISTRESS_RUTTING_STRAIGHT_EDGE
------------------------------


DISTRESS_SAND_PATCH
------------------------------


DISTRESS_SCHMIDT_HAMMER
------------------------------


DISTRESS_SOUND_ABSORPTION
------------------------------

DISTRESS_WATER_PERMEABILITY
------------------------------


select cell, lane, panel_no, count(*)
from mnr.DISTRESS_PROCEQ
group by cell,lane,panel_no
having count(*)>1
order by cell, lane
;
--
select *
from mnr.DISTRESS_PROCEQ
where cell=914 and lane = 'Passing' and panel_no=81
;

cell,lane,day
	mnr.DISTRESS_AC                   ;
	mnr.DISTRESS_AGG_SURVEY_SEMI      ;
	mnr.DISTRESS_ALPS_DATA            ;
	mnr.DISTRESS_ALPS_RESULTS_RUT     ;

select unique cell,lane,day,wheel_path from mnr.DISTRESS_CIRCULR_TEXTR_METER  ;

	mnr.DISTRESS_CUPPING              ;
	mnr.DISTRESS_FRICTION_GRIP        ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_FRICTION_DFT         ;


select unique cell,lane,day,wheelpath from mnr.DISTRESS_FRICTION_TRAILER     ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_JPCC                 ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_LANE_SHOULDER_DROPOFF;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_LIGHTWEIGHT_DEFLECT  ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_NUCLEAR_DENSITY      ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_OBSI_DATA            ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_OBSI_SUMMARY         ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_PCC_FAULTS           ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_PROCEQ               ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_RIDE_LISA            ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_RIDE_PATHWAYS        ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_RIDE_PAVETECH        ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_RUTTING_DIPSTICK     ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_RUTTING_DIPSTICK_PINS;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_RUTTING_DIPSTICK_RAW ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_RUTTING_STRAIGHT_EDGE;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_SAND_PATCH           ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_SCHMIDT_HAMMER       ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_SOUND_ABSORPTION     ;

select unique cell,lane,day,wheelpath from mnr.DISTRESS_WATER_PERMEABILITY   ;


