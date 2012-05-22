//
//  TestTableViewController.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-19.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Twitter/Twitter.h>
#import "WLANContext.h"
@interface TestTableViewController : UITableViewController
@property (nonatomic, strong) NSMutableArray *players;
@property (nonatomic, retain) NSMutableData* responseData;
@property (nonatomic, retain) NSURLConnection* connection;
@property (nonatomic, retain) NSString*        currentLocation;
- (IBAction)tweetTapped:(id)sender;
- (IBAction)refreshTweets:(id)sender;
- (void)testFunction2:(NSData*) data;
- (void)testFunction:(NSData*) data;
@end
