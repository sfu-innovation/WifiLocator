//
//  SFUMobileParserUtils.m
//  wifilocator
//
//  Created by Alex Kwan on 12-05-21.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import "SFUMobileParserUtils.h"
#import "SFUMobileFriend.h"
@implementation SFUMobileParserUtils

+(NSString*)parseZone:(NSData*)data{
    NSError *error; 
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:0 error:&error];
    NSDictionary* list = [dict objectForKey:@"list"];
    NSDictionary* zone = [list objectForKey:@"BSSIDZones"];
    NSLog(@" This is teh key!!! \n\n\n %@", [zone objectForKey:@"zones"]);
    return [zone objectForKey:@"zones"];
}
+(NSArray*)parseFriends:(NSData*)data{
    NSMutableArray* myFriends;
    NSError *error; 
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:0 error:&error];
   
    NSDictionary* list = [dict objectForKey:@"list"];
   //  NSLog(@"!!!!! %@", list);
    NSArray* friends = [list objectForKey:@"Friends"];
    int length = [friends count];

    myFriends = [[NSMutableArray alloc] initWithCapacity:length];
    SFUMobileFriend* tempFriend;
    NSDictionary* tempDic;
    for(int i = 0; i< length; i++){
        tempDic = (NSDictionary*)[friends objectAtIndex:i];
        tempFriend = [[SFUMobileFriend alloc] init];
        tempFriend.name = [tempDic objectForKey:@"friend_name"];
        tempFriend.location = @"";
        tempFriend.time = @"";
       // NSLog(@"Current friend = %@", [tempFriend name]);
        [myFriends addObject:tempFriend];
    }
    return myFriends;
}

+(NSString*)parseZoneName:(NSData*)data{
    NSError *error; 
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:data options:0 error:&error];
   // NSDictionary* list = [dict objectForKey:@"list"];
    NSDictionary* zoneNames = [dict objectForKey:@"Areas"];
    NSLog(@"Printing areas \n %@", zoneNames);
    NSLog(@" This is teh key!!! \n\n\n %@", [zoneNames objectForKey:@"zone_name"]);
    return [zoneNames objectForKey:@"zone_name"];
}
@end
