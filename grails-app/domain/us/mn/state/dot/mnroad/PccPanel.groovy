package us.mn.state.dot.mnroad

class PccPanel {

    Double panelLength
    Double panelWidth

    Date dateCreated
    Date lastUpdated
    String createdBy
    String lastUpdatedBy
  
    Layer layer
    static belongsTo = Layer

    static constraints = {
      panelLength             (nullable:true)
      panelWidth              (nullable:true)
    }
}
