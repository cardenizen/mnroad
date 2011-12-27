SELECT unique * FROM (
SELECT x.CELL, x.DAY,x.SEQ,x.HOUR,x.QHR,x.MINUTE,v.value value,x.value temperature
FROM mnr.vw_values_1994 v 
full outer join mnr.XV_VALUES_1994 x on
x.DAY=v.DAY and x.CELL=v.cell and x.SEQ=v.seq and x.HOUR=v.hour and x.QHR=v.qhr and x.MINUTE=v.minute
where 
x.cell=11 and 
x.day < '19APR-1994'
union all
SELECT v.CELL, v.DAY,v.SEQ,v.HOUR,v.QHR,v.MINUTE,v.value value,x.value temperature
FROM mnr.vw_values_1994 v 
full outer join mnr.XV_VALUES_1994 x on
x.DAY=v.DAY and x.CELL=v.cell and x.SEQ=v.seq and x.HOUR=v.hour and x.QHR=v.qhr and x.MINUTE=v.minute
where 
v.cell=11 and 
v.day < '19APR-1994'
)
where value is not null and temperature is not null
order by CELL,DAY,SEQ,HOUR,QHR,MINUTE

Equivalent to:

SELECT v.CELL,v.DAY,v.SEQ,v.HOUR,v.QHR,v.MINUTE,v.value,x.value temperature
FROM mnr.vw_values_1994 v join mnr.XV_VALUES_1994 x
on
x.DAY=v.DAY
and x.CELL=v.cell
and x.SEQ=v.seq
and x.HOUR=v.hour
and x.QHR=v.qhr
and x.MINUTE=v.minute
where 
x.cell=11 and 
x.day < '19APR-1994'
order by v.CELL,v.DAY,v.SEQ,v.HOUR,v.QHR,v.MINUTE


select cell, to_char(day,'mm/dd/yyyy') day, hour, qhr, minute, s101,s102,s103,s104,s105,s106,s107,s108,s109,s110,s111,s112,s113,s114,s115,s116
from (select cell,day,hour,qhr,minute
,min(decode(seq, 101, value)) as s101 
,min(decode(seq, 102, value)) as s102 
,min(decode(seq, 103, value)) as s103 
,min(decode(seq, 104, value)) as s104 
,min(decode(seq, 105, value)) as s105 
,min(decode(seq, 106, value)) as s106 
,min(decode(seq, 107, value)) as s107 
,min(decode(seq, 108, value)) as s108 
,min(decode(seq, 109, value)) as s109 
,min(decode(seq, 110, value)) as s110 
,min(decode(seq, 111, value)) as s111 
,min(decode(seq, 112, value)) as s112 
,min(decode(seq, 113, value)) as s113 
,min(decode(seq, 114, value)) as s114 
,min(decode(seq, 115, value)) as s115 
,min(decode(seq, 116, value)) as s116 
FROM mnr.tc_values
WHERE cell = 2
AND DAY BETWEEN '01-APR-2010' and '15-APR-2010'
GROUP BY cell,day,hour,qhr,minute)
order by cell,day,hour,qhr,minute

select cell, to_char(day,'mm/dd/yyyy') day, hour, qhr, minute, s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11
from (select cell,day,hour,qhr,minute
,min(decode(seq, 1, value)) as s1 
,min(decode(seq, 2, value)) as s2 
,min(decode(seq, 3, value)) as s3 
,min(decode(seq, 4, value)) as s4 
,min(decode(seq, 5, value)) as s5 
,min(decode(seq, 6, value)) as s6 
,min(decode(seq, 7, value)) as s7 
,min(decode(seq, 8, value)) as s8 
,min(decode(seq, 9, value)) as s9 
,min(decode(seq, 10, value)) as s10 
,min(decode(seq, 11, value)) as s11 
FROM mnr.tc_values_all
WHERE cell = 2
AND DAY BETWEEN '01-APR-2007' and '15-APR-2007'
GROUP BY cell,day,hour,qhr,minute)
order by cell,day,hour,qhr,minute

