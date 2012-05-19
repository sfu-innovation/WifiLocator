//
//  TestTableViewController.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-19.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Twitter/Twitter.h>
@interface TestTableViewController : UITableViewController
@property (nonatomic, strong) NSMutableArray *players;

- (IBAction)tweetTapped:(id)sender;
@end
