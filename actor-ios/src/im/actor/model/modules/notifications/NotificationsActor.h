//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/actor-ios/build/java/im/actor/model/modules/notifications/NotificationsActor.java
//

#ifndef _ImActorModelModulesNotificationsNotificationsActor_H_
#define _ImActorModelModulesNotificationsNotificationsActor_H_

@class AMContentDescription;
@class AMPeer;
@class ImActorModelDroidkitEngineSyncKeyValue;
@class ImActorModelModulesModules;
@class ImActorModelModulesNotificationsPendingStorage;
@protocol JavaUtilList;

#include "J2ObjC_header.h"
#include "im/actor/model/modules/utils/ModuleActor.h"

#define ImActorModelModulesNotificationsNotificationsActor_MAX_NOTIFICATION_COUNT 10

@interface ImActorModelModulesNotificationsNotificationsActor : ImActorModelModulesUtilsModuleActor {
}

- (instancetype)initWithImActorModelModulesModules:(ImActorModelModulesModules *)messenger;

- (void)preStart;

- (void)onNewMessageWithAMPeer:(AMPeer *)peer
                       withInt:(jint)sender
                      withLong:(jlong)date
      withAMContentDescription:(AMContentDescription *)description_;

- (void)onMessagesReadWithAMPeer:(AMPeer *)peer
                        withLong:(jlong)fromDate;

- (void)onConversationVisibleWithAMPeer:(AMPeer *)peer;

- (void)onConversationHiddenWithAMPeer:(AMPeer *)peer;

- (void)onAppVisible;

- (void)onAppHidden;

- (void)onDialogsVisible;

- (void)onDialogsHidden;

- (void)onReceiveWithId:(id)message;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor)

CF_EXTERN_C_BEGIN

FOUNDATION_EXPORT NSString *ImActorModelModulesNotificationsNotificationsActor_PREFERENCES_STORAGE_;
J2OBJC_STATIC_FIELD_GETTER(ImActorModelModulesNotificationsNotificationsActor, PREFERENCES_STORAGE_, NSString *)

J2OBJC_STATIC_FIELD_GETTER(ImActorModelModulesNotificationsNotificationsActor, MAX_NOTIFICATION_COUNT, jint)
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor)

@interface ImActorModelModulesNotificationsNotificationsActor_NewMessage : NSObject {
}

- (instancetype)initWithAMPeer:(AMPeer *)peer
                       withInt:(jint)sender
                      withLong:(jlong)sortDate
      withAMContentDescription:(AMContentDescription *)contentDescription;

- (AMPeer *)getPeer;

- (jint)getSender;

- (jlong)getSortDate;

- (AMContentDescription *)getContentDescription;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_NewMessage)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_NewMessage)

@interface ImActorModelModulesNotificationsNotificationsActor_MessagesRead : NSObject {
}

- (instancetype)initWithAMPeer:(AMPeer *)peer
                      withLong:(jlong)fromDate;

- (AMPeer *)getPeer;

- (jlong)getFromDate;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_MessagesRead)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_MessagesRead)

@interface ImActorModelModulesNotificationsNotificationsActor_OnConversationVisible : NSObject {
}

- (instancetype)initWithAMPeer:(AMPeer *)peer;

- (AMPeer *)getPeer;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_OnConversationVisible)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_OnConversationVisible)

@interface ImActorModelModulesNotificationsNotificationsActor_OnConversationHidden : NSObject {
}

- (instancetype)initWithAMPeer:(AMPeer *)peer;

- (AMPeer *)getPeer;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_OnConversationHidden)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_OnConversationHidden)

@interface ImActorModelModulesNotificationsNotificationsActor_OnAppVisible : NSObject {
}

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_OnAppVisible)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_OnAppVisible)

@interface ImActorModelModulesNotificationsNotificationsActor_OnAppHidden : NSObject {
}

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_OnAppHidden)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_OnAppHidden)

@interface ImActorModelModulesNotificationsNotificationsActor_OnDialogsVisible : NSObject {
}

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_OnDialogsVisible)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_OnDialogsVisible)

@interface ImActorModelModulesNotificationsNotificationsActor_OnDialogsHidden : NSObject {
}

- (instancetype)init;

@end

J2OBJC_EMPTY_STATIC_INIT(ImActorModelModulesNotificationsNotificationsActor_OnDialogsHidden)

CF_EXTERN_C_BEGIN
CF_EXTERN_C_END

J2OBJC_TYPE_LITERAL_HEADER(ImActorModelModulesNotificationsNotificationsActor_OnDialogsHidden)

#endif // _ImActorModelModulesNotificationsNotificationsActor_H_
