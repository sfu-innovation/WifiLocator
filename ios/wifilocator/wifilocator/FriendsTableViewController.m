//
//  FriendsTableViewController.m
//  wifilocator
//
//  Created by Alex Kwan on 12-05-20.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import "FriendsTableViewController.h"
#import "SFUMobileFriend.h"
@interface FriendsTableViewController ()

@end

@implementation FriendsTableViewController
@synthesize friends;
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
    return [friends count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView
                             dequeueReusableCellWithIdentifier:@"FriendCell"];
    SFUMobileFriend *friend = [self.friends objectAtIndex:indexPath.row];
    UILabel *nameLabel = (UILabel *)[cell viewWithTag:100];
    nameLabel.text = friend.name;
    
    UILabel *locationLabel = (UILabel *)[cell viewWithTag:101];
    locationLabel.text = friend.location;
    
    UILabel *timeLabel = (UILabel *)[cell viewWithTag:102];
    timeLabel.text = friend.time;
    
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

-(IBAction)refreshFriends:(id)sender{
    
    
    
    NSMutableArray* tempFriends = [[NSMutableArray alloc]initWithCapacity:20];
    SFUMobileFriend* mike = [[SFUMobileFriend alloc] init];
    mike.name = @"Mike";
    mike.location = @"SFUZone1";
    mike.time = @"2 hours ago";
    
    SFUMobileFriend* hedy = [[SFUMobileFriend alloc] init];
    hedy.name = @"Hedy";
    hedy.location = @"SFUZone2";
    hedy.time = @"5 minutes ago";
    
    SFUMobileFriend* catherine = [[SFUMobileFriend alloc] init];
    catherine.name = @"Catherine";
    catherine.location = @"SFUZone6";
    catherine.time = @"1 hour ago";
    
    SFUMobileFriend* jordan = [[SFUMobileFriend alloc] init];
    jordan.name = @"Jordan";
    jordan.location = @"SFUZone7";
    catherine.time = @"5 minutes ago";
    
    [tempFriends addObject:mike];
    [tempFriends addObject:catherine];
    [tempFriends addObject:hedy];
    [tempFriends addObject:jordan];
    
    friends = tempFriends;
    [self.tableView reloadData];
    
}
@end
