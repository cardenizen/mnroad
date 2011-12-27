class ContinuityService {

    boolean transactional = true

    def boolean isContiguous(List cells) {
      boolean rc = true
      if (cells) {
        boolean first = true
        double endStation = 0.0
        cells.each {
          if (first) {
            first = false
          }
          else {
            if (it.startStation != endStation) {
              rc = false
              return
            }
          }
        endStation  = it.endStation
        }
      }
      return rc
    }
}
