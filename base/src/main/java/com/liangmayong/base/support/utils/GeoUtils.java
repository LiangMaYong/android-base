package com.liangmayong.base.support.utils;

import java.util.BitSet;
import java.util.HashMap;

/**
 * GeoUtils
 *
 * @author LiangMaYong
 * @version 1.0
 */
public final class GeoUtils {
    // DEF_PI180
    private static double DEF_PI180 = 0.01745329252;
    // DEF_R
    private static double DEF_R = 6370693.5;
    // DEF_PI
    private static double DEF_PI = 3.14159265359;

    /**
     * get Delta
     *
     * @param latitude latitude
     * @param distance distance
     * @return Delta
     */
    public static double[] getDelta(double latitude, double distance) {
        return new double[]{getLongitudeDelta(latitude, distance), getLatitudeDelta(distance)};
    }

    /**
     * get Longitude Delta
     *
     * @param latitude latitude
     * @param distance distance
     * @return Longitude Delta
     */
    public static double getLongitudeDelta(double latitude, double distance) {
        distance = Math.cos(distance / DEF_R);
        double ns = latitude * DEF_PI180;
        double n = distance - Math.sin(ns) * Math.sin(ns);
        double s = Math.cos(ns) * Math.cos(ns);
        return Math.abs(Math.acos(n / s) / DEF_PI180);
    }

    /**
     * doGet Latitude Delta
     *
     * @param distance distance
     * @return Latitude Delta
     */
    public static double getLatitudeDelta(double distance) {
        distance = Math.cos(distance / DEF_R);
        return Math.abs(Math.acos(distance) / DEF_PI180);
    }

