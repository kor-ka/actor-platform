//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-proprietary/actor-apps/core/src/main/java/im/actor/model/entity/content/AbsContent.java
//

#ifndef _AMAbsContent_H_
#define _AMAbsContent_H_

#include "J2ObjC_header.h"

@class APMessage;
@class IOSByteArray;
@class ImActorModelEntityContentInternalAbsContentContainer;
@class ImActorModelEntityContentInternalContentLocalContainer;
@class ImActorModelEntityContentInternalContentRemoteContainer;

@interface AMAbsContent : NSObject

#pragma mark Public

- (instancetype)initWithImActorModelEntityContentInternalContentLocalContainer:(ImActorModelEntityContentInternalContentLocalContainer *)contentContainer;

- (instancetype)initWithImActorModelEntityContentInternalContentRemoteContainer:(ImActorModelEntityContentInternalContentRemoteContainer *)contentContainer;

+ (AMAbsContent *)fromMessageWithAPMessage:(APMessage *)message;

- (ImActorModelEntityContentInternalAbsContentContainer *)getContentContainer;

+ (AMAbsContent *)parseWithByteArray:(IOSByteArray *)data;

+ (IOSByteArray *)serializeWithAMAbsContent:(AMAbsContent *)content;

#pragma mark Protected

+ (AMAbsContent *)convertDataWithImActorModelEntityContentInternalAbsContentContainer:(ImActorModelEntityContentInternalAbsContentContainer *)container;

@end

J2OBJC_EMPTY_STATIC_INIT(AMAbsContent)

FOUNDATION_EXPORT IOSByteArray *AMAbsContent_serializeWithAMAbsContent_(AMAbsContent *content);

FOUNDATION_EXPORT AMAbsContent *AMAbsContent_fromMessageWithAPMessage_(APMessage *message);

FOUNDATION_EXPORT AMAbsContent *AMAbsContent_parseWithByteArray_(IOSByteArray *data);

FOUNDATION_EXPORT AMAbsContent *AMAbsContent_convertDataWithImActorModelEntityContentInternalAbsContentContainer_(ImActorModelEntityContentInternalAbsContentContainer *container);

FOUNDATION_EXPORT void AMAbsContent_initWithImActorModelEntityContentInternalContentRemoteContainer_(AMAbsContent *self, ImActorModelEntityContentInternalContentRemoteContainer *contentContainer);

FOUNDATION_EXPORT void AMAbsContent_initWithImActorModelEntityContentInternalContentLocalContainer_(AMAbsContent *self, ImActorModelEntityContentInternalContentLocalContainer *contentContainer);

J2OBJC_TYPE_LITERAL_HEADER(AMAbsContent)

typedef AMAbsContent ImActorModelEntityContentAbsContent;

#endif // _AMAbsContent_H_
