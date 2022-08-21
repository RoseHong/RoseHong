package com.xxh.rosehong.framework.simple.server;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/8/9 23:06
 */

import android.util.ArrayMap;
import android.util.Xml;

import com.xxh.rosehong.utils.system.RhIoUtils;
import com.xxh.rosehong.utils.system.RhLog;
import com.xxh.rosehong.utils.system.RhXmlUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 此类为com.android.server.SystemConfig的精简版，由于应用在安装的时候，需要判断哪些系统的库是可以被链接的，
 * 故，这里需要用到mShareLibraries成员，为了省内存，也没必要把/etc/permission/和/etc/sysconfig/里所有的文件都解析并缓存起来，
 * 所以这里只需要处理mShareLibraries即可。
 */
public class SystemConfigSimple {
    private static final String TAG = SystemConfigSimple.class.getSimpleName();

    private static SystemConfigSimple sInstance;

    public static final class SharedLibraryEntry {
        public final String name;
        public final String filename;
        public final String[] dependencies;

        public SharedLibraryEntry(String name, String filename, String[] dependencies) {
            this.name = name;
            this.filename = filename;
            this.dependencies = dependencies;
        }
    }

    final ArrayMap<String, SharedLibraryEntry> mSharedLibraries = new ArrayMap<>();

    public static SystemConfigSimple getInstance() {
        synchronized (SystemConfigSimple.class) {
            if (sInstance == null) {
                sInstance = new SystemConfigSimple();
            }
            return sInstance;
        }
    }

    public ArrayMap<String, SharedLibraryEntry> getSharedLibraries() {
        return mSharedLibraries;
    }

    public SharedLibraryEntry getSharedLibrariesEntry(String name) {
        return getSharedLibraries().get(name);
    }

    public SystemConfigSimple() {
        readAllPermissions();
    }

    private void readAllPermissions() {
        final XmlPullParser parser = Xml.newPullParser();
        readPermissions(parser, new File("/etc/permissions/"));
    }

    public void readPermissions(final XmlPullParser parser, File libraryDir) {
        // Read permissions from given directory.
        if (!libraryDir.exists() || !libraryDir.isDirectory()) {
            RhLog.w(TAG, "No directory " + libraryDir + ", skipping");
            return;
        }
        if (!libraryDir.canRead()) {
            RhLog.w(TAG, "Directory " + libraryDir + " cannot be read");
            return;
        }

        // Iterate over the files in the directory and scan .xml files
        File platformFile = null;
        for (File f : libraryDir.listFiles()) {
            if (!f.isFile()) {
                continue;
            }

            // We'll read platform.xml last
            if (f.getPath().endsWith("etc/permissions/platform.xml")) {
                platformFile = f;
                continue;
            }

            if (!f.getPath().endsWith(".xml")) {
                RhLog.i(TAG, "Non-xml file " + f + " in " + libraryDir + " directory, ignoring");
                continue;
            }
            if (!f.canRead()) {
                RhLog.w(TAG, "Permissions library file " + f + " cannot be read");
                continue;
            }

            readPermissionsFromXml(parser, f);
        }

        // Read platform permissions last so it will take precedence
        if (platformFile != null) {
            readPermissionsFromXml(parser, platformFile);
        }
    }

    private void readPermissionsFromXml(final XmlPullParser parser, File permFile) {
        final FileReader permReader;
        try {
            permReader = new FileReader(permFile);
        } catch (FileNotFoundException e) {
            RhLog.w(TAG, "Couldn't find or open permissions file " + permFile);
            return;
        }
        RhLog.i(TAG, "Reading permissions from " + permFile);

        try {
            parser.setInput(permReader);

            int type;
            while ((type=parser.next()) != parser.START_TAG
                    && type != parser.END_DOCUMENT) {
                ;
            }

            if (type != parser.START_TAG) {
                throw new XmlPullParserException("No start tag found");
            }

            if (!parser.getName().equals("permissions") && !parser.getName().equals("config")) {
                throw new XmlPullParserException("Unexpected start tag in " + permFile
                        + ": found " + parser.getName() + ", expected 'permissions' or 'config'");
            }

            while (true) {
                RhXmlUtils.nextElement(parser);
                if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
                    break;
                }

                String name = parser.getName();
                if (name == null) {
                    RhXmlUtils.skipCurrentTag(parser);
                    continue;
                }
                switch (name) {
                    case "library": {
                        String lname = parser.getAttributeValue(null, "name");
                        String lfile = parser.getAttributeValue(null, "file");
                        String ldependency = parser.getAttributeValue(null, "dependency");

                        if (lname == null) {
                            RhLog.w(TAG, "<" + name + "> without name in " + permFile + " at "
                                    + parser.getPositionDescription());
                        } else if (lfile == null) {
                            RhLog.w(TAG, "<" + name + "> without file in " + permFile + " at "
                                    + parser.getPositionDescription());
                        } else {
                            final boolean exists = new File(lfile).exists();
                            if (exists) {
                                SharedLibraryEntry entry = new SharedLibraryEntry(lname, lfile,
                                        ldependency == null
                                                ? new String[0] : ldependency.split(":"));
                                mSharedLibraries.put(lname, entry);
                            } else {
                                final StringBuilder msg = new StringBuilder(
                                        "Ignore shared library ").append(lname).append(":");
                                if (!exists) {
                                    msg.append(" ").append(lfile).append(" does not exist");
                                }
                                RhLog.i(TAG, msg.toString());
                            }
                        }
                        RhXmlUtils.skipCurrentTag(parser);
                    } break;
                    default: {
                        RhLog.w(TAG, "Tag " + name + " is unknown in "
                                + permFile + " at " + parser.getPositionDescription());
                        RhXmlUtils.skipCurrentTag(parser);
                    } break;
                }
            }
        } catch (XmlPullParserException e) {
            RhLog.w(TAG, "Got exception parsing permissions.", e);
        } catch (IOException e) {
            RhLog.w(TAG, "Got exception parsing permissions.", e);
        } finally {
            RhIoUtils.closeQuietly(permReader);
        }
    }
}
