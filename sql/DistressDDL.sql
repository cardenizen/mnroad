CREATE TABLE DISTRESS_AC
(
   CELL                  number(30) NOT NULL,
   LANE                  varchar2(15) NOT NULL,
   CONSTRUCTION_NO       number(2) NOT NULL,
   SURVEY_DATE           timestamp NOT NULL,
   SURVEYOR1             varchar2(36),
   FATIGUE_A_L           number(5,1),
   FATIGUE_A_M           number(5,1),
   FATIGUE_A_H           number(5,1),
   BLOCK_A_L             number(5,1),
   BLOCK_A_M             number(5,1),
   BLOCK_A_H             number(5,1),
   EDGE_L_L              number(4,1),
   EDGE_L_M              number(4,1),
   EDGE_L_H              number(4,1),
   LONG_WP_L_L           number(5,1),
   LONG_WP_L_M           number(4,1),
   LONG_WP_L_H           number(4,1),
   LONG_WP_SEAL_L_L      number(4,1),
   LONG_WP_SEAL_L_M      number(4,1),
   LONG_WP_SEAL_L_H      number(4,1),
   LONG_NWP_L_L          number(5,1),
   LONG_NWP_L_M          number(4,1),
   LONG_NWP_L_H          number(4,1),
   LONG_NWP_SEAL_L_L     number(4,1),
   LONG_NWP_SEAL_L_M     number(4,1),
   LONG_NWP_SEAL_L_H     number(4,1),
   TRANSVERSE_NO_L       number(22),
   TRANSVERSE_L_L        number(22),
   TRANSVERSE_NO_M       number(22),
   TRANSVERSE_L_M        number(22),
   TRANSVERSE_NO_H       number(22),
   TRANSVERSE_L_H        number(22),
   TRANSVERSE_SEAL_NO_L  number(22),
   TRANSVERSE_SEAL_L_L   number(22),
   TRANSVERSE_SEAL_NO_M  number(22),
   TRANSVERSE_SEAL_L_M   number(22),
   TRANSVERSE_SEAL_NO_H  number(22),
   TRANSVERSE_SEAL_L_H   number(22),
   PATCH_NO_L            number(3,0),
   PATCH_A_L             number(6,1),
   PATCH_NO_M            number(3,0),
   PATCH_A_M             number(4,1),
   PATCH_NO_H            number(3,0),
   PATCH_A_H             number(4,1),
   POTHOLES_NO_L         number(3,0),
   POTHOLES_A_L          number(4,1),
   POTHOLES_NO_M         number(3,0),
   POTHOLES_A_M          number(4,1),
   POTHOLES_NO_H         number(3,0),
   POTHOLES_A_H          number(4,1),
   SHOVING_NO            number(3,0),
   SHOVING_A             number(4,1),
   BLEEDING_A_L          number(4,1),
   BLEEDING_A_M          number(4,1),
   BLEEDING_A_H          number(4,1),
   POLISH_AGG_A          number(4,1),
   RAVELING_A_L          number(5,1),
   RAVELING_A_M          number(5,1),
   RAVELING_A_H          number(5,1),
   PUMPING_NO            number(3,0),
   PUMPING_L             number(4,1),
   CONST_SHLD_JNT_SEAL_L number(3,0),
   CONST_SHLD_JNT_SEAL_M number(3,0),
   CONST_SHLD_JNT_SEAL_H number(3,0),
   CONST_SHLD_JNT_L      number(3,0),
   CONST_SHLD_JNT_M      number(3,0),
   CONST_SHLD_JNT_H      number(3,0),
   CONST_CL_JNT_SEAL_L   number(3,0),
   CONST_CL_JNT_SEAL_M   number(3,0),
   CONST_CL_JNT_SEAL_H   number(3,0),
   CONST_CL_JNT_L        number(3,0),
   CONST_CL_JNT_M        number(3,0),
   CONST_CL_JNT_H        number(3,0),
   COMMENTS              varchar2(200),
   DATE_UPDATED          timestamp,
   TOPDOWN_L_L           number(5,1),
   TOPDOWN_L_M           number(5,1),
   TOPDOWN_L_H           number(5,1)
)
;
--
CREATE TABLE DISTRESS_AGG_SURVEY_SEMI
(
   DAY                 timestamp NOT NULL,
   CELL                number(2,0) NOT NULL,
   LANE                varchar2(20) NOT NULL,
   RUT_START           number(31),
   RUT_END             number(31),
   WASHBOARD_START     number(31),
   WASHBOARD_END       number(31),
   DUST_START          number(31),
   DUST_END            number(31),
   DISTRESS_COMPARISON number(20),
   DUST_COMPARISON     number(20),
   AVG_SPEED_MPH       number(20),
   SURVEY_COMMENT      varchar2(225)
)
;
--
CREATE TABLE DISTRESS_ALPS_DATA
(
   CELL             number(2,0),
   STATION          number(8,1),
   LANE             varchar2(15),
   MEASUREMENT_TYPE varchar2(30),
   DAY              timestamp,
   HOUR_MIN_SEC     varchar2(10),
   RECORD_NO        number(22),
   Z_DATA_IN        number(10,4),
   Y_STA_FT         number(14,4),
   X_OFFSET_FT      number(14,4),
   COMMENTS         varchar2(60),
   DATE_UPDATED     timestamp
)
;
CREATE INDEX DISTRESSALPSDATA_LANE_BIDX ON DISTRESS_ALPS_DATA(LANE)         ;
CREATE INDEX DISTRESSALPSDATA_CELL_BIDX ON DISTRESS_ALPS_DATA(CELL)         ;
CREATE INDEX DISTRESSALPS_STATIOIN_BIDX ON DISTRESS_ALPS_DATA(STATION)      ;
CREATE INDEX DISTRESSALPSDATA_DAY_BIDX ON DISTRESS_ALPS_DATA(DAY)           ;
CREATE INDEX DISTRESSALPSDATA_RECORDNO_BIDX ON DISTRESS_ALPS_DATA(RECORD_NO);
--
CREATE TABLE DISTRESS_ALPS_RESULTS_RUT
(
   CELL             number(2,0),
   STATION          number(8,1),
   LANE             varchar2(15),
   MEASUREMENT_TYPE varchar2(30),
   DAY              timestamp,
   HOUR_MIN_SEC     varchar2(10),
   WHEELPATH        varchar2(10),
   RUT_IN           number(6,4),
   WATER_IN         number(6,4),
   RUT_VOL_CFPF     number(6,4),
   WATER_VOL_CFPF   number(6,4),
   X_RUT_FT         number(6,4),
   X_WATER_FT       number(6,4),
   DATE_UPDATED     timestamp,
   COMMENTS         varchar2(60)
)
;
--
CREATE TABLE DISTRESS_CIRCULR_TEXTR_METER
(
   CELL                  number(3)    NOT NULL,
   LANE                  varchar2(30) NOT NULL,
   WHEEL_PATH            varchar2(3)  NOT NULL,
   DAY                   timestamp    NOT NULL,
   TIME                  varchar2(20) NOT NULL,
   OPERATOR              varchar2(30),
   FIELD_ID              varchar2(12),
   STATION               number(10,2),
   OFFSET                number(3),
   TRIAL                 varchar2(12),
   MEAN_PROFILE_DEPTH_MM number(4,2),
   ROOT_MEAN_SQUARED_TD  number(4,2)
)
;
--
CREATE TABLE DISTRESS_CUPPING
(
   CELL                    number(2),
   DAY                     timestamp,
   STA                     number(9,2),
   LOCATION                varchar2(40),
   CRACK_NO                varchar2(10),
   CUPPING_X_DISTANCE_INCH number(5,2),
   CUPDEPTH_Y_INCH         number(6,3),
   COMMENTS                varchar2(30),
   WEATHER                 varchar2(30)
)
;
--
CREATE TABLE DISTRESS_DYNAMIC_FRICTION
(
   CELL              number(3),
   LANE              varchar2(30),
   DAY               timestamp,
   STATION           number(10,1),
   OFFSET            number(10,1),
   WHEELPATH         varchar2(6),
   MEASURED_BY       varchar2(20),
   RUN_NO            number(2),
   SPEED_KPH         number(6,1),
   COEFF_FRICTION    number(10,3),
   COMMENTS          varchar2(100),
   DATE_UPDATED      timestamp
)
;
--
CREATE TABLE DISTRESS_FIELD_MOISTURE
(
   CELL                 number(3) NOT NULL,
   DATE_SAMPLE          timestamp NOT NULL,
   DATE_TEST            timestamp NOT NULL,
   STATION              number(8,1),
   OFFSET               number(6,1),
   OPERATOR             varchar2(30),
   MOISTURE_TEST_NO     varchar2(5),
   MATERIAL_TESTED      varchar2(50),
   MOISTURE_CONTENT_PCT number(6,1),
   COMMENTS             varchar2(50)
)
;
--
CREATE TABLE DISTRESS_FRICTION_DATA
(
   CELL                number(3),
   CONSTRUCTION_NUMBER number(3) NOT NULL,
   LANE                varchar2(15),
   DAY                 timestamp,
   TIME                varchar2(5),
   FN                  number(3,1),
   PEAK                number(5,2),
   SPEED               number(3,1),
   AIR_TEMP            number(5,2),
   PVMT_TEMP           number(5,2),
   TIRE_TYPE           varchar2(10),
   EQUIPMENT           varchar2(50),
   STA                 varchar2(10),
   DATE_UPDATED        timestamp,
   LATITUDE            varchar2(14),
   LONGITUDE           varchar2(14),
   MINFN               number(2),
   MAXFN               number(2),
   SLIP                number(2),
   COMMENTS            varchar2(80)
)
;
--
CREATE TABLE DISTRESS_FRICTION_GRIP
(
   CELL            number(3) NOT NULL,
   LANE            varchar2(30) NOT NULL,
   WHEEL_PATH      varchar2(3) NOT NULL,
   DAY             timestamp NOT NULL,
   TIME            varchar2(10) NOT NULL,
   OPERATOR        varchar2(30),
   DISTANCE_FT     number(6,0),
   CHAINAGE        number(6,0),
   STATION         number(8,1),
   OFFSET          number(1,0),
   RUN             number(2,0),
   SPEED           number(5,1),
   LOAD            number(3,0),
   GN              number(3,2)
)
;
CREATE INDEX DISTFRICTGRIP_WP_BIDX ON DISTRESS_FRICTION_GRIP(WHEEL_PATH);
CREATE INDEX DISTFRICTGRIP_LANE_BIDX ON DISTRESS_FRICTION_GRIP(LANE)    ;
CREATE INDEX DISTFRICTGRIP_DAY_BIDX ON DISTRESS_FRICTION_GRIP(DAY)      ;
CREATE INDEX DISTFRICTGRIP_CELL_BIDX ON DISTRESS_FRICTION_GRIP(CELL)    ;
--
CREATE TABLE DISTRESS_JPCC
(
   CELL                  number(3) NOT NULL,
   LANE                  varchar2(20) NOT NULL,
   CONSTRUCTION_NO       number(2) NOT NULL,
   SURVEY_DATE           timestamp NOT NULL,
   SURVEYOR1             varchar2(24),
   SURVEYOR2             varchar2(24),
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
   OTHER                 varchar2(80),
   RECORD_STATUS         varchar2(1),
   MAP_CRACK_NO          number(3,0),
   MAP_CRACK_A           number(5,1),
   LONG_CRACK_NO_L       number(3,0),
   LONG_CRACK_NO_M       number(3,0),
   LONG_CRACK_NO_H       number(3,0),
   DATE_UPDATED          timestamp
)
;
CREATE UNIQUE INDEX DISTRESS_JPCC_INDEX ON DISTRESS_JPCC
(
  SURVEY_DATE,
  CELL,
  LANE
)
;
--
CREATE TABLE DISTRESS_LANE_SHOULDER_DROPOFF
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
)
;
--
CREATE TABLE DISTRESS_LIGHTWEIGHT_DEFLECT
(
   CELL               number(3) NOT NULL,
   DAY                timestamp NOT NULL,
   OPERATOR           varchar2(50) NOT NULL,
   STATION            number(10,1),
   OFFSET             number(6,1),
   FIELD_TEST_NUMBER  varchar2(5),
   MATERIAL           varchar2(50),
   S1_MM              number(6,3),
   S2_MM              number(6,3),
   S3_MM              number(6,3),
   S_MM               number(6,3),
   EVD_MN_M2          number(8,3),
   DCP_TEST_NO        varchar2(5),
   MOISTURE_TEST_NO   varchar2(5),
   NG_DENSITY_TEST_NO varchar2(5),
   COMMENTS           varchar2(50)
)
;
--
CREATE TABLE DISTRESS_NUCLEAR_DENSITY
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
)
;
--
CREATE TABLE DISTRESS_OBSI_DATA
(
   CELL                     number(3) NOT NULL,
   LANE                     varchar2(30) NOT NULL,
   DAY                      timestamp NOT NULL,
   TIME                     varchar2(20) NOT NULL,
   OPERATOR                 varchar2(30) NOT NULL,
   TEST_TIRE                varchar2(10) NOT NULL,
   FREQ_HZ                  number(10,0) NOT NULL,
   LEADING_INTENSITY_LEVEL  number(10,6),
   LEADING_PI               number(10,6),
   LEADING_COH              number(10,6),
   TRAILING_INTENSITY_LEVEL number(10,6),
   TRAILING_PI              number(10,6),
   TRAILING_COH             number(10,6),
   AVG_INTENSITY_LEVEL      number(4,1),
   TRIAL                    number(4,0),
   GRIND                    varchar2(12),
   COMMENTS                 varchar2(500)
)
;
CREATE UNIQUE INDEX DISTRESS_OBSI_DATA_UIDX ON DISTRESS_OBSI_DATA
(
  CELL,
  LANE,
  DAY,
  TRIAL,
  TIME,
  FREQ_HZ
)
;
--
CREATE TABLE DISTRESS_OBSI_SUMMARY
(
   CELL          number(3) NOT NULL,
   LANE          varchar2(30) NOT NULL,
   DAY           timestamp NOT NULL,
   TIME          varchar2(20) NOT NULL,
   OPERATOR      varchar2(30) NOT NULL,
   TEST_TIRE     varchar2(10) NOT NULL,
   LEADING_OBSI  number(10,6),
   TRAILING_OBSI number(10,6),
   TRIAL         number(4),
   GRIND         varchar2(12),
   RUN_AVERAGE   number(10,6),
   COMMENTS      varchar2(500)
)
;
CREATE UNIQUE INDEX DISTRESS_OBSI_SUMMARY_UIDX ON DISTRESS_OBSI_SUMMARY
(
  CELL,
  LANE,
  DAY,
  TIME,
  TRIAL
)
;
--
CREATE TABLE DISTRESS_PATHWAYS_VALUES
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
)
;
CREATE TABLE DISTRESS_PAVETECH_VALUES
(
   DAY            timestamp NOT NULL,
   CELL           number(2) NOT NULL,
   LANE           varchar2(20) NOT NULL,
   IRI_VALUE      number(6,2),
   RUTTING_VALUE  number(6,2),
   TEXTURE_VALUE  number(6,2),
   FAULTING_VALUE number(6,2)
)
;
--
CREATE TABLE DISTRESS_PCC_FAULTS
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
)
;
CREATE UNIQUE INDEX DPF_UIDX ON DISTRESS_PCC_FAULTS
(
  SAMPLE_DATE,
  CELL,
  LANE,
  SAMPLE_TIME,
  JOINT_NUMBER,
  OFFSET_FROM_CENTERLINE
)
;
--
CREATE TABLE DISTRESS_PROCEQ
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
)
;
--
CREATE TABLE DISTRESS_RIDE_LISA
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
)
;
--
CREATE TABLE DISTRESS_RUTTING_DIPSTICK
(
   DAY                 timestamp NOT NULL,
   CELL                number(2) NOT NULL,
   CONSTRUCTION_NUMBER number(2) NOT NULL,
   STATION             number(8,2) NOT NULL,
   LANE                varchar2(15) NOT NULL,
   LEFT_WP_DEPTH       number(4,3),
   RIGHT_WP_DEPTH      number(4,3)
)
;
--
CREATE TABLE DISTRESS_RUTTING_DIPSTICK_PINS
(
   DAY               timestamp NOT NULL,
   CELL              number(2) NOT NULL,
   SEQ               number(3) NOT NULL,
   STATION           number(8,2),
   SURFACE_ELEVATION number(7,3),
   PIN_ELEVATION     number(7,3)
)
;
CREATE UNIQUE INDEX DIPSTICK_PINS_IDX ON DISTRESS_RUTTING_DIPSTICK_PINS
(
  DAY,
  CELL,
  SEQ
)
;
--
CREATE TABLE DISTRESS_RUTTING_DIPSTICK_RAW
(
   DAY           timestamp NOT NULL,
   CELL        number(2,0) NOT NULL,
   SEQ         number(3,0) NOT NULL,
   READING_NUM number(2,0) NOT NULL,
   VALUE       number(6,3) NOT NULL
)
;
CREATE UNIQUE INDEX RUTTING_DIPSTICK_RAW_IDX ON DISTRESS_RUTTING_DIPSTICK_RAW
(
  DAY,
  CELL,
  SEQ,
  READING_NUM
)
;
--
CREATE TABLE DISTRESS_RUTTING_STRAIGHT_EDGE
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
)
;
CREATE UNIQUE INDEX RUTTING_STRAIGHT_EDGE_IDX1 ON DISTRESS_RUTTING_STRAIGHT_EDGE
(
  DAY,
  CELL,
  CONSTRUCTION_NUMBER,
  STATION,
  LANE
)
;
--
CREATE TABLE DISTRESS_SAND_PATCH
(
   CELL        number(3) NOT NULL,
   LANE        varchar2(30) NOT NULL,
   DAY         timestamp NOT NULL,
   TIME        varchar2(12) NOT NULL,
   OFFSET      number(3) NOT NULL,
   STATION     number(8,1),
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
   COMMENTS    varchar2(2 00)
)
;
--
CREATE TABLE DISTRESS_SCHMIDT_HAMMER
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
)
;
--
CREATE TABLE DISTRESS_SOUND_ABSORPTION
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
)
;
CREATE UNIQUE INDEX DSA_UIDX ON DISTRESS_SOUND_ABSORPTION
(
  DAY,
  CELL,
  STATION,
  OFFSET,
  LANE,
  WHEELPATH,
  TRIAL,
  FREQUENCY
)
;
--
CREATE TABLE DISTRESS_WATER_PERMEABILITY
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
)
;


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