    /**
     * get Distance
     *
     * @param lon1 lon1
     * @param lat1 lat1
     * @param lon2 lon2
     * @param lat2 lat2
     * @return Distance
     */
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double distance;
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
        if (distance > 1.0)
            distance = 1.0;
        else if (distance < -1.0)
            distance = -1.0;
        distance = DEF_R * Math.acos(distance);
        return distance;
    }

    /**
     * getDistance
     *
     * @param startPoint startPoint
     * @param endPoint   endPoint
     * @return Distance
     */
    public static double getDistance(Geo startPoint, Geo endPoint) {
        double mLat1 = startPoint.getLatitude(); // point1 lat
        double mLon1 = startPoint.getLongitude(); // point1 long
        double mLat2 = endPoint.getLatitude(); // point1 lat
        double mLon2 = endPoint.getLongitude(); // point1 long
        return getDistance(mLon1, mLat1, mLon2, mLat2);
    }

    /**
     * getAngle
     *
     * @param startPoint startPoint
     * @param endPoint   endPoint
     * @return Angle
     */
    public static double getAngle(Geo startPoint, Geo endPoint) {
        double rotateAngle = 0;
        double slat, slng, elat, elng;
        slat = startPoint.getLatitude();// x
        slng = startPoint.getLongitude();// y
        elat = endPoint.getLatitude();
        elng = endPoint.getLongitude();
        Geo Lotpoint = new Geo(slat, elng);
        Geo Latpoint = new Geo(elat, slng);
        double x = getDistance(startPoint, Latpoint);// w
        double y = getDistance(startPoint, Lotpoint);// h
        double z = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
        double sn = z * z + y * y - x * x;
        double sh = (2 * z * y);
        double degrees = 0;
        if (sh != 0) {
            degrees = Math.asin(sn / sh);
        }
        rotateAngle = degrees * 180 / DEF_PI;
        if (slat == elat) {
            if (slng > elng) {
                rotateAngle = 180;
            } else {
                rotateAngle = 0;
            }
        } else if (slng == elng) {
            if (slat > elat) {
                rotateAngle = 270;
            } else {
                rotateAngle = 90;
            }
        } else if (slat < elat && slng > elng) {// 4
            rotateAngle = 360 - rotateAngle;
        } else if (slat > elat && slng < elng) {// 2
            rotateAngle = 180 - rotateAngle;
        } else if (slat > elat && slng > elng) {// 3
            rotateAngle = 180 + rotateAngle;
        } else if (slat < elat && slng < elng) {// 1
        }
        if (rotateAngle == 360) {
            rotateAngle = 0;
        }
        return rotateAngle;
    }

    public static class Geo {
        private double longitude;
        private double latitude;

        public Geo(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return "Dot [longitude=" + longitude + ", latitude=" + latitude + "]";
        }
    }

    private static int numbits = 6 * 5;
    private final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();

    static {
        int i = 0;
        for (char c : digits)
            lookup.put(c, i++);
    }

    /**
     * decode
     *
     * @param geohash geohash
     * @return Dot
     */
    public static Geo decodeGeoHash(String geohash) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(unbase32(geohash));

        BitSet lonset = new BitSet();
        BitSet latset = new BitSet();

        // even bits
        int j = 0;
        for (int i = 0; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            lonset.set(j++, isSet);
        }

        // odd bits
        j = 0;
        for (int i = 1; i < numbits * 2; i += 2) {
            boolean isSet = false;
            if (i < buffer.length())
                isSet = buffer.charAt(i) == '1';
            latset.set(j++, isSet);
        }

        double lon = decodeGeoHash(lonset, -180, 180);
        double lat = decodeGeoHash(latset, -90, 90);
        return new Geo(lon, lat);
    }

    private static double decodeGeoHash(BitSet bs, double floor, double ceiling) {
        double mid = 0;
        for (int i = 0; i < bs.length(); i++) {
            mid = (floor + ceiling) / 2;
            if (bs.get(i))
                floor = mid;
            else
                ceiling = mid;
        }
        return mid;
    }

    /**
     * encode
     *
     * @param lat lat
     * @param lon lon
     * @return geohash
     */
    public static String encodeGeoHash(double lon, double lat) {
        BitSet latbits = getBits(lat, -90, 90);
        BitSet lonbits = getBits(lon, -180, 180);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < numbits; i++) {
            buffer.append((lonbits.get(i)) ? '1' : '0');
            buffer.append((latbits.get(i)) ? '1' : '0');
        }
        return base32(Long.parseLong(buffer.toString(), 2));
    }

    /**
     * encode
     *
     * @param point point
     * @return geohash
     */
    public static String encodeGeoHash(Geo point) {
        return encodeGeoHash(point.longitude, point.latitude);
    }


    /**
     * getBits
     *
     * @param lat
     * @param floor
     * @param ceiling
     * @return
     */
    private static BitSet getBits(double lat, double floor, double ceiling) {
        BitSet buffer = new BitSet(numbits);
        for (int i = 0; i < numbits; i++) {
            double mid = (floor + ceiling) / 2;
            if (lat >= mid) {
                buffer.set(i);
                floor = mid;
            } else {
                ceiling = mid;
            }
        }
        return buffer;
    }

    /**
     * base32
     *
     * @param i i
     * @return
     */
    private static String base32(long i) {
        char[] buf = new char[65];
        int charPos = 64;
        boolean negative = (i < 0);
        if (!negative)
            i = -i;
        while (i <= -32) {
            buf[charPos--] = digits[(int) (-(i % 32))];
            i /= 32;
        }
        buf[charPos] = digits[(int) (-i)];

        if (negative)
            buf[--charPos] = '-';
        return new String(buf, charPos, (65 - charPos));
    }

    /**
     * unbase32
     *
     * @param geohash geohash
     * @return geohash
     */
    private static String unbase32(String geohash) {
        StringBuilder buffer = new StringBuilder();
        for (char c : geohash.toCharArray()) {
            int i = lookup.get(c) + 32;
            buffer.append(Integer.toString(i, 2).substring(1));
        }
        return buffer.toString();
    }
}
