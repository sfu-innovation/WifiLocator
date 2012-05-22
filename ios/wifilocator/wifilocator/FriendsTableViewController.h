//
//  FriendsTableViewController.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-20.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface FriendsTableViewController : UITableViewController
@property (nonatomic, retain) NSMutableArray* friends;
-(void)loadRefreshedFriendsList:(NSData*)data;
-(IBAction)refreshFriends:(id)sender;
@end
