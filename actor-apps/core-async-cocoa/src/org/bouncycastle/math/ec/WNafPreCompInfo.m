//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-proprietary/actor-apps/core-crypto/src/main/java/org/bouncycastle/math/ec/WNafPreCompInfo.java
//


#include "IOSObjectArray.h"
#include "J2ObjC_source.h"
#include "org/bouncycastle/math/ec/ECPoint.h"
#include "org/bouncycastle/math/ec/WNafPreCompInfo.h"

@implementation OrgBouncycastleMathEcWNafPreCompInfo

- (IOSObjectArray *)getPreComp {
  return preComp_;
}

- (void)setPreCompWithOrgBouncycastleMathEcECPointArray:(IOSObjectArray *)preComp {
  self->preComp_ = preComp;
}

- (IOSObjectArray *)getPreCompNeg {
  return preCompNeg_;
}

- (void)setPreCompNegWithOrgBouncycastleMathEcECPointArray:(IOSObjectArray *)preCompNeg {
  self->preCompNeg_ = preCompNeg;
}

- (OrgBouncycastleMathEcECPoint *)getTwice {
  return twice_;
}

- (void)setTwiceWithOrgBouncycastleMathEcECPoint:(OrgBouncycastleMathEcECPoint *)twice {
  self->twice_ = twice;
}

- (instancetype)init {
  OrgBouncycastleMathEcWNafPreCompInfo_init(self);
  return self;
}

@end

void OrgBouncycastleMathEcWNafPreCompInfo_init(OrgBouncycastleMathEcWNafPreCompInfo *self) {
  (void) NSObject_init(self);
  self->preComp_ = nil;
  self->preCompNeg_ = nil;
  self->twice_ = nil;
}

OrgBouncycastleMathEcWNafPreCompInfo *new_OrgBouncycastleMathEcWNafPreCompInfo_init() {
  OrgBouncycastleMathEcWNafPreCompInfo *self = [OrgBouncycastleMathEcWNafPreCompInfo alloc];
  OrgBouncycastleMathEcWNafPreCompInfo_init(self);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(OrgBouncycastleMathEcWNafPreCompInfo)
