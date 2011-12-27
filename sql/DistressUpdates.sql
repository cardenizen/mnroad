select dac.id, cell, lane dup_lane, ln.name lane, survey_date from distress_ac dac
    join distress d on d.id=dac.id
    join lane ln on d.lane_id=ln.id
order by dac.id, cell, lane, survey_date

exp  mnr/mnr@localhost:1521/XE tables=(DISTRESS_AC,DISTRESS_AGG_SURVEY_SEMI,DISTRESS_ALPS_DATA,DISTRESS_ALPS_RESULTS_RUT,DISTRESS_CIRCULR_TEXTR_METER,DISTRESS_DYNAMIC_FRICTION,DISTRESS_FRICTION_DATA,DISTRESS_FRICTION_GRIP,DISTRESS_JPCC,DISTRESS_LANE_SHOULDER_DROPOFF,DISTRESS_NUCLEAR_DENSITY,DISTRESS_OBSI_DATA,DISTRESS_OBSI_SUMMARY,DISTRESS_PATHWAYS_VALUES,DISTRESS_PAVETECH_VALUES,DISTRESS_PCC_FAULTS,DISTRESS_PROCEQ,DISTRESS_RIDE_LISA,DISTRESS_RUTTING_DIPSTICK,DISTRESS_RUTTING_STRAIGHT_EDGE,DISTRESS_SAND_PATCH,DISTRESS_SOUND_ABSORPTION,DISTRESS_WATER_PERMEABILITY) file=distressXE.dmp
imp tables=(DISTRESS_AC,DISTRESS_AGG_SURVEY_SEMI,DISTRESS_ALPS_RESULTS_RUT,DISTRESS_CIRCULR_TEXTR_METER,DISTRESS_DYNAMIC_FRICTION,DISTRESS_FRICTION_DATA,DISTRESS_FRICTION_GRIP,DISTRESS_JPCC,DISTRESS_LANE_SHOULDER_DROPOFF,DISTRESS_NUCLEAR_DENSITY,DISTRESS_OBSI_DATA,DISTRESS_OBSI_SUMMARY,DISTRESS_PATHWAYS_VALUES,DISTRESS_PAVETECH_VALUES,DISTRESS_PCC_FAULTS,DISTRESS_PROCEQ,DISTRESS_RIDE_LISA,DISTRESS_RUTTING_DIPSTICK,DISTRESS_RUTTING_STRAIGHT_EDGE,DISTRESS_SAND_PATCH,DISTRESS_SOUND_ABSORPTION,DISTRESS_WATER_PERMEABILITY) touser=mnr file=distressXE.dmp

mnr/mnr@mrlxpw718/XE as sysdba

imp tables=(DISTRESS_AC,DISTRESS_AGG_SURVEY_SEMI,DISTRESS_ALPS_RESULTS_RUT,DISTRESS_CIRCULR_TEXTR_METER,DISTRESS_DYNAMIC_FRICTION,DISTRESS_FRICTION_DATA,DISTRESS_FRICTION_GRIP,DISTRESS_JPCC,DISTRESS_LANE_SHOULDER_DROPOFF,DISTRESS_NUCLEAR_DENSITY,DISTRESS_OBSI_DATA,DISTRESS_OBSI_SUMMARY,DISTRESS_PATHWAYS_VALUES,DISTRESS_PAVETECH_VALUES,DISTRESS_PCC_FAULTS,DISTRESS_PROCEQ,DISTRESS_RIDE_LISA,DISTRESS_RUTTING_DIPSTICK,DISTRESS_RUTTING_STRAIGHT_EDGE,DISTRESS_SAND_PATCH,DISTRESS_SOUND_ABSORPTION,DISTRESS_WATER_PERMEABILITY) touser=mnr file=distressProd.dmp
mnr/mnr@mrlxpw017/XE as sysdba

exp  mnr/eiei0@MRL2K3MRDB.ad.dot.state.mn.us:1521/mnrd.ad.dot.state.mn.us tables=(DISTRESS_AC,DISTRESS_AGG_SURVEY_SEMI,DISTRESS_ALPS_DATA,DISTRESS_ALPS_RESULTS_RUT,DISTRESS_CIRCULR_TEXTR_METER,DISTRESS_DYNAMIC_FRICTION,DISTRESS_FRICTION_DATA,DISTRESS_FRICTION_GRIP,DISTRESS_JPCC,DISTRESS_LANE_SHOULDER_DROPOFF,DISTRESS_NUCLEAR_DENSITY,DISTRESS_OBSI_DATA,DISTRESS_OBSI_SUMMARY,DISTRESS_PATHWAYS_VALUES,DISTRESS_PAVETECH_VALUES,DISTRESS_PCC_FAULTS,DISTRESS_PROCEQ,DISTRESS_RIDE_LISA,DISTRESS_RUTTING_DIPSTICK,DISTRESS_RUTTING_STRAIGHT_EDGE,DISTRESS_SAND_PATCH,DISTRESS_SOUND_ABSORPTION,DISTRESS_WATER_PERMEABILITY) file=distressProd.dmp

drop table DISTRESS_AC                    purge;
drop table DISTRESS_AGG_SURVEY_SEMI       purge;
--drop table DISTRESS_ALPS_DATA             purge;
drop table DISTRESS_ALPS_RESULTS_RUT      purge;
drop table DISTRESS_CIRCULR_TEXTR_METER   purge;
drop table DISTRESS_DYNAMIC_FRICTION      purge;
drop table DISTRESS_FRICTION_DATA         purge;
drop table DISTRESS_FRICTION_GRIP         purge;
drop table DISTRESS_JPCC                  purge;
drop table DISTRESS_LANE_SHOULDER_DROPOFF purge;
drop table DISTRESS_NUCLEAR_DENSITY       purge;
drop table DISTRESS_OBSI_DATA             purge;
drop table DISTRESS_OBSI_SUMMARY          purge;
drop table DISTRESS_PATHWAYS_VALUES       purge;
drop table DISTRESS_PAVETECH_VALUES       purge;
drop table DISTRESS_PCC_FAULTS            purge;
drop table DISTRESS_PROCEQ                purge;
drop table DISTRESS_RIDE_LISA             purge;
drop table DISTRESS_RUTTING_DIPSTICK      purge;
drop table DISTRESS_RUTTING_STRAIGHT_EDGE purge;
drop table DISTRESS_SAND_PATCH            purge;
drop table DISTRESS_SOUND_ABSORPTION      purge;
drop table DISTRESS_WATER_PERMEABILITY    purge;

select distinct 'DISTRESS_AC                   ' tableName, lane,count(*) from DISTRESS_AC                      group by lane union all
select distinct 'DISTRESS_AGG_SURVEY_SEMI      ' tableName, lane,count(*) from DISTRESS_AGG_SURVEY_SEMI         group by lane union all
select distinct 'DISTRESS_ALPS_DATA            ' tableName, lane,count(*) from DISTRESS_ALPS_DATA               group by lane union all
select distinct 'DISTRESS_ALPS_RESULTS_RUT     ' tableName, lane,count(*) from DISTRESS_ALPS_RESULTS_RUT        group by lane union all
select distinct 'DISTRESS_CIRCULR_TEXTR_METER  ' tableName, lane,count(*) from DISTRESS_CIRCULR_TEXTR_METER     group by lane union all
select distinct 'DISTRESS_DYNAMIC_FRICTION     ' tableName, lane,count(*) from DISTRESS_DYNAMIC_FRICTION        group by lane union all
select distinct 'DISTRESS_FRICTION_DATA        ' tableName, lane,count(*) from DISTRESS_FRICTION_DATA           group by lane union all
select distinct 'DISTRESS_FRICTION_GRIP        ' tableName, lane,count(*) from DISTRESS_FRICTION_GRIP           group by lane union all
select distinct 'DISTRESS_JPCC                 ' tableName, lane,count(*) from DISTRESS_JPCC                    group by lane union all
select distinct 'DISTRESS_LANE_SHOULDER_DROPOFF' tableName, lane,count(*) from DISTRESS_LANE_SHOULDER_DROPOFF   group by lane union all
select distinct 'DISTRESS_NUCLEAR_DENSITY      ' tableName, lane,count(*) from DISTRESS_NUCLEAR_DENSITY         group by lane union all
select distinct 'DISTRESS_OBSI_DATA            ' tableName, lane,count(*) from DISTRESS_OBSI_DATA               group by lane union all
select distinct 'DISTRESS_OBSI_SUMMARY         ' tableName, lane,count(*) from DISTRESS_OBSI_SUMMARY            group by lane union all
select distinct 'DISTRESS_PATHWAYS_VALUES      ' tableName, lane,count(*) from DISTRESS_PATHWAYS_VALUES         group by lane union all
select distinct 'DISTRESS_PAVETECH_VALUES      ' tableName, lane,count(*) from DISTRESS_PAVETECH_VALUES         group by lane union all
select distinct 'DISTRESS_PCC_FAULTS           ' tableName, lane,count(*) from DISTRESS_PCC_FAULTS              group by lane union all
select distinct 'DISTRESS_PROCEQ               ' tableName, lane,count(*) from DISTRESS_PROCEQ                  group by lane union all
select distinct 'DISTRESS_RIDE_LISA            ' tableName, lane,count(*) from DISTRESS_RIDE_LISA               group by lane union all
select distinct 'DISTRESS_RUTTING_DIPSTICK     ' tableName, lane,count(*) from DISTRESS_RUTTING_DIPSTICK        group by lane union all
select distinct 'DISTRESS_RUTTING_STRAIGHT_EDGE' tableName, lane,count(*) from DISTRESS_RUTTING_STRAIGHT_EDGE   group by lane union all
select distinct 'DISTRESS_SAND_PATCH           ' tableName, lane,count(*) from DISTRESS_SAND_PATCH              group by lane union all
select distinct 'DISTRESS_SOUND_ABSORPTION     ' tableName, lane,count(*) from DISTRESS_SOUND_ABSORPTION        group by lane union all
select distinct 'DISTRESS_WATER_PERMEABILITY   ' tableName, lane,count(*) from DISTRESS_WATER_PERMEABILITY      group by lane;

