//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/actor-ios/build/java/im/actor/model/network/mtp/AuthIdRetriever.java
//

#ifndef _MTAuthIdRetriever_H_
#define _MTAuthIdRetriever_H_

@class AMEndpoints;
@class IOSBooleanArray;
@class IOSByteArray;
@protocol AMConnection;
@protocol AMNetworkProvider;
@protocol MTAuthIdRetriever_AuthIdCallback;

#include "J2ObjC_header.h"
#include "im/actor/model/network/ConnectionCallback.h"
#include "im/actor/model/network/CreateConnectionCallback.h"

@interface MTAuthIdRetriever : NSObject {
}

+ (void)requestAuthIdWithAMEndpoints:(AMEndpoints *)endpoints
               withAMNetworkProvider:(id<AMNetworkProvider>)networkProvider
withMTAuthIdRetriever_AuthIdCallback:(id<MTAuthIdRetriever_AuthIdCallback>)callback;

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(MTAuthIdRetriever)

CF_EXTERN_C_BEGIN

FOUNDATION_EXPORT void MTAuthIdRetriever_requestAuthIdWithAMEndpoints_withAMNetworkProvider_withMTAuthIdRetriever_AuthIdCallback_(AMEndpoints *endpoints, id<AMNetworkProvider> networkProvider, id<MTAuthIdRetriever_AuthIdCallback> callback);

FOUNDATION_EXPORT NSString *MTAuthIdRetriever_TAG_;
J2OBJC_STATIC_FIELD_GETTER(MTAuthIdRetriever, TAG_, NSString *)
CF_EXTERN_C_END

typedef MTAuthIdRetriever ImActorModelNetworkMtpAuthIdRetriever;

J2OBJC_TYPE_LITERAL_HEADER(MTAuthIdRetriever)

@protocol MTAuthIdRetriever_AuthIdCallback < NSObject, JavaObject >

- (void)onSuccessWithLong:(jlong)authId;

- (void)onFailure;

@end

J2OBJC_EMPTY_STATIC_INIT(MTAuthIdRetriever_AuthIdCallback)

J2OBJC_TYPE_LITERAL_HEADER(MTAuthIdRetriever_AuthIdCallback)

@interface MTAuthIdRetriever_$1 : NSObject < AMConnectionCallback > {
}

- (void)onMessage:(IOSByteArray *)data withOffset:(jint)offset withLen:(jint)len;

- (void)onConnectionDie;

- (instancetype)initWithBooleanArray:(IOSBooleanArray *)capture$0
withMTAuthIdRetriever_AuthIdCallback:(id<MTAuthIdRetriever_AuthIdCallback>)capture$1;

@end

J2OBJC_EMPTY_STATIC_INIT(MTAuthIdRetriever_$1)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(MTAuthIdRetriever_$1)

@interface MTAuthIdRetriever_$2 : NSObject < AMCreateConnectionCallback > {
}

- (void)onConnectionCreated:(id<AMConnection>)connection;

- (void)onConnectionCreateError;

- (instancetype)initWithBooleanArray:(IOSBooleanArray *)capture$0
withMTAuthIdRetriever_AuthIdCallback:(id<MTAuthIdRetriever_AuthIdCallback>)capture$1;

@end

J2OBJC_EMPTY_STATIC_INIT(MTAuthIdRetriever_$2)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(MTAuthIdRetriever_$2)

#endif // _MTAuthIdRetriever_H_
