//
//  TweetTestViewController.m
//  wifilocator
//
//  Created by Alex Kwan on 12-05-18.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import "TweetTestViewController.h"

@interface TweetTestViewController ()

@end

@implementation TweetTestViewController

- (IBAction)tweetTapped:(id)sender { 
    {
        if ([TWTweetComposeViewController canSendTweet])
        {
            TWTweetComposeViewController *tweetSheet = 
            [[TWTweetComposeViewController alloc] init];
            [tweetSheet setInitialText:
             @"[SFU-Burnaby Library]"];
            [self presentModalViewController:tweetSheet animated:YES];
        }
        else
        {
            UIAlertView *alertView = [[UIAlertView alloc] 
                                      initWithTitle:@"Sorry"                                                             
                                      message:@"You can't send a tweet right now, make sure  \
                                      your device has an internet connection and you have    \
                                      at least one Twitter account setup"                                                          
                                      delegate:self                                              
                                      cancelButtonTitle:@"OK"                                                   
                                      otherButtonTitles:nil];
            [alertView show];
        }
    }
}
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