select * from (

select distinct CELL,lane, 'MNR.DISTRESS_AC                   ' xtable from MNR.DISTRESS_AC                       union all
select distinct CELL,lane, 'MNR.DISTRESS_AGG_SURVEY_SEMI      ' xtable from MNR.DISTRESS_AGG_SURVEY_SEMI          union all
select distinct CELL,lane, 'MNR.DISTRESS_ALPS_DATA            ' xtable from MNR.DISTRESS_ALPS_DATA                union all
select distinct CELL,lane, 'MNR.DISTRESS_ALPS_RESULTS_RUT     ' xtable from MNR.DISTRESS_ALPS_RESULTS_RUT         union all
select distinct CELL,lane, 'MNR.DISTRESS_CIRCULR_TEXTR_METER  ' xtable from MNR.DISTRESS_CIRCULR_TEXTR_METER      union all
select distinct CELL,lane, 'MNR.DISTRESS_FRICTION_DFT         ' xtable from MNR.DISTRESS_FRICTION_DFT             union all
select distinct CELL,lane, 'MNR.DISTRESS_FRICTION_TRAILER     ' xtable from MNR.DISTRESS_FRICTION_TRAILER         union all
select distinct CELL,lane, 'MNR.DISTRESS_FRICTION_GRIP        ' xtable from MNR.DISTRESS_FRICTION_GRIP            union all
select distinct CELL,lane, 'MNR.DISTRESS_JPCC                 ' xtable from MNR.DISTRESS_JPCC                     union all
select distinct CELL,lane, 'MNR.DISTRESS_LANE_SHOULDER_DROPOFF' xtable from MNR.DISTRESS_LANE_SHOULDER_DROPOFF    union all
select distinct CELL,lane, 'MNR.DISTRESS_NUCLEAR_DENSITY      ' xtable from MNR.DISTRESS_NUCLEAR_DENSITY          union all
select distinct CELL,lane, 'MNR.DISTRESS_OBSI_DATA            ' xtable from MNR.DISTRESS_OBSI_DATA                union all
select distinct CELL,lane, 'MNR.DISTRESS_OBSI_SUMMARY         ' xtable from MNR.DISTRESS_OBSI_SUMMARY             union all
select distinct CELL,lane, 'MNR.DISTRESS_RIDE_PATHWAYS        ' xtable from MNR.DISTRESS_RIDE_PATHWAYS            union all
select distinct CELL,lane, 'MNR.DISTRESS_RIDE_PAVETECH        ' xtable from MNR.DISTRESS_RIDE_PAVETECH            union all
select distinct CELL,lane, 'MNR.DISTRESS_PCC_FAULTS           ' xtable from MNR.DISTRESS_PCC_FAULTS               union all
select distinct CELL,lane, 'MNR.DISTRESS_RIDE_LISA            ' xtable from MNR.DISTRESS_RIDE_LISA                union all
select distinct CELL,lane, 'MNR.DISTRESS_RUTTING_DIPSTICK     ' xtable from MNR.DISTRESS_RUTTING_DIPSTICK         union all
select distinct CELL,lane, 'MNR.DISTRESS_RUTTING_STRAIGHT_EDGE' xtable from MNR.DISTRESS_RUTTING_STRAIGHT_EDGE    union all
select distinct CELL,lane, 'MNR.DISTRESS_SAND_PATCH           ' xtable from MNR.DISTRESS_SAND_PATCH               union all
select distinct CELL,lane, 'MNR.DISTRESS_SOUND_ABSORPTION     ' xtable from MNR.DISTRESS_SOUND_ABSORPTION         union all
select distinct CELL,lane, 'MNR.DISTRESS_WATER_PERMEABILITY   ' xtable from MNR.DISTRESS_WATER_PERMEABILITY

)
where lane='Driving Lane_Right Wheel Path'
ORDER BY CELL,LANE


update DISTRESS_AC set lane='Eastbound' where lane = 'Farm Eastbound';
update DISTRESS_AC set lane='Westbound' where lane = 'Farm Westbound';
update DISTRESS_AC set lane='Inside'    where lane = 'LVR Inside';
update DISTRESS_AC set lane='Outside'   where lane = 'LVR Outside';
update DISTRESS_AC set lane='Driving'   where lane = 'ML Driving';
update DISTRESS_AC set lane='Passing'   where lane = 'ML Passing';
--
update DISTRESS_ALPS_RESULTS_RUT set lane='Eastbound' where lane = 'Eastbound';
update DISTRESS_ALPS_RESULTS_RUT set lane='Eastbound' where lane = 'East Bound';
update DISTRESS_ALPS_RESULTS_RUT set lane='Westbound' where lane = 'West Bound';
update DISTRESS_ALPS_RESULTS_RUT set lane='Westbound' where lane = 'Westbound';
update DISTRESS_ALPS_RESULTS_RUT set lane='Inside'    where lane = '80k'       ;
update DISTRESS_ALPS_RESULTS_RUT set lane='Outside'   where lane = '102k'     ;
update DISTRESS_ALPS_RESULTS_RUT set lane='Inside'    where lane = 'LVR Inside'       ;
update DISTRESS_ALPS_RESULTS_RUT set lane='Outside'   where lane = 'LVR Outside'     ;
update DISTRESS_ALPS_RESULTS_RUT set lane='Driving'   where lane = 'ML Driving'      ;
update DISTRESS_ALPS_RESULTS_RUT set lane='Passing'   where lane = 'ML Passing'      ;
--
update DISTRESS_CIRCULR_TEXTR_METER set lane='Inside'  where lane = 'LVR-Inside';
update DISTRESS_CIRCULR_TEXTR_METER set lane='Outside' where lane = 'LVR-Outside';
update DISTRESS_CIRCULR_TEXTR_METER set lane='Driving' where lane = 'Mainline-Driving';
update DISTRESS_CIRCULR_TEXTR_METER set lane='Passing' where lane = 'Mailine-Passing';
update DISTRESS_CIRCULR_TEXTR_METER set lane='Passing' where lane = 'Mainline-Passing';
--
update DISTRESS_CUPPING set lane='Driving';
update DISTRESS_CUPPING set location='Left Wheel Path'  where location='Driving Lane - Left Wheel Path';
update DISTRESS_CUPPING set location='Right Wheel Path' where location='Driving Lane - Right Wheel Path';
update DISTRESS_CUPPING set location='Mid-Lane'         where location='Driving Lane - Mid-Lane';
alter table DISTRESS_CUPPING rename column location to wheel_path;
--

--
--update DISTRESS_SAND_PATCH set lane='??????' where lane = 'LS/LWP';
--update DISTRESS_SAND_PATCH set lane='??????' where lane = 'LS/RWP';
update DISTRESS_SAND_PATCH set lane='Inside' where lane = 'LVR-Inside Lane';
update DISTRESS_SAND_PATCH set lane='Inside' where lane = 'LVR-Inside Lane';
update DISTRESS_SAND_PATCH set lane='Inside' where lane = 'LVR-Inside Lane ';
update DISTRESS_SAND_PATCH set lane='Outside' where lane = 'LVR-Outside Lane';
update DISTRESS_SAND_PATCH set lane='Driving' where lane = 'Mailine Driving';
update DISTRESS_SAND_PATCH set lane='Driving' where lane = 'Mainline Driving';
update DISTRESS_SAND_PATCH set lane='Passing' where lane = 'Mainline Passing';
update DISTRESS_SAND_PATCH set lane='Passing' where lane = 'Mainline Passing ';

CREATE TABLE tempd
(
   DAY timestamp NOT NULL,
   CELL number(2) NOT NULL,
   LANE varchar2(20) NOT NULL,
   RUT_START number(3,1),
   RUT_END number(3,1),
   WASHBOARD_START number(3,1),
   WASHBOARD_END number(3,1),
   DUST_START number(3,1),
   DUST_END number(3,1),
   DISTRESS_COMPARISON number(2),
   DUST_COMPARISON number(2),
   AVG_SPEED_MPH number(2),
   SURVEY_COMMENT varchar2(225),
   ID number(22)
)
;
--
INSERT INTO tempd
SELECT
    DAY
,   CELL
,   LANE
,   RUT_START
,   RUT_END
,   WASHBOARD_START
,   WASHBOARD_END
,   DUST_START
,   DUST_END
,   DISTRESS_COMPARISON
,   DUST_COMPARISON
,   AVG_SPEED_MPH
,   SURVEY_COMMENT
,   DISTRESS_SEQ.NEXTVAL
FROM DISTRESS_AGG_SURVEY_SEMI;
--
truncate table DISTRESS_AGG_SURVEY_SEMI;
--
INSERT INTO DISTRESS_AGG_SURVEY_SEMI
SELECT
    DAY
,   CELL
,   LANE
,   RUT_START
,   RUT_END
,   WASHBOARD_START
,   WASHBOARD_END
,   DUST_START
,   DUST_END
,   DISTRESS_COMPARISON
,   DUST_COMPARISON
,   AVG_SPEED_MPH
,   SURVEY_COMMENT
,   id
FROM tempd;
--
drop table tempd purge;
-- DISTRESS_ALPS_RESULTS_RUT
CREATE TABLE tempd
(
   CELL                         number(2),
   STATION                      number(8,1),
   LANE                         varchar2(15),
   MEASUREMENT_TYPE             varchar2(30),
   DAY                          timestamp,
   HOUR_MIN_SEC                 varchar2(10),
   WHEELPATH                    varchar2(10),
   RUT_IN                       number(6,4),
   WATER_IN                     number(6,4),
   RUT_VOL_CFPF                 number(6,4),
   WATER_VOL_CFPF               number(6,4),
   X_RUT_FT                     number(6,4),
   X_WATER_FT                   number(6,4),
   DATE_UPDATED                 timestamp,
   COMMENTS                     varchar2(60)
,  ID                           number(22)
)
;
--
INSERT INTO tempd
SELECT
   CELL
