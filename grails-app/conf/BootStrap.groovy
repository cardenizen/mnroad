import grails.util.GrailsUtil
import java.text.SimpleDateFormat

class BootStrap {

     def grailsApplication
  
     def init = { servletContext ->
       grailsApplication.applicationMeta.put("app.environment",GrailsUtil.getEnvironment())

        String.metaClass.asTypeSafeNumber = {Class c ->
            try {
                delegate.asType(c)
            }
            catch (Exception) {
                return null
            }
        }

       String.metaClass.asTypeSafeDate = {String formatString ->
         try {
           def sdf = new SimpleDateFormat(formatString)
           sdf.parse(delegate)
         } catch (Exception iae) {
           return null
         }
       }

     }

    def destroy = {
    }


}
