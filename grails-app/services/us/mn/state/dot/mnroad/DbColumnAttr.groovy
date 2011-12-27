package us.mn.state.dot.mnroad

public enum DbColumnAttr {
  NAME('Column name'),
  TYPE('Data Type'),
  LENGTH('Total size'),
  NULLABLE('Can be null'),
  NUMDISTINCT('Number of distinct values'),
  NUMNULL('Number of nulls'),
  PRECISION('Decimal places')

  String name

  DbColumnAttr(String name) {
    this.name = name
  }

  static list() {
    [NAME,TYPE,LENGTH,NULLABLE,NUMDISTINCT,NUMNULL,PRECISION]
  }
}