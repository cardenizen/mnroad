//package us.mn.state.dot.mnroad
/**
 * Created by IntelliJ IDEA.
 * User: Carr1Den
 * Date: Oct 5, 2010
 * Time: 6:12:19 PM
 * To change this template use File | Settings | File Templates.
 */
import groovy.sql.Sql

public class RefreshReadingCounts {

  static def  sql
  static def config

  static main (args) {
    // Command line:
    // Oracle JDBC driver named ojdbc6.jar must be in the current directory
		// set JAVA_OPTS=-Xms128m -Xmx512m
		// groovy TcQuery.groovy -e prod

    def om = options(args)
    if (!om)
        return
    try {
      def cf = new File('TcQueryParams.groovy')
      config = new ConfigSlurper(om.env).parse(cf.toURL())
    } catch (java.io.FileNotFoundException fnfe) {
      println "${fnfe}"
      return
    }
    RefreshReadingCounts dbq = new RefreshReadingCounts()
    dbq.initiate(config.db.server, config.db.user, config.db.pw)
    if (!sql) {
      println "Unable to connect to the database."
      System.exit(1)
    }
    def msg = "Connected to the ${om.env} environment."
    println msg
    dbq.refreshReadingCounts()

  } // end main

  void initiate(String url, String user, String pw) {
    try {
      this.class.classLoader.rootLoader.addURL(new URL("file:../lib/ojdbc6.jar"))
      sql = Sql.newInstance( url, user, pw )
    } catch (Exception e) {
      println "Unable to connect to the database: ${e.message}."
    }
  } // end initiate


    static def options (def args) {
        def om = [:]
        def validEnvs = ['production','test','development']
        def shortEnvs = ['prod','test','dev']
        def cli = new CliBuilder()
        cli.with
            {
            h(longOpt: 'help', 'Help - Usage Information')
            e(longOpt: 'name', '(production | test | development)', args: 1, type: String, required: true)
            c(longOpt: 'cell', 'Start at cell', args: 1, type: Integer, optionalArg: true)
            y(longOpt: 'year', 'Start at selected year in the selsected cell', args: 1, type: Integer, optionalArg: true)
            }
        def opt = cli.parse(args)
        if (!opt)
            return
        if (opt.h)
            cli.usage()
        def given = opt.e
        if (!validEnvs.contains(opt.e))     {
            if (!shortEnvs.contains(opt.e))     {
                cli.usage()
                return
            }
            if (opt.e == 'dev')
                given = 'development'
            if (opt.e == 'prod')
                given = 'production'
        }
        om.put('env', given)
        om.put('cell', (opt.c.class.toString().endsWith("String"))?opt.c:"0")
        om.put('year', (opt.y.class.toString().endsWith("String"))?opt.y:"0")
        return om
    }

  def refreshReadingCounts() {
    boolean tf = false
    def table
      try {
        def tnfm = tableNamesFromModels(config)
        tnfm.each { tableName ->
          table = tableName.split(":")[0]
          def qdel = "DELETE FROM SENSOR_COUNTS WHERE TABLE_NAME = ?"
          tf = sql.execute(qdel.toString(),[table])
          println "${sql.updateCount} deleted from ${table}."
          def q = "insert into sensor_counts select distinct '${table}' table_name,cell,seq,min(day) from_day,max(day) to_day,count(*) num_readings,sysdate as_of from ${table} group by cell,seq,to_number(to_char(day,'yyyy')) order by TABLE_NAME,CELL,SEQ,min(day)"
          tf = sql.execute(q.toString())
          println "${sql.updateCount} inserted into ${table}."
        }
      } catch (java.sql.SQLSyntaxErrorException sqlse) {
        println "${sqlse.message} - ${table}."
      } catch (java.sql.SQLException sqle) {
        println "${sqle.message} - ${table}."
      } catch (Exception e) {
        println e.message
      }

  }


    /*
     *
     * This method returns a list on table names in which sensor reading values are stored.  Most tables contain
     * only a single value column named 'VALUE'.  Those that do not follow this convention are identified as a member
     * of the "valueColumnNames" map in Config.groovy.  The map key is table name and the value is a comma separated
     * list of the names of columns which hold sensor reading values.
     *
     */
    def tableNamesFromModels(def config) {
      def cabinetTempModel='CT'
      def rc = []
      def q = "SELECT substr(table_name,1,2) model_from_table_names FROM user_tables where table_name like '%_VALUES' and substr(table_name,3,2)='_V' order by table_name"

      def ans = sql.rows(q.toString())
      ans.each { row ->
        if (row.MODEL_FROM_TABLE_NAMES != cabinetTempModel)
          rc << row.MODEL_FROM_TABLE_NAMES
      }

      // Adjust names for the large table model types to use Views
      def tableNames = rc.collect {
        if (config.largeTables.contains(it)) {
            it+'_VALUES_ALL'  // or use te View
        }
        else {
          it+'_VALUES'
        }
      }
      rc = tableNames.collect {
        if (config.valueColumnNames.keySet().contains(it)) {
          it+":${config.valueColumnNames.get(it)}"
        }
        else
          it
      }
      return rc
    }

}