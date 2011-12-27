class UnitConvertService {

    boolean transactional = false

    static double in2mm = 25.4
    def Double mmToInches(Double mm) {
        Double d = 0.0
        if (!mm || mm == 0.0)
            return d
        else
        {
            d = mm / in2mm
        }
        return roundTwo(d);
    }

    def Double inchToMms(Double pInch) {
        double d = 0.0
        if (!pInch || pInch == 0.0)
            return d
        else
        {
            d = pInch * in2mm
        }
        return roundTwo(d);
    }

    def Double roundTwo(Double d) {
        if (!d)
            return 0.0
        long l = (int)Math.round(d * 100); // truncates
        return l / 100.0;
        }
}
