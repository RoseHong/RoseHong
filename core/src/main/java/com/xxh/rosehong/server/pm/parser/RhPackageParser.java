package com.xxh.rosehong.server.pm.parser;

/**
 * @author xxh
 * @email mike_just@163.com
 * @date 2022/4/27 23:39
 */

import com.xxh.rosehong.framework.ref.android.content.pm.PackageParserRef;
import com.xxh.rosehong.server.pm.parser.component.activity.RhActivity;
import com.xxh.rosehong.server.pm.parser.component.instrumentation.RhInstrumentation;
import com.xxh.rosehong.server.pm.parser.component.permission.RhPermission;
import com.xxh.rosehong.server.pm.parser.component.permission.RhPermissionGroup;
import com.xxh.rosehong.server.pm.parser.component.provider.RhProvider;
import com.xxh.rosehong.server.pm.parser.component.service.RhService;

import java.io.File;
import java.util.ArrayList;

/**
 * 此类对应着android.content.pm.PackageParser，换句话讲就是，此类对apk的解析基本上是参考PackageParser来实现的，
 * 同时有些实现就是直接调用PackageParser里的方法。
 */
public class RhPackageParser {
    private static final String TAG = RhPackageParser.class.getSimpleName();

    public static RhPackage parsePackage(File packageFile) {
        Object packageParser = PackageParserRef.constructor.newInstance();
        Object basePackage = PackageParserRef.parsePackage.call(packageParser, packageFile, 0);

        RhPackage rhPackage = new RhPackage();
        rhPackage.packageName = PackageParserRef.PackageRef.packageName.get(basePackage);
        rhPackage.splitNames = PackageParserRef.PackageRef.splitNames.get(basePackage);
        rhPackage.codePath = PackageParserRef.PackageRef.codePath.get(basePackage);
        rhPackage.baseCodePath = PackageParserRef.PackageRef.baseCodePath.get(basePackage);
        rhPackage.splitCodePaths = PackageParserRef.PackageRef.splitCodePaths.get(basePackage);
        rhPackage.mSharedUserId = PackageParserRef.PackageRef.mSharedUserId.get(basePackage);
        rhPackage.mVersionCode = PackageParserRef.PackageRef.mVersionCode.get(basePackage);
        rhPackage.mVersionName = PackageParserRef.PackageRef.mVersionName.get(basePackage);
        rhPackage.mSharedUserLabel = PackageParserRef.PackageRef.mSharedUserLabel.get(basePackage);
        rhPackage.mExtras = PackageParserRef.PackageRef.mExtras.get(basePackage);
        rhPackage.use32bitAbi = PackageParserRef.PackageRef.use32bitAbi.get(basePackage);
        rhPackage.mAppMetaData = PackageParserRef.PackageRef.mAppMetaData.get(basePackage);
        rhPackage.mPreferredOrder = PackageParserRef.PackageRef.mPreferredOrder.get(basePackage);

        rhPackage.applicationInfo = PackageParserRef.PackageRef.applicationInfo.get(basePackage);

        ArrayList<Object> _permissions = PackageParserRef.PackageRef.permissions.get(basePackage);
        rhPackage.permissions = new ArrayList<>(_permissions.size());
        for (Object basePermission : _permissions) {
            rhPackage.permissions.add(RhPermission.make(rhPackage, basePermission));
        }
        
        ArrayList<Object> _permissionGroups = PackageParserRef.PackageRef.permissionGroups.get(basePackage);
        rhPackage.permissionGroups = new ArrayList<>(_permissionGroups.size());
        for (Object basePermissionGroup : _permissionGroups) {
            rhPackage.permissionGroups.add(RhPermissionGroup.make(rhPackage, basePermissionGroup));
        }

        ArrayList<Object> _activities = PackageParserRef.PackageRef.activities.get(basePackage);
        rhPackage.activities = new ArrayList<>(_activities.size());
        for (Object baseActivity : _activities) {
            rhPackage.activities.add(RhActivity.make(rhPackage, baseActivity));
        }

        ArrayList<Object> _receivers = PackageParserRef.PackageRef.receivers.get(basePackage);
        rhPackage.receivers = new ArrayList<>(_receivers.size());
        for (Object baseReceiver : _receivers) {
            rhPackage.receivers.add(RhActivity.make(rhPackage, baseReceiver));
        }

        ArrayList<Object> _providers = PackageParserRef.PackageRef.providers.get(basePackage);
        rhPackage.providers = new ArrayList<>(_providers.size());
        for (Object baseProvider : _providers) {
            rhPackage.providers.add(RhProvider.make(rhPackage, baseProvider));
        }

        ArrayList<Object> _services = PackageParserRef.PackageRef.services.get(basePackage);
        rhPackage.services = new ArrayList<>(_services.size());
        for (Object baseService : _services) {
            rhPackage.services.add(RhService.make(rhPackage, baseService));
        }

        ArrayList<Object> _instrumentation = PackageParserRef.PackageRef.instrumentation.get(basePackage);
        rhPackage.instrumentation = new ArrayList<>(_instrumentation.size());
        for (Object baseInstrumentation : _instrumentation) {
            rhPackage.instrumentation.add(RhInstrumentation.make(rhPackage, baseInstrumentation));
        }

        rhPackage.requestedPermissions = PackageParserRef.PackageRef.requestedPermissions.get(basePackage);
        rhPackage.usesLibraries = PackageParserRef.PackageRef.usesLibraries.get(basePackage);
        rhPackage.usesOptionalLibraries = PackageParserRef.PackageRef.usesOptionalLibraries.get(basePackage);
        rhPackage.configPreferences = PackageParserRef.PackageRef.configPreferences.get(basePackage);
        rhPackage.reqFeatures = PackageParserRef.PackageRef.reqFeatures.get(basePackage);
        if (PackageParserRef.PackageRef.mSignatures.isValid()) {    // Android P被封装成一个类了
            rhPackage.mSignatures = PackageParserRef.PackageRef.mSignatures.get(basePackage);
        } else {
            rhPackage.mSigningDetails = PackageParserRef.PackageRef.mSigningDetails.get(basePackage);
        }

        return rhPackage;
    }
}
