package us.mn.state.dot.mnroad
//import org.springframework.web.context.request.RequestContextHolder
class Material {

  String  basicMaterial
  String  description
  String  mnDotSpecNumber
  Integer specYear
  String  binder
  // binder pick list is in table APP_CONFIG
  String  designLevel
  // designLevel pick list is in table APP_CONFIG
  String  course
  // course pick list is in table APP_CONFIG
  String  modifier
  // modifier pick list is in table APP_CONFIG
  Integer percentRap
  String fiberType
  String gradationName
// 3 to be deleted
//  String  displayName
//  String  componentModification
//  String  processModification

  Date dateCreated
  Date lastUpdated
  String createdBy
  String lastUpdatedBy

  static constraints = {
    basicMaterial         (nullable:false, size:1..25)
    mnDotSpecNumber       (nullable:true, size:1..20)
    specYear              (nullable:true, range:1950..2050)
    binder                (nullable:true, size:1..10)
    modifier              (nullable:true, size:1..250)
    fiberType             (nullable:true, size:1..30)
    designLevel           (nullable:true, size:1..20)
    course                (nullable:true, size:1..10)
    percentRap            (nullable:true, range:0..100)
    description           (nullable:true, size:1..255)
    gradationName         (nullable:true, size:1..50)
    createdBy             (nullable:true)
    lastUpdatedBy         (nullable:true)
  }

  static mapping = {
    columns { id column:'id' }
    id generator:'sequence', params:[sequence:'MNROAD_ID_SEQ']
  }

  String toString() {
    //(basicMaterial?:"N/A") + " - " + description
    display()
  }

  String display () {
    if (basicMaterial == "Asphalt") {
     return asphaltDescription()
    } else  if (basicMaterial == "Concrete") {
      return concreteDescription()
    } else  if (basicMaterial == "Gravel") {
      return gravelDescription()
    } else {
      return (basicMaterial?:"N/A") + " - " + description
    }
  }

  String gravelDescription() {
    String rc = (basicMaterial?:"N/A") + " - " + description
    if (gradationName)
      rc += " - " + gradationName
    return rc
  }

  String concreteDescription() {
    String rc = (basicMaterial?:"N/A") + " - " + description
    if (fiberType)
      rc += " - " + fiberType
    if (modifier)
      rc += " - " + modifier
    return rc
  }

  String asphaltDescription() {
    String rc = (basicMaterial?:"N/A") + " - " + description

    if (binder)
      rc += " - " + binder
    if (modifier)
      rc += " - " + modifier
    if (designLevel)
      rc += " - " + designLevel
    if (course)
      rc += " - " + course
    if (percentRap)
      rc += " - " + percentRap + "% " + /*modifier?:*/"RAP"
    return rc
  }
// Accessing Session Map in the Domain or Service Layer
//   After import of RequestContextHolder the session variable can be defined in the Service class or Domain method as:
//
//        def session = RequestContextHolder.currentRequestAttributes().getSession()
//
//    The session attributes can now be accessed as
//
//        session.attribute

}
/*
CREATE TABLE RoadMaterial (
    ID number(19),
    Name VARCHAR2(50) NOT NULL,
    PreparationTime INT,
    Skill VARCHAR,
    ServingUnit VARCHAR,
    ServingSize DOUBLE,
    Instructions VARCHAR,
    Credits VARCHAR,
    Notes VARCHAR
);

CREATE TABLE Ingredients (
    IngredientID IDENTITY,
    IngredientName VARCHAR NOT NULL,
    AmountAvailable INT,
    Unit VARCHAR
);

CREATE TABLE IngredientsUsed (
    Amount DOUBLE NOT NULL,
    IngredientID INT,
    RecipeID INT,
    FOREIGN KEY (IngredientID) REFERENCES Ingredients,
    FOREIGN KEY (RecipeID) REFERENCES Recipes,
    PRIMARY KEY (IngredientID, RecipeID)
);
*/