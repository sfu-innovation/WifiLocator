//
//  LoginViewController.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-21.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface LoginViewController : UIViewController{
    IBOutlet UITextField *usernameField;
    IBOutlet UITextField *passwordField;
}
-(IBAction)closeTextField:(id)sender;
@end
