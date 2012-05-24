//
//  GetRequestResponse.m
//  wifilocator
//
//  Created by Alex Kwan on 12-05-21.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//

#import "NSRequestResponseWrapper.h"

@implementation NSRequestResponseWrapper;

@synthesize _responseData, _target, _connection;

- (void)initGetRequest:(NSString*) urlString
            withTarget:(id)reciever onAction:(SEL) action{
        self._target = reciever;
        self->_action = action;
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:urlString]];
    
	_connection = [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
}

- (void)initPostRequest:(NSString*) urlString
             withParams:(NSDictionary*) params
             withTarget:(id)reciever onAction:(SEL) action;{
    NSLog(@" POST Request not implemented yet");
}


- (void)connection:(NSURLConnection *)connection 
  didFailWithError:(NSError *)error{
    NSLog(@" an error occured, %@", [error description]);
}

- (NSCachedURLResponse *)connection:(NSURLConnection *)connection willCacheResponse:(NSCachedURLResponse *)cachedResponse{
    return cachedResponse;
}

- (void)connection:(NSURLConnection *)connection 
didReceiveResponse:(NSURLResponse *)response{
    _responseData = [[NSMutableData alloc] init];
    
}

- (void)connection:(NSURLConnection *)connection 
    didReceiveData:(NSData *)data{
    [_responseData appendData:data];
    NSLog(@"Current data as a string \n %@",
          [[NSString alloc] initWithData:_responseData encoding:NSUTF8StringEncoding]);
}

- (void)connection:(NSURLConnection *)connection 
   didSendBodyData:(NSInteger)bytesWritten 
 totalBytesWritten:(NSInteger)totalBytesWritten totalBytesExpectedToWrite:(NSInteger)totalBytesExpectedToWrite{
    NSLog(@" Sent the request -> bytes written %d, total bytes written %d , \
          total bytes to write %d:",
          bytesWritten,  totalBytesWritten, totalBytesExpectedToWrite);
}

- (NSURLRequest *)connection:(NSURLConnection *)connection 
willSendRequest:(NSURLRequest *)request 
            redirectResponse:(NSURLResponse *)redirectResponse{
    NSLog(@"Sending request nao!\n %@", [[request URL] absoluteString]);
    return request;
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection{
  /*  NSLog(@"connectionDidFinishLoading");
    NSString *responseString = [[NSString alloc] initWithData:_responseData encoding:NSUTF8StringEncoding];
    NSLog(@"This is the response from the request that you just sent");*/
	[_target performSelector:_action
                  withObject:_responseData];
}

@end
