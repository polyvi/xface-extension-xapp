
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
//  XAppExt.m
//

#import "XAppExt.h"
#import <xFace/XApplication.h>
#import <xFace/XAppInfo.h>
#import <Cordova/CDVInvokedUrlCommand.h>
#import <Cordova/CDVPluginResult.h>

@implementation XAppExt

- (void) openUrl:(CDVInvokedUrlCommand*)command
{
    NSString *urlStr = [command.arguments objectAtIndex:0];
    [self openURL:urlStr callbackId:command.callbackId];
}

- (void) getChannel:(CDVInvokedUrlCommand*)command
{
    id<XApplication> app = [self ownerApp];
    CDVPluginResult *result = nil;

    if (app.appInfo.channelId.length > 0 && app.appInfo.channelName.length > 0)
    {
        NSDictionary* channel = @{@"id": app.appInfo.channelId, @"name":app.appInfo.channelName};
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:channel];
    }
    else
    {
        result = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    // 将执行结果返回给js端
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void) startNativeApp:(CDVInvokedUrlCommand*)command
{
    NSString *appURL = [command.arguments objectAtIndex:0];
    NSString *parameter = [command.arguments objectAtIndex:1];
    NSString *url = [appURL stringByAppendingFormat:@"%@", parameter];
    [self openURL:url callbackId:command.callbackId];
}

#pragma mark Privates

-(void) openURL:(NSString*)urlStr callbackId:(NSString*)callbackId
{
    NSURL *url = [NSURL URLWithString:urlStr];
    BOOL ret = [[UIApplication sharedApplication] canOpenURL:url] &&
    [[UIApplication sharedApplication] openURL:url];

    CDVPluginResult *result = [CDVPluginResult resultWithStatus:ret ? CDVCommandStatus_OK : CDVCommandStatus_ERROR];

    // 将执行结果返回给js端
    [self.commandDelegate sendPluginResult:result callbackId:callbackId];
}

@end