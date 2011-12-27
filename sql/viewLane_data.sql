create or replace view lane_data as
select
C.ID                       CELL_ID
,SUBSTR(C.CLASS,24)        CELL_TYPE                  
,C.CELL_NUMBER             CELL
,C.CONSTRUCTION_ENDED_DATE
,C.DEMOLISHED_DATE        
,C.DESIGN_LIFE            
,C.DRAINAGE_SYSTEM        
,C.CELL_END_STATION       
,C.END_STATION              CELL_DESCRIPTION
,C.NAME                   
,C.ROAD_SECTION_ID        
,C.CELL_START_STATION     
,C.START_STATION          
,C.HMA_DESIGN             
,C.FIBER_TYPE             
,C.SURFACE_TEXTURE        
,C.SHOULDER_TYPE          
,C.MNDOT_MIX_SPECIFICATION
,C.TIEBARS                
,C.LONGITUDINAL_JOINT_SEAL
,LN.ID                     LANE_ID
,LN.LANE_NUM               
,LN.NAME                   LANE
,LN.WIDTH                  
,LN.PANEL_LENGTH           
,LN.PANEL_WIDTH            
,LN.OFFSET_REF 
FROM MNR.CELL C JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
