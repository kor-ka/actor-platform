//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-proprietary/actor-apps/core/src/main/java/im/actor/model/network/mtp/entity/SessionLost.java
//

#ifndef _MTSessionLost_H_
#define _MTSessionLost_H_

#include "J2ObjC_header.h"
#include "im/actor/model/network/mtp/entity/ProtoStruct.h"

@class BSDataInput;
@class BSDataOutput;

#define MTSessionLost_HEADER 16

@interface MTSessionLost : MTProtoStruct

#pragma mark Public

- (instancetype)init;

- (instancetype)initWithBSDataInput:(BSDataInput *)stream;

#pragma mark Protected

- (jbyte)getHeader;

- (void)readBodyWithBSDataInput:(BSDataInput *)bs;

- (void)writeBodyWithBSDataOutput:(BSDataOutput *)bs;

@end

J2OBJC_EMPTY_STATIC_INIT(MTSessionLost)

J2OBJC_STATIC_FIELD_GETTER(MTSessionLost, HEADER, jint)

FOUNDATION_EXPORT void MTSessionLost_initWithBSDataInput_(MTSessionLost *self, BSDataInput *stream);

FOUNDATION_EXPORT MTSessionLost *new_MTSessionLost_initWithBSDataInput_(BSDataInput *stream) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT void MTSessionLost_init(MTSessionLost *self);

FOUNDATION_EXPORT MTSessionLost *new_MTSessionLost_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(MTSessionLost)

typedef MTSessionLost ImActorModelNetworkMtpEntitySessionLost;

#endif // _MTSessionLost_H_
