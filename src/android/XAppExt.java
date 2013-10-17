/*
 This file was modified from or inspired by Apache Cordova.

 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements. See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership. The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License. You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied. See the License for the
 specific language governing permissions and limitations
 under the License.
 */

package com.polyvi.xface.extension.app;

import java.io.File;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.polyvi.xface.core.XConfiguration;
import com.polyvi.xface.util.XConstant;
import com.polyvi.xface.util.XFileUtils;
import com.polyvi.xface.util.XLog;
import com.polyvi.xface.util.XStringUtils;

public class XAppExt extends CordovaPlugin {
	private static final String CLASS_NAME = "XAppExt";
	private static final String COMMAND_OPEN_URL = "openUrl";
	private static final String COMMAND_INSTALL = "install";
	private static final String COMMAND_START_SYSTEM_COMPONENT = "startSystemComponent";
	private static final String COMMAND_SET_WIFI_SLEEP_POLICY = "setWifiSleepPolicy";
	private static final String COMMAND_START_NATIVE_APP = "startNativeApp";
	private static final String COMMAND_IS_NATIVE_APP_INSTALLED = "isNativeAppInstalled";
	private static final String COMMAND_TEL_LINK_ENABLE = "telLinkEnable";

	private static final String APK_TYPE = "application/vnd.android.package-archive";

	/** 要启动的component名字 */
	public enum SysComponent {
		VPN_CONFIG, // vpn设置界面
		WIRELESS_CONFIG, // 网络设置界面
		GPS_CONFIG, // gps设置界面
		UNKNOWN // 未知的组件名
	};

