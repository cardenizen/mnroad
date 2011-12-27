package us.mn.state.dot.mnroad

import java.io.Serializable

class HmaCell extends Cell implements Serializable {

    String hmaDesign
    String mndotMixSpecification

    static constraints = {
      hmaDesign(nullable:true)
      mndotMixSpecification(nullable:true)
    }

    String toString() {
      "HMA Cell  ${cellNumber} (${startStation} - ${endStation}) (${fromDate()} - ${toDate()}, ${name})"
    }

}
