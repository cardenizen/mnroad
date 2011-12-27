package us.mn.state.dot.mnroad

import java.io.Serializable

class PccCell extends Cell implements Serializable {

    Boolean tiebars
    String surfaceTexture
    String longitudinalJointSeal


    static constraints = {
      tiebars(nullable:true)
      surfaceTexture(nullable:true)
      longitudinalJointSeal(nullable:true, size:1..30)
    }

    String toString() {
      "PCC Cell ${cellNumber} (${startStation} - ${endStation}) (${fromDate()} - ${toDate()}, ${name})"
    }

}
