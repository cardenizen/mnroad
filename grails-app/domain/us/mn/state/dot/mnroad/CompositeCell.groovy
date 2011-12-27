package us.mn.state.dot.mnroad

import java.io.Serializable

class CompositeCell extends Cell implements Serializable {

// HMA
    String hmaDesign
    String mndotMixSpecification
// PCC
    Boolean tiebars
    String surfaceTexture
    String longitudinalJointSeal
    

    String toString() {
      "Composite Cell ${cellNumber} (${startStation} - ${endStation}) (${fromDate()} - ${toDate()}, ${name})"
    }

    static constraints = {
      tiebars(nullable:true)
      hmaDesign(nullable:true)
      mndotMixSpecification(nullable:true)
      surfaceTexture(nullable:true)
      longitudinalJointSeal(nullable:true, size:1..30)
      }

}
