//
//  TestTableViewController.m
//  wifilocator
//
//  Created by Alex Kwan on 12-05-19.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import "TestTableViewController.h"
#import "SFUMobileTweet.h"
#import "WLANContext.h"
#import <SystemConfiguration/CaptiveNetwork.h>
@interface TestTableViewController ()

@end

@implementation TestTableViewController

@synthesize players, responseData, connection;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    responseData = [[NSMutableData alloc] init];
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.players count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"TweetCell"];
    SFUMobileTweet *player = [self.players objectAtIndex:indexPath.row];
    UILabel *nameLabel = (UILabel *)[cell viewWithTag:101];
    nameLabel.text = player.name;
    
    UILabel *gameLabel = (UILabel *)[cell viewWithTag:100];
    gameLabel.text = player.text;
    
    UILabel *timeLabel = (UILabel *)[cell viewWithTag:102];
    timeLabel.text = player.time;
    
    UIImageView *imageLabel = (UIImageView *)[cell viewWithTag:103];
    imageLabel.image =  [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:player.image]]];
    return cell;
}

/*
// Override to support conditional editing of the table view.
- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return YES;
}
*/

/*
// Override to support editing the table view.
- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        // Delete the row from the data source
        [tableView deleteRowsAtIndexPaths:[NSArray arrayWithObject:indexPath] withRowAnimation:UITableViewRowAnimationFade];
    }   
    else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
    }   
}
*/

/*
// Override to support rearranging the table view.
- (void)tableView:(UITableView *)tableView moveRowAtIndexPath:(NSIndexPath *)fromIndexPath toIndexPath:(NSIndexPath *)toIndexPath
{
}
*/

/*
// Override to support conditional rearranging of the table view.
- (BOOL)tableView:(UITableView *)tableView canMoveRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the item to be re-orderable.
    return YES;
}
*/

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Navigation logic may go here. Create and push another view controller.
    /*
     <#DetailViewController#> *detailViewController = [[<#DetailViewController#> alloc] initWithNibName:@"<#Nib name#>" bundle:nil];
     // ...
     // Pass the selected object to the new view controller.
     [self.navigationController pushViewController:detailViewController animated:YES];
     */
}


- (IBAction)tweetTapped:(id)sender { 
    {
        if ([TWTweetComposeViewController canSendTweet])
        {
            TWTweetComposeViewController *tweetSheet = 
            [[TWTweetComposeViewController alloc] init];
            [tweetSheet setInitialText:
             @"#SFUZone6 "];
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

-(IBAction)refreshTweets:(id)sender{
    WLANContext* context = [[WLANContext alloc] init];
    [context getBSSID];
    
    NSString* macString = [NSString stringWithFormat:@"%@%@",
                           @"http://wifi-location.appspot.com/rest/Zones?feq_mac_address=",
                          /* @"00:1f:45:64:17:08"*/
                          [context getBSSID]];
    NSLog(@" The url I am sending is %@", macString);
    NSURLRequest *request2 = [NSURLRequest requestWithURL:[NSURL URLWithString:macString]];
    
    
    
	connection = [[NSURLConnection alloc] initWithRequest:request2 delegate:self];
    
    NSString* temp = @"SFUZone6";
    NSString* urlString = [NSString stringWithFormat:@"%@%@",
                           @"http://search.twitter.com/search.json?q=%23",
                           temp];
    
    TWRequest *request = [[TWRequest alloc] initWithURL:[NSURL URLWithString:
                                                         urlString] 
                                             parameters:nil requestMethod:TWRequestMethodGET];
    
    [request performRequestWithHandler:^(NSData *responseData, NSHTTPURLResponse *urlResponse, NSError *error)
     {
         if ([urlResponse statusCode] == 200) 
         {
             // The response from Twitter is in JSON format
             // Move the response into a dictionary and print
             NSError *error;        
             NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:responseData options:0 error:&error];
             NSArray* results = [dict objectForKey:@"results"];
             NSDictionary* temp;
             NSMutableArray* tweets = [NSMutableArray arrayWithCapacity:20];
             for(int i = 0; i < [results count]; i++){
                 temp = (NSDictionary*)[results objectAtIndex:i];
                 SFUMobileTweet* tweet = [[SFUMobileTweet alloc] init];
                /* NSLog(@"Name = %@\nText = %@\nImage = %@\nTime = %@",
                       [temp objectForKey:@"from_user"],
                       [temp objectForKey:@"text"],
                       [temp objectForKey:@"profile_image_url"],
                       [temp objectForKey:@"created_at"]);*/
                 tweet.name = [temp objectForKey:@"from_user"];
                 tweet.text = [temp objectForKey:@"text"];
                 tweet.image = [temp objectForKey:@"profile_image_url"];
                 tweet.time = [temp objectForKey:@"created_at"];
                 [tweets addObject:tweet];
             }
             self.players = tweets;
             [self.tableView reloadData];
         }
         else
             NSLog(@"Twitter error, HTTP response: %i", [urlResponse statusCode]);
     }];

}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
	[responseData setLength:0];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    if (responseData == nil ) {
        NSLog(@"responseData was null :(");
    }
	[responseData appendData:data];
    NSLog(@"We got data :D length = %d", [responseData length]);
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
	//label.text = [NSString stringWithFormat:@"Connection failed: %@", [error //description]];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
	NSString *responseString = [[NSString alloc] initWithData:responseData encoding:NSUTF8StringEncoding];
    NSError *error; 
	 NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:responseData options:0 error:&error];
    NSDictionary* list = [dict objectForKey:@"list"];
    NSDictionary* zone = [list objectForKey:@"Zones"];
    NSString* area = [zone objectForKey:@"zone_id"];
    NSLog(@"%@", area);
    self.navigationItem.prompt = area;
/*	NSMutableString *text = [NSMutableString stringWithString:@"Lucky numbers:\n"];
    
	for (int i = 0; i < [luckyNumbers count]; i++)
		[text appendFormat:@"%@\n", [luckyNumbers objectAtIndex:i]];
    
	label.text =  text;*/
}
@end
