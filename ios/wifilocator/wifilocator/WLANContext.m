//
//  WLANContext.m
//  wifilocator
//
//  Created by Alex Kwan on 12-05-19.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import "WLANContext.h"
#import "SFUMobileTweet.h"
@implementation WLANContext
-(NSString*)getBSSID{
    CFArrayRef myArray = CNCopySupportedInterfaces();
    if(myArray!=nil){
        CFDictionaryRef myDict = CNCopyCurrentNetworkInfo(/*CFArrayGetValueAtIndex(myArray, 0)*/CFSTR("en0"));
        return (__bridge_transfer NSString *)CFDictionaryGetValue( myDict, CFSTR("BSSID"));

    }
}

-(NSMutableArray*)getData{
    NSMutableArray* players = [NSMutableArray arrayWithCapacity:20];
    SFUMobileTweet* player = [[SFUMobileTweet alloc] init];
    player.name = @"Bill Evans";
    player.game = @"Tic-Tac-Toe";
    [players addObject:player];
    player = [[SFUMobileTweet alloc] init];
    player.name = @"Oscar Peterson";
    player.game = @"Spin the Bottle";
    [players addObject:player];
    player = [[SFUMobileTweet alloc] init];
    player.name = @"Dave Brubeck";
    player.game = @"Texas Hold’em Poker";
    [players addObject:player];
NSLog(@" ===========In getData %@", players);
    return players;
}
@end