select distinct lane from (
select distinct lane from DISTRESS_AC                       union all
select distinct lane from DISTRESS_AGG_SURVEY_SEMI          union all
select distinct lane from DISTRESS_ALPS_DATA                union all
select distinct lane from DISTRESS_ALPS_RESULTS_RUT         union all
select distinct lane from DISTRESS_CIRCULR_TEXTR_METER      union all
select distinct lane from DISTRESS_DYNAMIC_FRICTION         union all
select distinct lane from DISTRESS_FRICTION_DATA            union all
select distinct lane from DISTRESS_FRICTION_GRIP            union all
select distinct lane from DISTRESS_JPCC                     union all
select distinct lane from DISTRESS_LANE_SHOULDER_DROPOFF    union all
select distinct lane from DISTRESS_NUCLEAR_DENSITY          union all
select distinct lane from DISTRESS_OBSI_DATA                union all
select distinct lane from DISTRESS_OBSI_SUMMARY             union all
select distinct lane from DISTRESS_PATHWAYS_VALUES          union all
select distinct lane from DISTRESS_PAVETECH_VALUES          union all
select distinct lane from DISTRESS_PCC_FAULTS               union all
select distinct lane from DISTRESS_PROCEQ                   union all
select distinct lane from DISTRESS_RIDE_LISA                union all
select distinct lane from DISTRESS_RUTTING_DIPSTICK         union all
select distinct lane from DISTRESS_RUTTING_STRAIGHT_EDGE    union all
select distinct lane from DISTRESS_SAND_PATCH               union all
select distinct lane from DISTRESS_SOUND_ABSORPTION         union all
select distinct lane from DISTRESS_WATER_PERMEABILITY
);

