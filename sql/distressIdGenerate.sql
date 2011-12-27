DROP SEQUENCE "DISTRESS_SEQ";
CREATE SEQUENCE "DISTRESS_SEQ"
INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999999999999999999999 
CACHE 20 NOCYCLE;

UPDATE DISTRESS_AC                    SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_AGG_SURVEY_SEMI       SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_ALPS_RESULTS_RUT      SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_CIRCULR_TEXTR_METER   SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_CUPPING               SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_FRICTION_DFT          SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_FRICTION_TRAILER      SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_JPCC                  SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_LANE_SHOULDER_DROPOFF SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_NUCLEAR_DENSITY       SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_OBSI_DATA             SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_OBSI_SUMMARY          SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_PCC_FAULTS            SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_PERMEABILITY_DIRECT   SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_RIDE_LISA             SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_RIDE_PATHWAYS         SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_RIDE_PAVETECH         SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_RUTTING_DIPSTICK      SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_RUTTING_STRAIGHT_EDGE SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_SAND_PATCH            SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_SCHMIDT_HAMMER        SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_SOUND_ABSORPTION      SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_WARP_CURL             SET ID=DISTRESS_SEQ.NEXTVAL;
UPDATE DISTRESS_WATER_PERMEABILITY    SET ID=DISTRESS_SEQ.NEXTVAL;

DELETE FROM DISTRESS;

INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_AC                    D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_AGG_SURVEY_SEMI       D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_ALPS_RESULTS_RUT      D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_CIRCULR_TEXTR_METER   D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_CUPPING               D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_FRICTION_DFT          D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_FRICTION_TRAILER      D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_JPCC                  D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_LANE_SHOULDER_DROPOFF D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_NUCLEAR_DENSITY       D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_OBSI_DATA             D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_OBSI_SUMMARY          D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_PCC_FAULTS            D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_PERMEABILITY_DIRECT   D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_RIDE_LISA             D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_RIDE_PATHWAYS         D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_RIDE_PAVETECH         D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_RUTTING_DIPSTICK      D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_RUTTING_STRAIGHT_EDGE D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_SAND_PATCH            D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_SCHMIDT_HAMMER        D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_SOUND_ABSORPTION      D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_WARP_CURL             D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
INSERT INTO DISTRESS SELECT UNIQUE ID, 1, 'carr1den', 'carr1den', SYSDATE, SYSDATE, LD.LANE_ID FROM DISTRESS_WATER_PERMEABILITY    D JOIN LANE_DATES LD ON LD.CELL=D.CELL AND LD.LANE=D.LANE AND D.DAY BETWEEN LD.FROM_DATE AND LD.TO_DATE;
                       