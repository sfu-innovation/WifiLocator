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
/*Class designed to just get wlan stuff in this case BSSID*/
+(NSString*)getBSSID{
    CFArrayRef myArray = CNCopySupportedInterfaces();
    if(myArray!=nil){
        CFDictionaryRef myDict = 
        
        CNCopyCurrentNetworkInfo(/*CFArrayGetValueAtIndex(myArray, 0)*/CFSTR("en0"));
        NSLog(@"%@", myDict);
       
        NSString* str =  [(__bridge_transfer NSString *)CFDictionaryGetValue( myDict, CFSTR("BSSID")) copy ];
        //CFRelease( myDict );
        //CFRelease( myArray );
     //   NSLog(@"This is the bssid %@", str );
        return str;

    }

    
}

@end
