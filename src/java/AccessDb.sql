select s.* from Sessions s
select s.SessionID, t.* from Sessions s, Stations t where t.SessionID=s.SessionID
select s.SessionID, t.StationID, d.DropID, d.D8 from Sessions s, Stations t, Drops d where  t.SessionID=s.SessionID and d.StationID=t.StationID

--Sessions
--SessionID	Program	Operator	Trailer	Electronics	TestSetup	Units	Date	FacilityName	FacilityCode	FacilityDirection	FacilityType	FacilityLanes	SectionName	SectionCode	SectionDirection	District	Class	Start	End	Lane	LaneNumber	Traffic	Smoothing	StationMin	StationMax	Radius	X1	X2	X3	X4	X5	X6	X7	X8	X9	X10	X11	X12	X13	X14	X15	Y1	Y2	Y3	Y4	Y5	Y6	Y7	Y8	Y9	Y10	Y11	Y12	Y13	Y14	Y15
--Stations
--SessionID	StationID	Station	StationUnit	StationDirection	Lane	LaneNumber	PavementType	SlabID	SlabPosition	SlabPositionCode	AsphaltTemperature	SurfaceTemperature	AirTemperature	Cracks	Time	GPSStatus	Satellites	UTC	Latitude	Longitude	Height	Comment
--Drops
--StationID	DropID	History	Stress	Force	D1	D2	D3	D4	D5	D6	D7	D8	D9	D10	D11	D12	D13	D14	D15

update fwd_drop set DEFLECTION_8_MICRONS=  where drop_id =

select D8, t.StationDirection
,
from Sessions s, Stations t, Drops d where  t.SessionID=s.SessionID and d.StationID=t.StationID

Parse file name
004OWPD042808


section_name
lane
direction
date (mmddyy)

select D8 as DEFLECTION_8_MICRONS
, s.SectionName as SECTION_NAME
, t.StationDirection as STATION_DIRECTION
, t.Lane as TEST_POSITION
, t.Time as DATE_STATION 
, d.Stress as STRESS_KPA
, D1  as DEFLECTION_1_MICRONS
, D2  as DEFLECTION_2_MICRONS
, D3  as DEFLECTION_3_MICRONS
, D4  as DEFLECTION_4_MICRONS
, D5  as DEFLECTION_5_MICRONS
, D6  as DEFLECTION_6_MICRONS
, D7  as DEFLECTION_7_MICRONS
, D9  as DEFLECTION_9_MICRONS
, D10 as DEFLECTION_10_MICRONS
from Sessions s, Stations t, Drops d where  t.SessionID=s.SessionID and d.StationID=t.StationID

select D8 as DEFLECTION_8_MICRONS, s.SectionName as SECTION_NAME, t.StationDirection as STATION_DIRECTION, t.Lane as TEST_POSITION, t.Time as DATE_STATION , d.Stress as STRESS_KPA, D1  as DEFLECTION_1_MICRONS, D2  as DEFLECTION_2_MICRONS, D3  as DEFLECTION_3_MICRONS, D4  as DEFLECTION_4_MICRONS, D5  as DEFLECTION_5_MICRONS, D6  as DEFLECTION_6_MICRONS, D7  as DEFLECTION_7_MICRONS, D9  as DEFLECTION_9_MICRONS, D10 as DEFLECTION_10_MICRONS from Sessions s, Stations t, Drops d where  t.SessionID=s.SessionID and d.StationID=t.StationID
