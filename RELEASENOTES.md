<!--
#
# Copyright 2012-2013, Polyvi Inc. (http://polyvi.github.io/openxface)
# This program is distributed under the terms of the GNU General Public License.
# 
# This file is part of xFace.
# 
# xFace is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
# 
# xFace is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with xFace.  If not, see <http://www.gnu.org/licenses/>.
#
-->

# Release Notes
### 1.0.0 Tue Jan 07 2014 16:08:57 GMT+0800 (CST)
 *  [android][Sync xFace3.1 core]Move startNativeApp method from XAppExt to XAppUtils
 *  added jasmine tests unique id
### 1.0.1 Mon Jan 27 2014 15:56:34 GMT+0800 (CST)
 *  batch modify .reviewboard
 *  #6:set the Wifi sleep is fail
 *  polyvi/xface-extension-xapp#2[polyvi/xface-extension-xapp]Fix open pdf file and doc file failed
 *  [android]Fix crash when xFace was quitted, and user registered app ext but didn't use it at same time
 *  [android][Sync xFace3.1]Add interface 'queryInstalledNativeApp' and 'uninstallNativeApp'
 *  [android][Sync xFace3.1]1.[Fixed bug on Bestv] Interface 'openUrl' of app extension can not work as expected;2.Interface 'openUrl' support https protocol
 *  Incremented plugin version on dev branch to 1.0.1-dev

## 1.0.2 (Fri Feb 28 2014)


 *  [Android]OpenUrl and install interfaces support url 'cdvfile://localhost/<filesystemType>/<path to file>' in xapp plugin
 *  issue 1 [android][Fix bug]Fix 'loadUrlByAppCleanHistory' button can't be used in test page. Bug reason:load url interface params have been changed in cordova 3.3. Solution:Use new interface params
 *  Incremented plugin version on dev branch to 1.0.2-dev


## 1.0.3 (Wed Mar 19 2014)


 *  renamed app.js to xapp.js, to avoid overwritten while performing doc integration
 *  reduced size of testing data
 *  issue 3 App: setTelLinkEnable does not work.Solution:delete this test
 *  fix bug http://gitlab.polyvi.com/xface/xface-extension-xapp/issues/2
 *  Incremented plugin version on dev branch to 1.0.3-dev


## 1.0.4 (Thu Apr 03 2014)


 *  issue 5 app->setWifiSleepDefault/setWifiSleepNeverWhilePlugged/setWifiSleepNever has no result. Bug reason:missing android.permission.WRITE_SETTINGS in AndroidManifest.xml. Solution:Add it in AndroidManifest.xml
 *  Incremented plugin version on dev branch to 1.0.4-dev
