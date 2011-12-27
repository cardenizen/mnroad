package us.mn.state.dot.mnroad

/**
 * Created by IntelliJ IDEA.
 * User: Carr1Den
 * Date: Feb 11, 2010
 * Time: 5:46:56 PM
 * To change this template use File | Settings | File Templates.
 */

public class SensorRecord {

  static int nPeriods = 96
  Long sid
  int cell
  String model
  int seq
  String dt
  String values

  SensorRecord(def cell, def model, def seq, def id, def yyyy_mm_dd, def readings) {
    this.cell = cell
    this.model = model
    this.seq = seq
    sid = id
    dt = yyyy_mm_dd
    values = readings
  }

  String toString() {
    return "${cell},${model},${seq},${sid},${dt},${values}"
  }
}