road_section -> cell mapping


select cell_number from cell c where not
(
--MnROAD LVR North
c.cell_number between 33 and 43
or c.cell_number = 46
--MnROAD LVR South
or c.cell_number between 24 and 32
or c.cell_number between 44 and 45
or c.cell_number between 52 and 54
or c.cell_number between 77 and 79
or c.cell_number between 85 and 89
--MnROAD Mainline
or c.cell_number between 1 and 23
or c.cell_number in (50,51)
or c.cell_number between 60 and 63
or c.cell_number between 92 and 97
or c.cell_number in (105,205,305,405)
or c.cell_number in (106,206)
or c.cell_number in (110,210,310,410)
or c.cell_number in (111,211,311,411,511,611)
or c.cell_number in (113,213,313,413,513)
or c.cell_number in (114,214,314,414,514,614,714,814,914)
--MnROAD Farm
or c.cell_number between 83 and 84
--MnROAD Parking Lot
or c.cell_number = 64
--MnROAD Sidewalk
or c.cell_number = 74
)

Facilities and Road Sections

facility.id facility.name      road_section.id    road_section.description
76          Mainline           77                 Westbound Interstate 94
632         Low Volume Road    633                LVR South Side
632         Low Volume Road    917                LVR North Side
1073        Farm Road          1074               Farm Study Road
1075        Public Road        1076               MNTH 60 Heron - Brewster
16419       MnRoad Parking Lot 16420              Polebarn Parking Lot
16431       MnRoad Sidewalk    16432              Front Sidewalk

