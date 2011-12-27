/**
 * The WeatherStates entity.
 *
 * @author  Dennis Carroll  MnDOT
 *
 *
 */
class WeatherStates {
    static mapping = {
         table 'WEATHER_STATES'
         // version is set to false, because this isn't available by default for legacy databases
         version false
         id column:'WEATHER_STATE'
    }
    String weatherState
    String weatherStateDesc

    static constraints = {
        weatherState(size: 1..2, blank: false)
        weatherStateDesc(size: 0..50)
    }
    String toString() {
        return "${weatherState}" 
    }
}
