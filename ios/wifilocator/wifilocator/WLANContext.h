//
//  WLANContext.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-19.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface WLANContext : NSObject
-(NSString*)getBSSID;
-(NSMutableArray*)getData;
@end
