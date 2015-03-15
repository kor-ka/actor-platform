//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/actor-ios/build/java/im/actor/model/network/ActorApi.java
//

#include "J2ObjC_source.h"
#include "im/actor/model/NetworkProvider.h"
#include "im/actor/model/droidkit/actors/ActorRef.h"
#include "im/actor/model/network/ActorApi.h"
#include "im/actor/model/network/ActorApiCallback.h"
#include "im/actor/model/network/AuthKeyStorage.h"
#include "im/actor/model/network/Endpoints.h"
#include "im/actor/model/network/RpcCallback.h"
#include "im/actor/model/network/api/ApiBroker.h"
#include "im/actor/model/network/parser/Request.h"

@interface AMActorApi () {
 @public
  DKActorRef *apiBroker_;
}
@end

J2OBJC_FIELD_SETTER(AMActorApi, apiBroker_, DKActorRef *)

@implementation AMActorApi

- (instancetype)initWithAMEndpoints:(AMEndpoints *)endpoints
               withAMAuthKeyStorage:(id<AMAuthKeyStorage>)keyStorage
             withAMActorApiCallback:(id<AMActorApiCallback>)callback
              withAMNetworkProvider:(id<AMNetworkProvider>)networkProvider {
  if (self = [super init]) {
    self->apiBroker_ = ImActorModelNetworkApiApiBroker_getWithAMEndpoints_withAMAuthKeyStorage_withAMActorApiCallback_withAMNetworkProvider_(endpoints, keyStorage, callback, networkProvider);
  }
  return self;
}

- (void)requestWithImActorModelNetworkParserRequest:(ImActorModelNetworkParserRequest *)request
                                  withAMRpcCallback:(id<AMRpcCallback>)callback {
  [((DKActorRef *) nil_chk(self->apiBroker_)) sendWithId:[[ImActorModelNetworkApiApiBroker_PerformRequest alloc] initWithImActorModelNetworkParserRequest:request withAMRpcCallback:callback]];
}

- (void)copyAllFieldsTo:(AMActorApi *)other {
  [super copyAllFieldsTo:other];
  other->apiBroker_ = apiBroker_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(AMActorApi)
