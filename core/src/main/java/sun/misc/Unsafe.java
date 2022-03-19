package sun.misc;

import java.lang.reflect.Field;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/2/4 上午12:52
 */
public class Unsafe {
    public long objectFieldOffset(Field field) {
        throw new RuntimeException("Stub!");
    }
    public int arrayBaseOffset(Class cls) {
        throw new RuntimeException("Stub!");
    }

    public native int addressSize();

    public native byte getBytes(long offset);
    public native int getInt(long offset);
    public native int getInt(Object obj, long offset);
    public native long getLong(long offset);
    public native long getLong(Object obj, long offset);
    public native Object getObject(Object obj, long offset);
    public native short getShort(Object obj, long offset);

    public native void putByte(long offset, byte b);
    public native void putInt(Object obj, long offset, int i);
    public native void putLong(Object obj, long offset, long l);
    public native void putObject(Object obj, long offset, Object o);
}
