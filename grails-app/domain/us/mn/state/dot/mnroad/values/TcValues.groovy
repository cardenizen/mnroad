package us.mn.state.dot.mnroad.values

class TcValues {
    Integer cell
    Integer seq
    Date day
    Integer hour
    Integer minute
    Integer qhr
    Double value

    static constraints = {
    }

    static mapping = {
      table 'tc_values'
    }
}
