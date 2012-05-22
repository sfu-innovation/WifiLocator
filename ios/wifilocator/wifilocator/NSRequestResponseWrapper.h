//
//  NSRequestResponseWrapper.h
//  wifilocator
//
//  Created by Alex Kwan on 12-05-21.
//  Copyright (c) 2012 Simon Fraser University. All rights reserved.
//


@interface NSRequestResponseWrapper : NSObject <NSURLConnectionDelegate>
{
    NSMutableData*  _responseData;
    NSString*       _urlString;
    NSDictionary*   _params;
    NSURLConnection* _connection;
    id             _target;
    SEL            _action;
}
@property( nonatomic, retain) NSMutableData* _responseData;
@property( nonatomic, retain) id             _target;

@property (nonatomic, retain) NSURLConnection* _connection;

- (void)initGetRequest:(NSString*) urlString
            withTarget:(id)reciever onAction:(SEL) action;

- (void)initPostRequest:(NSString*) urlString
             withParams:(NSDictionary*) params
 withTarget:(id)reciever onAction:(SEL) action;


- (void)connection:(NSURLConnection *)connection 
                didFailWithError:(NSError *)error;

- (NSCachedURLResponse *)connection:(NSURLConnection *)connection willCacheResponse:(NSCachedURLResponse *)cachedResponse;

- (void)connection:(NSURLConnection *)connection 
                didReceiveResponse:(NSURLResponse *)response;

- (void)connection:(NSURLConnection *)connection 
                didReceiveData:(NSData *)data;

- (void)connection:(NSURLConnection *)connection 
                didSendBodyData:(NSInteger)bytesWritten 
                totalBytesWritten:(NSInteger)totalBytesWritten totalBytesExpectedToWrite:(NSInteger)totalBytesExpectedToWrite;

- (NSURLRequest *)connection:(NSURLConnection *)connection 
             willSendRequest:(NSURLRequest *)request 
             redirectResponse:(NSURLResponse *)redirectResponse;

- (void)connectionDidFinishLoading:(NSURLConnection *)connection;

@end
