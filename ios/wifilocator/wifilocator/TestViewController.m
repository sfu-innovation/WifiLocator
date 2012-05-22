//
//  TestViewController.m
//  wifilocator
//
//  Created by Alex Kwan on 12-05-18.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import "TestViewController.h"
#import <Twitter/Twitter.h>
@interface TestViewController ()

@end

@implementation TestViewController

- (IBAction)pageInfo {
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Info"
                                                    message:@"Currently Displaying View One"
                                                   delegate:self
                                          cancelButtonTitle:@"OK"
                                           otherButtonTitles:nil];
   // TWRequest* request;
    [alert show];
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
