//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-proprietary/actor-apps/core/src/main/java/im/actor/model/modules/avatar/GroupAvatarChangeActor.java
//

#ifndef _ImActorModelModulesAvatarGroupAvatarChangeActor_H_
#define _ImActorModelModulesAvatarGroupAvatarChangeActor_H_

#include "J2ObjC_header.h"
#include "im/actor/model/modules/utils/ModuleActor.h"

@class AMFileReference;
@class ImActorModelModulesModules;

@interface ImActorModelModulesAvatarGroupAvatarChangeActor : ImActorModelModulesUtilsModuleActor

#pragma mark Public

- (instancetype)initWithImActorModelModulesModules:(ImActorModelModulesModules *)modules;

- (void)avatarChangedWithInt:(jint)gid
                    withLong:(jlong)rid;

- (void)changeAvatarWithInt:(jint)gid
               withNSString:(NSString *)descriptor;

- (void)onReceiveWithId:(id)message;

- (void)removeAvatarWithInt:(jint)gid;

- (void)uploadCompletedWithLong:(jlong)rid
            withAMFileReference:(AMFileReference *)fileReference;

- (void)uploadErrorWithLong:(jlong)rid;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesAvatarGroupAvatarChangeActor)

FOUNDATION_EXPORT void ImActorModelModulesAvatarGroupAvatarChangeActor_initWithImActorModelModulesModules_(ImActorModelModulesAvatarGroupAvatarChangeActor *self, ImActorModelModulesModules *modules);

FOUNDATION_EXPORT ImActorModelModulesAvatarGroupAvatarChangeActor *new_ImActorModelModulesAvatarGroupAvatarChangeActor_initWithImActorModelModulesModules_(ImActorModelModulesModules *modules) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesAvatarGroupAvatarChangeActor)

@interface ImActorModelModulesAvatarGroupAvatarChangeActor_AvatarChanged : NSObject

#pragma mark Public

- (instancetype)initWithInt:(jint)gid
                   withLong:(jlong)rid;

- (jint)getGid;

- (jlong)getRid;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesAvatarGroupAvatarChangeActor_AvatarChanged)

FOUNDATION_EXPORT void ImActorModelModulesAvatarGroupAvatarChangeActor_AvatarChanged_initWithInt_withLong_(ImActorModelModulesAvatarGroupAvatarChangeActor_AvatarChanged *self, jint gid, jlong rid);

FOUNDATION_EXPORT ImActorModelModulesAvatarGroupAvatarChangeActor_AvatarChanged *new_ImActorModelModulesAvatarGroupAvatarChangeActor_AvatarChanged_initWithInt_withLong_(jint gid, jlong rid) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesAvatarGroupAvatarChangeActor_AvatarChanged)

@interface ImActorModelModulesAvatarGroupAvatarChangeActor_ChangeAvatar : NSObject

#pragma mark Public

- (instancetype)initWithInt:(jint)gid
               withNSString:(NSString *)descriptor;

- (NSString *)getDescriptor;

- (jint)getGid;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesAvatarGroupAvatarChangeActor_ChangeAvatar)

FOUNDATION_EXPORT void ImActorModelModulesAvatarGroupAvatarChangeActor_ChangeAvatar_initWithInt_withNSString_(ImActorModelModulesAvatarGroupAvatarChangeActor_ChangeAvatar *self, jint gid, NSString *descriptor);

FOUNDATION_EXPORT ImActorModelModulesAvatarGroupAvatarChangeActor_ChangeAvatar *new_ImActorModelModulesAvatarGroupAvatarChangeActor_ChangeAvatar_initWithInt_withNSString_(jint gid, NSString *descriptor) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesAvatarGroupAvatarChangeActor_ChangeAvatar)

@interface ImActorModelModulesAvatarGroupAvatarChangeActor_RemoveAvatar : NSObject

#pragma mark Public

- (instancetype)initWithInt:(jint)gid;

- (jint)getGid;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesAvatarGroupAvatarChangeActor_RemoveAvatar)

FOUNDATION_EXPORT void ImActorModelModulesAvatarGroupAvatarChangeActor_RemoveAvatar_initWithInt_(ImActorModelModulesAvatarGroupAvatarChangeActor_RemoveAvatar *self, jint gid);

FOUNDATION_EXPORT ImActorModelModulesAvatarGroupAvatarChangeActor_RemoveAvatar *new_ImActorModelModulesAvatarGroupAvatarChangeActor_RemoveAvatar_initWithInt_(jint gid) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesAvatarGroupAvatarChangeActor_RemoveAvatar)

#endif // _ImActorModelModulesAvatarGroupAvatarChangeActor_H_
