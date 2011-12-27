package us.mn.state.dot.mnroad

import java.io.Serializable
class AggCell extends Cell implements Serializable {

    String toString() {
      "AGG Cell ${cellNumber} (${startStation} - ${endStation}) (${fromDate()} - ${toDate()}, ${name})"
    }

}
