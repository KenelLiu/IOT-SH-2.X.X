package com.iot.sh.util.system;

import lombok.extern.slf4j.Slf4j;

import java.security.AccessController;
import java.security.PrivilegedAction;
/**
 * Returns the value of the Java system property with the specified {@code key}
 * @author Kenel Liu
 */
@Slf4j
public class SystemPropertyUtil {

    public static boolean contains(String key) {
        return get(key) != null;
    }
    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to {@code null} if the property access fails.
     *
     * @return the property value or {@code null}
     */
    public static String get(String key) {
        return get(key, null);
    }
    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to the specified default value if
     * the property access fails.
     *
     * @return the property value.
     *         {@code def} if there's no such property or if an access to the
     *         specified property is not allowed.
     */
    public static String get(final String key, String def) {
        String value = null;
        try {
            if (System.getSecurityManager() == null) {
                value = System.getProperty(key);
            } else {
                value = AccessController.doPrivileged(new PrivilegedAction<String>() {
                    @Override
                    public String run() {
                        return System.getProperty(key);
                    }
                });
            }
        } catch (SecurityException e) {
            log.warn("Unable to retrieve a system property "+key+" default values will be used.", e);
        }
        if (value == null) {
            return def;
        }
        return value;
    }
    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to the specified default value if
     * the property access fails.
     *
     * @return the property value.
     *         {@code def} if there's no such property or if an access to the
     *         specified property is not allowed.
     */
    public static boolean getBoolean(String key, boolean def) {
        String value = get(key);
        if (value == null) {
            return def;
        }
        value = value.trim().toLowerCase();
        if (value.isEmpty()) {
            return def;
        }
        if ("true".equals(value) || "yes".equals(value) || "1".equals(value)) {
            return true;
        }
        if ("false".equals(value) || "no".equals(value) || "0".equals(value)) {
            return false;
        }
        log.warn("Unable to parse the boolean system property '"+key+"':"+value+" - using the default value: "+def);
        return def;
    }
    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to the specified default value if
     * the property access fails.
     *
     * @return the property value.
     *         {@code def} if there's no such property or if an access to the
     *         specified property is not allowed.
     */
    public static int getInt(String key, int def) {
        String value = get(key);
        if (value == null) {
            return def;
        }
        value = value.trim();
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            // Ignore
        }
        log.warn("Unable to parse the integer system property '"+key+"':"+value+" - using the default value:"+def);
        return def;
    }

    /**
     * Returns the value of the Java system property with the specified
     * {@code key}, while falling back to the specified default value if
     * the property access fails.
     *
     * @return the property value.
     *         {@code def} if there's no such property or if an access to the
     *         specified property is not allowed.
     */
    public static long getLong(String key, long def) {
        String value = get(key);
        if (value == null) {
            return def;
        }
        value = value.trim();
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            // Ignore
        }
        log.warn("Unable to parse the long integer system property '"+key+"':"+value+" - using the default value:"+def);
        return def;
    }
}
