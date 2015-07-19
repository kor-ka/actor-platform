//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-proprietary/actor-apps/core-crypto/src/main/java/im/actor/model/crypto/bouncycastle/RandomProvider.java
//

#ifndef _BCRandomProvider_H_
#define _BCRandomProvider_H_

#include "J2ObjC_header.h"

@class IOSByteArray;
@class JavaMathBigInteger;

@protocol BCRandomProvider < NSObject, JavaObject >

- (IOSByteArray *)randomBytesWithInt:(jint)length;

- (jint)randomIntWithInt:(jint)maxValue;

- (void)nextBytesWithByteArray:(IOSByteArray *)data;

- (JavaMathBigInteger *)generateBigIntegerWithInt:(jint)numBits;

- (JavaMathBigInteger *)generateBigIntegerWithInt:(jint)numBits
                                          withInt:(jint)certanity;

@end

J2OBJC_EMPTY_STATIC_INIT(BCRandomProvider)

J2OBJC_TYPE_LITERAL_HEADER(BCRandomProvider)

#define ImActorModelCryptoBouncycastleRandomProvider BCRandomProvider

#endif // _BCRandomProvider_H_
