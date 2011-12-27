package us.mn.state.dot
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClassProperty;

/**
 * Created by IntelliJ IDEA.
 * User: Carr1Den
 * Date: Feb 11, 2011
 * Time: 6:12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class SqlDataFormat {

  static String formatSqlValue(Object val, String typ, Boolean useQuotes, Boolean useId) {
      String rc = ""
      if (typ == "java.util.Date" || typ == "DATE" || typ == "TIMESTAMP(6)" || typ.startsWith("TIMESTAMP")) {
        if (val == null)
          rc = ""
        else if ("${val}".endsWith("00:00:00.0")){
          rc="${val}".split(" ")[0]
        }
        else if ((typ == "TIMESTAMP(6)") || (typ.startsWith("TIMESTAMP"))) {
            def ov = val.stringValue()
            try {
            rc = ov[0..ov.lastIndexOf('.')+3] // truncate to thousandths of a second
            } catch (StringIndexOutOfBoundsException siob) {
              rc = ov //println "${ov} ${siob.message}"
            }
        }
        else
          rc = "${val}"
      }
      else if (typ.equals("java.lang.String") || typ.startsWith("VARCHAR2")) {
        if (val == null)
          return ""
        else {
          def nv = val.toString()
          if (val.toString()[0]=='"' && val.toString()[-1]=='"') {
            def nnv = val.toString()[1..-2]
            if (nnv.indexOf('"')>-1) {
              nnv.replace('"','""')
              nv = '"'+nnv+'"'
            }
          }
          def rnv = ((nv.toString().indexOf('\r\n')>-1))?nv.replace("\r\n"," "):"$nv"
          if (!useQuotes) {
            def bi = (rnv[0] != '"')?0:1
            def ei = (rnv[-1] != '"')?-1:-2
            if (rnv[0] == '"' || rnv[-1] == '"') {
              nv = rnv[bi..ei]
            } else {
              nv = rnv
            }
          } else {
            def bq = (rnv[0] == '"')
            def eq = (rnv[-1] == '"')
            if (!bq || !eq) {
              if (!bq) {
                nv = '"' + rnv
              }
              if (!eq) {
                nv = rnv + '"'
              }
            } else {
              nv = rnv
            }
          }
          rc = nv
        }
      }
      else if (typ.equals("java.lang.Long") || typ.startsWith("NUMBER")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Double") || typ.equals("java.math.BigDecimal") || typ.startsWith("BIG_DECIMAL")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Boolean")) {
        int b = (val?1:0)
        rc = val == null?"":"$b"
      }
      else if (typ.equals("java.lang.Integer") || typ.startsWith("NUMBER")) {
        rc = val == null?"":val
      }
      else {
        if (useId)
          rc = ""+val?.id
        else
          println "Unknown type: $typ"
      }
      rc.replaceAll('/n','')
      return rc
    }

    static String formatSqlValueForInserts(Object val, String typ, Boolean useId) {
      String rc = ""
      if (typ.equals("java.util.Date")) {
        rc = val == null?"null":"to_timestamp('" + val + "','yyyy-mm-dd hh24:mi:ss.ff3')"
      }
      else if (typ.equals("java.lang.String")) {
        if (val == null)
          return "''"
        if (val.contains("\'")) {
          def s = val
          def n = s.replace("\'","\'\'")
          rc = "'$n'"
        }
        else
          rc = "'$val'"
      }
      else if (typ.equals("java.lang.Long")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Double")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Boolean")) {
        int b = (val?1:0)
        rc = val == null?"":"$b"
      }
      else if (typ.equals("java.lang.Integer")) {
        rc = val == null?"":val
      }
      else {
        if (useId)
          rc = ""+val?.id
        else
          println "Unknown type: $typ"
      }
      rc.replaceAll('/n','')
      return rc
    }

      static Object formatValue(Object val, DefaultGrailsDomainClassProperty fldProps, Boolean useId) {
        Object rc = val
        if (fldProps.type.name.equals("java.util.Date")) {
          def fd = val == null?"":"${val}"
          if (fd.endsWith(" 00:00:00.0"))
             rc = fd.split(" ")[0]
          else { // Time is included - chop off the seconds digits so correct format is selected by Excel
             rc = fd?fd.substring(0,fd.lastIndexOf(":")):""
          }
        }
        else if (fldProps.type.name.equals("java.lang.String")) {
          if (val == null)
            return ""
          rc = "\"${val}\""
          if (val.contains("\'")) {
            def s = val
            def n = s.replace("\'","\'\'")
            rc = "\"$n\""
          }
          if (val.contains("\"'")) {
            def nv = (val.toString().indexOf('"')>-1)?val.replace('"','""'):"$val"
            def nv1 = ((nv.toString().indexOf('\r\n')>-1))?nv.replace("\r\n"," "):"$nv"
            rc =  '"' + nv1 +'"'
          }
        }
        else if (fldProps.type.name.equals("java.lang.Long")) {
          rc = val == null?"":val
        }
        else if (fldProps.type.name.equals("java.lang.Double")) {
          rc = val == null?"":val
        }
        else if (fldProps.type.name.equals("java.lang.Boolean")) {
          rc = (val?1:0)
        }
        else if (fldProps.type.name.equals("java.lang.Integer")) {
          rc = val == null?"":val
        }
        else {
          if (useId)
            rc = ""+val?.id
          else {
            rc = val?.toString()
            println "Unknown type: $fldProps.type.name"
          }
        }
        return rc
      }

//      static String formatValue(Object val, DefaultGrailsDomainClassProperty fldProps, Boolean useId) {
//        String rc = ""
//        if (fldProps.type.name.equals("java.util.Date")) {
//          def fd = val == null?"":"${val}"
//          if (fd.endsWith(" 00:00:00.0"))
//             rc = fd.split(" ")[0]
//          else { // Time is included - chop off the seconds digits so correct format is selected by Excel
//             rc = fd?fd.substring(0,fd.lastIndexOf(":")):""
//          }
//        }
//        else if (fldProps.type.name.equals("java.lang.String")) {
//          if (val == null)
//            return "\"\""
//          if (val.contains("\'")) {
//            def s = val
//            def n = s.replace("\'","\'\'")
//            rc = "\"$n\""
//          }
//          else
//            rc = "\"${val}\""
//        }
//        else if (fldProps.type.name.equals("java.lang.Long")) {
//          rc = val == null?"":val
//        }
//        else if (fldProps.type.name.equals("java.lang.Double")) {
//          rc = val == null?"":val
//        }
//        else if (fldProps.type.name.equals("java.lang.Boolean")) {
//          int b = (val?1:0)
//          rc = val == null?"":"$b"
//        }
//        else if (fldProps.type.name.equals("java.lang.Integer")) {
//          rc = val == null?"":val
//        }
//        else {
//          if (useId)
//            rc = ""+val?.id
//          else {
//            rc = val?.toString()
//            println "Unknown type: $fldProps.type.name"
//          }
//        }
//        return rc
//      }

    static String formatSqlValueForCsv(Object val, String typ, Boolean useId) {
      String rc = ""
      if (typ.equals("java.util.Date")) {
        def fd = val == null?"":"${val}"
        if (fd.endsWith(" 00:00:00.0"))
           rc = fd.split(" ")[0]
        else { // Time is included - chop off the seconds digits so correct format is selected by Excel
           rc = fd?fd.substring(0,fd.lastIndexOf(":")):""
        }
      }
      else if (typ.equals("java.lang.String")) {
        if (val == null)
          return "\"\""
        rc = "\"${val}\""
        if (val.contains("\'")) {
          def s = val
          def n = s.replace("\'","\'\'")
          rc = "\"$n\""
        }
        if (val.contains("\"'")) {
          def nv = (val.toString().indexOf('"')>-1)?val.replace('"','""'):"$val"
          def nv1 = ((nv.toString().indexOf('\r\n')>-1))?nv.replace("\r\n"," "):"$nv"
          rc =  '"' + nv1 +'"'
        }
      }
      else if (typ.equals("java.lang.Long")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Double")) {
        rc = val == null?"":val
      }
      else if (typ.equals("java.lang.Boolean")) {
        int b = (val?1:0)
        rc = val == null?"":"$b"
      }
      else if (typ.equals("java.lang.Integer")) {
        rc = val == null?"":val
      }
      else {
        if (useId)
          rc = ""+val?.id
        else {
          rc = val?.toString()
          println "Unknown type: $typ"
        }
      }
      return rc
    }

}
