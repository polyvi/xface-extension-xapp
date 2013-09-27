
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

//
//  XAppExt.h
//

#import <xFace/CDVPlugin+XPlugin.h>

@interface XAppExt : CDVPlugin

/**
    调用系统自带的浏览器打开url
    @param command.arguments
        - 0 NSString* url          待打开的url
 */
- (void) openUrl:(CDVInvokedUrlCommand*)command;

/**
    启动本地应用
    @param command.arguments
        - 0 NSString* appURL       app的URL
        - 1 NSString* parameter    app启动参数
 */
- (void) startNativeApp:(CDVInvokedUrlCommand*)command;

/**
    获取渠道信息
    @param command.arguments 无参数
 */
- (void) getChannel:(CDVInvokedUrlCommand*)command;

@end