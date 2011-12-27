CREATE OR REPLACE VIEW CELL_SENSORS AS
SELECT
S.ID ,
S.MODEL ,
S.SEQ ,
S.STATION_FT STATION ,
S.OFFSET_FT OFFSET ,
S.SENSOR_DEPTH_IN DEPTH ,
TO_CHAR(S.DATE_INSTALLED,'YYYY-MM-DD') INSTALLED ,
TO_CHAR(S.DATE_REMOVED,'YYYY-MM-DD') REMOVED ,
UC.CELL_UNDER CELL ,
SUBSTR(UC.CLASS_UNDER,24) TYPE ,
LN.NAME LANE,
M.BASIC_MATERIAL ,
UC.FROM_STATION_UNDER FR_STATION ,
UC.TO_STATION_UNDER TO_STATION ,
TO_CHAR(UC.FROM_DATE_UNDER,'YYYY-MM-DD') FR_DATE ,
TO_CHAR(UC.TO_DATE_UNDER,'YYYY-MM-DD') TO_DATE ,
'Under' POSITION ,
UC.CELL_OVER CELL_O,
SUBSTR(UC.CLASS_OVER,24) TYPE_O ,
UC.FROM_STATION_OVER FR_STATION_O ,
UC.TO_STATION_OVER TO_STATION_O ,
TO_CHAR(UC.FROM_DATE_OVER,'YYYY-MM-DD') FR_DATE_O ,
TO_CHAR(UC.TO_DATE_OVER,'YYYY-MM-DD') TO_DATE_O ,
LN.ID LANE_ID,
L.ID LAYER_ID,
M.ID MATERIAL_ID
FROM
(
   SELECT
   *
   FROM CELL_ON_CELL C
   WHERE C.CELL_OVER IN
   (
      SELECT
      (SELECT CELL_NUMBER FROM CELL WHERE ID=CELL_ID)
      FROM CELL_CELL CC JOIN CELL C ON C.ID=CC.CELL_COVERED_BY_ID
   )
)
UC JOIN LANE LN ON LN.CELL_ID=UC.ID_UNDER JOIN LAYER L ON L.LANE_ID=LN.ID JOIN SENSORS S ON S.LAYER_ID = L.ID JOIN MATERIAL M ON L.MATERIAL_ID=M.ID
WHERE S.STATION_FT BETWEEN UC.FROM_STATION_OVER
AND UC.TO_STATION_OVER
UNION
ALL
SELECT
S.ID ,
S.MODEL ,
S.SEQ ,
S.STATION_FT STATION ,
S.OFFSET_FT OFFSET ,
S.SENSOR_DEPTH_IN DEPTH ,
TO_CHAR(S.DATE_INSTALLED,'YYYY-MM-DD') INSTALLED ,
TO_CHAR(S.DATE_REMOVED,'YYYY-MM-DD') REMOVED ,
UC.CELL_OVER CELL ,
SUBSTR(UC.CLASS_OVER,24) TYPE ,
LN.NAME LANE,
M.BASIC_MATERIAL ,
UC.FROM_STATION_OVER FR_STATION ,
UC.TO_STATION_OVER TO_STATION ,
TO_CHAR(UC.FROM_DATE_OVER,'YYYY-MM-DD') FR_DATE ,
TO_CHAR(UC.TO_DATE_OVER,'YYYY-MM-DD') TO_DATE ,
'Over' POSITION ,
UC.CELL_UNDER CELL_O,
SUBSTR(UC.CLASS_UNDER,24) TYPE_O ,
UC.FROM_STATION_UNDER FR_STATION_O ,
UC.TO_STATION_UNDER TO_STATION_O ,
TO_CHAR(UC.FROM_DATE_UNDER,'YYYY-MM-DD') FR_DATE_O ,
TO_CHAR(UC.TO_DATE_UNDER,'YYYY-MM-DD') TO_DATE_O ,
LN.ID LANE_ID,
L.ID LAYER_ID,
M.ID MATERIAL_ID
FROM CELL_ON_CELL UC JOIN LANE LN ON LN.CELL_ID=UC.ID_OVER JOIN LAYER L ON L.LANE_ID=LN.ID JOIN SENSORS S ON S.LAYER_ID = L.ID JOIN MATERIAL M ON L.MATERIAL_ID=M.ID