	/** wifi休眠3种方式 */
	private static final String WIFI_SLEEP_POLICY_DEFAULT = "wifi_sleep_policy_default";
	private static final String WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED = "wifi_sleep_policy_never_while_plugged";
	private static final String WIFI_SLEEP_POLICY_NEVER = "wifi_sleep_policy_never";

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		try {
			if (COMMAND_OPEN_URL.equals(action)) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				String url = args.getString(0);
				Uri uri = getUrlFromPath(url);
				if (null == uri) {
					String errorResult = "file not exist";
					callbackContext.error(errorResult);
					return true;
				}
				setDirPermisionUntilWorkspace(uri);
				setIntentByUri(intent, uri);
				cordova.getActivity().startActivity(intent);
			} else if (COMMAND_INSTALL.equals(action)) {
				install(args.getString(0));
			} else if (COMMAND_START_SYSTEM_COMPONENT.equals(action)) {
				if (!startSystemComponent(args.getInt(0))) {
					String errorResult = "Unsupported component:: "
							+ args.getString(0);
					callbackContext.error(errorResult);
					return true;
				}
			} else if (COMMAND_SET_WIFI_SLEEP_POLICY.equals(action)) {
				if (!setWifiSleepPolicy(args.getString(0))) {
					String errorResult = "set wifi sleep policy error";
					callbackContext.error(errorResult);
					return true;
				}
			} else if (COMMAND_START_NATIVE_APP.equalsIgnoreCase(action)) {
				if (!startNativeApp(cordova.getActivity(), args.getString(0),
						args.getString(1))) {
					PluginResult result = new PluginResult(
							PluginResult.Status.ERROR);
					callbackContext.sendPluginResult(result);
					return true;
				}
			} else if (COMMAND_IS_NATIVE_APP_INSTALLED.equals(action)) {
				boolean installResult = false;
				if (isAppInstalled(args.getString(0))) {
					installResult = true;
				}
				PluginResult result = new PluginResult(PluginResult.Status.OK,
						installResult);
				callbackContext.sendPluginResult(result);
				return true;
			} else if (COMMAND_TEL_LINK_ENABLE.equals(action)) {
				XConfiguration.getInstance().setTelLinkEnabled(
						args.optBoolean(0, true));
			}
			callbackContext.success();
			return true;
		} catch (IllegalArgumentException e) {
			Log.e(CLASS_NAME, e.getMessage());
			callbackContext.sendPluginResult(new PluginResult(
					PluginResult.Status.ERROR));
		}
		return false;
	}

	private void setIntentByUri(Intent intent, Uri uri) {
		if (!XConstant.FILE_SCHEME.contains(uri.getScheme())) {
			intent.setData(uri);
		} else {
			String mimeType = XFileUtils.getMIMEType(uri.toString());
			intent.setDataAndType(uri,
					XStringUtils.isEmptyString(mimeType) ? "*/*" : mimeType);
		}

	}

	private void setDirPermisionUntilWorkspace(Uri uri) {
		// 设置播放文件的权限
		// 注意:需要设置到workspace这一层
		if (!XConstant.FILE_SCHEME.contains(uri.getScheme())) {
			return;
		}
		String filePath = uri.getPath();
		File fileObj = new File(filePath);
		String path = fileObj.getAbsolutePath();
		// 设置文件权限
		XFileUtils.setPermission(XFileUtils.ALL_PERMISSION, path);
		fileObj = new File(fileObj.getParent());
	}

	/**
	 * 安装apk
	 *
	 * @param path
	 *            要安装的apk本地文件的路径
	 */
	public void install(String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		// 修改文件的权限为其它用户可读, 否则系统apk安装程序无法安装
		XFileUtils.setPermission(XFileUtils.READABLE_BY_OTHER, path);
		intent.setDataAndType(Uri.fromFile(new File(path)), APK_TYPE);
		cordova.getActivity().startActivity(intent);
	}

	/**
	 * 开启一个系统componen
	 *
	 * @param componenName
	 *            要启动的componen名字 return true：启动成功,false：启动失败。
	 */
	private boolean startSystemComponent(int componentCode) {
		SysComponent componentName = SysComponent.UNKNOWN;
		try {
			componentName = SysComponent.values()[componentCode];
		} catch (ArrayIndexOutOfBoundsException e) {
			XLog.d(CLASS_NAME, "unkown component name!", e);
			return false;
		}
		Intent intent = null;
		boolean success = false;
		switch (componentName) {
		case VPN_CONFIG:
			intent = new Intent("android.net.vpn.SETTINGS");
			break;
		case WIRELESS_CONFIG:
			intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
			break;
		case GPS_CONFIG:
			intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			break;
		default:
			XLog.d(CLASS_NAME, "unkown component name!");
			break;
		}
		if (null != intent) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			cordova.getActivity().startActivity(intent);
			success = true;
		}
		return success;
	}

	/**
	 * 获取给定path的uri
	 *
	 * @param app
	 *            应用
	 * @param path
	 *            url路径 return Uri 成功:uri,失败:null。
	 */
	private Uri getUrlFromPath(String path) {
		Uri uri = null;
		if (path.startsWith(XConstant.HTTP_SCHEME)) {
			uri = Uri.parse(path);
		} else {
			File file = new File(path);
			if (file.exists()) {
				uri = Uri.fromFile(file);
			}
		}
		return uri;
	}

	/**
	 * 设置wifi休眠策略
	 *
	 * @param wifiSleepPolicy
	 *            休眠策略
	 * @return true:设置成功,false:设置失败
	 */
	private boolean setWifiSleepPolicy(String wifiSleepPolicy) {
		if (WIFI_SLEEP_POLICY_DEFAULT.equals(wifiSleepPolicy)) {
			return Settings.System.putInt(cordova.getActivity()
					.getContentResolver(), Settings.System.WIFI_SLEEP_POLICY,
					Settings.System.WIFI_SLEEP_POLICY_DEFAULT);
		} else if (WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED
				.equals(wifiSleepPolicy)) {
			return Settings.System.putInt(cordova.getActivity()
					.getContentResolver(), Settings.System.WIFI_SLEEP_POLICY,
					Settings.System.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED);
		} else if (WIFI_SLEEP_POLICY_NEVER.equals(wifiSleepPolicy)) {
			return Settings.System.putInt(cordova.getActivity()
					.getContentResolver(), Settings.System.WIFI_SLEEP_POLICY,
					Settings.System.WIFI_SLEEP_POLICY_NEVER);
		} else {
			return false;
		}
	}

	/**
	 * 启动应用程序
	 *
	 * @param packageName
	 *            应用程序包的名字
	 * @param parameter
	 *            应用程序参数
	 * @return 成功返回true,失败返回false
	 */
	public boolean startNativeApp(Context context, String packageName,
			String parameter) {
		if (null == packageName) {
			return false;
		}

		PackageManager pm = context.getPackageManager();
		Intent intent = null;
		try {
			intent = pm.getLaunchIntentForPackage(packageName);
			if (null == intent) {
				return false;
			}
			intent.putExtra(XConstant.TAG_APP_START_PARAMS, parameter);
			context.startActivity(intent);
		} catch (Exception e) {
			XLog.e(CLASS_NAME, "error when startNativeApp:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 判断程序是否安装成功
	 *
	 * @param appId
	 *            程序id号码
	 * @return true：系统已经安装，程序未安装
	 */
	public boolean isAppInstalled(String appId) {
		if (null == appId) {
			return false;
		}

		try {
			cordova.getActivity()
					.getPackageManager()
					.getApplicationInfo(appId,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
}
