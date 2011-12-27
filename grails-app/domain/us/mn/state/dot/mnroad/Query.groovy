package us.mn.state.dot.mnroad

class Query {
//  Cell cell
//  SensorModel sensorModel

//  String columnsClause
//  String fromClause
//  String whereClause
//  String orderByClause
//  byte[] picture
//  static constraints = {
//      picture(size:0..5000000)  // to store files upto 5MB approx
//  }
  //  dateCreated,lastUpdated populated by Grails
  Date dateCreated
  Date lastUpdated
  static mapping = {
    version false
  }
}
