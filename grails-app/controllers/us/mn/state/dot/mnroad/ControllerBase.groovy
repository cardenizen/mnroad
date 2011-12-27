package us.mn.state.dot.mnroad

import org.jsecurity.*;
import org.codehaus.groovy.grails.commons.ApplicationHolder
class ControllerBase {

  // the delete, save and update actions only accept POST requests
  static def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def dataSource

    void prepareListParams() {
        if (!params.max) {
            params.max = 10
        }
        if (!params.order) {
            params.order = "asc"
        }
        if (!params.sort) {
            params.sort = "id"
        }
    }

    def dbEnv={
      def dbenv = ""
      if (dataSource.url.endsWith("MRL2K3MRDB.ad.dot.state.mn.us:1521:mnrd"))
        dbenv = "Production"
      else if (dataSource.url.endsWith("MRL2K3dev.ad.dot.state.mn.us:1521:DEV11"))
        dbenv = "Test"
      else if (dataSource.url.endsWith("XE"))
        dbenv = "XE"
    }
  
    def grailsVersion={
      ApplicationHolder.application.metadata['app.grails.version']
    }

    def defaultAction = "list"

    Long getId(def var) {
      Long rc = 1;
      if (var instanceof Long) {
        return var
      }
      if (var instanceof String) {
        def s = var
        if (var.contains(",")) {
          s -= ","
        }
        rc = Long.parseLong(s)
      }
      return rc
    }

    String getUserName() {
      return MrUtils.getUserName()
//      def userName = ""
//      def subject = SecurityUtils.getSubject();
//      if (subject?.authenticated) {
//           userName = subject.principal
//      }
//      return userName
    }

    // Workaround for http://jira.codehaus.org/browse/GRAILS-1793
    def paramsDateCheck ( params ) {
      def rc
      def names = []
      params.each{
        if (it.value == "struct")
          names.add(it.key)
      }
      names.each {
        def aDateString = ""
        def allBlank = (params[it+"_day"] == '' & params[it+"_month"] == '' & params[it+"_year"] == '')
        def someBlank = (params[it+"_day"] == '' | params[it+"_month"] == '' | params[it+"_year"] == '')
        if (allBlank)
          params[it] =  null
        if (someBlank & !allBlank){
          params[it] =  null
          def day = params[it+"_day"] == ''?'dd':params[it+"_day"]
          def mon = params[it+"_month"] == ''?'mm':params[it+"_month"]
          def year = params[it+"_year"] == ''?'yyyy':params[it+"_year"]
          aDateString = "${day}/${mon}/${year}"
          rc = ", ${it}, ${aDateString}, ignored"
        }
      }
      return rc?rc:""
    }
  
}