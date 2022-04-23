package com.xxh.rosehong.utils.ref;

import com.xxh.rosehong.utils.system.RhBuild;
import com.xxh.rosehong.utils.system.RhLog;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import dalvik.system.VMRuntime;
import sun.misc.Unsafe;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/1/30 上午12:10
 */
public class RhReflection {
    private static final String TAG = RhReflection.class.getSimpleName();

    public static void bypassHiddenApi() {
        bypassHiddenApiP();
        bypassHiddenApiR();
    }

    public static void bypassHiddenApiP() {
        if (RhBuild.isP()) {
            try {
                Method forName = Class.class.getDeclaredMethod("forName", String.class);
                Method getDeclareMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
                Class VMRuntime = (Class) forName.invoke(null, "dalvik.system.VMRuntime");
                Method getRuntime = VMRuntime.getDeclaredMethod("getRuntime");
                Object runtimeObj = getRuntime.invoke(null);
                Method setHiddenApiExemptions = (Method) getDeclareMethod.invoke(VMRuntime, "setHiddenApiExemptions", new Class[]{String[].class});
                setHiddenApiExemptions.invoke(runtimeObj, new Object[]{new String[]{"L"}});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void bypassHiddenApiR() {
        if (RhBuild.isR()) {
            try {
                Method getUnsafeMethod = Unsafe.class.getDeclaredMethod("getUnsafe");
                getUnsafeMethod.setAccessible(true);
                Unsafe unsafe = (Unsafe) getUnsafeMethod.invoke(null);

                MethodHandle methodHandleA = MethodHandles.lookup().unreflect(RhCustomClass.class.getDeclaredMethod("methodA"));
                MethodHandle methodHandleB = MethodHandles.lookup().unreflect(RhCustomClass.class.getDeclaredMethod("methodB"));
                long artFieldOrMethodOffset = 0;
                if (RhBuild.isR()) {
                    artFieldOrMethodOffset = unsafe.objectFieldOffset(RhMethodHandleR.class.getDeclaredField("artFieldOrMethod"));
                } else if (RhBuild.isS()) {
                    artFieldOrMethodOffset = unsafe.objectFieldOffset(RhMethodHandleS.class.getDeclaredField("artFieldOrMethod"));
                }
                long methodsOffset = unsafe.objectFieldOffset(RhClass.class.getDeclaredField("methods"));
                long methodsRhCustomClassAddress = unsafe.getLong(RhCustomClass.class, methodsOffset);
                long artFieldOrMethodAddressA = unsafe.getLong(methodHandleA, artFieldOrMethodOffset);
                long artFieldOrMethodAddressB = unsafe.getLong(methodHandleB, artFieldOrMethodOffset);
                long artFieldOrMethodSize = artFieldOrMethodAddressB - artFieldOrMethodAddressA;
                long firstArtFieldOrMethodOffset = artFieldOrMethodAddressA - methodsRhCustomClassAddress - artFieldOrMethodSize;
                long infoOffset = 0;
                if (RhBuild.isR()) {
                    infoOffset = unsafe.objectFieldOffset(RhMethodHandleImplR.class.getDeclaredField("info"));
                } else if (RhBuild.isS()) {
                    infoOffset = unsafe.objectFieldOffset(RhMethodHandleImplS.class.getDeclaredField("info"));
                }
                long memberOffset = unsafe.objectFieldOffset(RhMethodInfo.class.getDeclaredField("member"));
                //找出VMRuntime.getRuntime和VMRuntime.setHiddenApiExemptions
                MethodHandle replaceMethodHandle = MethodHandles.lookup().unreflect(RhCustomClass.class.getDeclaredMethod("methodA"));
                long methodsAddressVMRuntime = unsafe.getLong(VMRuntime.class, methodsOffset);
                int methodCount = unsafe.getInt(methodsAddressVMRuntime);
                Method getRuntimeMethod = null;
                Method setHiddenApiExemptionsMethod = null;

                RhLog.w(TAG, "size: " + artFieldOrMethodSize);
                RhLog.w(TAG, "firstArtFieldOrMethodOffset: " + firstArtFieldOrMethodOffset);
                RhLog.w(TAG, "memberOffset: " + memberOffset);
                RhLog.w(TAG, "infoOffset: " + infoOffset);
                RhLog.w(TAG, "artFieldOrMethodOffset: " + artFieldOrMethodOffset);
                RhLog.w(TAG, "methodsOffset: " + methodsOffset);

                for (int i=0; i<methodCount; i++) {
                    long curMethodAddress = (i * artFieldOrMethodSize) + methodsAddressVMRuntime + firstArtFieldOrMethodOffset;
                    unsafe.putLong(replaceMethodHandle, artFieldOrMethodOffset, curMethodAddress);
                    unsafe.putObject(replaceMethodHandle, infoOffset, null);
                    //让replaceMethodHandle重新生成info，这里会new一个MethodInfo对象，所以下面找到的可以直接赋值
                    try {
                        MethodHandles.lookup().revealDirect(replaceMethodHandle);
                    } catch (Exception ee) {
                        //ignore
                    }
                    MethodHandleInfo infoOffValueReplaceMethod = (MethodHandleInfo) unsafe.getObject(replaceMethodHandle, infoOffset);
                    Object curMethodObj = unsafe.getObject(infoOffValueReplaceMethod, memberOffset);
                    if (curMethodObj instanceof Method) {
                        Method curMethod = (Method) curMethodObj;
                        if (curMethod.getName().equals("getRuntime")) {
                            getRuntimeMethod = curMethod;
                        } else if (curMethod.getName().equals("setHiddenApiExemptions")) {
                            setHiddenApiExemptionsMethod = curMethod;
                        }
                    }
                }
                if (getRuntimeMethod != null && setHiddenApiExemptionsMethod != null) {
                    Object runtimeObj = getRuntimeMethod.invoke(null);
                    setHiddenApiExemptionsMethod.invoke(runtimeObj, new Object[]{new String[]{"L"}});
                } else {
                    RhLog.e(TAG, "bypassHiddenApiEx failed!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class RhCustomClass {
        public static void methodA() {}
        public static void methodB() {}
    }

    /** 需要拿到MethodHandle的artFieldOrMethod的偏移
     * R: {
     *     private final MethodType type;
     *     private MethodType nominalType;
     *     private MethodHandle cachedSpreadInvoker;
     *     protected final int handleKind;
     *     protected final long artFieldOrMethod;
     * }
     *
     * S: {
     *     private final MethodType type;
     *     MethodHandle asTypeCache;
     *     private MethodHandle cachedSpreadInvoker;
     *     protected final int handleKind;
     *     protected final long artFieldOrMethod;
     * }
     */
    private static class RhMethodHandleR {
        private final MethodType type = null;
        private MethodType nominalType;
        private MethodHandle cachedSpreadInvoker;
        protected final int handleKind = 0;
        protected final long artFieldOrMethod = 0;
    }
    private static class RhMethodHandleS {
        private final MethodType type = null;
        MethodHandle asTypeCache;
        private MethodHandle cachedSpreadInvoker;
        protected final int handleKind = 0;
        protected final long artFieldOrMethod = 0;
    }
    private static class RhMethodInfo {
        private final Member member = null;
        private final MethodHandle handle = null;
    }
    private static class RhMethodHandleImplR extends RhMethodHandleR {
        private final MethodHandleInfo info = null;
    }
    private static class RhMethodHandleImplS extends RhMethodHandleS {
        private final MethodHandleInfo info = null;
    }
    private static class RhClass {
        private transient ClassLoader classLoader;
        private transient Class<?> componentType;
        private transient Object dexCache;
        private transient RhClassExt extData;
        private transient Object[] ifTable;
        private transient String name;
        private transient Class<?> superClass;
        private transient Object vtable;
        private transient long iFields;
        private transient long methods;
        private transient long sFields;
        private transient int accessFlags;
        private transient int classFlags;
        private transient int classSize;
        private transient int clinitThreadId;
        private transient int dexClassDefIndex;
        private transient volatile int dexTypeIndex;
        private transient int numReferenceInstanceFields;
        private transient int numReferenceStaticFields;
        private transient int objectSize;
        private transient int objectSizeAllocFastPath;
        private transient int primitiveType;
        private transient int referenceInstanceOffsets;
        private transient int status;
        private transient short copiedMethodsOffset;
        private transient short virtualMethodsOffset;
    }
    private static class RhClassExt {
        private Throwable erroneousStateError;
        private Object instanceJfieldIDs;
        private Object jmethodIDs;
        private Class<?> obsoleteClass;
        private Object[] obsoleteDexCaches;
        private Object obsoleteMethods;
        private Object originalDexFile;
        private Object staticJfieldIDs;
        private long preRedefineDexFilePtr;
        private int preRedefineClassDefIndex;
    }
}
