//
//  SFUMobileParserUtils.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-21.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SFUMobileParserUtils : NSObject

+(NSString*)parseZone:(NSData*)data;
+(NSArray*)parseFriends:(NSData*)data;
+(NSString*)parseZoneName:(NSData*)data;

@end
