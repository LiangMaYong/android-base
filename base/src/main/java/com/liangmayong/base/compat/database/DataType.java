package com.liangmayong.base.compat.database;

/**
 * DataType
 *
 * @author LiangMaYong
 * @version 1.0
 */
public class DataType {

    public static final DataType TEXT() {
        return new DataType(Type.TEXT);
    }

    public static final DataType INTEGER() {
        return new DataType(Type.INTEGER);
    }

    public static final DataType FLOAT() {
        return new DataType(Type.FLOAT);
    }

    public static final DataType BLOB() {
        return new DataType(Type.BLOB);
    }

    // type
    private final Type type;
    // isNotNull
    private boolean isNotNull = false;

    private DataType(Type type) {
        this.type = type;
    }

    /**
     * isNotNull
     *
     * @return isNotNull
     */
    public boolean isNotNull() {
        return isNotNull;
    }

    /**
     * notNull
     *
     * @return type
     */
    public DataType notNull() {
        this.isNotNull = true;
        return this;
    }

    /**
     * name
     *
     * @return type name
     */
    public String name() {
        return type.name();
    }

    @Override
    public String toString() {
        return name();
    }

    /**
     * Type
     */
    private enum Type {
        INTEGER, TEXT, FLOAT, BLOB;
    }
}
