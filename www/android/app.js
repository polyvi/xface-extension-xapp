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

/**
 * @module app
 */
var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

module.exports = {
	openUrl : function(url, successCallback, errorCallback){
        argscheck.checkArgs('sFF', 'XApp.openUrl', arguments);
        exec(successCallback, errorCallback, "XApp", "openUrl", [url]);
	},
	
	startNativeApp : function(packageName, parameter, successCallback, errorCallback){
        argscheck.checkArgs('sSFF', 'XApp.startNativeApp', arguments);
        exec(successCallback, errorCallback, "XApp", "startNativeApp", [packageName, parameter]);
    },
	
	isNativeAppInstalled : function(packageName, successCallback, errorCallback){
        argscheck.checkArgs('sFF', 'XApp.isNativeAppInstalled', arguments);
        exec(successCallback, errorCallback, "XApp", "isNativeAppInstalled", [packageName]);
    },
	
	telLinkEnable : function(isTelLinkEnable, successCallback, errorCallback){
        argscheck.checkArgs('bFF', 'XApp.telLinkEnable', arguments);
        exec(successCallback, errorCallback, "XApp", "telLinkEnable", [isTelLinkEnable]);
    },
	
	setWifiSleepPolicy : function(wifiSleepPolicy,successCallback, errorCallback){
        argscheck.checkArgs('sFF', 'XApp.setWifiSleepPolicy', arguments);
        exec(successCallback, errorCallback, "XApp", "setWifiSleepPolicy", [wifiSleepPolicy]);
    },
	
    startSystemComponent : function(name, successCallback, errorCallback){
        argscheck.checkArgs('nFF', 'XApp.startSystemComponent', arguments);
        exec(successCallback, errorCallback, "XApp", "startSystemComponent", [name]);
    },
	
	install : function(path, successCallback, errorCallback){
        argscheck.checkArgs('sFF', 'XApp.install', arguments);
        exec(successCallback, errorCallback, "XApp", "install", [path]);
    }
};