LANE.name
Outside Shldr
Inside Shldr
Eastbound
Passing
Right Shldr
Westbound
NA

select id from (
select l.id, rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank
from cell c join lane l on l.cell_id=c.id
where c.cell_number=1
 and l.name='Driving'
 and to_char(c.CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') <= '2009-02-11'
 and nvl(to_char(c.demolished_date,'yyyy-mm-dd'),to_char(sysdate,'yyyy-mm-dd')) >= '2009-02-11'
)
where age_rank=(select max(age_rank) from
(
select rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank
from cell c join lane l on l.cell_id=c.id
where c.cell_number=1 and l.name='Driving'
 and to_char(c.CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') <= '2009-02-11'
 and nvl(to_char(c.demolished_date,'yyyy-mm-dd'),to_char(sysdate,'yyyy-mm-dd')) >= '2009-02-11'
));

select id,
    road_section_id,
    (case
        when (c.cell_number between 33 and 43
            or c.cell_number = 46
            )
        then 'MnROAD LVR North'
        when (
            c.cell_number between 24 and 32
            or c.cell_number between 44 and 45
            or c.cell_number between 52 and 54
            or c.cell_number between 77 and 79
            or c.cell_number between 85 and 89
            )
        then 'MnROAD LVR South'
        when (c.cell_number between 1 and 23
            or c.cell_number in (50,51)
            or c.cell_number between 60 and 63
            or c.cell_number between 92 and 97
            or c.cell_number in (105,205,305,405)
            or c.cell_number in (106,206)
            or c.cell_number in (110,210,310,410)
            or c.cell_number in (111,211,311,411,511,611)
            or c.cell_number in (113,213,313,413,513)
            or c.cell_number in (114,214,314,414,514,614,714,814,914)
            )
        then 'MnROAD Mainline'
        when (c.cell_number between 83 and 84)
        then 'MnROAD Farm'
        when (c.cell_number = 64)
        then 'MnROAD Parking Lot'
        when (c.cell_number = 74)
        then 'MnROAD Sidewalk'
        end
    ) RoadSection,
    cell_number cell,
    to_char(CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') begin_date,
    nvl(to_char(DEMOLISHED_DATE,'yyyy-mm-dd'),'None') end_date,
    START_STATION,
    END_STATION,
    rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank
    ,subsumed_by
from cell c
;

        ROAD                              CONSTRUCTION
        SECTION                    CELL   ENDED        DEMOLISHED
ID      ID      RoadSection        NUMBER DATE         DATE        START_STATION END_STATION AGE_RANK                        SUBSUMED_BY
80      77      MnROAD Mainline    1      1992-10-15   <null>      110,284.54    110,839.54  1                               15849=1
15,849  77      MnROAD Mainline    1      2006-07-07   <null>      110,284.54    110,839.54  2                               <null>
107     77      MnROAD Mainline    2      1992-09-28   <null>      110,839.54    111,399.54  1                               17047=2
17,047  77      MnROAD Mainline    2      2008-10-01   <null>      110,839.54    111,399.54  2                               <null>
13,430  77      MnROAD Mainline    3      1992-10-28   <null>      111,399.54    111,984.54  1                               17075=3
17,075  77      MnROAD Mainline    3      2008-10-29   <null>      111,399.54    111,984.54  2                               <null>
163     77      MnROAD Mainline    4      1992-09-29   2008-07-08  111,984.54    112,585     1                               <null>
13,449  77      MnROAD Mainline    4      2008-10-29   <null>      111,984.54    112,585     1                               <null>
184     77      MnROAD Mainline    5      1992-10-15   <null>      112,585       113,172.5   1                               15865=105, 15866=205, 15867=305, 15868=405
207     77      MnROAD Mainline    6      1992-10-15   2008-05-01  113,172.5     113,710     1                               <null>
226     77      MnROAD Mainline    7      1992-09-16   <null>      113,710       114,255     1                               <null>
249     77      MnROAD Mainline    8      1992-09-16   <null>      114,255       114,795     1                               <null>
270     77      MnROAD Mainline    9      1992-09-16   <null>      114,795       115,327.5   1                               <null>
299     77      MnROAD Mainline    10     1993-06-14   <null>      116,753       117,302.5   1                               <null>
322     77      MnROAD Mainline    11     1993-06-14   <null>      117,302.5     117,835     1                               <null>
339     77      MnROAD Mainline    12     1993-06-14   <null>      117,835       118,345     1                               <null>
356     77      MnROAD Mainline    13     1993-06-14   2008-05-01  118,345       118,890     1                               <null>
373     77      MnROAD Mainline    14     1993-07-29   <null>      118,890       119,445     1                               15668=114, 15669=214, 15670=314, 15671=414, 15672=514, 15673=614, 15674=714, 15675=814, 15676=914
396     77      MnROAD Mainline    15     1993-07-28   <null>      119,445       120,017.5   1                               15856=15
15,856  77      MnROAD Mainline    15     2008-05-01   <null>      119,445       120,017.5   2                               <null>
419     77      MnROAD Mainline    16     1993-07-29   2008-05-01  120,017.5     120,590     1                               <null>
14,437  77      MnROAD Mainline    16     2008-09-15   <null>      120,017.5     120,590     1                               <null>
444     77      MnROAD Mainline    17     1993-07-29   2008-05-01  120,590       121,150     1                               <null>
13,617  77      MnROAD Mainline    17     2008-09-15   <null>      120,590       121,150     1                               <null>
469     77      MnROAD Mainline    18     1993-07-28   2008-05-01  121,150       121,720     1                               <null>
13,634  77      MnROAD Mainline    18     2008-09-15   <null>      121,150       121,720     1                               <null>
498     77      MnROAD Mainline    19     1993-07-29   2008-05-01  121,720       122,280     1                               <null>
13,670  77      MnROAD Mainline    19     2008-09-15   <null>      121,720       122,280     1                               <null>
523     77      MnROAD Mainline    20     1993-07-15   2008-05-01  122,280       122,850     1                               <null>
13,689  77      MnROAD Mainline    20     2008-09-15   <null>      122,280       122,850     1                               <null>
552     77      MnROAD Mainline    21     1993-07-29   2008-05-01  122,850       123,435     1                               <null>
13,695  77      MnROAD Mainline    21     2008-09-15   <null>      122,850       123,435     1                               <null>
576     77      MnROAD Mainline    22     1993-07-29   2008-05-01  123,435       124,015     1                               <null>
13,696  77      MnROAD Mainline    22     2008-09-15   <null>      123,435       124,015     1                               <null>
601     77      MnROAD Mainline    23     1993-08-29   2008-05-01  124,015       124,580     1                               <null>
13,697  77      MnROAD Mainline    23     2008-09-15   <null>      124,015       124,580     1                               <null>
634     633     MnROAD LVR South   24     1993-08-15   <null>      15,800        16,367.5    1                               16120=24
16,120  633     MnROAD LVR South   24     2008-10-16   <null>      15,800        16,367.5    2                               <null>
653     633     MnROAD LVR South   25     1993-08-15   2008-05-01  16,367.5      16,992.5    1                               <null>
666     633     MnROAD LVR South   26     1993-08-15   2000-09-01  16,992.5      17,497.5    1                               <null>
16,284  633     MnROAD LVR South   26     2000-09-11   2004-05-01  16,992.5      17,497.5    1                               <null>
16,494  633     MnROAD LVR South   26     2004-05-15   2008-05-01  16,992.5      17,490      1                               <null>
705     633     MnROAD LVR South   27     1993-08-15   1999-08-01  17,497.5      18,065      1                               <null>
16,290  633     MnROAD LVR South   27     1999-08-15   2000-08-01  17,497.5      18,065      1                               <null>
16,291  633     MnROAD LVR South   27     2000-09-15   2006-06-19  17,497.5      18,065      1                               <null>
16,293  633     MnROAD LVR South   27     2006-06-19   <null>      17,497.5      18,065      1                               <null>
773     633     MnROAD LVR South   28     1993-08-15   1999-08-15  18,065        18,635      1                               <null>
16,292  633     MnROAD LVR South   28     1999-08-15   2006-06-19  18,065        18,635      1                               <null>
16,294  633     MnROAD LVR South   28     2006-06-19   <null>      18,065        18,635      1                               <null>
821     633     MnROAD LVR South   29     1993-08-15   2008-05-01  18,635        19,205      1                               <null>
842     633     MnROAD LVR South   30     1993-08-15   2008-05-01  19,205        19,775      1                               <null>
863     633     MnROAD LVR South   31     1993-08-15   <null>      19,775        20,342.5    1                               16253=31
16,253  633     MnROAD LVR South   31     2004-08-19   <null>      19,775        20,342.5    2                               <null>
897     633     MnROAD LVR South   32     1993-06-01   <null>      20,342.5      20,900      1                               16130=32
16,130  633     MnROAD LVR South   32     2000-06-30   <null>      20,342.5      20,900      2                               <null>
918     917     MnROAD LVR North   33     1993-09-01   1999-08-15  6,350         6,910       1                               <null>
927     917     MnROAD LVR North   33     1999-08-15   <null>      6,350         6,910       1                               16368=33
16,368  917     MnROAD LVR North   33     2007-09-13   <null>      6,350         6,910       2                               <null>
945     917     MnROAD LVR North   34     1993-09-01   1999-08-15  6,910         7,470       1                               <null>
954     917     MnROAD LVR North   34     1999-08-15   <null>      6,910         7,470       1                               16379=34
16,379  917     MnROAD LVR North   34     2007-09-13   <null>      6,910         7,470       2                               <null>
972     917     MnROAD LVR North   35     1993-09-01   1999-08-15  7,470         8,062       1                               <null>
981     917     MnROAD LVR North   35     1999-08-15   <null>      7,470         8,062       1                               16390=35
16,390  917     MnROAD LVR North   35     2007-09-11   <null>      7,470         8,062       2                               <null>
998     917     MnROAD LVR North   36     1993-07-19   <null>      8,062         8,629.5     1                               <null>
1,013   917     MnROAD LVR North   37     1993-07-19   <null>      8,629.5       9,172.5     1                               <null>
1,028   917     MnROAD LVR North   38     1993-07-19   <null>      9,172.5       9,690       1                               <null>
1,043   917     MnROAD LVR North   39     1993-07-19   <null>      9,690         10,232.5    1                               16273=39
16,273  917     MnROAD LVR North   39     2008-10-17   <null>      9,690         10,232.5    2                               <null>
1,058   917     MnROAD LVR North   40     1993-07-19   <null>      10,232.5      10,745      1                               <null>
16,943  917     MnROAD LVR North   41     1993-07-15   <null>      15,700        5,005       1                               <null>
16,960  917     MnROAD LVR North   42     1993-07-15   <null>      5,005         5,640       1                               <null>
16,977  917     MnROAD LVR North   43     1993-07-15   <null>      5,640         6,250       1                               <null>
16,994  633     MnROAD LVR South   44     1993-07-15   <null>      21,300        21,905      1                               <null>
17,011  633     MnROAD LVR South   45     1993-07-15   <null>      21,905        22,525      1                               <null>
17,028  917     MnROAD LVR North   46     1993-07-15   <null>      22,525        10,750      1                               <null>
78      77      MnROAD Mainline    50     1997-07-15   2008-05-01  109,788       110,013     1                               <null>
79      77      MnROAD Mainline    51     1997-07-15   2008-05-01  110,013       110,238     1                               <null>
913     633     MnROAD LVR South   52     2000-06-30   <null>      20,800        21,085      1                               <null>
914     633     MnROAD LVR South   53     2000-06-30   2008-05-01  21,085        21,200      1                               <null>
16,139  633     MnROAD LVR South   53     2008-10-16   <null>      21,085        21,200      1                               <null>
915     633     MnROAD LVR South   54     2000-10-15   2004-10-15  21,200        21,398      1                               <null>
16,665  633     MnROAD LVR South   54     2004-10-26   <null>      21,200        21,398      1                               <null>
15,797  77      MnROAD Mainline    60     2004-11-08   <null>      115,327.5     115,560     1                               <null>
15,798  77      MnROAD Mainline    61     2004-11-08   <null>      115,560       115,785     1                               <null>
15,799  77      MnROAD Mainline    62     2004-11-08   <null>      115,785       116,010     1                               <null>
15,800  77      MnROAD Mainline    63     2004-11-08   <null>      116,010       116,234     1                               <null>
16,684  16,420  MnROAD Parking Lot 64     2005-09-27   <null>      0             60          1                               <null>
16,485  16,432  MnROAD Sidewalk    74     2006-09-18   <null>      0             1           1                               <null>
16,209  633     MnROAD LVR South   77     2007-10-25   <null>      18,635        18,975      1                               <null>
16,210  633     MnROAD LVR South   78     2007-10-25   <null>      18,975        19,387      1                               <null>
16,211  633     MnROAD LVR South   79     2007-10-25   <null>      19,387        19,765      1                               <null>
1       1,074   MnROAD Farm        83     2007-10-01   <null>      99,893        100,325     1                               <null>
16,346  1,074   MnROAD Farm        84     2007-10-01   <null>      100,325       100,700     1                               <null>
16,162  633     MnROAD LVR South   85     2008-10-16   <null>      16,367.5      16,594      1                               <null>
16,163  633     MnROAD LVR South   86     2008-10-16   <null>      16,594        16,820      1                               <null>
16,164  633     MnROAD LVR South   87     2008-10-16   <null>      16,820        17,046      1                               <null>
16,165  633     MnROAD LVR South   88     2008-10-16   <null>      17,046        17,272      1                               <null>
16,166  633     MnROAD LVR South   89     2008-10-16   <null>      17,272        17,497.5    1                               <null>
1,138   77      MnROAD Mainline    92     1993-06-29   <null>      116,578       116,750     1                               <null>
15,228  77      MnROAD Mainline    93     1993-06-29   <null>      115,327.5     115,635     1                               15797=60, 15798=61
15,229  77      MnROAD Mainline    94     1993-06-29   <null>      115,635       115,935     1                               15798=61, 15799=62
15,230  77      MnROAD Mainline    95     1993-06-29   <null>      115,935       116,234     1                               15799=62, 15800=63
1,136   77      MnROAD Mainline    96     1993-06-29   <null>      116,234       116,406     1                               <null>
1,137   77      MnROAD Mainline    97     1993-06-28   <null>      116,406       116,578     1                               <null>
15,865  77      MnROAD Mainline    105    2008-11-08   <null>      112,585       112,758     1                               <null>
13,424  77      MnROAD Mainline    106    2008-10-30   <null>      113,172.5     113,440     1                               <null>
15,448  77      MnROAD Mainline    113    2008-10-10   <null>      118,434       118,530     1                               <null>
15,668  77      MnROAD Mainline    114    2008-10-10   <null>      118,890       118,989     1                               <null>
15,866  77      MnROAD Mainline    205    2008-11-08   <null>      112,758       112,893     1                               <null>
13,425  77      MnROAD Mainline    206    2008-10-30   <null>      113,440       113,710     1                               <null>
15,449  77      MnROAD Mainline    213    2008-10-10   <null>      118,530       118,650     1                               <null>
15,669  77      MnROAD Mainline    214    2008-10-10   <null>      118,989       119,013     1                               <null>
15,867  77      MnROAD Mainline    305    2008-11-08   <null>      112,893       113,043     1                               <null>
15,450  77      MnROAD Mainline    313    2008-10-10   <null>      118,650       118,770     1                               <null>
15,670  77      MnROAD Mainline    314    2008-10-10   <null>      119,013       119,151     1                               <null>
15,868  77      MnROAD Mainline    405    2008-11-08   <null>      113,043       113,172.5   1                               <null>
15,546  77      MnROAD Mainline    413    2008-10-10   <null>      118,770       118,890     1                               <null>
15,671  77      MnROAD Mainline    414    2008-10-10   <null>      119,151       119,181     1                               <null>
15,447  77      MnROAD Mainline    513    2008-10-10   <null>      118,345       118,434     1                               <null>
15,672  77      MnROAD Mainline    514    2008-10-10   <null>      119,181       119,217     1                               <null>
15,673  77      MnROAD Mainline    614    2008-10-10   <null>      119,217       119,325     1                               <null>
15,674  77      MnROAD Mainline    714    2008-10-10   <null>      119,325       119,343     1                               <null>
15,675  77      MnROAD Mainline    814    2008-10-10   <null>      119,343       119,367     1                               <null>
15,676  77      MnROAD Mainline    914    2008-10-10   <null>      119,367       119,445     1                               <null>

select id from (
select l.id, rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank
from cell c join lane l on l.cell_id=c.id
where c.cell_number=1
 and l.name='Driving'
 and to_char(c.CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') <= '2009-02-11'
 and nvl(to_char(c.demolished_date,'yyyy-mm-dd'),to_char(sysdate,'yyyy-mm-dd')) >= '2009-02-11'
)
where age_rank=(select max(age_rank) from
(
select rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank
from cell c join lane l on l.cell_id=c.id
where c.cell_number=1 and l.name='Driving'
 and to_char(c.CONSTRUCTION_ENDED_DATE,'yyyy-mm-dd') <= '2009-02-11'
 and nvl(to_char(c.demolished_date,'yyyy-mm-dd'),to_char(sysdate,'yyyy-mm-dd')) >= '2009-02-11'
));

select id from (select l.id, rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank from cell c join lane l on l.cell_id=c.id where c.cell_number=? and l.name= ? and c.CONSTRUCTION_ENDED_DATE <= ? and nvl(c.demolished_date,sysdate) >= ? ) where age_rank=(select max(age_rank) from (select rank() over (partition by cell_number,demolished_date order by CONSTRUCTION_ENDED_DATE) age_rank from cell c join lane l on l.cell_id=c.id where c.cell_number=? and l.name=? and c.CONSTRUCTION_ENDED_DATE <= ? and nvl(c.demolished_date,sysdate) >= ? ));

Cell 21 has extra lane (557 and 575) used for demolished structure (id 552) and it successor (id 13695).  Create new lanes for cell w/id=13695
select distinct lane from DISTRESS_AC;
update DISTRESS_AC set lane='Eastbound' where lane = 'Farm Eastbound';
update DISTRESS_AC set lane='Westbound' where lane = 'Farm Westbound';
update DISTRESS_AC set lane='Inside' where lane = 'LVR Inside'       ;
update DISTRESS_AC set lane='Outside' where lane = 'LVR Outside'     ;
update DISTRESS_AC set lane='Driving' where lane = 'ML Driving'      ;
update DISTRESS_AC set lane='Passing' where lane = 'ML Passing'      ;
select distinct lane from DISTRESS_AC;
--
select distinct lane from DISTRESS_AGG_SURVEY_SEMI;
update DISTRESS_AGG_SURVEY_SEMI set lane='Inside' where lane = 'LVR Inside'       ;
update DISTRESS_AGG_SURVEY_SEMI set lane='Outside' where lane = 'LVR Outside'     ;



-- If a cell is completely removed (has a non-null DEMOLISHED_DATE) SUBSUMED_BY must be null
select id,cell_number,CONSTRUCTION_ENDED_DATE,DEMOLISHED_DATE,SUBSUMED_BY from cell where cell_number in (
  select cell_number from cell where DEMOLISHED_DATE is not null and SUBSUMED_BY is not null)
ID              CELL_NUMBER     CONSTRUCTION_ENDED_DATE         DEMOLISHED_DATE SUBSUMED_BY
356             13              1993-06-14 00:00:00.0           2008-05-01 00:00:00.0           15447=513, 15448=113, 15449=213, 15450=313, 15546=413
927             33              1999-08-15 00:00:00.0           <null>          16368=33
918             33              1993-09-01 00:00:00.0           1999-08-15 00:00:00.0           16368=33
16,368          33              2007-09-13 00:00:00.0           <null>          <null>
981             35              1999-08-15 00:00:00.0           <null>          16390=35
972             35              1993-09-01 00:00:00.0           1999-08-15 00:00:00.0           16390=35
16,390          35              2007-09-11 00:00:00.0           <null>          <null>
-- Those with non-null demolished date are not subsumed
update cell set subsumed_by='' where demolished_date is not null and subsumed_by is not null;

select cell, to_char(min(DAY), 'dd-MON-yyyy') from_day, 'select id from cell where cell_number='||cell||' and CONSTRUCTION_ENDED_DATE<='''||to_char(min(DAY), 'dd-MON-yyyy')||''' and nvl(DEMOLISHED_DATE,sysdate)>'''|| to_char(max(DAY), 'dd-MON-yyyy')||''';'
from DISTRESS_AC group by cell
order by cell, from_day;


-- Lane names found in DISTRESS_* tables ...
102k
80K
80k
CELL 64
Driving
East Bound
Eastbound
Farm Eastbound
Farm Westbound
Inside
LRV-Inside
LS/LWP
LS/RWP
LVR Inside
LVR Outside
LVR-102K-OL
LVR-80K-IL
LVR-Inside
LVR-Inside Lane
LVR-Inside Lane
LVR-Outside
LVR-Outside Lane
LVR_IL_TRAFFIC
LVR_Inside_Traffic
LVR_OL_ENVIRONMENTAL
LVR_OUTSIDE_ENVIRONMENTAL
LVR_Outside_Environmental
MAINLINE_DRIVING
MAINLINE_PASSING
ML Driving
ML Passing
ML-Driving
ML-Driving Shoulder
ML-Driving-RL
ML-Passing
ML-Passing Shoulder
ML-Passing-LL
ML_LL_PASSING
ML_RL_DRIVING
Mailine Driving
Mailine-Passing
Main Line Driving
Main Line Passing
Mainline Driving
Mainline Passing
Mainline-Driving
Mainline-Passing
Mainline_Driving
Mainline_Passing
OT
Outside
Outside
Passing
West Bound
Westbound
<null>

DISTRESS_AC                    	CELL,LANE,DAY
DISTRESS_AGG_SURVEY_SEMI       	CELL,LANE,DAY 
DISTRESS_ALPS_DATA             	CELL,LANE,DAY
DISTRESS_ALPS_RESULTS_RUT      	CELL,LANE,DAY
DISTRESS_CIRCULR_TEXTR_METER   	CELL,LANE,DAY
DISTRESS_DYNAMIC_FRICTION      	CELL,LANE,DAY
DISTRESS_FRICTION_DATA         	CELL,LANE,DAY
DISTRESS_FRICTION_GRIP         	CELL,LANE,DAY
DISTRESS_JPCC                  	CELL,LANE,DAY
DISTRESS_LANE_SHOULDER_DROPOFF 	CELL,LANE,DAY
DISTRESS_NUCLEAR_DENSITY       	CELL,LANE,DAY
DISTRESS_OBSI_DATA             	CELL,LANE,DAY
DISTRESS_OBSI_SUMMARY          	CELL,LANE,DAY
DISTRESS_PATHWAYS_VALUES       	CELL,LANE,DAY
DISTRESS_PAVETECH_VALUES       	CELL,LANE,DAY
DISTRESS_PCC_FAULTS            	CELL,LANE,SAMPLE_DATE
DISTRESS_PROCEQ                	CELL,LANE,DAY
DISTRESS_RIDE_LISA             	CELL,LANE,DAY
DISTRESS_RUTTING_DIPSTICK      	CELL,LANE,DAY
DISTRESS_RUTTING_STRAIGHT_EDGE 	CELL,LANE,DAY
DISTRESS_SAND_PATCH            	CELL,LANE,DAY
DISTRESS_SOUND_ABSORPTION      	CELL,LANE,DAY
DISTRESS_WATER_PERMEABILITY    	CELL,LANE,DAY

DISTRESS_FIELD_MOISTURE	CELL,OFFSET,DATE_SAMPLE,DATE_TEST	
DISTRESS_LIGHTWEIGHT_DEFLECT	CELL,OFFSET,DAY
DISTRESS_RUTTING_DIPSTICK_PINS  CELL,DAY,SEQ/STATION
DISTRESS_RUTTING_DIPSTICK_RAW   CELL,DAY,SEQ,READING_NUM


-- xx
CREATE TABLE tempd
(
,  ID                           number(22),
   LANE_ID                      number(22)
)
;
--
INSERT INTO tempd
SELECT
,   DISTRESS_SEQ.NEXTVAL
from xx;
--
truncate table xx;
alter table xx add (id number(22));
alter table xx modify (cell number(3));
--
INSERT INTO xx
SELECT
,   id
FROM tempd;
--
drop table tempd purge;
--
update xx set lane='' where lane = '';
select distinct lane from xx;
