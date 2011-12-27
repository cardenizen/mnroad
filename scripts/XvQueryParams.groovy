/*
This is the parameter configuration info for DbQuery.groovy.  
Supply the appropriate parameters, place this file into an empty subdirectory of mnroad.
Run:
  groovy.bat  -classpath ..\target\bigfile.jar C:\grailsApps\1.3.3\mnroad\src\groovy\\us\mn\state\dot\mnroad\DbQuery.groovy year=
  Note the Unicode backslash u escape to prevent "antlr.TokenStreamIOException: Did not find four digit hex character code. line: 5 col:85"
Requires bigfile.jar in the ..\target directory.  bigfile.jar can be built from src\java with this command:
  ant -f bigfile_jar.xml
*/

beginCell=0
batchSize=5000

db {
  server = 'jdbc:oracle:thin:@localhost:1521:XE'
  user = 'mnr'
  pw = 'mnr'
}

// Special Handling must be added for certain tables
// larges tables hold readings in separate tables for each year
// and a view is used to read them all as one table
// TC_VALUES_ALL, VW_VALUES_ALL, WM_VALUES_ALL, XV_VALUES_ALL
largeTables=['TC','VW','WM','XV']
//largeTables=[] // Used for testing on XE database
// In some tables the reading(s) is stored in a column named other than "VALUE"
valueColumnNames = [
 'TB_VALUES':'NORTH_VALUE,SOUTH_VALUE'
,'TD_VALUES':'VALUE,LA'
,'HD_VALUES':'TEMPDIFF,INITTEMP,TEMPAT39SEC'
]

oracleDefaultQueryFormat = "dd-MMM-yyyy"
zipFileNameBase = "mnroad.zip"
batchSize=5000
firstSensorReadingYear = 1996

environments {
  development {
       db {
         server = 'jdbc:oracle:thin:@localhost:1521:XE'
         user = 'mnr'
         pw = 'mnr'
       }
  }
  test {
       db {
         server = 'jdbc:oracle:thin:@MRL2K3dev.ad.dot.state.mn.us:1521:DEV11'
         user = 'mnr'
         pw = 'dev11mnr'
       }
  }
  production {
      db {
        server = 'jdbc:oracle:thin:@MRL2K3MRDB.ad.dot.state.mn.us:1521:mnrd'
        user = 'mnr'
        pw = 'eiei0'
      }
      //beginCell=27
  }
}

