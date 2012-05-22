//
//  SFUMobileFriend.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-20.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface SFUMobileFriend : NSObject
{
    NSString* name;
    NSString* location;
    NSString* time;
}
@property (nonatomic, retain) NSString *name;
@property (nonatomic, retain) NSString *location;
@property (nonatomic, retain) NSString *time;

@end
