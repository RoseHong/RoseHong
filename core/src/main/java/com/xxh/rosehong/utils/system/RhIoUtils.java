package com.xxh.rosehong.utils.system;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/10 22:33
 */
public class RhIoUtils {
    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }
}
