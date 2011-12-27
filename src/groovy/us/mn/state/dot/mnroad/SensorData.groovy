package us.mn.state.dot.mnroad

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics
import java.text.DecimalFormat
import org.apache.commons.math.util.MathUtils
import org.apache.commons.math.optimization.fitting.PolynomialFitter
import org.apache.commons.math.optimization.general.LevenbergMarquardtOptimizer
import org.apache.commons.math.analysis.polynomials.PolynomialFunction

/**
 * Created by IntelliJ IDEA.
 * User: carr1den
 * Date: Feb 2, 2010
 * Time: 7:25:18 AM
 * To change this template use File | Settings | File Templates.
 */

public class SensorData {

  List getSensorIds(String fileName) {
    def rc = []
      if (!fileName)
        return rc
    BigFile file = new BigFile(fileName)
    def i = 0
    Iterator iter = file.iterator()
    while (iter.hasNext()) {
      String line = iter.next()
      if (i++ == 2) {
        rc = line.split(",")
        rc -= rc[0]
        break
      }
    }
    file.Close()
    return rc
  }

    List getSensorIds(String fileName, String model) {
    def rc = []

    BigFile file = new BigFile(fileName)
    def i = 0
    Iterator iter = file.iterator()
    while (iter.hasNext()) {
      String line = iter.next()
      if (i++ == 2) {
        def l = line.split(",")
        l -= l[0]
        l.each {
          if (it.contains(model))
            rc << it
        }
        break
      }
    }
    file.Close()
    return rc
  }

    void getStats(String fileName, String model, int windowSize) {
      def columns = []
      def rows = []
//      def sids = []
//      def idxs = []
      def i = 0
      TimePeriod tp = new TimePeriod()
      BigFile file = new BigFile(fileName)
      Iterator iter = file.iterator()
      // Create a DescriptiveStats instance and set the window size
      DescriptiveStatistics stats = DescriptiveStatistics.newInstance();
      stats.setWindowSize(windowSize);
      long nLines = 0;
      double deltav = 0.0
      double lastvar = 0.0
//      PolynomialFitter fitter = new PolynomialFitter(2, new LevenbergMarquardtOptimizer());
      while (iter.hasNext()) {
        String line = iter.next()
        int idx = 0
        if (i++ == 2) {
          def models = line.split(",")
          models -= models[0]
          int k = 0
          models.each {
            // Create the indices of all sensors of a given model
            if (it  == model) {
              idx = k
            }
            k++
          }
        }
        if (i < 4)
          continue;
        def parts = line.split(",")
        if (i == 4) {
          tp.initialize(parts[0])
        }
        Double elapsed = tp.elapsedSeconds(parts[0])
        // remove the first column leaving the reading values
        parts -= parts[0]
        Double d = Double.parseDouble(parts[idx])
        rows << d
        stats.addValue(d);
        if (nLines++ == windowSize) {
          nLines = 0;
          double var = stats.getVariance()
          deltav = Math.abs(var - lastvar)
          lastvar = var
//          fitter.addObservedPoint(deltav,elapsed,d);
          println "${MathUtils.round(elapsed,2)}, \t\t${MathUtils.round(stats.getMean(),3)}, \t\t${MathUtils.round(var,6)}, \t\t${MathUtils.round(deltav,6)}, \t\t${(deltav > 0.05)?"*****":""}"
        }
      }
//      PolynomialFunction fitted = fitter.fit();
      file.Close()
    }
}