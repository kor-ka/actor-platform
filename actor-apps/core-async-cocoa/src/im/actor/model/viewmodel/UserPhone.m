//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-proprietary/actor-apps/core/src/main/java/im/actor/model/viewmodel/UserPhone.java
//


#include "IOSClass.h"
#include "J2ObjC_source.h"
#include "im/actor/model/viewmodel/UserPhone.h"

@interface AMUserPhone () {
 @public
  jlong phone_;
  NSString *title_;
}

@end

J2OBJC_FIELD_SETTER(AMUserPhone, title_, NSString *)

@implementation AMUserPhone

- (instancetype)initWithLong:(jlong)phone
                withNSString:(NSString *)title {
  AMUserPhone_initWithLong_withNSString_(self, phone, title);
  return self;
}

- (jlong)getPhone {
  return phone_;
}

- (NSString *)getTitle {
  return title_;
}

- (jboolean)isEqual:(id)o {
  if (self == o) return YES;
  if (o == nil || [self getClass] != [o getClass]) return NO;
  AMUserPhone *userPhone = (AMUserPhone *) check_class_cast(o, [AMUserPhone class]);
  if (phone_ != ((AMUserPhone *) nil_chk(userPhone))->phone_) return NO;
  return YES;
}

- (NSUInteger)hash {
  return (jint) (phone_ ^ (URShift64(phone_, 32)));
}

@end

void AMUserPhone_initWithLong_withNSString_(AMUserPhone *self, jlong phone, NSString *title) {
  (void) NSObject_init(self);
  self->phone_ = phone;
  self->title_ = title;
}

AMUserPhone *new_AMUserPhone_initWithLong_withNSString_(jlong phone, NSString *title) {
  AMUserPhone *self = [AMUserPhone alloc];
  AMUserPhone_initWithLong_withNSString_(self, phone, title);
  return self;
}

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(AMUserPhone)
