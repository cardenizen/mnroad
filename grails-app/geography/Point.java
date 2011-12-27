package geography;

/**
 * Created by IntelliJ IDEA.
 * User: carr1den
 * Date: Sep 24, 2009
 * Time: 1:56:38 PM
 * To change this template use File | Settings | File Templates.
 */

/** A point on the surface of the earth.  Represented by a longitude
 * and a latitude.  We always write the longitude first, since
 * (longitude,latitude) is rather like (x,y).  These measurements are
 * in degrees, not radians.
 */

public class Point {

  // ---------- CONSTANTS ----------

  /** Radius of the earth, in meters, at the equator. */
  protected static final double GLOBE_RADIUS_EQUATOR = 6378000;

  /** Radius of the earth, in meters, at the poles. */
  protected static final double GLOBE_RADIUS_POLES = 6357000;

  // ---------- INSTANCE VARIABLES ----------

  /** Longitude of this point, in degrees. */
  protected double longitude;

  /** Latitude of this point, in degrees. */
  protected double latitude;

  // ---------- CONSTRUCTORS ----------

  public Point(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public Point(String longitude, String latitude) throws NumberFormatException {
    this(Double.parseDouble(longitude), Double.parseDouble(latitude));
  }

  // ---------- OVERRIDE METHODS INHERITED FROM Object ----------

  public boolean equals(Object p) {
    return (p instanceof Point) && (longitude == ((Point)p).longitude)
                                && (latitude == ((Point)p).latitude);
  }

  public int hashCode() {
    return (new Double(longitude)).hashCode() << 5 + (new Double(latitude)).hashCode();
  }

  public String toString() {
    return "("+longitude+", "+latitude+")";
  }

  // ---------- DISTANCE AND DIRECTION BETWEEN POINTS ----------

  /** Distance from this point to another point, using the Haversine formula.
   * We take the squashed shape of the earth into account (approximately).
   *
   * Q: Why can't we just use the Pythagorean theorem?  We could just
   *    return sqrt(dx*dx + dy*dy), where dx is the change in latitude and
   *    dy is the change in longitude.
   * A: That doesn't take the curvature of the earth into account.
   * Q: But if we're just driving short distances, isn't the curvature of
   *    the earth too slight to matter?
   * A: True, but we don't want our code to mysteriously break if we start
   *    using it to drive around the world.  Anyway, your formula is still
   *    more complicated than you think, since you have to find dx
   *    in meters.  That's harder than you thought: 1 degree of longitude is
   *    a long way at the equator, but isn't a lot near the poles.
   */
  public double distanceTo(Point p) {
    // WARNING: These two lines of code are duplicated in another method.
    double lat1 = radians(latitude), lat2 = radians(p.latitude), dlat = lat2-lat1;
    double dlong = radians(p.longitude)-radians(longitude);

    // Formula from http://williams.best.vwh.net/avform.htm#Dist
    // See http://mathforum.org/library/drmath/view/51879.html for a derivation.
    //
    // I've adapted the formula slightly to deal with the squashed
    // earth.  We still make an approximation by taking the radius of
    // curvature r to be constant throughout the route.  It actually
    // changes, so we should integrate over the whole route.  But such
    // "elliptic integrals" don't have a closed form and can't be
    // found using trigonometry.

    double a = square(Math.sin(dlat/2))
               + Math.cos(lat1)*Math.cos(lat2)*square(Math.sin(dlong/2));
    double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));  // angle in radians formed by start point, earth's center, & end point
    double r = globeRadiusOfCurvature((lat1+lat2)/2);      // radius of earth at midpoint of route
    return r*c;
  }

  /** The direction that you have to go from this point to get to p.
   *
   * Answer is returned in degrees, between -180.0 and 180.0.
   * Here -180, -90, 0, 90, and 180 correspond to west, south, east,
   * north, and west again -- just like theta in polar coordinates.
   *
   * It would be tempting to just return atan2(dy,dx); see
   * the comment in distanceTo() for why we don't do that.
   */
  public double directionTo(Point p) {
    // WARNING: These two lines of code are duplicated in another method.
    double lat1 = radians(latitude), lat2 = radians(p.latitude), dlat = lat2-lat1;
    double dlong = radians(p.longitude)-radians(longitude);

    // Formula from http://williams.best.vwh.net/avform.htm#GCF
    double radians = Math.atan2(Math.sin(-dlong)*Math.cos(lat2),
			        Math.cos(lat1)*Math.sin(lat2)
			        - Math.sin(lat1)*Math.cos(lat2)*Math.cos(-dlong));

    double deg = degrees(radians);

    // That formula has 0 degrees being due north.  Rotate it
    // so that 90 degrees is due east.
    deg += 90;
    if (deg > 180) deg -= 360;

    return deg;
  }


  // ---------- MATHEMATICAL UTILITY FUNCTIONS ----------

  protected static final double radians(double degrees) {
    return degrees * (2*Math.PI) / 360;
  }

  protected static final double degrees(double radians) {
    return radians * 360 / (2*Math.PI);
  }

  protected static final double square(double d) {
    return d*d;
  }

  /** Computes the earth's radius of curvature at a particular latitude,
   * assuming that the earth is a squashed sphere with
   * elliptical cross-section.  Since we supposedly have latitude
   * and longitude to lots of decimal places, I decided to worry
   * about the squashing, just for fun.
   *
   * The radius of curvature at a latitude is NOT the same as the
   * actual radius.  The actual radius is smaller at the poles
   * than at the equator, but the earth is less curved there, as
   * if it were the surface of a *bigger* sphere!
   *
   * The actual radius could be computed by
   *   return Math.sqrt(square(GLOBE_RADIUS_EQUATOR*Math.cos(lat))
   *                    + square(GLOBE_RADIUS_POLES*Math.sin(lat)));
   *
   * The radius of curvature depends not only on the latitude you're at
   * but also on the direction you are traveling.  But I'll use the
   * approximate formula recommended at
   *    http://www.census.gov/cgi-bin/geo/gisfaq?Q5.1
   * which ignores the direction.  There is a whole range of possible
   * answers depending on direction; the formula returns the geometric
   * mean of the max and min of that range.
   *
   * @param lat - latitude in radians.  This is the angle
   *    that a point at this latitude makes with the horizontal.
   */
  protected static final double globeRadiusOfCurvature(double lat) {
    double a = GLOBE_RADIUS_EQUATOR;  // major axis
    double b = GLOBE_RADIUS_POLES;    // minor axis
    double e = Math.sqrt(1 - square(b/a));  // eccentricity
    return a*Math.sqrt(1-square(e)) / (1-square(e*Math.sin(lat)));
  }

  // ---------- TEST METHODS ----------

  public static void test(double long1, double lat1, double long2, double lat2) {
    Point p1 = new Point(long1, lat1);
    Point p2 = new Point(long2, lat2);
    System.out.println("From "+p1+" to "+p2+" is "+p1.distanceTo(p2)+" meters at "+p1.directionTo(p2)+" degrees above east");
  }

  public static void main(String args[]) {
    test(0,0,0,0);
    test(0,0,0,90);
    test(0,0,90,0);
    test(0,0,0,-90);
    test(0,0,-90,0);
    test(0,0,0,45);
    test(0,0,45,0);

    test(-76.609237, 39.330001, -76.609289, 39.329406);
    test(-76.609289, 39.329406, -76.609289, 39.328606);

    test(-76.609289, 39.329406, -76.609299, 39.328606);
    test(-76.609289, 39.329406, -76.609279, 39.328606);

    test(-76.289, 39.9406, -76.299, 39.8606);
    test(-76.289, 39.9406, -76.279, 39.8606);

  }
}

