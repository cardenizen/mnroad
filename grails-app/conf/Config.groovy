// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

//if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
//}

grails.plugin.databasemigration.updateOnStart = true
grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
// Added for the export plugin
        pdf: 'application/pdf',
        excel: 'application/vnd.ms-excel',
        ods: 'application/vnd.oasis.opendocument.spreadsheet',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

grails.views.javascript.library="jquery"
grails.attachmentable.maxInMemorySize = 1024
grails.attachmentable.maxUploadSize = 1024000000
grails.attachmentable.uploadDir = '/temp'

grails.attachmentable.poster.evaluator = {
    [id: 1, name: 'Mihai', 'class': [name: 'SomeClass']]
}
// set per-environment serverURL stem for creating absolute links
environments {
    production {
      grails.serverURL = "http://www.changeme.com"
    }
    development {
      maxExportRowsPerTable = 16000
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    development {
      maxExportRowsPerTable = 16000
        grails.serverURL = "http://localhost:8080/${appName}"
    }
}

// Copied from www.gnumims.org
/**
 * Log4j configuration.
 * Causing this file to reload (e.g. edit+save) may break the appLog destination
 * and further logs will be written to files or directories like "[:]".
 * For more info see http://logging.apache.org/log4j/1.2/manual.html
 * For log levels see http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/Level.html
 * Basic log levels are ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF
 */
// Pickup the Tomcat/Catalina logDirectory else use the current dir.
def catalinaBase = System.properties.getProperty('catalina.base')
def logDirectory = catalinaBase ? "${catalinaBase}/logs" : '.'
log4j = {
    appenders {
        // Use if we want to prevent creation of a stacktrace.log file.
        'null' name:'stacktrace'

        // Use this if we want to modify the default appender called 'stdout'.
        console name:'stdout', layout:pattern(conversionPattern: '%d{dd-MMM@HH:mm:ss}-%m%n')

        // Custom log file.
        rollingFile name:"appLog",
                        file:"${logDirectory}/${appName}.log".toString(),
                        maxFileSize:'300kB',
                        maxBackupIndex:0,
                        layout:pattern(conversionPattern: '%d{[EEE, dd-MMM-yyyy @ HH:mm:ss.SSS]} [%t] %-5p %c %x - %m%n')
    }

    // Configure the root logger to output to stdout and appLog appenders.
    root {
        error 'stdout','appLog'
        additivity = true
    }

    // This is for the builtin stuff and from the default Grails-1.1.1 config.
    error 'org.codehaus.groovy.grails.web.servlet',  //  controllers
            'org.codehaus.groovy.grails.web.pages', //  GSP
            'org.codehaus.groovy.grails.web.sitemesh', //  layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping', // URL mapping
            'org.codehaus.groovy.grails.commons', // core / classloading
            'org.codehaus.groovy.grails.plugins', // plugins
            'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
            'org.springframework',
            'org.hibernate'

    warn   'org.mortbay.log' // Jetty

    error "grails.app" // Set the default log level for our app code.
    info "grails.app.bootstrap" // Set the log level per type and per type.class
//    error "grails.app.service.NavigationService"
//    error "grails.app.service.com.zeddware.grails.plugins.filterpane.FilterService"

    // Move anything that should behave differently into this section.
    switch(environment) {
        case 'development':
            debug "grails.app.service"
            debug "grails.app.controller"
            break
        case 'test':
            debug "grails.app.service"
            debug "grails.app.controller"
            info "us.mn.state.dot.mnroad"
            break
        case 'production':
            warn "grails.app.service"
            warn "grails.app.controller"
            info "us.mn.state.dot.mnroad"
//            info "grails.app.service.QueryService"
//            info "grails.app.service.PersonCsvService"
//            info "grails.app.service.InventoryCsvService"
            break
    }
}

ldap {
  server.url = "ldap://ldapad:389/"
  search.base = "DC=ad,DC=dot,DC=state,DC=mn,DC=us"
  search.user = "ldapbrowse"
  search.pass = "ldapbrowse"
  username.attribute = "sAMAccountName"
  usersearch.filter = "(&(sAMAccountName={0})(objectClass=person))"
  referrals = "follow"
  role.base = "DC=ad,DC=dot,DC=state,DC=mn,DC=us"
  role.search="(&(member={0})(objectClass=group))"
//  allowEmptyPasswords=true
//  skip.authentication = true
//  // skip.credentialsCheck if in development not connected to an LDAP server
//  skip.credentialsCheck  = true
  allowEmptyPasswords=false
  skip.authentication = false
  // skip.credentialsCheck if in development not connected to an LDAP server
  switch(environment) {
      case 'development':
        skip.credentialsCheck  = true
          break
      case 'test':
        skip.credentialsCheck  = false
          break
      case 'production':
          break
  }
}

mainlineLaneNames = ["Left Shldr","Passing","Driving","Right Shldr"]
lvrSouthNames = ["Inside Shldr","Inside","Outside","Outside Shldr"]
farmNames = ["Left Shldr","Westbound","Eastbound","Right Shldr"]

//jsecurity.authentication.strategy = new org.jsecurity.authc.pam.AtLeastOneSuccessfulModularAuthenticationStrategy()
currentSchema='MNR'
//exportOrder=["data":["us.mn.state.dot.mnroad.AppConfig","Facility","RoadSection","AggCell","CompositeCell","HmaCell","PccCell","Lane","Material","Layer","MaterialSample","Sensor","SensorEvaluation","SensorModel"]
//exportOrder=["data":["Facility","RoadSection","AggCell","CompositeCell","HmaCell","PccCell","Lane","Material","Layer","MaterialSample","Sensor","SensorEvaluation","SensorModel"]
exportOrder=["data":["Facility","RoadSection","Cell","Lane","Material","Layer","MaterialSample","Sensor","SensorEvaluation","SensorModel"]
        ,"security":["us.mn.state.dot.secure.JsecUserRoleRel","us.mn.state.dot.secure.JsecUser","us.mn.state.dot.secure.JsecRole", "us.mn.state.dot.secure.JsecPermission","us.mn.state.dot.secure.JsecRolePermissionRel","us.mn.state.dot.secure.JsecUserPermissionRel"]]
maxExportRowsPerTable = 100

//largeTables=['TC','VW','WM','XV']
//sensorReadingTableNames=['CR_VALUES','EC_VALUES','ET_VALUES','EW_VALUES','FG_VALUES','FL_VALUES','FP_VALUES','FT_VALUES','FW_VALUES','HC_VALUES','HD_VALUES','HV_VALUES','KB_VALUES','MC_VALUES','MH_VALUES','MM_VALUES','MT_VALUES','NT_VALUES','PL_VALUES','PT_VALUES','RE_VALUES','RG_VALUES','RH_VALUES','RM_VALUES','RP_VALUES','RT_VALUES','RW_VALUES','SW_VALUES','TB_VALUES','TD_VALUES','TL_VALUES','TM_VALUES','TT_VALUES','VG_VALUES','VM_VALUES','XB_VALUES','XG_VALUES','XH_VALUES','XL_VALUES','XM_VALUES','XS_VALUES','XT_VALUES','TC_VALUES_ALL','VW_VALUES_ALL','WM_VALUES_ALL','XV_VALUES_ALL']

liveTrafficDataroot = "\\\\mrl2k3cadd\\MnRoad\$/MnROAD"
liveTrafficDatasource = [
    "mnroad" : "${liveTrafficDataroot}"
  , "dataCollection" : "Data - Collection"
  , "type"   : "Dynamic Load Testing"
  , "season" : "Mainline Summer 2009"
  , "cell"   : "3"
//  , "date" : "10-01-26"
  ]

// Special Handling must be added for certain tables
// larges tables hold readings in separate tables for each year
// and a view is used to read them all as one table
// TC_VALUES_ALL, VW_VALUES_ALL, WM_VALUES_ALL, XV_VALUES_ALL
largeTables=['TC','VW','WM','XV']
useLargeTables=false
//largeTables=[] // Used for testing on XE database
// In some tables the reading(s) is stored in a column named other than "VALUE"
valueColumnNames = [
 'TB_VALUES':'NORTH_VALUE,SOUTH_VALUE'
,'TD_VALUES':'VALUE,LA'
,'HD_VALUES':'TEMPDIFF,INITTEMP,TEMPAT39SEC'
]

constrPeriodLenDays=63
oracleDefaultQueryFormat = "dd-MMM-yyyy"
zipFileNameBase = "mnroad.zip"
batchSize=5000
firstSensorReadingYear = 1993
beginCell=0

disableBatchJobs = true

uncdrive = "\\\\Ad\\Mrl\\SECTIONS\\RESEARCH\\"
//uncdrive = "\\\\mrl2k3cadd\\research\$\\"
wrdrive = "R:\\"
cdrive = "C:\\"
rdrive = "${uncdrive}"
loaderSampleFiles = "${rdrive}MnROAD\\Data - Sensors\\LoaderSampleFiles\\LoaderDataFeb-10-2011"
//fileUploadFolder="MnROAD\\UploadedFiles"
fileUploadFolder="/Java/Tomcat 6.0/uploads"
//fileUploadBackupSubdir="backup"

dataProductVersion="1.0"
dataProductDataFolder = "MnROAD\\Software\\Data Product v ${dataProductVersion}\\"
overwriteLayerFiles = true
exportLayersFiles = false
overwriteSensorFiles = true
exportSensorFiles = false
overwriteFwdFiles = true
exportFwdFiles = true
overwriteDistressFiles = true
exportDistressFiles = false
overwriteDcpFiles = true
exportDcpFiles = true
overwriteMatSamplesFiles = true
exportMatSamplesFiles = false
overwriteTransverseJointsFiles = true
exportTransverseJointsFiles = false
ddByType = "D. Test Cell Maps and Information\\Design Details by Pavement Type"
sensorByType = "E. Sensor Information\\Sensor Details by Pavement Type"
measuredDataFolder = "H. Measured Data\\"
materialSamplesDataFolder = "I. Material Samples\\"
loadFolders = "Load Response\\FWD"
physMeasureFolder = "Physical Measurements"
matTestsFolder = "Material Tests"

qFileUploadFolder = "${rdrive}${fileUploadFolder}"
loaderFiles = "\\\\mrl2k3loader\\Loader\\loader_files\\campbell_cs"
downloadMetadataSheetname="dbColumns"
pavementTypes = ["AggCell":"AGG - Aggregate","CompositeCell":"Composite - HMA & PCC","HmaCell":"HMA - Asphalt","PccCell":"PCC - Concrete"]
//validExportTargets = ["layer","sensor","drop"]
excludedColumns = ["date_created","created_by","last_updated","last_updated_by","version","road_section_id","sensor_id"]
fieldsExcludedFromUpdate = ['id', 'dateCreated', 'createdBy', 'dateModified', 'modifiedBy']
distressTableFolderMap = [
         "DISTRESS_AC"                   :"Surface Characteristics\\Visual Distress"
        ,"DISTRESS_AGG_SURVEY_SEMI"      :"Surface Characteristics\\Visual Distress"
        ,"DISTRESS_ALPS_RESULTS_RUT"     :"Surface Characteristics\\Asphalt Rutting"
        ,"DISTRESS_CIRCULR_TEXTR_METER"  :"Surface Characteristics\\Texture"
        ,"DISTRESS_CUPPING"              :"Surface Characteristics\\Crack Cupping"
        ,"DISTRESS_FRICTION_DFT"         :"Surface Characteristics\\Friction"
        ,"DISTRESS_FRICTION_TRAILER"     :"Surface Characteristics\\Friction"
        ,"DISTRESS_JPCC"                 :"Surface Characteristics\\Visual Distress"
        ,"DISTRESS_LANE_SHOULDER_DROPOFF":"Surface Characteristics\\Lane - Shoulder Drop-off"
        ,"DCP_LOCATION"                  :"Soil Strength\\DCP - Dynamic Cone Penetrometer"
        ,"DISTRESS_LIGHTWEIGHT_DEFLECT"  :"Soil Strength\\LWD - Lightweight Deflectometer"
        ,"DISTRESS_NUCLEAR_DENSITY"      :"Surface Characteristics\\Nuclear Density"
        ,"DISTRESS_OBSI_DATA"            :"Surface Characteristics\\Noise"
        ,"DISTRESS_OBSI_SUMMARY"         :"Surface Characteristics\\Noise"
        ,"DISTRESS_PCC_FAULTS"           :"Surface Characteristics\\Joint Faulting"
        ,"DISTRESS_PERMEABILITY_DIRECT"  :"Surface Characteristics\\Permeability"
        ,"DISTRESS_RIDE_LISA"            :"Surface Characteristics\\Ride Quality"
        ,"DISTRESS_RIDE_PATHWAYS"        :"Surface Characteristics\\Ride Quality"
        ,"DISTRESS_RIDE_PAVETECH"        :"Surface Characteristics\\Ride Quality"
        ,"DISTRESS_RUTTING_DIPSTICK"     :"Surface Characteristics\\Asphalt Rutting"
        ,"DISTRESS_RUTTING_STRAIGHT_EDGE":"Surface Characteristics\\Asphalt Rutting"
        ,"DISTRESS_SAND_PATCH"           :"Surface Characteristics\\Texture"
        ,"DISTRESS_SCHMIDT_HAMMER"       :"Surface Characteristics\\PCC Strength"
        ,"DISTRESS_SOUND_ABSORPTION"     :"Surface Characteristics\\Noise"
        ,"DISTRESS_WATER_PERMEABILITY"   :"Surface Characteristics\\Permeability"
]

distressFileNameMap=[
         "DISTRESS_AC"                :"HMA_DISTRESS"
        ,"DISTRESS_JPCC"              :"PCC_DISTRESS"
        ,"DISTRESS_AGG_SURVEY_SEMI"   :"AGG_DISTRESS"
        ,"DISTRESS_PCC_FAULTS"        :"PCC_JOINT_FAULTING"
        ,"DISTRESS_WATER_PERMEABILITY":"PERMEABILITY"
]

distressNaturalKeyMap = [
 "DISTRESS_AC"                   :"CELL,LANE,DAY"
,"DISTRESS_AGG_SURVEY_SEMI"      :"CELL,LANE,DAY"
,"DISTRESS_ALPS_DATA"            :"CELL,LANE,DAY,HOUR_MIN_SEC,Y_STA_FT,X_OFFSET_FT,MEASUREMENT_TYPE,RECORD_NO"
,"DISTRESS_ALPS_RESULTS_RUT"     :"CELL,LANE,WHEELPATH,STATION,DAY,HOUR_MIN_SEC"
,"DISTRESS_CIRCULR_TEXTR_METER"  :"CELL,LANE,DAY,WHEEL_PATH,STATION,OFFSET_FT,FIELD_ID,TRIAL"
,"DISTRESS_CUPPING"              :"CELL,LANE,DAY,WHEEL_PATH,STATION,CUPPING_X_DISTANCE_INCH"
,"DISTRESS_FRICTION_DFT"         :"CELL,LANE,DAY,STATION,OFFSET_FT,WHEELPATH,RUN_NO,SPEED_KPH"
,"DISTRESS_FRICTION_TRAILER"     :"CELL,LANE,DAY,TIME,TIRE_TYPE,FRICTION_NUMBER"
,"DISTRESS_JPCC"                 :"CELL,LANE,DAY"
,"DISTRESS_LANE_SHOULDER_DROPOFF":"CELL,LANE,DAY,STATION,OFFSET_FT"
,"DISTRESS_LIGHTWEIGHT_DEFLECT"  :"CELL,DAY,STATION,OFFSET_FT,FIELD_TEST_NUMBER,S_MM"
,"DISTRESS_NUCLEAR_DENSITY"      :"CELL,LANE,DAY,STATION,OFFSET_FT,WHEELPATH,STATION_AVERAGE_PCF,MOISTURE_CONTENT_PCT"
,"DISTRESS_OBSI_DATA"            :"CELL,LANE,DAY,TIME,TRIAL,FREQ_HZ"
,"DISTRESS_OBSI_SUMMARY"         :"CELL,LANE,DAY,TIME,TRIAL"
,"DISTRESS_PCC_FAULTS"           :"CELL,LANE,DAY,SAMPLE_TIME,JOINT_NUMBER,OFFSET_FROM_CENTERLINE_FT"
,"DISTRESS_PERMEABILITY_DIRECT"  :"CELL,LANE,DAY,TIME,STATION,OFFSET_FT,PANEL_NO"
,"DISTRESS_RIDE_LISA"            :"CELL,LANE,DAY,WHEELPATH,RUN_NO"
,"DISTRESS_RIDE_PATHWAYS"        :"CELL,LANE,DAY,RUN"
,"DISTRESS_RIDE_PAVETECH"        :"CELL,LANE,DAY"
,"DISTRESS_RUTTING_DIPSTICK"     :"CELL,LANE,DAY,STATION"
,"DISTRESS_RUTTING_STRAIGHT_EDGE":"CELL,LANE,DAY,STATION"
,"DISTRESS_SAND_PATCH"           :"CELL,LANE,DAY,STATION,OFFSET_FT,FIELD_ID"
,"DISTRESS_SCHMIDT_HAMMER"       :"CELL,LANE,DAY,STATION,OFFSET_FT,COMP_STRENGTH_PSI"
,"DISTRESS_SOUND_ABSORPTION"     :"CELL,LANE,DAY,STATION,WHEELPATH,TRIAL,FREQUENCY_HZ"
,"DISTRESS_WARP_CURL"            :"CELL,LANE,DAY,TIME,PANEL,TEST_RUN,STATION,OFFSET_FT"
,"DISTRESS_WATER_PERMEABILITY"   :"CELL,LANE,DAY,STATION,OFFSET_FT,FLOW_TIME_S,INITIAL_HEAD_CM,FINAL_HEAD_CM"
]

CellsQuery = """SELECT ID, CELL, TYPE, FROM_DATE
, CASE
  WHEN CS.TO_DATE IS NOT NULL THEN CS.TO_DATE
  ELSE LEAD(FIRST_LAYER_DATE,1,SYSDATE)  OVER
      (PARTITION BY CELL ORDER BY CELL,FROM_DATE)
END TO_DATE
FROM MNR.CELLS CS WHERE TYPE=?"""

AggCellQuery = """SELECT MC.CELL CELL_NUMBER
 ,C.NAME CELL_NAME
 ,NVL(TO_CHAR(C.DESIGN_LIFE),'') DESIGN_LIFE_YRS
 ,F.NAME FACILITY
 ,R.DESCRIPTION ROAD_SECTION
 ,TO_CHAR(MC.FROM_DATE,'yyyy-mm-dd') BUILT_DATE
 ,TO_CHAR(MC.TO_DATE,'yyyy-mm-dd') DEMOLISHED_DATE
 ,C.START_STATION START_STATION_FT
 ,C.END_STATION END_STATION_FT
 ,C.DRAINAGE_SYSTEM DRAINAGE_SYSTEM
 ,C.SHOULDER_TYPE SHOULDER_TYPE
 ,LN.NAME LANE_NAME
 ,LN.LANE_NUM
 ,LN.WIDTH LANE_WIDTH_FT
 ,LN.OFFSET_REF LANE_OFFSET_REF
 ,L.LAYER_NUM LAYER_NUM
 ,TO_CHAR(L.CONSTRUCT_START_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_START_DATE
 ,TO_CHAR(L.CONSTRUCT_END_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_END_DATE
 ,L.THICKNESS/25.4 LAYER_THICKNESS_IN
 ,NVL(M.MN_DOT_SPEC_NUMBER,'n/a') SPEC_NUMBER
 ,M.BASIC_MATERIAL || ' - ' || M.DESCRIPTION  MATERIAL
 ,MC.ID CELL_ID
 ,LN.ID LANE_ID
 ,L.ID LAYER_ID
 ,M.ID MATERIAL_ID
FROM MNR.CELLS MC JOIN MNR.CELL C ON MC.ID=C.ID
 JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
 JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
 JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
 JOIN MNR.ROAD_SECTION R ON C.ROAD_SECTION_ID=R.ID
 JOIN MNR.FACILITY F ON R.FACILITY_ID=F.ID
"""

CompositeCellQuery = """SELECT
MC.CELL CELL_NUMBER
 ,C.NAME CELL_NAME
 ,NVL(TO_CHAR(C.DESIGN_LIFE),'') DESIGN_LIFE_YRS
 ,F.NAME FACILITY
 ,R.DESCRIPTION ROAD_SECTION
 ,TO_CHAR(L.CONSTRUCT_START_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_START_DATE
 ,TO_CHAR(L.CONSTRUCT_END_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_END_DATE
 ,TO_CHAR(MC.FROM_DATE,'yyyy-mm-dd') BUILT_DATE
 ,TO_CHAR(MC.TO_DATE,'yyyy-mm-dd') DEMOLISHED_DATE
 ,C.START_STATION START_STATION_FT
 ,C.END_STATION END_STATION_FT
 ,C.DRAINAGE_SYSTEM DRAINAGE_SYSTEM
 ,C.SHOULDER_TYPE SHOULDER_TYPE
 ,LN.NAME LANE_NAME
 ,LN.LANE_NUM
 ,LN.WIDTH LANE_WIDTH_FT
 ,LN.OFFSET_REF LANE_OFFSET_REF
 ,LN.PANEL_LENGTH PANEL_LENGTH_FT
 ,LN.PANEL_WIDTH PANEL_WIDTH_FT
 ,L.LAYER_NUM LAYER_NUM
 ,L.THICKNESS/25.4 LAYER_THICKNESS_IN
 ,NVL(M.MN_DOT_SPEC_NUMBER,'n/a') SPEC_NUMBER
 ,M.BASIC_MATERIAL || ' - ' || M.DESCRIPTION  MATERIAL
 ,C.LONGITUDINAL_JOINT_SEAL
 ,C.SURFACE_TEXTURE
 ,C.TIEBARS
 ,L.TRANSVERSE_STEEL
 ,MC.ID CELL_ID
 ,LN.ID LANE_ID
 ,L.ID LAYER_ID
 ,M.ID MATERIAL_ID
FROM MNR.CELLS MC JOIN MNR.CELL C ON MC.ID=C.ID
 JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
 JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
 JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
 JOIN MNR.ROAD_SECTION R ON C.ROAD_SECTION_ID=R.ID
 JOIN MNR.FACILITY F ON R.FACILITY_ID=F.ID
"""

HmaCellQuery = """SELECT
MC.CELL CELL_NUMBER
 ,C.NAME CELL_NAME
 ,NVL(TO_CHAR(C.DESIGN_LIFE),'') DESIGN_LIFE_YRS
 ,F.NAME FACILITY
 ,R.DESCRIPTION ROAD_SECTION
 ,TO_CHAR(L.CONSTRUCT_START_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_START_DATE
 ,TO_CHAR(L.CONSTRUCT_END_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_END_DATE
 ,TO_CHAR(MC.FROM_DATE,'yyyy-mm-dd') BUILT_DATE
 ,TO_CHAR(MC.TO_DATE,'yyyy-mm-dd') DEMOLISHED_DATE
 ,C.START_STATION START_STATION_FT
 ,C.END_STATION END_STATION_FT
 ,C.DRAINAGE_SYSTEM DRAINAGE_SYSTEM
 ,C.SHOULDER_TYPE SHOULDER_TYPE
 ,LN.NAME LANE_NAME
 ,LN.LANE_NUM
 ,LN.WIDTH LANE_WIDTH_FT
 ,LN.OFFSET_REF LANE_OFFSET_REF
 ,L.LAYER_NUM LAYER_NUM
 ,L.THICKNESS/25.4 LAYER_THICKNESS_IN
 ,NVL(M.MN_DOT_SPEC_NUMBER,'n/a') SPEC_NUMBER
 ,M.BASIC_MATERIAL || ' - ' || M.DESCRIPTION  MATERIAL
 ,C.HMA_DESIGN
 ,C.SURFACE_TEXTURE
 ,MC.ID CELL_ID
 ,LN.ID LANE_ID
 ,L.ID LAYER_ID
 ,M.ID MATERIAL_ID
FROM MNR.CELLS MC JOIN MNR.CELL C ON MC.ID=C.ID
 JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
 JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
 JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
 JOIN MNR.ROAD_SECTION R ON C.ROAD_SECTION_ID=R.ID
 JOIN MNR.FACILITY F ON R.FACILITY_ID=F.ID
"""

PccCellQuery = """SELECT
MC.CELL CELL_NUMBER
 ,C.NAME CELL_NAME
 ,NVL(TO_CHAR(C.DESIGN_LIFE),'') DESIGN_LIFE_YRS
 ,F.NAME FACILITY
 ,R.DESCRIPTION ROAD_SECTION
 ,TO_CHAR(L.CONSTRUCT_START_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_START_DATE
 ,TO_CHAR(L.CONSTRUCT_END_DATE,'yyyy-mm-dd') LAYER_CONSTRUCT_END_DATE
 ,TO_CHAR(MC.FROM_DATE,'yyyy-mm-dd') BUILT_DATE
 ,TO_CHAR(MC.TO_DATE,'yyyy-mm-dd') DEMOLISHED_DATE
 ,C.START_STATION START_STATION_FT
 ,C.END_STATION END_STATION_FT
 ,C.DRAINAGE_SYSTEM DRAINAGE_SYSTEM
 ,C.SHOULDER_TYPE SHOULDER_TYPE
 ,LN.NAME LANE_NAME
 ,LN.LANE_NUM
 ,LN.WIDTH LANE_WIDTH_FT
 ,LN.OFFSET_REF LANE_OFFSET_REF
 ,LN.PANEL_LENGTH PANEL_LENGTH_FT
 ,LN.PANEL_WIDTH PANEL_WIDTH_FT
 ,L.LAYER_NUM LAYER_NUM
 ,L.THICKNESS/25.4 LAYER_THICKNESS_IN
 ,NVL(M.MN_DOT_SPEC_NUMBER,'n/a') SPEC_NUMBER
 ,M.BASIC_MATERIAL || ' - ' || M.DESCRIPTION  MATERIAL
 ,C.LONGITUDINAL_JOINT_SEAL
 ,C.SURFACE_TEXTURE
 ,C.TIEBARS
 ,L.TRANSVERSE_STEEL
 ,MC.ID CELL_ID
 ,LN.ID LANE_ID
 ,L.ID LAYER_ID
 ,M.ID MATERIAL_ID
FROM MNR.CELLS MC JOIN MNR.CELL C ON MC.ID=C.ID
 JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
 JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
 JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
 JOIN MNR.ROAD_SECTION R ON C.ROAD_SECTION_ID=R.ID
 JOIN MNR.FACILITY F ON R.FACILITY_ID=F.ID
 """

sensorQuery = """
SELECT
C.CELL_NUMBER CELL
 ,C.NAME CELL_NAME
 ,SUBSTR(C.CLASS,24) PAVEMENT_TYPE
 ,SM.MODEL
 ,S.SEQ SEQUENCE
 ,S.SENSOR_DEPTH_IN DEPTH_IN
 ,S.STATION_FT
 ,S.OFFSET_FT
 ,M.BASIC_MATERIAL LAYER_TYPE
 ,TO_CHAR(S.DATE_INSTALLED,'yyyy-mm-dd') DATE_INSTALLED
 ,TO_CHAR(S.DATE_REMOVED,'yyyy-mm-dd') DATE_REMOVED
 ,C.ID CELL_ID
 ,LN.ID LANE_ID
 ,L.ID LAYER_ID
FROM MNR.CELL C
 JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
 JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
 JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
 JOIN MNR.SENSOR S ON S.LAYER_ID = L.ID
 JOIN MNR.SENSOR_MODEL SM ON S.SENSOR_MODEL_ID = SM.ID
"""

fwdQuery="""
SELECT S.SESSION_ID
,S.FACILITY_NAME
,S.SECTION_NAME CELL
,S.LANE
,TO_CHAR(S.SESSION_DATE,'yyyy-mm-dd') SESSION_DATE
,S.UNITS
,S.PAVEMENT_TYPE
,S.RADIUS_PLATE_MM
,S.SENSOR_OFFSET_X1_MM
,S.SENSOR_OFFSET_X2_MM
,S.SENSOR_OFFSET_X3_MM
,S.SENSOR_OFFSET_X4_MM
,S.SENSOR_OFFSET_X5_MM
,S.SENSOR_OFFSET_X6_MM
,S.SENSOR_OFFSET_X7_MM
,S.SENSOR_OFFSET_X8_MM
,S.SENSOR_OFFSET_X9_MM
,S.SENSOR_OFFSET_X10_MM
,T.STATION_ID
,T.TEST_SEQUENCE
,T.SLAB_ID
,T.TEST_POSITION
,T.STATION
,T.DATE_STATION
,T.TIME_STATION
,T.SURFACE_INFRARED_TEMP_C
,T.AIR_TEMP_C
,T.COMMENT_STATION
,D.DROP_ID
,D.STRESS_KPA
,D.SEQUENCE_NO
,D.DEFLECTION_1_MICRONS
,D.DEFLECTION_2_MICRONS
,D.DEFLECTION_3_MICRONS
,D.DEFLECTION_4_MICRONS
,D.DEFLECTION_5_MICRONS
,D.DEFLECTION_6_MICRONS
,D.DEFLECTION_7_MICRONS
,D.DEFLECTION_8_MICRONS
,D.DEFLECTION_9_MICRONS
,D.DEFLECTION_10_MICRONS
FROM
MNR.CELLS MC JOIN
MNR.FWD_SESSION S
ON TO_CHAR(MC.CELL)=S.SECTION_NAME AND S.SESSION_DATE BETWEEN MC.FROM_DATE AND NVL(MC.TO_DATE,SYSDATE)
JOIN MNR.FWD_STATION T ON T.SESSION_ID=S.SESSION_ID
JOIN MNR.FWD_DROP D ON D.STATION_ID=T.STATION_ID
"""

jointDowelFolder = "${rdrive}${dataProductDataFolder}${ddByType}\\PCC - Concrete\\Concrete Panel Details"
trjQuery="""
SELECT
 MC.CELL CELL_NUMBER
,LN.NAME LANE_NAME
,L.LAYER_NUM LAYER_NUM
,TJ.STATION
,TJ.JOINT_NUMBER
,TJ.SEALANT_TYPE TRANVERSE_JOINT_SEALANT_TYPE
,D.TYPE DOWEL_TYPE
,D.DOWEL_NUMBER
,D.DIAMETER_WIDTH DOWEL_DIAMETER_IN
,D.LENGTH BAR_LENGTH_IN
,D.EMBEDMENT_LENGTH_IN
,D.TRANSVERSE_OFFSET_IN
,MC.ID CELL_ID
,LN.ID LANE_ID
,L.ID LAYER_ID
FROM
MNR.CELLS MC JOIN MNR.CELL C ON MC.ID=C.ID
JOIN MNR.LANE LN ON LN.CELL_ID=C.ID
JOIN MNR.LAYER L ON L.LANE_ID=LN.ID
JOIN MNR.MATERIAL M ON L.MATERIAL_ID=M.ID
JOIN MNR.ROAD_SECTION R ON C.ROAD_SECTION_ID=R.ID
JOIN MNR.FACILITY F ON R.FACILITY_ID=F.ID
JOIN MNR.TRANSVERSE_JOINT TJ ON TJ.LAYER_ID=L.ID
JOIN MNR.DOWEL_BAR D ON D.TR_JOINT_ID=TJ.ID
"""

dcpFolder = "${rdrive}${dataProductDataFolder}${ddByType}\\PCC - Concrete\\Concrete Panel Details"
dcpQuery="""
SELECT
L.DCP_ID
,L.PROJECT_DESC
,L.CELL
,L.STATION
,L.OFFSET_FT
,L.DAY
,L.LAYER
,L.COMMENTS
,L.MOISTURE_PERCENT
,L.DENSITY_PCF
,L.DEPTH_BELOW_GRADE_IN
,V.BLOW_COUNT
,V.PROBE_DEPTH_IN
 FROM MNR.DCP_LOCATION L JOIN MNR.DCP_VALUES V ON V.DCP_ID=L.DCP_ID
"""
//ORDER BY CELL,STATION,OFFSET_FT

matSamplesQuery = """
SELECT UNIQUE
MS.CELL
,MS.MNROAD_ID
,MS.STATION
,MS.OFFSET
,MS.SAMPLE_DATE
,MS.MATERIAL_GROUP
,MS.CONTAINER_TYPE
,MS.STORAGE_LOCATION
,MS.COMMENTS
,MS.FIELD_ID
,MS.CONTACT_PERSON
,MS.COURSE
,MS.LIFT_NUMBER
,MS.DEPTH_CODE
,MS.SAMPLE_DEPTH_TOP
,MS.SAMPLE_DEPTH_BOTTOM
,MS.SAMPLE_TIME
,MS.SAMPLE_CURE_TIME
,MS.SPEC
,MST.*
FROM
MNR.MAT_SAMPLES MS
JOIN MNR.CELLS CS ON CS.CELL=MS.CELL AND MS.SAMPLE_DATE BETWEEN CS.FIRST_LAYER_DATE AND NVL(CS.TO_DATE,SYSDATE)
"""
  //-- JOIN MNR.MAT_BINDER_ABCD_TEST MST ON MST.MNROAD_ID=MS.MNROAD_ID
//--ORDER BY MS.MNROAD_ID,MST.SAMPLE_DESCRIPTION

matTableFolderMap = [
"MAT_BINDER_ABCD_TEST"           :""
,"MAT_BINDER_BBR_TEST"           :""
,"MAT_BINDER_CRITICAL_CRACK_TEMP":""
,"MAT_BINDER_DENT_FRACTURE"      :""
,"MAT_BINDER_DILATOMETR_TST"     :""
,"MAT_BINDER_DSR_TESTS"          :""
,"MAT_BINDER_DT_TEST"            :""
,"MAT_BINDER_FATIGUE"            :""
,"MAT_BINDER_REPEATED_CREEP"     :""
,"MAT_BINDER_STRAIN_SWEEPS"      :""
,"MAT_BINDER_TRAD_TESTS"         :""
,"MAT_CONC_AIR_VOID_RESULTS"     :""
,"MAT_CONC_FIELD_RESULTS"        :""
,"MAT_CONC_FLEX_STRENGTH"        :""
,"MAT_CONC_FREEZE_THAW_RESULTS"  :""
,"MAT_CONC_MIX_GRAD_RESULTS"     :""
,"MAT_CONC_MOD_POISSON_RESULTS"  :""
,"MAT_CONC_RAPID_CHLORIDE"       :""
,"MAT_CONC_STRENGTH_RESULTS"     :""
,"MAT_CONC_THERMAL_EXPANSION"    :""
,"MAT_CORE_LENGTHS"              :""
,"MAT_HMA_AGING"                 :""
,"MAT_HMA_APA"                   :""
,"MAT_HMA_BBR_TEST"              :""
,"MAT_HMA_COMPLEX_SHEAR_MODU"    :""
,"MAT_HMA_CORE_TESTS"            :""
,"MAT_HMA_DCT_TEST"              :""
,"MAT_HMA_DILATOMETRIC_TEST"     :""
,"MAT_HMA_DYNAMIC_MODULUS"       :""
,"MAT_HMA_FLOW_NUMBER"           :""
,"MAT_HMA_HAMBURG"               :""
,"MAT_HMA_IDT_TEST"              :""
,"MAT_HMA_INDIRECT_TENS_FATI"    :""
,"MAT_HMA_MIX_TESTS"             :""
,"MAT_HMA_ORIGINAL_DENSITY_AIR"  :""
,"MAT_HMA_REPEAT_PERM_DEFORM"    :""
,"MAT_HMA_REPEAT_SHEAR"          :""
,"MAT_HMA_SCB_TEST"              :""
,"MAT_HMA_SENB_TEST"             :""
,"MAT_HMA_SIEVE_DATA"            :""
,"MAT_HMA_TRIAXIAL_STATIC_CREEP" :""
,"MAT_HMA_TRIAXIAL_STRENGTH"     :""
,"MAT_HMA_TSRST_TEST"            :""
,"MAT_HMA_TTI_OVERLAY"           :""
,"MAT_HMA_ULTRASONIC_MODULUS"    :""
,"MAT_SOIL_MR_RESULTS"           :""
,"MAT_SOIL_TESTS"                :""
,"MAT_UNBOUND_GRADATIONS"        :""
,"MAT_UNBOUND_TUBE_SUCTION"      :""
]

// To use this in dev to access test data add -Dpassword=xxxxxxx as a VM option.
// e.g. -XX:PermSize=64m -XX:MaxPermSize=128m -Dpassword=xxxxxxx
grails.naming.entries = [
  'jdbc/test_mnr': [  // This binding used for run-app in the test environment
    type: "javax.sql.DataSource", //required
    auth: "Container", // optional
    description: "Data source for ...",
    url: "jdbc:oracle:thin:@MRL2K3dev.ad.dot.state.mn.us:1521:DEV11",
    username: "mnru",
    password: "${System.getProperty('password')}".toString(),
    driverClassName: "oracle.jdbc.driver.OracleDriver",
    maxActive: "8",
    maxIdle: "4",
    removeAbandoned: "true",
    removeAbandonedTimeout: "60",
    testOnBorrow: "true",
    logAbandoned: "true",
    factory: "org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory",
    validationQuery: "select count(*) from dual",
    maxWait: "-1"
  ],
  'java:comp/env/jdbc/test_mnr': [ // This binding used for test-app in the test environment
    type: "javax.sql.DataSource", //required
    auth: "Container", // optional
    description: "Data source for ...",
    url: "jdbc:oracle:thin:@MRL2K3dev.ad.dot.state.mn.us:1521:DEV11",
    username: "mnru",
    password: "${System.getProperty('password')}".toString(),
    driverClassName: "oracle.jdbc.driver.OracleDriver",
    maxActive: "8",
    maxIdle: "4",
    removeAbandoned: "true",
    removeAbandonedTimeout: "60",
    testOnBorrow: "true",
    logAbandoned: "true",
    factory: "org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory",
    validationQuery: "select count(*) from dual",
    maxWait: "-1"
  ]
]

