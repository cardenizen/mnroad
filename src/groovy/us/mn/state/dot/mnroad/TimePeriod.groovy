package us.mn.state.dot.mnroad
/**
 * Created by IntelliJ IDEA.
 * User: Carr1Den
 * Date: Feb 5, 2010
 * Time: 8:56:57 AM
 * To change this template use File | Settings | File Templates.
 */

public class TimePeriod {

    private String dateTimeString
    private Date startDate
    private Integer microSeconds

    public TimePeriod() {
      startDate = new Date()
      microSeconds = 0
    }

    void initialize(String dts ) {
      // dts is expected to be in 10/19/2009  13:32:39.245311 format
      dateTimeString = dts
      startDate = MrUtils.getFormattedDate(dts,"M/dd/yyyy  hh:mm:ss")
      microSeconds = (dts.indexOf('.') > -1)?Integer.parseInt(dts.substring(dts.indexOf('.')+1)) : 0
    }

    public TimePeriod(String dt) {
      // dt is expected to be in 10/19/2009  13:32:39.245311 format
      startDate = MrUtils.getFormattedDate(dt,"M/dd/yyyy  hh:mm:ss")
      microSeconds = (dts.indexOf('.') > -1)?Integer.parseInt(dts.substring(dts.indexOf('.')+1)) : 0
    }

    public Double elapsedSeconds(String endDateTime) {
      Date endDate = MrUtils.getFormattedDate(endDateTime,"M/dd/yyyy  hh:mm:ss")
      Integer msEnd = (endDateTime.indexOf('.') > -1)?Integer.parseInt(endDateTime.substring(endDateTime.indexOf('.')+1)) : 0

      int secnds = (endDate.getTime() - startDate.getTime())/1000
      def ms = 0.0
      if (!endDateTime.equals(dateTimeString))
        ms = (msEnd + (1000000-microSeconds))/1000000.0
      Double elapsedTime = new Double(secnds) + ms 
      return elapsedTime
    }

}