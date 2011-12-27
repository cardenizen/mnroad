drop  TABLE SENSOR_COUNTS purge;
CREATE TABLE SENSOR_COUNTS
(
   TABLE_NAME varchar2(13),
   CELL decimal(22),
   SEQ decimal(22),
   YEAR decimal(22),
   NUM_READINGS decimal(22),
   AS_OF timestamp
);

delete from sensor_counts;
insert into sensor_counts select distinct 'CR_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from CR_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'CT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from CT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'EC_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from EC_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'ET_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from ET_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'EW_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from EW_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'FG_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from FG_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'FL_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from FL_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'FP_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from FP_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'FT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from FT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'FW_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from FW_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'HC_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from HC_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'HD_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from HD_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'HV_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from HV_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'KB_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from KB_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'MC_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from MC_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'MH_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from MH_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'MM_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from MM_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'MT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from MT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'NT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from NT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'PL_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from PL_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'PT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from PT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'RE_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from RE_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'RG_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from RG_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'RH_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from RH_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'RM_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from RM_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'RP_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from RP_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'RT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from RT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'RW_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from RW_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'SW_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from SW_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'TB_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from TB_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'TD_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from TD_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'TL_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from TL_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'TM_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from TM_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'TT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from TT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'VG_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from VG_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'VM_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from VM_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XB_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XB_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XG_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XG_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XH_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XH_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XL_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XL_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XM_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XM_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XS_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XS_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XT_VALUES'     table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XT_VALUES     group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'TC_VALUES_ALL' table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from TC_VALUES_ALL group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'VW_VALUES_ALL' table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from VW_VALUES_ALL group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'WM_VALUES_ALL' table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from WM_VALUES_ALL group by cell,seq,to_number(to_char(day,'yyyy'));
insert into sensor_counts select distinct 'XV_VALUES_ALL' table_name, cell, seq, to_number(to_char(day,'yyyy')) year, COUNT(*) NUM_READINGS, SYSDATE AS_OF from XV_VALUES_ALL group by cell,seq,to_number(to_char(day,'yyyy'));