,   STATION
,   LANE
,   MEASUREMENT_TYPE
,   DAY
,   HOUR_MIN_SEC
,   WHEELPATH
,   RUT_IN
,   WATER_IN
,   RUT_VOL_CFPF
,   WATER_VOL_CFPF
,   X_RUT_FT
,   X_WATER_FT
,   DATE_UPDATED
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_ALPS_RESULTS_RUT;
--
truncate table DISTRESS_ALPS_RESULTS_RUT;
--
INSERT INTO DISTRESS_ALPS_RESULTS_RUT
SELECT
   CELL
,   STATION
,   LANE
,   MEASUREMENT_TYPE
,   DAY
,   HOUR_MIN_SEC
,   WHEELPATH
,   RUT_IN
,   WATER_IN
,   RUT_VOL_CFPF
,   WATER_VOL_CFPF
,   X_RUT_FT
,   X_WATER_FT
,   DATE_UPDATED
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_CIRCULR_TEXTR_METER
CREATE TABLE tempd
(
   CELL                         number(3) NOT NULL,
   LANE                         varchar2(30) NOT NULL,
   WHEEL_PATH                   varchar2(3) NOT NULL,
   DAY                          timestamp NOT NULL,
   TIME                         varchar2(20) NOT NULL,
   OPERATOR                     varchar2(30),
   FIELD_ID                     varchar2(12),
   STATION                      number(10,2),
   OFFSET                       number(3),
   TRIAL                        varchar2(12),
   MEAN_PROFILE_DEPTH_MM        number(4,2),
   ROOT_MEAN_SQUARED_TD         number(4,2)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,  LANE
,  WHEEL_PATH
,  DAY
,  TIME
,  OPERATOR
,  FIELD_ID
,  STATION
,  OFFSET
,  TRIAL
,  MEAN_PROFILE_DEPTH_MM
,  ROOT_MEAN_SQUARED_TD
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_CIRCULR_TEXTR_METER;
--
truncate table DISTRESS_CIRCULR_TEXTR_METER;
--
INSERT INTO DISTRESS_CIRCULR_TEXTR_METER
SELECT
   CELL
,  LANE
,  WHEEL_PATH
,  DAY
,  TIME
,  OPERATOR
,  FIELD_ID
,  STATION
,  OFFSET
,  TRIAL
,  MEAN_PROFILE_DEPTH_MM
,  ROOT_MEAN_SQUARED_TD
,  ID
FROM tempd;
--
drop table tempd purge;
-- DISTRESS_CUPPING
CREATE TABLE tempd
(
   CELL                         number(2),
   DAY                          timestamp,
   STA                          number(9,2),
   WHEEL_PATH                     varchar2(40),
   CRACK_NO                     varchar2(10),
   CUPPING_X_DISTANCE_INCH      number(5,2),
   CUPDEPTH_Y_INCH              number(6,3),
   COMMENTS                     varchar2(30),
   WEATHER                      varchar2(30)
,  ID                           number(22)
,  lane varchar(10)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
,   DAY
,   STA
,   WHEEL_PATH
,   CRACK_NO
,   CUPPING_X_DISTANCE_INCH
,   CUPDEPTH_Y_INCH
,   COMMENTS
,   WEATHER
,   DISTRESS_SEQ.NEXTVAL
,   lane
from DISTRESS_CUPPING;
--
--
truncate table DISTRESS_CUPPING;
--
INSERT INTO DISTRESS_CUPPING
SELECT
    CELL
,   DAY
,   STA
,   WHEEL_PATH
,   CRACK_NO
,   CUPPING_X_DISTANCE_INCH
,   CUPDEPTH_Y_INCH
,   COMMENTS
,   WEATHER
,   id
,   lane
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_DYNAMIC_FRICTION
CREATE TABLE tempd
(
   CELL                         NUMBER(3),
   LANE                         varchar2(30),
   DAY                          timestamp,
   STATION                      NUMBER(10,1),
   OFFSET                       NUMBER(10,1),
   WHEELPATH                    varchar2(6),
   MEASURED_BY                  varchar2(20),
   RUN_NO                       NUMBER(2),
   SPEED_KPH                    NUMBER(6,1),
   COEFF_FRICTION               NUMBER(10,3),
   COMMENTS                     varchar2(100),
   DATE_UPDATED                 timestamp
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   LANE
,   DAY
,   STATION
,   OFFSET
,   WHEELPATH
,   MEASURED_BY
,   RUN_NO
,   SPEED_KPH
,   COEFF_FRICTION
,   COMMENTS
,   DATE_UPDATED
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_DYNAMIC_FRICTION;
--
truncate table DISTRESS_DYNAMIC_FRICTION;
--
INSERT INTO DISTRESS_DYNAMIC_FRICTION
SELECT
   CELL
,   LANE
,   DAY
,   STATION
,   OFFSET
,   WHEELPATH
,   MEASURED_BY
,   RUN_NO
,   SPEED_KPH
,   COEFF_FRICTION
,   COMMENTS
,   DATE_UPDATED
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_FRICTION_DATA
CREATE TABLE tempd
(
   CELL                 number(3),
   CONSTRUCTION_NUMBER  number(3) NOT NULL,
   LANE                 varchar2(15),
   DAY                  timestamp,
   TIME                 varchar2(5),
   FN                   number(3,1),
   PEAK                 number(5,2),
   SPEED                number(3,1),
   AIR_TEMP             number(5,2),
   PVMT_TEMP            number(5,2),
   TIRE_TYPE            varchar2(10),
   EQUIPMENT            varchar2(50),
   STA                  varchar2(10),
   DATE_UPDATED         timestamp,
   LATITUDE             varchar2(14),
   LONGITUDE            varchar2(14),
   MINFN                number(2),
   MAXFN                number(2),
   SLIP                 number(2),
   COMMENTS             varchar2(80)
,  ID                   number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   CONSTRUCTION_NUMBER
,   LANE
,   DAY
,   TIME
,   FN
,   PEAK
,   SPEED
,   AIR_TEMP
,   PVMT_TEMP
,   TIRE_TYPE
,   EQUIPMENT
,   STA
,   DATE_UPDATED
,   LATITUDE
,   LONGITUDE
,   MINFN
,   MAXFN
,   SLIP
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_FRICTION_DATA;
--
truncate table DISTRESS_FRICTION_DATA;
--
INSERT INTO DISTRESS_FRICTION_DATA
SELECT
   CELL
,   CONSTRUCTION_NUMBER
,   LANE
,   DAY
,   TIME
,   FN
,   PEAK
,   SPEED
,   AIR_TEMP
,   PVMT_TEMP
,   TIRE_TYPE
,   EQUIPMENT
,   STA
,   DATE_UPDATED
,   LATITUDE
,   LONGITUDE
,   MINFN
,   MAXFN
,   SLIP
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_FRICTION_GRIP
CREATE TABLE tempd
(
   CELL         NUMBER(3) NOT NULL,
   LANE         varchar2(30) NOT NULL,
   WHEEL_PATH   varchar2(3) NOT NULL,
   DAY          timestamp NOT NULL,
   TIME         varchar2(10) NOT NULL,
   OPERATOR     varchar2(30),
   DISTANCE_FT  NUMBER(6),
   CHAINAGE     NUMBER(6),
   STATION      NUMBER(8),
   OFFSET       NUMBER(1),
   RUN          NUMBER(2),
   SPEED        NUMBER(5),
   LOAD         NUMBER(3),
   GN           NUMBER(3)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
, LANE
, WHEEL_PATH
, DAY
, TIME
, OPERATOR
, DISTANCE_FT
, CHAINAGE
, STATION
, OFFSET
, RUN
, SPEED
, LOAD
, GN
, DISTRESS_SEQ.NEXTVAL
from DISTRESS_FRICTION_GRIP;
--
truncate table DISTRESS_FRICTION_GRIP;
--
INSERT INTO DISTRESS_FRICTION_GRIP
SELECT
CELL
, LANE
, WHEEL_PATH
, DAY
, TIME
, OPERATOR
, DISTANCE_FT
, CHAINAGE
, STATION
, OFFSET
, RUN
, SPEED
, LOAD
, GN
, id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_JPCC
CREATE TABLE tempd
(
   CELL         number(3) NOT NULL,
   LANE         varchar2(20) NOT NULL,
   CONSTRUCTION_NO number(2) NOT NULL,
   SURVEY_DATE     timestamp NOT NULL,
   SURVEYOR1          varchar2(24),
   SURVEYOR2          varchar2(24),
   CORNER_BREAKS_NO_L    number(3,0),
   CORNER_BREAKS_NO_M    number(3,0),
   CORNER_BREAKS_NO_H    number(3,0),
   DURAB_CRACK_NO_L      number(3,0),
   DURAB_CRACK_NO_M      number(3,0),
   DURAB_CRACK_NO_H      number(3,0),
   DURAB_CRACK_A_L       number(4,1),
   DURAB_CRACK_A_M       number(4,1),
   DURAB_CRACK_A_H       number(4,1),
   LONG_CRACK_L_L        number(5,1),
   LONG_CRACK_L_M        number(5,1),
   LONG_CRACK_L_H        number(5,1),
   LONG_CRACK_SEAL_L_L   number(5,1),
   LONG_CRACK_SEAL_L_M   number(5,1),
   LONG_CRACK_SEAL_L_H   number(5,1),
   TRANS_CRACK_NO_L      number(3,0),
   TRANS_CRACK_NO_M      number(3,0),
   TRANS_CRACK_NO_H      number(3,0),
   TRANS_CRACK_L_L       number(5,1),
   TRANS_CRACK_L_M       number(5,1),
   TRANS_CRACK_L_H       number(5,1),
   TRANS_CRACK_SEAL_L_L  number(5,1),
   TRANS_CRACK_SEAL_L_M  number(5,1),
   TRANS_CRACK_SEAL_L_H  number(5,1),
   JT_SEALED             varchar2(2),
   JOINT_SEAL_TRANS_NO_L number(2,0),
   JOINT_SEAL_TRANS_NO_M number(2,0),
   JOINT_SEAL_TRANS_NO_H number(2,0),
   LONG_JT_SEAL_NO       number(1,0),
   LONG_JT_SEAL_DAM_L    number(4,1),
   LONG_SPALLING_L_L     number(4,1),
   LONG_SPALLING_L_M     number(4,1),
   LONG_SPALLING_L_H     number(4,1),
   TRANS_SPALLING_NO_L   number(2,0),
   TRANS_SPALLING_NO_M   number(2,0),
   TRANS_SPALLING_NO_H   number(2,0),
   TRANS_SPALLING_L_L    number(4,1),
   TRANS_SPALLING_L_M    number(4,1),
   TRANS_SPALLING_L_H    number(4,1),
   SCALING_NO            number(3,0),
   SCALING_A             number(5,1),
   POLISH_AGG_A          number(4,1),
   POPOUTS_NO_AREA       number(4,0),
   BLOWUPS_NO            number(3,0),
   PATCH_FLEX_NO_L       number(3,0),
   PATCH_FLEX_NO_M       number(3,0),
   PATCH_FLEX_NO_H       number(3,0),
   PATCH_FLEX_A_L        number(4,1),
   PATCH_FLEX_A_M        number(4,1),
   PATCH_FLEX_A_H        number(4,1),
   PATCH_RIGID_NO_L      number(3,0),
   PATCH_RIGID_NO_M      number(3,0),
   PATCH_RIGID_NO_H      number(3,0),
   PATCH_RIGID_A_L       number(4,1),
   PATCH_RIGID_A_M       number(4,1),
   PATCH_RIGID_A_H       number(4,1),
   PUMPING_NO            number(3,0),
   PUMPING_L             number(4,1),
   OTHER                varchar2(80),
   RECORD_STATUS         varchar2(1),
   MAP_CRACK_NO          number(3,0),
   MAP_CRACK_A           number(5,1),
   LONG_CRACK_NO_L       number(3,0),
   LONG_CRACK_NO_M       number(3,0),
   LONG_CRACK_NO_H       number(3,0),
   DATE_UPDATED timestamp
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL                  ,
   LANE                  ,
   CONSTRUCTION_NO       ,
   SURVEY_DATE           ,
   SURVEYOR1             ,
   SURVEYOR2             ,
   CORNER_BREAKS_NO_L    ,
   CORNER_BREAKS_NO_M    ,
   CORNER_BREAKS_NO_H    ,
   DURAB_CRACK_NO_L      ,
   DURAB_CRACK_NO_M      ,
   DURAB_CRACK_NO_H      ,
   DURAB_CRACK_A_L       ,
   DURAB_CRACK_A_M       ,
   DURAB_CRACK_A_H       ,
   LONG_CRACK_L_L        ,
   LONG_CRACK_L_M        ,
   LONG_CRACK_L_H        ,
   LONG_CRACK_SEAL_L_L   ,
   LONG_CRACK_SEAL_L_M   ,
   LONG_CRACK_SEAL_L_H   ,
   TRANS_CRACK_NO_L      ,
   TRANS_CRACK_NO_M      ,
   TRANS_CRACK_NO_H      ,
   TRANS_CRACK_L_L       ,
   TRANS_CRACK_L_M       ,
   TRANS_CRACK_L_H       ,
   TRANS_CRACK_SEAL_L_L  ,
   TRANS_CRACK_SEAL_L_M  ,
   TRANS_CRACK_SEAL_L_H  ,
   JT_SEALED             ,
   JOINT_SEAL_TRANS_NO_L ,
   JOINT_SEAL_TRANS_NO_M ,
   JOINT_SEAL_TRANS_NO_H ,
   LONG_JT_SEAL_NO       ,
   LONG_JT_SEAL_DAM_L    ,
   LONG_SPALLING_L_L     ,
   LONG_SPALLING_L_M     ,
   LONG_SPALLING_L_H     ,
   TRANS_SPALLING_NO_L   ,
   TRANS_SPALLING_NO_M   ,
   TRANS_SPALLING_NO_H   ,
   TRANS_SPALLING_L_L    ,
   TRANS_SPALLING_L_M    ,
   TRANS_SPALLING_L_H    ,
   SCALING_NO            ,
   SCALING_A             ,
   POLISH_AGG_A          ,
   POPOUTS_NO_AREA       ,
   BLOWUPS_NO            ,
   PATCH_FLEX_NO_L       ,
   PATCH_FLEX_NO_M       ,
   PATCH_FLEX_NO_H       ,
   PATCH_FLEX_A_L        ,
   PATCH_FLEX_A_M        ,
   PATCH_FLEX_A_H        ,
   PATCH_RIGID_NO_L      ,
   PATCH_RIGID_NO_M      ,
   PATCH_RIGID_NO_H      ,
   PATCH_RIGID_A_L       ,
   PATCH_RIGID_A_M       ,
   PATCH_RIGID_A_H       ,
   PUMPING_NO            ,
   PUMPING_L             ,
   OTHER                 ,
   RECORD_STATUS         ,
   MAP_CRACK_NO          ,
   MAP_CRACK_A           ,
   LONG_CRACK_NO_L       ,
   LONG_CRACK_NO_M       ,
   LONG_CRACK_NO_H       ,
   DATE_UPDATED
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_JPCC;
--
truncate table DISTRESS_JPCC;
--
INSERT INTO DISTRESS_JPCC
SELECT
   CELL                  ,
   LANE                  ,
   CONSTRUCTION_NO       ,
   SURVEY_DATE           ,
   SURVEYOR1             ,
   SURVEYOR2             ,
   CORNER_BREAKS_NO_L    ,
   CORNER_BREAKS_NO_M    ,
   CORNER_BREAKS_NO_H    ,
   DURAB_CRACK_NO_L      ,
   DURAB_CRACK_NO_M      ,
   DURAB_CRACK_NO_H      ,
   DURAB_CRACK_A_L       ,
   DURAB_CRACK_A_M       ,
   DURAB_CRACK_A_H       ,
   LONG_CRACK_L_L        ,
   LONG_CRACK_L_M        ,
   LONG_CRACK_L_H        ,
   LONG_CRACK_SEAL_L_L   ,
   LONG_CRACK_SEAL_L_M   ,
   LONG_CRACK_SEAL_L_H   ,
   TRANS_CRACK_NO_L      ,
   TRANS_CRACK_NO_M      ,
   TRANS_CRACK_NO_H      ,
   TRANS_CRACK_L_L       ,
   TRANS_CRACK_L_M       ,
   TRANS_CRACK_L_H       ,
   TRANS_CRACK_SEAL_L_L  ,
   TRANS_CRACK_SEAL_L_M  ,
   TRANS_CRACK_SEAL_L_H  ,
   JT_SEALED             ,
   JOINT_SEAL_TRANS_NO_L ,
   JOINT_SEAL_TRANS_NO_M ,
   JOINT_SEAL_TRANS_NO_H ,
   LONG_JT_SEAL_NO       ,
   LONG_JT_SEAL_DAM_L    ,
   LONG_SPALLING_L_L     ,
   LONG_SPALLING_L_M     ,
   LONG_SPALLING_L_H     ,
   TRANS_SPALLING_NO_L   ,
   TRANS_SPALLING_NO_M   ,
   TRANS_SPALLING_NO_H   ,
   TRANS_SPALLING_L_L    ,
   TRANS_SPALLING_L_M    ,
   TRANS_SPALLING_L_H    ,
   SCALING_NO            ,
   SCALING_A             ,
   POLISH_AGG_A          ,
   POPOUTS_NO_AREA       ,
   BLOWUPS_NO            ,
   PATCH_FLEX_NO_L       ,
   PATCH_FLEX_NO_M       ,
   PATCH_FLEX_NO_H       ,
   PATCH_FLEX_A_L        ,
   PATCH_FLEX_A_M        ,
   PATCH_FLEX_A_H        ,
   PATCH_RIGID_NO_L      ,
   PATCH_RIGID_NO_M      ,
   PATCH_RIGID_NO_H      ,
   PATCH_RIGID_A_L       ,
   PATCH_RIGID_A_M       ,
   PATCH_RIGID_A_H       ,
   PUMPING_NO            ,
   PUMPING_L             ,
   OTHER                 ,
   RECORD_STATUS         ,
   MAP_CRACK_NO          ,
   MAP_CRACK_A           ,
   LONG_CRACK_NO_L       ,
   LONG_CRACK_NO_M       ,
   LONG_CRACK_NO_H       ,
   DATE_UPDATED
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_LANE_SHOULDER_DROPOFF
CREATE TABLE tempd
(
   CELL               number(3),
   LANE            varchar2(60),
   DAY                timestamp,
   TIME            varchar2(20),
   OPERATOR        varchar2(30),
   CONSTRUCTION_NO    number(6),
   STATION         number(10,1),
   OFFSET           number(4,1),
   FAULT_DEPTH_MM   number(6,1),
   COMMENTS       varchar2(500)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   LANE
,   DAY
,   TIME
,   OPERATOR
,   CONSTRUCTION_NO
,   STATION
,   OFFSET
,   FAULT_DEPTH_MM
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_LANE_SHOULDER_DROPOFF;
--
--
truncate table DISTRESS_LANE_SHOULDER_DROPOFF;
--
INSERT INTO DISTRESS_LANE_SHOULDER_DROPOFF
SELECT
   CELL
,   LANE
,   DAY
,   TIME
,   OPERATOR
,   CONSTRUCTION_NO
,   STATION
,   OFFSET
,   FAULT_DEPTH_MM
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_NUCLEAR_DENSITY
CREATE TABLE tempd
(
   CELL                  number(4) NOT NULL,
   DAY                   timestamp NOT NULL,
   WHEELPATH             varchar2(3) NOT NULL,
   STATION               number(10,1),
   OFFSET                number(4,1),
   LANE                  varchar2(30),
   OPERATOR              varchar2(30),
   EQUIPMENT             varchar2(10),
   SETUP                 varchar2(30),
   MATERIAL_TESTED       varchar2(20),
   RUN1_TRANSVERSE_PCF   number(6,1),
   RUN2_TRANSVERSE_PCF   number(6,1),
   RUN3_LONGITUDINAL_PCF number(6,1),
   RUN4_LONGITUDINAL_PCF number(6,1),
   STATION_AVERAGE_PCF   number(6,1),
   MOISTURE_CONTENT_PCT  number(6,1),
   COMMENTS              varchar2(100)
,  ID                    number(22)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
,   DAY
,   WHEELPATH
,   STATION
,   OFFSET
,   LANE
,   OPERATOR
,   EQUIPMENT
,   SETUP
,   MATERIAL_TESTED
,   RUN1_TRANSVERSE_PCF
,   RUN2_TRANSVERSE_PCF
,   RUN3_LONGITUDINAL_PCF
,   RUN4_LONGITUDINAL_PCF
,   STATION_AVERAGE_PCF
,   MOISTURE_CONTENT_PCT
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_NUCLEAR_DENSITY;
--
truncate table DISTRESS_NUCLEAR_DENSITY;
--
INSERT INTO DISTRESS_NUCLEAR_DENSITY
SELECT
CELL
,   DAY
,   STATION
,   OFFSET
,   LANE
,   WHEELPATH
,   OPERATOR
,   EQUIPMENT
,   SETUP
,   MATERIAL_TESTED
,   RUN1_TRANSVERSE_PCF
,   RUN2_TRANSVERSE_PCF
,   RUN3_LONGITUDINAL_PCF
,   RUN4_LONGITUDINAL_PCF
,   STATION_AVERAGE_PCF
,   MOISTURE_CONTENT_PCT
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_OBSI_DATA
CREATE TABLE tempd
(
   CELL                      number(3) NOT NULL,
   LANE                      varchar2(30) NOT NULL,
   DAY                       timestamp NOT NULL,
   TIME                      varchar2(20) NOT NULL,
   OPERATOR                  varchar2(30) NOT NULL,
   TEST_TIRE                 varchar2(10) NOT NULL,
   FREQ_HZ                   number(10,0) NOT NULL,
   LEADING_INTENSITY_LEVEL   number(10,6),
   LEADING_PI                number(10,6),
   LEADING_COH               number(10,6),
   TRAILING_INTENSITY_LEVEL  number(10,6),
   TRAILING_PI               number(10,6),
   TRAILING_COH              number(10,6),
   AVG_INTENSITY_LEVEL       number(4,1),
   TRIAL                     number(4,0),
   GRIND                     varchar2(12),
   COMMENTS                  varchar2(500)
,  ID                        number(22)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
,   LANE
,   DAY
,   TIME
,   OPERATOR
,   TEST_TIRE
,   FREQ_HZ
,   LEADING_INTENSITY_LEVEL
,   LEADING_PI
,   LEADING_COH
,   TRAILING_INTENSITY_LEVEL
,   TRAILING_PI
,   TRAILING_COH
,   AVG_INTENSITY_LEVEL
,   TRIAL
,   GRIND
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_OBSI_DATA;
--
--
truncate table DISTRESS_OBSI_DATA;
--
INSERT INTO DISTRESS_OBSI_DATA
SELECT
CELL
,   LANE
,   DAY
,   TIME
,   OPERATOR
,   TEST_TIRE
,   FREQ_HZ
,   LEADING_INTENSITY_LEVEL
,   LEADING_PI
,   LEADING_COH
,   TRAILING_INTENSITY_LEVEL
,   TRAILING_PI
,   TRAILING_COH
,   AVG_INTENSITY_LEVEL
,   TRIAL
,   GRIND
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_OBSI_SUMMARY
CREATE TABLE tempd
(
   CELL           number(3) NOT NULL,
   LANE           varchar2(30) NOT NULL,
   DAY            timestamp NOT NULL,
   TIME           varchar2(20) NOT NULL,
   OPERATOR       varchar2(30) NOT NULL,
   TEST_TIRE      varchar2(10) NOT NULL,
   LEADING_OBSI   number(10,6),
   TRAILING_OBSI  number(10,6),
   TRIAL          number(4),
   GRIND          varchar2(12),
   RUN_AVERAGE    number(10,6),
   COMMENTS       varchar2(500)
,  ID             number(22)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
,   LANE
,   DAY
,   TIME
,   OPERATOR
,   TEST_TIRE
,   LEADING_OBSI
,   TRAILING_OBSI
,   TRIAL
,   GRIND
,   RUN_AVERAGE
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_OBSI_SUMMARY;
--
--
truncate table DISTRESS_OBSI_SUMMARY;
--
INSERT INTO DISTRESS_OBSI_SUMMARY
SELECT
CELL
,   LANE
,   DAY
,   TIME
,   OPERATOR
,   TEST_TIRE
,   LEADING_OBSI
,   TRAILING_OBSI
,   TRIAL
,   GRIND
,   RUN_AVERAGE
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
-- DISTRESS_PATHWAYS_VALUES
CREATE TABLE tempd
(
   CELL           number(4),
   LANE           varchar2(16),
   LENGTH_FT      number(6,2) NOT NULL,
   TEST_LENGTH_FT number(6,2) NOT NULL,
   DAY            timestamp NOT NULL,
   TIME           number(4,0),
   RUN            number(4,0),
   IRI_LWP        number(6,2),
   IRI_RWP        number(6,2),
   IRI_AVG        number(6,2),
   HRI            number(6,2),
   RUT_LWP        number(6,2),
   RUT_CEN        number(6,2),
   RUT_RWP        number(6,2),
   RUT_AVG        number(6,2),
   FAULT_AVG      number(6,2),
   FAULT_NUM      number(6,2),
   TEXTURE_AVG    number(6,2),
   PSR_LWP        number(6,2),
   SR_PM          number(6,2),
   DATE_UPDATED   timestamp,
   COMMENTS       varchar2(250),
   RQI            number(3,1)
,  ID             number(22)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
,   LANE
,   LENGTH_FT
,   TEST_LENGTH_FT
,   DAY
,   TIME
,   RUN
,   IRI_LWP
,   IRI_RWP
,   IRI_AVG
,   HRI
,   RUT_LWP
,   RUT_CEN
,   RUT_RWP
,   RUT_AVG
,   FAULT_AVG
,   FAULT_NUM
,   TEXTURE_AVG
,   PSR_LWP
,   SR_PM
,   DATE_UPDATED
,   COMMENTS
,   RQI
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_PATHWAYS_VALUES;
--
--
truncate table DISTRESS_PATHWAYS_VALUES;
--
INSERT INTO DISTRESS_PATHWAYS_VALUES
SELECT
CELL
,   LANE
,   LENGTH_FT
,   TEST_LENGTH_FT
,   DAY
,   TIME
,   RUN
,   IRI_LWP
,   IRI_RWP
,   IRI_AVG
,   HRI
,   RUT_LWP
,   RUT_CEN
,   RUT_RWP
,   RUT_AVG
,   FAULT_AVG
,   FAULT_NUM
,   TEXTURE_AVG
,   PSR_LWP
,   SR_PM
,   DATE_UPDATED
,   COMMENTS
,   RQI
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_PAVETECH_VALUES
CREATE TABLE tempd
(
   DAY            timestamp NOT NULL,
   CELL           number(2) NOT NULL,
   LANE           varchar2(20) NOT NULL,
   IRI_VALUE      number(6,2),
   RUTTING_VALUE  number(6,2),
   TEXTURE_VALUE  number(6,2),
   FAULTING_VALUE number(6,2)
,  ID             number(22)
)
;
--
--
INSERT INTO tempd
SELECT
DAY
,   CELL
,   LANE
,   IRI_VALUE
,   RUTTING_VALUE
,   TEXTURE_VALUE
,   FAULTING_VALUE
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_PAVETECH_VALUES;
--
truncate table DISTRESS_PAVETECH_VALUES;
--
INSERT INTO DISTRESS_PAVETECH_VALUES
SELECT
DAY
,   CELL
,   LANE
,   IRI_VALUE
,   RUTTING_VALUE
,   TEXTURE_VALUE
,   FAULTING_VALUE
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_PCC_FAULTS
CREATE TABLE tempd
(
   CELL                   number(3)    NOT NULL,
   CONSTRUCTION_NUMBER    number(2)    NOT NULL,
   LANE                   varchar2(20) NOT NULL,
   SAMPLE_DATE            timestamp    NOT NULL,
   JOINT_NUMBER           number(6)    NOT NULL,
   SAMPLE_TIME            number(4),
   FAULT_DEPTH            number(6,2),
   OFFSET_FROM_FOGLINE    number(6,2),
   COMMENTS               varchar2(250),
   TEMP                   number(4),
   DATE_UPDATED           timestamp,
   WEATHER                varchar2(36),
   OFFSET_FROM_CENTERLINE number(10,2)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
,   CONSTRUCTION_NUMBER
,   LANE
,   SAMPLE_DATE
,   JOINT_NUMBER
,   SAMPLE_TIME
,   FAULT_DEPTH
,   OFFSET_FROM_FOGLINE
,   COMMENTS
,   TEMP
,   DATE_UPDATED
,   WEATHER
,   OFFSET_FROM_CENTERLINE
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_PCC_FAULTS;
--
--
truncate table DISTRESS_PCC_FAULTS;
--
INSERT INTO DISTRESS_PCC_FAULTS
SELECT
CELL
,   CONSTRUCTION_NUMBER
,   LANE
,   SAMPLE_DATE
,   SAMPLE_TIME
,   JOINT_NUMBER
,   FAULT_DEPTH
,   OFFSET_FROM_FOGLINE
,   COMMENTS
,   TEMP
,   DATE_UPDATED
,   WEATHER
,   OFFSET_FROM_CENTERLINE
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_PROCEQ
CREATE TABLE tempd
(
   CELL                number(3) NOT NULL,
   CONSTRUCTION        number(3) NOT NULL,
   DAY                 timestamp NOT NULL,
   LANE                varchar2(50) NOT NULL,
   PANEL_NO            number(3,0) NOT NULL,
   TIME                varchar2(10),
   STATION             number(10,2),
   OFFSET              number(4,1),
   RESISTIVITY_KOHM_CM number(3,0),
   PA_MBAR             number(6,2),
   TMAX_S              number(3,0),
   DELTA_PMAX_MBAR     number(3,1),
   KT_10E_16_M2        number(6,3),
   L_MM                number(3,1),
   CONCRETE_QUALITY    varchar2(20),
   COMMENTS            varchar2(100)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
CELL
,   CONSTRUCTION
,   DAY
,   LANE
,   PANEL_NO
,   TIME
,   STATION
,   OFFSET
,   RESISTIVITY_KOHM_CM
,   PA_MBAR
,   TMAX_S
,   DELTA_PMAX_MBAR
,   KT_10E_16_M2
,   L_MM
,   CONCRETE_QUALITY
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_PROCEQ;
--
--
truncate table DISTRESS_PROCEQ;
--
INSERT INTO DISTRESS_PROCEQ
SELECT
CELL
,   CONSTRUCTION
,   DAY
,   TIME
,   STATION
,   OFFSET
,   LANE
,   PANEL_NO
,   RESISTIVITY_KOHM_CM
,   PA_MBAR
,   TMAX_S
,   DELTA_PMAX_MBAR
,   KT_10E_16_M2
,   L_MM
,   CONCRETE_QUALITY
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_RIDE_LISA
CREATE TABLE tempd
(
   CELL              number(4) NOT NULL,
   DAY               timestamp NOT NULL,
   LANE              varchar2(30) NOT NULL,
   WHEELPATH         varchar2(10) NOT NULL,
   COLLECTION_METHOD varchar2(30) NOT NULL,
   RUN_NO            number(2) NOT NULL,
   OPERATOR1         varchar2(50),
   OPERATOR2         varchar2(50),
   IRI_RUN_M_KM      number(5,2),
   COMMENTS          varchar2(50),
   ROLINE_IRI_M_KM   number(6,2)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   DAY
,   LANE
,   WHEELPATH
,   COLLECTION_METHOD
,   RUN_NO
,   OPERATOR1
,   OPERATOR2
,   IRI_RUN_M_KM
,   COMMENTS
,   ROLINE_IRI_M_KM
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_RIDE_LISA;
--
--
truncate table DISTRESS_RIDE_LISA;
--
INSERT INTO DISTRESS_RIDE_LISA
SELECT
   CELL
,   DAY
,   LANE
,   WHEELPATH
,   OPERATOR1
,   OPERATOR2
,   COLLECTION_METHOD
,   RUN_NO
,   IRI_RUN_M_KM
,   COMMENTS
,   ROLINE_IRI_M_KM
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_RUTTING_DIPSTICK
CREATE TABLE tempd
(
   DAY                 timestamp NOT NULL,
   CELL                number(2) NOT NULL,
   CONSTRUCTION_NUMBER number(2) NOT NULL,
   STATION             number(8,2) NOT NULL,
   LANE                varchar2(15) NOT NULL,
   LEFT_WP_DEPTH       number(4,3),
   RIGHT_WP_DEPTH      number(4,3)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   DAY
,    CELL
,    CONSTRUCTION_NUMBER
,    STATION
,    LANE
,    LEFT_WP_DEPTH
,    RIGHT_WP_DEPTH
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_RUTTING_DIPSTICK;
--
truncate table DISTRESS_RUTTING_DIPSTICK;
--
INSERT INTO DISTRESS_RUTTING_DIPSTICK
SELECT
   DAY
,    CELL
,    CONSTRUCTION_NUMBER
,    STATION
,    LANE
,    LEFT_WP_DEPTH
,    RIGHT_WP_DEPTH
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_RUTTING_STRAIGHT_EDGE
CREATE TABLE tempd
(
   DAY                 timestamp    NOT NULL,
   CELL                number(2)    NOT NULL,
   CONSTRUCTION_NUMBER number(2)    NOT NULL,
   STATION             number(10,2) NOT NULL,
   LANE                varchar2(15) NOT NULL,
   LEFT_WP_DEPTH       number(6,5),
   RIGHT_WP_DEPTH      number(6,5),
   COMMENTS            varchar2(255),
   DATE_UPDATED        timestamp
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   DAY
,  CELL
,  CONSTRUCTION_NUMBER
,  STATION
,  LANE
,  LEFT_WP_DEPTH
,  RIGHT_WP_DEPTH
,  COMMENTS
,  DATE_UPDATED
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_RUTTING_STRAIGHT_EDGE;
--
--
truncate table DISTRESS_RUTTING_STRAIGHT_EDGE;
--
INSERT INTO DISTRESS_RUTTING_STRAIGHT_EDGE
SELECT
DAY
,  CELL
,  CONSTRUCTION_NUMBER
,  STATION
,  LANE
,  LEFT_WP_DEPTH
,  RIGHT_WP_DEPTH
,  COMMENTS
,  DATE_UPDATED
,   id
FROM tempd;
--
drop table tempd purge;
--
--DISTRESS_SAND_PATCH
CREATE TABLE tempd
(
   CELL        number(3) NOT NULL,
   LANE     varchar2(30) NOT NULL,
   DAY         timestamp NOT NULL,
   TIME     varchar2(12) NOT NULL,
   OFFSET      number(3) NOT NULL,
   STATION      number(8,1),
   MEASURED_BY varchar2(80),
   METHOD      varchar2(10),
   FIELD_ID    varchar2(12),
   X1_MM       number(5,1),
   X2_MM       number(5,1),
   X3_MM       number(5,1),
   X4_MM       number(5,1),
   X_AVG_MM    number(5,1),
   VOLUME_MM3  number(6,0),
   TEXTURE_MM  number(8,3),
   COMMENTS   varchar2(200)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   LANE
,   DAY
,   TIME
,   OFFSET
,   STATION
,   MEASURED_BY
,   METHOD
,   FIELD_ID
,   X1_MM
,   X2_MM
,   X3_MM
,   X4_MM
,   X_AVG_MM
,   VOLUME_MM3
,   TEXTURE_MM
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_SAND_PATCH;
--
--
truncate table DISTRESS_SAND_PATCH;
--
INSERT INTO DISTRESS_SAND_PATCH
SELECT
   CELL
,   LANE
,   DAY
,   TIME
,   STATION
,   OFFSET
,   MEASURED_BY
,   METHOD
,   FIELD_ID
,   X1_MM
,   X2_MM
,   X3_MM
,   X4_MM
,   X_AVG_MM
,   VOLUME_MM3
,   TEXTURE_MM
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
-- DISTRESS_SCHMIDT_HAMMER
CREATE TABLE tempd
(
   CELL              number(3),
   DAY               timestamp,
   TIME              varchar2(10),
   CONSTRUCTION      number(6),
   STATION           number(10,1),
   OFFSET            number(4,1),
   PANEL_NO          number(3),
   LANE              varchar2(60),
   REBOUND_NO        number(4,1),
   COMP_STRENGTH_PSI number(6),
   STDEV_PSI         number(4,1),
   COMMENTS          varchar2(100)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   DAY
,   TIME
,   CONSTRUCTION
,   STATION
,   OFFSET
,   PANEL_NO
,   LANE
,   REBOUND_NO
,   COMP_STRENGTH_PSI
,   STDEV_PSI
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_SCHMIDT_HAMMER;
--
--
truncate table DISTRESS_SCHMIDT_HAMMER;
--
INSERT INTO DISTRESS_SCHMIDT_HAMMER
SELECT
   CELL
,   DAY
,   TIME
,   CONSTRUCTION
,   STATION
,   OFFSET
,   PANEL_NO
,   LANE
,   REBOUND_NO
,   COMP_STRENGTH_PSI
,   STDEV_PSI
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;
--
--DISTRESS_SOUND_ABSORPTION
CREATE TABLE tempd
(
   CELL              number(3) NOT NULL,
   STATION           varchar2(20) NOT NULL,
   LANE              varchar2(30) NOT NULL,
   WHEELPATH         varchar2(3) NOT NULL,
   DAY               timestamp NOT NULL,
   TRIAL             varchar2(12),
   OPERATOR          varchar2(30),
   TIME              varchar2(20),
   TEMP_C            number(5,2),
   ATMOS_PRESSURE    number(10,2),
   CONDITION         varchar2(20),
   FREQUENCY         number(6,2),
   SOUND_ABSORPTION  number(6,2),
   COMMENTS          varchar2(100),
   OFFSET            number(4,1)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   STATION
,   LANE
,   WHEELPATH
,   DAY
,   TRIAL
,   OPERATOR
,   TIME
,   TEMP_C
,   ATMOS_PRESSURE
,   CONDITION
,   FREQUENCY
,   SOUND_ABSORPTION
,   COMMENTS
,   OFFSET
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_SOUND_ABSORPTION;
--
--
truncate table DISTRESS_SOUND_ABSORPTION;
--
INSERT INTO DISTRESS_SOUND_ABSORPTION
SELECT
   CELL
,   STATION
,   LANE
,   WHEELPATH
,   TRIAL
,   OPERATOR
,   DAY
,   TIME
,   TEMP_C
,   ATMOS_PRESSURE
,   CONDITION
,   FREQUENCY
,   SOUND_ABSORPTION
,   COMMENTS
,   OFFSET
,   id
FROM tempd;
--
drop table tempd purge;
--
--DISTRESS_WATER_PERMEABILITY
CREATE TABLE tempd
(
   CELL                   number(3) NOT NULL,
   DAY                    timestamp NOT NULL,
   LANE                   varchar2(50) NOT NULL,
   TIME                   varchar2(10),
   STATION                number(10,2),
   OFFSET                 number(4,1),
   FLOW_TIME_S            number(6,2),
   INITIAL_HEAD_CM        number(6,2),
   FINAL_HEAD_CM          number(6,2),
   PAVEMENT_THICKNESS_CM  number(4,1),
   CROSS_SECTION_AREA_CM2 number(6,2),
   COMMENTS               varchar2( 100)
,  ID                           number(22)
)
;
--
--
INSERT INTO tempd
SELECT
   CELL
,   DAY
,   LANE
,   TIME
,   STATION
,   OFFSET
,   FLOW_TIME_S
,   INITIAL_HEAD_CM
,   FINAL_HEAD_CM
,   PAVEMENT_THICKNESS_CM
,   CROSS_SECTION_AREA_CM2
,   COMMENTS
,   DISTRESS_SEQ.NEXTVAL
from DISTRESS_WATER_PERMEABILITY;
--
--
truncate table DISTRESS_WATER_PERMEABILITY;
--
INSERT INTO DISTRESS_WATER_PERMEABILITY
SELECT
   CELL
,   DAY
,   TIME
,   STATION
,   OFFSET
,   LANE
,   FLOW_TIME_S
,   INITIAL_HEAD_CM
,   FINAL_HEAD_CM
,   PAVEMENT_THICKNESS_CM
,   CROSS_SECTION_AREA_CM2
,   COMMENTS
,   id
FROM tempd;
--
drop table tempd purge;


SELECT
CELL_NUMBER_OVER
,CELL_ID_OVER
,TO_DATE(CELL_OVER_FROM_DATE,'YYYYMMDD') CELL_OVER_FROM_DATE
,TO_DATE(CELL_OVER_TO_DATE,'YYYYMMDD') CELL_OVER_TO_DATE
,CELL_NUMBER_UNDER
,CELL_ID_UNDER
,TO_DATE(CELL_UNDER_FROM_DATE,'YYYYMMDD') CELL_UNDER_FROM_DATE
, TO_DATE((case
                when cell_under_to_date = cell_over_to_date
                then cell_over_from_date
                else cell_under_to_date
                end
                ),'YYYYMMDD')
 CELL_UNDER_TO_DATE
,FROM_STATION
,TO_STATION
FROM
(
select
(select cell_number from cell where id=under_cell_id) CELL_NUMBER_OVER
, under_cell_id CELL_ID_OVER
,TO_CHAR((select CONSTRUCTION_ENDED_DATE from cell where id=under_cell_id),'YYYYMMDD') CELL_OVER_FROM_DATE
,TO_CHAR(NVL((select DEMOLISHED_DATE from cell where id=under_cell_id),SYSDATE),'YYYYMMDD') CELL_OVER_TO_DATE
,(select cell_number from cell where id=OVER_CELL_ID) CELL_NUMBER_UNDER
, OVER_CELL_ID CELL_ID_UNDER
,TO_CHAR((select CONSTRUCTION_ENDED_DATE from cell where id=OVER_CELL_ID),'YYYYMMDD') CELL_UNDER_FROM_DATE
,TO_CHAR(NVL((select DEMOLISHED_DATE from cell where id=OVER_CELL_ID),SYSDATE),'YYYYMMDD') CELL_UNDER_TO_DATE
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
)
order by FROM_STATION
;

SELECT C.CELL_NUMBER, L.NAME, AD.* FROM  DISTRESS_ALPS_RESULTS_RUT AD JOIN DISTRESS D ON D.ID=AD.ID join lane L ON D.LANE_ID=L.ID JOIN CELL C ON L.CELL_ID=C.ID
WHERE C.CELL_NUMBER=1


SELECT
--COUNT(*)
C.*, DV.ID DISTRESS_ID, DV.LANE, DV.DAY,L.ID LANE_ID
FROM CELLS C JOIN LANE L ON L.CELL_ID=C.ID
-- JOIN VIA CELL,LANE and DAY
JOIN DISTRESS_AC DV ON DV.CELL=C.CELL AND DV.LANE=L.NAME AND DV.DAY BETWEEN C.FROM_DATE AND C.TO_DATE
MINUS
SELECT
--COUNT(*)
C.*, DV.ID DISTRESS_ID, DV.LANE, DV.DAY,L.ID LANE_ID
-- JOIN VIA IDs
FROM CELLS C JOIN LANE L ON L.CELL_ID=C.ID
JOIN DISTRESS D ON L.ID=D.LANE_ID
JOIN DISTRESS_AC DV ON D.ID=DV.ID
CELL ID  FROM_DATE   TO_DATE    DISTRESS_ID LANE    DAY         LANE_ID
28   773 1993-08-15  1999-08-15 869         Inside  1999-08-15  786
28   773 1993-08-15  1999-08-15 892         Outside 1999-08-15  782
31   863 1993-08-15  2004-08-19 1,169       Inside  2004-08-19  875
31   863 1993-08-15  2004-08-19 1,194       Outside 2004-08-19  870


SELECT
--COUNT(*)
C.*, DV.ID DISTRESS_ID, DV.LANE, DV.DAY,L.ID LANE_ID
FROM CELLS C JOIN LANE L ON L.CELL_ID=C.ID
-- JOIN VIA CELL,LANE and DAY
JOIN DISTRESS_AC DV ON DV.CELL=C.CELL AND DV.LANE=L.NAME AND DV.DAY BETWEEN C.FROM_DATE AND C.TO_DATE
--WHERE DV.ID IS NOT NULL AND L.ID IS NOT NULL
WHERE DV.ID IN (869,892,1169,1194)
CELL ID      FROM_DATE   TO_DATE    DISTRESS_ID LANE    DAY         LANE_ID
28   773     1993-08-15  1999-08-15 869         Inside  1999-08-15  786
28   16,292  1999-08-15  2006-06-19 869         Inside  1999-08-15  16,561
28   773     1993-08-15  1999-08-15 892         Outside 1999-08-15  782
28   16,292  1999-08-15  2006-06-19 892         Outside 1999-08-15  16,560
31   16,253  2004-08-19  2010-03-30 1,169       Inside  2004-08-19  16,256
31   863     1993-08-15  2004-08-19 1,169       Inside  2004-08-19  875
31   16,253  2004-08-19  2010-03-30 1,194       Outside 2004-08-19  16,255
31   863     1993-08-15  2004-08-19 1,194       Outside 2004-08-19  870


SELECT
--COUNT(*)
C.*, DV.ID DISTRESS_ID, DV.LANE, DV.DAY,L.ID LANE_ID
-- JOIN VIA IDs
FROM CELLS C JOIN LANE L ON L.CELL_ID=C.ID
JOIN DISTRESS D ON L.ID=D.LANE_ID
JOIN DISTRESS_AC DV ON D.ID=DV.ID
WHERE DV.ID IN (869,892,1169,1194)
CELL ID      FROM_DATE   TO_DATE    DISTRESS_ID LANE    DAY         LANE_ID
31   16,253  2004-08-19  2010-03-30 1,194       Outside 2004-08-19  16,255
31   16,253  2004-08-19  2010-03-30 1,169       Inside  2004-08-19  16,256
28   16,292  1999-08-15  2006-06-19 892         Outside 1999-08-15  16,560
28   16,292  1999-08-15  2006-06-19 869         Inside  1999-08-15  16,561

CREATE OR REPLACE VIEW ORPHAN_DISTRESSES_COUNTS AS
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_AC                   ' table_name FROM MNR.DISTRESS_AC                    where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_AGG_SURVEY_SEMI      ' table_name FROM MNR.DISTRESS_AGG_SURVEY_SEMI       where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_ALPS_RESULTS_RUT     ' table_name FROM MNR.DISTRESS_ALPS_RESULTS_RUT      where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_CIRCULR_TEXTR_METER  ' table_name FROM MNR.DISTRESS_CIRCULR_TEXTR_METER   where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_CUPPING              ' table_name FROM MNR.DISTRESS_CUPPING               where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_FRICTION_DFT         ' table_name FROM MNR.DISTRESS_FRICTION_DFT          where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_FRICTION_TRAILER     ' table_name FROM MNR.DISTRESS_FRICTION_TRAILER      where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_JPCC                 ' table_name FROM MNR.DISTRESS_JPCC                  where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_LANE_SHOULDER_DROPOFF' table_name FROM MNR.DISTRESS_LANE_SHOULDER_DROPOFF where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_NUCLEAR_DENSITY      ' table_name FROM MNR.DISTRESS_NUCLEAR_DENSITY       where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_OBSI_DATA            ' table_name FROM MNR.DISTRESS_OBSI_DATA             where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_OBSI_SUMMARY         ' table_name FROM MNR.DISTRESS_OBSI_SUMMARY          where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_PCC_FAULTS           ' table_name FROM MNR.DISTRESS_PCC_FAULTS            where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_PERMEABILITY_DIRECT  ' table_name FROM MNR.DISTRESS_PERMEABILITY_DIRECT   where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_RIDE_LISA            ' table_name FROM MNR.DISTRESS_RIDE_LISA             where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_RIDE_PATHWAYS        ' table_name FROM MNR.DISTRESS_RIDE_PATHWAYS         where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_RIDE_PAVETECH        ' table_name FROM MNR.DISTRESS_RIDE_PAVETECH         where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_RUTTING_DIPSTICK     ' table_name FROM MNR.DISTRESS_RUTTING_DIPSTICK      where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_RUTTING_STRAIGHT_EDGE' table_name FROM MNR.DISTRESS_RUTTING_STRAIGHT_EDGE where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_SAND_PATCH           ' table_name FROM MNR.DISTRESS_SAND_PATCH            where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_SCHMIDT_HAMMER       ' table_name FROM MNR.DISTRESS_SCHMIDT_HAMMER        where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_SOUND_ABSORPTION     ' table_name FROM MNR.DISTRESS_SOUND_ABSORPTION      where id is null UNION ALL
SELECT count(*) ORPHAN_ROW_COUNT,'DISTRESS_WATER_PERMEABILITY   ' table_name FROM MNR.DISTRESS_WATER_PERMEABILITY    where id is null


SELECT TABLE_NAME, COLUMN_ID "ORDINAL_POSITION", COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE
    FROM ALL_TAB_COLUMNS
WHERE OWNER='MNR' AND TABLE_NAME LIKE  'DISTRESS_%' AND COLUMN_ID < 6
ORDER BY TABLE_NAME,COLUMN_ID

SELECT COUNT(*),CELL, LANE, DAY, SURVEYOR1, FATIGUE_A_L     FROM MNR.DISTRESS_AC                     GROUP BY CELL, LANE, DAY, SURVEYOR1, FATIGUE_A_L    HAVING COUNT(*) > 1;
SELECT COUNT(*),DAY, CELL, LANE, RUT_START, RUT_END         FROM MNR.DISTRESS_AGG_SURVEY_SEMI        GROUP BY DAY, CELL, LANE, RUT_START, RUT_END        HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, STATION, LANE, MEASUREMENT_TYPE, DAY  FROM MNR.DISTRESS_ALPS_DATA              GROUP BY CELL, STATION, LANE, MEASUREMENT_TYPE, DAY HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, STATION, LANE, MEASUREMENT_TYPE, DAY  FROM MNR.DISTRESS_ALPS_RESULTS_RUT       GROUP BY CELL, STATION, LANE, MEASUREMENT_TYPE, DAY HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, WHEEL_PATH, DAY, TIME           FROM MNR.DISTRESS_CIRCULR_TEXTR_METER    GROUP BY CELL, LANE, WHEEL_PATH, DAY, TIME          HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, DAY, STATION, WHEEL_PATH, CRACK_NO    FROM MNR.DISTRESS_CUPPING                GROUP BY CELL, DAY, STATION, WHEEL_PATH, CRACK_NO   HAVING COUNT(*) > 1;
SELECT COUNT(*),DAY, CELL, LANE, TABLE_CODE                 FROM MNR.DISTRESS_DATES_ML               GROUP BY DAY, CELL, LANE, TABLE_CODE                HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, AC, RUTTING, PATHWAYS           FROM MNR.DISTRESS_DECODE_DATES_ML        GROUP BY CELL, LANE, AC, RUTTING, PATHWAYS          HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, STATION, OFFSET_FT         FROM MNR.DISTRESS_FRICTION_DFT           GROUP BY CELL, LANE, DAY, STATION, OFFSET_FT        HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, WHEEL_PATH, DAY, TIME           FROM MNR.DISTRESS_FRICTION_GRIP          GROUP BY CELL, LANE, WHEEL_PATH, DAY, TIME          HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, TIME, FRICTION_NUMBER      FROM MNR.DISTRESS_FRICTION_TRAILER       GROUP BY CELL, LANE, DAY, TIME, FRICTION_NUMBER     HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, SURVEYOR1, SURVEYOR2       FROM MNR.DISTRESS_JPCC                   GROUP BY CELL, LANE, DAY, SURVEYOR1, SURVEYOR2      HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, TIME, OPERATOR             FROM MNR.DISTRESS_LANE_SHOULDER_DROPOFF  GROUP BY CELL, LANE, DAY, TIME, OPERATOR            HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, DAY, STATION, OFFSET_FT, OPERATOR     FROM MNR.DISTRESS_LIGHTWEIGHT_DEFLECT    GROUP BY CELL, DAY, STATION, OFFSET_FT, OPERATOR    HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, DAY, STATION, OFFSET_FT, LANE         FROM MNR.DISTRESS_NUCLEAR_DENSITY        GROUP BY CELL, DAY, STATION, OFFSET_FT, LANE        HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, TIME, OPERATOR             FROM MNR.DISTRESS_OBSI_DATA              GROUP BY CELL, LANE, DAY, TIME, OPERATOR            HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, TIME, OPERATOR             FROM MNR.DISTRESS_OBSI_SUMMARY           GROUP BY CELL, LANE, DAY, TIME, OPERATOR            HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, SAMPLE_TIME, JOINT_NUMBER  FROM MNR.DISTRESS_PCC_FAULTS             GROUP BY CELL, LANE, DAY, SAMPLE_TIME, JOINT_NUMBER HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, DAY, TIME, STATION, OFFSET_FT         FROM MNR.DISTRESS_PERMEABILITY_DIRECT    GROUP BY CELL, DAY, TIME, STATION, OFFSET_FT        HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, DAY, LANE, WHEELPATH, OPERATOR1       FROM MNR.DISTRESS_RIDE_LISA              GROUP BY CELL, DAY, LANE, WHEELPATH, OPERATOR1      HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, TEST_LENGTH_FT, DAY, TIME       FROM MNR.DISTRESS_RIDE_PATHWAYS          GROUP BY CELL, LANE, TEST_LENGTH_FT, DAY, TIME      HAVING COUNT(*) > 1;
SELECT COUNT(*),DAY, CELL, LANE, IRI_M_PER_KM, RUT_IN       FROM MNR.DISTRESS_RIDE_PAVETECH          GROUP BY DAY, CELL, LANE, IRI_M_PER_KM, RUT_IN      HAVING COUNT(*) > 1;
SELECT COUNT(*),DAY, CELL, STATION, LANE, LEFT_WP_DEPTH_IN  FROM MNR.DISTRESS_RUTTING_DIPSTICK       GROUP BY DAY, CELL, STATION, LANE, LEFT_WP_DEPTH_IN HAVING COUNT(*) > 1;
SELECT COUNT(*),DAY, CELL, STATION, LANE, LEFT_WP_DEPTH_IN  FROM MNR.DISTRESS_RUTTING_STRAIGHT_EDGE  GROUP BY DAY, CELL, STATION, LANE, LEFT_WP_DEPTH_IN HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, LANE, DAY, TIME, STATION              FROM MNR.DISTRESS_SAND_PATCH             GROUP BY CELL, LANE, DAY, TIME, STATION             HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, DAY, STATION, OFFSET_FT, PANEL_NO     FROM MNR.DISTRESS_SCHMIDT_HAMMER         GROUP BY CELL, DAY, STATION, OFFSET_FT, PANEL_NO    HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, STATION, LANE, WHEELPATH, TRIAL       FROM MNR.DISTRESS_SOUND_ABSORPTION       GROUP BY CELL, STATION, LANE, WHEELPATH, TRIAL      HAVING COUNT(*) > 1;
SELECT COUNT(*),CELL, DAY, TIME, STATION, OFFSET_FT         FROM MNR.DISTRESS_WATER_PERMEABILITY     GROUP BY CELL, DAY, TIME, STATION, OFFSET_FT        HAVING COUNT(*) > 1;


select * from (
select distinct id,CELL,lane, 'MNR.DISTRESS_AC                   ' xtable from MNR.DISTRESS_AC                       union all
select distinct id,CELL,lane, 'MNR.DISTRESS_AGG_SURVEY_SEMI      ' xtable from MNR.DISTRESS_AGG_SURVEY_SEMI          union all
select distinct id,CELL,lane, 'MNR.DISTRESS_ALPS_DATA            ' xtable from MNR.DISTRESS_ALPS_DATA                union all
select distinct id,CELL,lane, 'MNR.DISTRESS_ALPS_RESULTS_RUT     ' xtable from MNR.DISTRESS_ALPS_RESULTS_RUT         union all
select distinct id,CELL,lane, 'MNR.DISTRESS_CIRCULR_TEXTR_METER  ' xtable from MNR.DISTRESS_CIRCULR_TEXTR_METER      union all
select distinct id,CELL,lane, 'MNR.DISTRESS_FRICTION_DFT         ' xtable from MNR.DISTRESS_FRICTION_DFT             union all
select distinct id,CELL,lane, 'MNR.DISTRESS_FRICTION_TRAILER     ' xtable from MNR.DISTRESS_FRICTION_TRAILER         union all
select distinct id,CELL,lane, 'MNR.DISTRESS_FRICTION_GRIP        ' xtable from MNR.DISTRESS_FRICTION_GRIP            union all
select distinct id,CELL,lane, 'MNR.DISTRESS_JPCC                 ' xtable from MNR.DISTRESS_JPCC                     union all
select distinct id,CELL,lane, 'MNR.DISTRESS_LANE_SHOULDER_DROPOFF' xtable from MNR.DISTRESS_LANE_SHOULDER_DROPOFF    union all
select distinct id,CELL,lane, 'MNR.DISTRESS_NUCLEAR_DENSITY      ' xtable from MNR.DISTRESS_NUCLEAR_DENSITY          union all
select distinct id,CELL,lane, 'MNR.DISTRESS_OBSI_DATA            ' xtable from MNR.DISTRESS_OBSI_DATA                union all
select distinct id,CELL,lane, 'MNR.DISTRESS_OBSI_SUMMARY         ' xtable from MNR.DISTRESS_OBSI_SUMMARY             union all
select distinct id,CELL,lane, 'MNR.DISTRESS_RIDE_PATHWAYS        ' xtable from MNR.DISTRESS_RIDE_PATHWAYS            union all
select distinct id,CELL,lane, 'MNR.DISTRESS_RIDE_PAVETECH        ' xtable from MNR.DISTRESS_RIDE_PAVETECH            union all
select distinct id,CELL,lane, 'MNR.DISTRESS_PCC_FAULTS           ' xtable from MNR.DISTRESS_PCC_FAULTS               union all
select distinct id,CELL,lane, 'MNR.DISTRESS_RIDE_LISA            ' xtable from MNR.DISTRESS_RIDE_LISA                union all
select distinct id,CELL,lane, 'MNR.DISTRESS_RUTTING_DIPSTICK     ' xtable from MNR.DISTRESS_RUTTING_DIPSTICK         union all
select distinct id,CELL,lane, 'MNR.DISTRESS_RUTTING_STRAIGHT_EDGE' xtable from MNR.DISTRESS_RUTTING_STRAIGHT_EDGE    union all
select distinct id,CELL,lane, 'MNR.DISTRESS_SAND_PATCH           ' xtable from MNR.DISTRESS_SAND_PATCH               union all
select distinct id,CELL,lane, 'MNR.DISTRESS_SOUND_ABSORPTION     ' xtable from MNR.DISTRESS_SOUND_ABSORPTION         union all
select distinct id,CELL,lane, 'MNR.DISTRESS_WATER_PERMEABILITY   ' xtable from MNR.DISTRESS_WATER_PERMEABILITY
)
where 
