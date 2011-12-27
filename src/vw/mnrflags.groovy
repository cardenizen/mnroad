
public class mnrflags {
/*
This script must in the same directory as runvw.m, umn_data_analysis.m, and cellwrite.m

TODO
Parse and format arguments to runvw.m
groovy mnrflags -f/grailsApps/1.3.3/mnroad/src/vw -mVW -nall -s8 -i/data/vwdata/in -o/data/vwdata/out -e4

see http://commons.apache.org/cli/api-1.2/org/apache/commons/cli/Option.html
and http://groovy.codehaus.org/gapi/groovy/util/CliBuilder.html

*/
  static def config
  static def opts

  static int main (args) {
    int rc = 0
    def om = options(args)
    if (!om) {
      println ("No options specified or option parsing failed.")
      return 1
    }
    if (!om || !om["directory"]) {
      return 1
    }
    def cmd = ""
//    cmd = """matlab -logfile matlabmnr.log -r runvw('/grailsApps/1.3.3/mnroad/src/vw','VW','all','8','/data/vwdata/in','/data/vwdata/out',4);quit; -nosplash"""
    def dir = om.directory
    if (!om.m) {
      println "Supplied model, ${opts.m}, is invalid. Model must be one of ${config.validModels}"
      return 1
    }
    def model = om.m
    if (!om.i) {
      println "Invalid input folder. Exiting."
      return 1
    }
    def inDataFolder = om.i
    if (!om.o) {
      println "Invalid ouput folder. Exiting."
      return 1
    }
    def outDataFolder = om.o
    def sensorFileName = om.n?:"all"
    def sequenceToProcess = om.s?:"all"
    def outlierStdDeviations = om.e?om.e:""
    def sd = outlierStdDeviations?",${outlierStdDeviations}":""
    def parms = "'${model}','${sensorFileName}','${sequenceToProcess}','${inDataFolder}','${outDataFolder}'${sd}"
    println "Starting MATLAB in folder ${dir}."
    println "\tParameters: ${parms}"
    cmd = """matlab -r runvw('${dir}',${parms});quit; -nosplash"""
    def proc = cmd.execute()
    proc.waitFor()
    rc = proc.exitValue()
    try {
      if (rc)
        println "Error starting MATLAB. Process exit value: ${rc}"
      else
        println "MATLAB successfully started."
    } catch (Exception e) {
      println e.message
    }
    return rc
  }

  static def options (def args) {
    def om = [:]
    def cli = new CliBuilder()
    cli.with
        {
          h(longOpt: 'help', 'Help - Usage Information')
          f(longOpt: 'folder', args:1, required: true, 'The folder that holds the matlab scripts.')
          m(longOpt: 'model', args:1, required: true, 'The sensor model to process.')
          i(longOpt: 'input', args:1, required: true, 'Folder holding the input data')
          o(longOpt: 'output', args:1, required: true, 'Folder to hold process output.')
          n(longOpt: 'filename', args:1, required: false, '')
          s(longOpt: 'sequence', args:1, required: false, '')
          e(longOpt: 'stdDev', args:1, required: false, '')
        }

    opts = cli.parse(args)
    if (!opts || opts.h) {
      return om
    }
    def cf = new File('MnrflagsValidParms.groovy')
    config = new ConfigSlurper().parse(cf.toURL())

    if (opts.f) {
      if ((new File(opts.f)).exists()) {
        om.put("directory", opts.f)
      }
      else {
        println ("Supplied folder, ${opts.f} does not exist.")        
      }
    }
    else {
      return om
    }
    if (opts.m) {
      if (config.validModels.contains(opts.m)) {
        om.put("m", opts.m)
      }
    }
    if (opts.i) {
      if ((new File(opts.i)).exists()) {
        om.put("i", opts.i)
      }
      else {
        println ("Supplied input folder, ${opts.i} does not exist.")
      }
    }
    if (opts.o) {
      if ((new File(opts.o)).exists()) {
        om.put("o", opts.o)
      }
      else {
        println ("Supplied output folder, ${opts.o} does not exist.")        
      }
    }
    if (opts.n) {
      if (opts.n == 'all')
        om.put("n", opts.n)
      else {
        if ((new File("${opts.i}/${opts.n}")).exists()) {
          om.put("n", opts.n)
        }
        else {
          println ("Supplied file, ${opts.n} does not exist in the input folder ${opts.i}.")
        }
      }
    }
    if (opts.s) {
      om.put("s", opts.s)
    }
    if (opts.e) {
      om.put("e", opts.e)
    }

    return om
  }

}