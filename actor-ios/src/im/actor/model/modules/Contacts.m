//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/actor-ios/build/java/im/actor/model/modules/Contacts.java
//

#include "IOSClass.h"
#include "IOSObjectArray.h"
#include "IOSPrimitiveArray.h"
#include "J2ObjC_source.h"
#include "im/actor/model/Configuration.h"
#include "im/actor/model/StorageProvider.h"
#include "im/actor/model/api/base/SeqUpdate.h"
#include "im/actor/model/api/rpc/RequestAddContact.h"
#include "im/actor/model/api/rpc/RequestRemoveContact.h"
#include "im/actor/model/api/rpc/RequestSearchContacts.h"
#include "im/actor/model/api/rpc/ResponseSearchContacts.h"
#include "im/actor/model/api/rpc/ResponseSeq.h"
#include "im/actor/model/api/updates/UpdateContactsAdded.h"
#include "im/actor/model/api/updates/UpdateContactsRemoved.h"
#include "im/actor/model/concurrency/Command.h"
#include "im/actor/model/concurrency/CommandCallback.h"
#include "im/actor/model/droidkit/actors/ActorRef.h"
#include "im/actor/model/droidkit/actors/ActorSystem.h"
#include "im/actor/model/droidkit/actors/Props.h"
#include "im/actor/model/droidkit/engine/KeyValueEngine.h"
#include "im/actor/model/droidkit/engine/ListEngine.h"
#include "im/actor/model/droidkit/engine/ListStorage.h"
#include "im/actor/model/droidkit/engine/PreferencesStorage.h"
#include "im/actor/model/entity/User.h"
#include "im/actor/model/modules/BaseModule.h"
#include "im/actor/model/modules/Contacts.h"
#include "im/actor/model/modules/Modules.h"
#include "im/actor/model/modules/Updates.h"
#include "im/actor/model/modules/contacts/BookImportActor.h"
#include "im/actor/model/modules/contacts/ContactsSyncActor.h"
#include "im/actor/model/modules/updates/internal/UsersFounded.h"
#include "im/actor/model/network/RpcException.h"
#include "im/actor/model/network/RpcInternalException.h"
#include "im/actor/model/viewmodel/UserVM.h"
#include "java/lang/Boolean.h"
#include "java/lang/Integer.h"
#include "java/util/ArrayList.h"
#include "java/util/List.h"

@interface ImActorModelModulesContacts () {
 @public
  id<ImActorModelDroidkitEngineListEngine> contacts_;
  DKActorRef *bookImportActor_;
  DKActorRef *contactSyncActor_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts, contacts_, id<ImActorModelDroidkitEngineListEngine>)
J2OBJC_FIELD_SETTER(ImActorModelModulesContacts, bookImportActor_, DKActorRef *)
J2OBJC_FIELD_SETTER(ImActorModelModulesContacts, contactSyncActor_, DKActorRef *)

@interface ImActorModelModulesContacts_$1 () {
 @public
  ImActorModelModulesModules *val$modules_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$1, val$modules_, ImActorModelModulesModules *)

@interface ImActorModelModulesContacts_$2 () {
 @public
  ImActorModelModulesModules *val$modules_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$2, val$modules_, ImActorModelModulesModules *)

@interface ImActorModelModulesContacts_$3 () {
 @public
  ImActorModelModulesContacts *this$0_;
  NSString *val$query_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$3, this$0_, ImActorModelModulesContacts *)
J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$3, val$query_, NSString *)

@interface ImActorModelModulesContacts_$3_$1 () {
 @public
  ImActorModelModulesContacts_$3 *this$0_;
  id<AMCommandCallback> val$callback_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$3_$1, this$0_, ImActorModelModulesContacts_$3 *)
J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$3_$1, val$callback_, id<AMCommandCallback>)

@interface ImActorModelModulesContacts_$3_$1_$1 () {
 @public
  ImActorModelModulesContacts_$3_$1 *this$0_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$3_$1_$1, this$0_, ImActorModelModulesContacts_$3_$1 *)

@interface ImActorModelModulesContacts_$3_$1_$2 () {
 @public
  ImActorModelModulesContacts_$3_$1 *this$0_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$3_$1_$2, this$0_, ImActorModelModulesContacts_$3_$1 *)

@interface ImActorModelModulesContacts_$4 () {
 @public
  ImActorModelModulesContacts *this$0_;
  jint val$uid_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$4, this$0_, ImActorModelModulesContacts *)

@interface ImActorModelModulesContacts_$4_$1 () {
 @public
  id<AMCommandCallback> val$callback_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$4_$1, val$callback_, id<AMCommandCallback>)

@interface ImActorModelModulesContacts_$4_$2 () {
 @public
  ImActorModelModulesContacts_$4 *this$0_;
  id<AMCommandCallback> val$callback_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$4_$2, this$0_, ImActorModelModulesContacts_$4 *)
J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$4_$2, val$callback_, id<AMCommandCallback>)

@interface ImActorModelModulesContacts_$4_$2_$1 () {
 @public
  ImActorModelModulesContacts_$4_$2 *this$0_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$4_$2_$1, this$0_, ImActorModelModulesContacts_$4_$2 *)

@interface ImActorModelModulesContacts_$4_$2_$2 () {
 @public
  ImActorModelModulesContacts_$4_$2 *this$0_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$4_$2_$2, this$0_, ImActorModelModulesContacts_$4_$2 *)

@interface ImActorModelModulesContacts_$5 () {
 @public
  ImActorModelModulesContacts *this$0_;
  jint val$uid_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$5, this$0_, ImActorModelModulesContacts *)

@interface ImActorModelModulesContacts_$5_$1 () {
 @public
  id<AMCommandCallback> val$callback_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$5_$1, val$callback_, id<AMCommandCallback>)

@interface ImActorModelModulesContacts_$5_$2 () {
 @public
  ImActorModelModulesContacts_$5 *this$0_;
  id<AMCommandCallback> val$callback_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$5_$2, this$0_, ImActorModelModulesContacts_$5 *)
J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$5_$2, val$callback_, id<AMCommandCallback>)

@interface ImActorModelModulesContacts_$5_$2_$1 () {
 @public
  ImActorModelModulesContacts_$5_$2 *this$0_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$5_$2_$1, this$0_, ImActorModelModulesContacts_$5_$2 *)

@interface ImActorModelModulesContacts_$5_$2_$2 () {
 @public
  ImActorModelModulesContacts_$5_$2 *this$0_;
}
@end

J2OBJC_FIELD_SETTER(ImActorModelModulesContacts_$5_$2_$2, this$0_, ImActorModelModulesContacts_$5_$2 *)

@implementation ImActorModelModulesContacts

- (instancetype)initWithImActorModelModulesModules:(ImActorModelModulesModules *)modules {
  if (self = [super initWithImActorModelModulesModules:modules]) {
    contacts_ = [((id<AMStorageProvider>) nil_chk([((AMConfiguration *) nil_chk([((ImActorModelModulesModules *) nil_chk(modules)) getConfiguration])) getStorageProvider])) createContactsListWithImActorModelDroidkitEngineListStorage:[((id<AMStorageProvider>) nil_chk([((AMConfiguration *) nil_chk([modules getConfiguration])) getStorageProvider])) createListWithNSString:ImActorModelModulesBaseModule_get_STORAGE_CONTACTS_()]];
    bookImportActor_ = [((DKActorSystem *) nil_chk(DKActorSystem_system())) actorOfWithDKProps:DKProps_createWithIOSClass_withDKActorCreator_(ImActorModelModulesContactsBookImportActor_class_(), [[ImActorModelModulesContacts_$1 alloc] initWithImActorModelModulesModules:modules]) withNSString:@"actor/book_import"];
    contactSyncActor_ = [((DKActorSystem *) nil_chk(DKActorSystem_system())) actorOfWithDKProps:DKProps_createWithIOSClass_withDKActorCreator_(ImActorModelModulesContactsContactsSyncActor_class_(), [[ImActorModelModulesContacts_$2 alloc] initWithImActorModelModulesModules:modules]) withNSString:@"actor/contacts_sync"];
  }
  return self;
}

- (id<ImActorModelDroidkitEngineListEngine>)getContacts {
  return contacts_;
}

- (void)onPhoneBookChanged {
  [((DKActorRef *) nil_chk(bookImportActor_)) sendWithId:[[ImActorModelModulesContactsBookImportActor_PerformSync alloc] init]];
}

- (DKActorRef *)getContactSyncActor {
  return contactSyncActor_;
}

- (void)markContactWithInt:(jint)uid {
  [((id<ImActorModelDroidkitEnginePreferencesStorage>) nil_chk([self preferences])) putBoolWithNSString:JreStrcat("$I", @"contact_", uid) withBoolean:YES];
}

- (void)markNonContactWithInt:(jint)uid {
  [((id<ImActorModelDroidkitEnginePreferencesStorage>) nil_chk([self preferences])) putBoolWithNSString:JreStrcat("$I", @"contact_", uid) withBoolean:NO];
}

- (jboolean)isUserContactWithInt:(jint)uid {
  return [((id<ImActorModelDroidkitEnginePreferencesStorage>) nil_chk([self preferences])) getBoolWithNSString:JreStrcat("$I", @"contact_", uid) withBoolean:NO];
}

- (id<AMCommand>)findUsersWithNSString:(NSString *)query {
  return [[ImActorModelModulesContacts_$3 alloc] initWithImActorModelModulesContacts:self withNSString:query];
}

- (id<AMCommand>)addContactWithInt:(jint)uid {
  return [[ImActorModelModulesContacts_$4 alloc] initWithImActorModelModulesContacts:self withInt:uid];
}

- (id<AMCommand>)removeContactWithInt:(jint)uid {
  return [[ImActorModelModulesContacts_$5 alloc] initWithImActorModelModulesContacts:self withInt:uid];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts *)other {
  [super copyAllFieldsTo:other];
  other->contacts_ = contacts_;
  other->bookImportActor_ = bookImportActor_;
  other->contactSyncActor_ = contactSyncActor_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts)

@implementation ImActorModelModulesContacts_$1

- (ImActorModelModulesContactsBookImportActor *)create {
  return [[ImActorModelModulesContactsBookImportActor alloc] initWithImActorModelModulesModules:val$modules_];
}

- (instancetype)initWithImActorModelModulesModules:(ImActorModelModulesModules *)capture$0 {
  val$modules_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$1 *)other {
  [super copyAllFieldsTo:other];
  other->val$modules_ = val$modules_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$1)

@implementation ImActorModelModulesContacts_$2

- (ImActorModelModulesContactsContactsSyncActor *)create {
  return [[ImActorModelModulesContactsContactsSyncActor alloc] initWithImActorModelModulesModules:val$modules_];
}

- (instancetype)initWithImActorModelModulesModules:(ImActorModelModulesModules *)capture$0 {
  val$modules_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$2 *)other {
  [super copyAllFieldsTo:other];
  other->val$modules_ = val$modules_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$2)

@implementation ImActorModelModulesContacts_$3

- (void)startWithAMCommandCallback:(id<AMCommandCallback>)callback {
  [this$0_ requestWithImActorModelNetworkParserRequest:[[ImActorModelApiRpcRequestSearchContacts alloc] initWithNSString:val$query_] withAMRpcCallback:[[ImActorModelModulesContacts_$3_$1 alloc] initWithImActorModelModulesContacts_$3:self withAMCommandCallback:callback]];
}

- (instancetype)initWithImActorModelModulesContacts:(ImActorModelModulesContacts *)outer$
                                       withNSString:(NSString *)capture$0 {
  this$0_ = outer$;
  val$query_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$3 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
  other->val$query_ = val$query_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$3)

@implementation ImActorModelModulesContacts_$3_$1

- (void)onResultWithImActorModelNetworkParserResponse:(ImActorModelApiRpcResponseSearchContacts *)response {
  if ([((id<JavaUtilList>) nil_chk([((ImActorModelApiRpcResponseSearchContacts *) nil_chk(response)) getUsers])) size] == 0) {
    [this$0_->this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$3_$1_$1 alloc] initWithImActorModelModulesContacts_$3_$1:self]];
    return;
  }
  [((ImActorModelModulesUpdates *) nil_chk([this$0_->this$0_ updates])) onUpdateReceivedWithId:[[ImActorModelModulesUpdatesInternalUsersFounded alloc] initWithJavaUtilList:[response getUsers] withAMCommandCallback:val$callback_]];
}

- (void)onErrorWithAMRpcException:(AMRpcException *)e {
  [((AMRpcException *) nil_chk(e)) printStackTrace];
  [this$0_->this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$3_$1_$2 alloc] initWithImActorModelModulesContacts_$3_$1:self]];
}

- (instancetype)initWithImActorModelModulesContacts_$3:(ImActorModelModulesContacts_$3 *)outer$
                                 withAMCommandCallback:(id<AMCommandCallback>)capture$0 {
  this$0_ = outer$;
  val$callback_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$3_$1 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
  other->val$callback_ = val$callback_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$3_$1)

@implementation ImActorModelModulesContacts_$3_$1_$1

- (void)run {
  [((id<AMCommandCallback>) nil_chk(this$0_->val$callback_)) onResultWithId:[IOSObjectArray newArrayWithLength:0 type:AMUserVM_class_()]];
}

- (instancetype)initWithImActorModelModulesContacts_$3_$1:(ImActorModelModulesContacts_$3_$1 *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$3_$1_$1 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$3_$1_$1)

@implementation ImActorModelModulesContacts_$3_$1_$2

- (void)run {
  [((id<AMCommandCallback>) nil_chk(this$0_->val$callback_)) onResultWithId:[IOSObjectArray newArrayWithLength:0 type:AMUserVM_class_()]];
}

- (instancetype)initWithImActorModelModulesContacts_$3_$1:(ImActorModelModulesContacts_$3_$1 *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$3_$1_$2 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$3_$1_$2)

@implementation ImActorModelModulesContacts_$4

- (void)startWithAMCommandCallback:(id<AMCommandCallback>)callback {
  AMUser *user = [((id<ImActorModelDroidkitEngineKeyValueEngine>) nil_chk([this$0_ users])) getValueWithLong:val$uid_];
  if (user == nil) {
    [this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$4_$1 alloc] initWithAMCommandCallback:callback]];
    return;
  }
  [this$0_ requestWithImActorModelNetworkParserRequest:[[ImActorModelApiRpcRequestAddContact alloc] initWithInt:val$uid_ withLong:[((AMUser *) nil_chk(user)) getAccessHash]] withAMRpcCallback:[[ImActorModelModulesContacts_$4_$2 alloc] initWithImActorModelModulesContacts_$4:self withAMCommandCallback:callback]];
}

- (instancetype)initWithImActorModelModulesContacts:(ImActorModelModulesContacts *)outer$
                                            withInt:(jint)capture$0 {
  this$0_ = outer$;
  val$uid_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$4 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
  other->val$uid_ = val$uid_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$4)

@implementation ImActorModelModulesContacts_$4_$1

- (void)run {
  [((id<AMCommandCallback>) nil_chk(val$callback_)) onErrorWithJavaLangException:[[AMRpcInternalException alloc] init]];
}

- (instancetype)initWithAMCommandCallback:(id<AMCommandCallback>)capture$0 {
  val$callback_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$4_$1 *)other {
  [super copyAllFieldsTo:other];
  other->val$callback_ = val$callback_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$4_$1)

@implementation ImActorModelModulesContacts_$4_$2

- (void)onResultWithImActorModelNetworkParserResponse:(ImActorModelApiRpcResponseSeq *)response {
  JavaUtilArrayList *uids = [[JavaUtilArrayList alloc] init];
  [uids addWithId:JavaLangInteger_valueOfWithInt_(this$0_->val$uid_)];
  ImActorModelApiBaseSeqUpdate *update = [[ImActorModelApiBaseSeqUpdate alloc] initWithInt:[((ImActorModelApiRpcResponseSeq *) nil_chk(response)) getSeq] withByteArray:[response getState] withInt:ImActorModelApiUpdatesUpdateContactsAdded_HEADER withByteArray:[((ImActorModelApiUpdatesUpdateContactsAdded *) [[ImActorModelApiUpdatesUpdateContactsAdded alloc] initWithJavaUtilList:uids]) toByteArray]];
  [((ImActorModelModulesUpdates *) nil_chk([this$0_->this$0_ updates])) onUpdateReceivedWithId:update];
  [this$0_->this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$4_$2_$1 alloc] initWithImActorModelModulesContacts_$4_$2:self]];
}

- (void)onErrorWithAMRpcException:(AMRpcException *)e {
  [this$0_->this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$4_$2_$2 alloc] initWithImActorModelModulesContacts_$4_$2:self]];
}

- (instancetype)initWithImActorModelModulesContacts_$4:(ImActorModelModulesContacts_$4 *)outer$
                                 withAMCommandCallback:(id<AMCommandCallback>)capture$0 {
  this$0_ = outer$;
  val$callback_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$4_$2 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
  other->val$callback_ = val$callback_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$4_$2)

@implementation ImActorModelModulesContacts_$4_$2_$1

- (void)run {
  [((id<AMCommandCallback>) nil_chk(this$0_->val$callback_)) onResultWithId:JavaLangBoolean_valueOfWithBoolean_(YES)];
}

- (instancetype)initWithImActorModelModulesContacts_$4_$2:(ImActorModelModulesContacts_$4_$2 *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$4_$2_$1 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$4_$2_$1)

@implementation ImActorModelModulesContacts_$4_$2_$2

- (void)run {
  [((id<AMCommandCallback>) nil_chk(this$0_->val$callback_)) onErrorWithJavaLangException:[[AMRpcInternalException alloc] init]];
}

- (instancetype)initWithImActorModelModulesContacts_$4_$2:(ImActorModelModulesContacts_$4_$2 *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$4_$2_$2 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$4_$2_$2)

@implementation ImActorModelModulesContacts_$5

- (void)startWithAMCommandCallback:(id<AMCommandCallback>)callback {
  AMUser *user = [((id<ImActorModelDroidkitEngineKeyValueEngine>) nil_chk([this$0_ users])) getValueWithLong:val$uid_];
  if (user == nil) {
    [this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$5_$1 alloc] initWithAMCommandCallback:callback]];
    return;
  }
  [this$0_ requestWithImActorModelNetworkParserRequest:[[ImActorModelApiRpcRequestRemoveContact alloc] initWithInt:val$uid_ withLong:[((AMUser *) nil_chk(user)) getAccessHash]] withAMRpcCallback:[[ImActorModelModulesContacts_$5_$2 alloc] initWithImActorModelModulesContacts_$5:self withAMCommandCallback:callback]];
}

- (instancetype)initWithImActorModelModulesContacts:(ImActorModelModulesContacts *)outer$
                                            withInt:(jint)capture$0 {
  this$0_ = outer$;
  val$uid_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$5 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
  other->val$uid_ = val$uid_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$5)

@implementation ImActorModelModulesContacts_$5_$1

- (void)run {
  [((id<AMCommandCallback>) nil_chk(val$callback_)) onErrorWithJavaLangException:[[AMRpcInternalException alloc] init]];
}

- (instancetype)initWithAMCommandCallback:(id<AMCommandCallback>)capture$0 {
  val$callback_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$5_$1 *)other {
  [super copyAllFieldsTo:other];
  other->val$callback_ = val$callback_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$5_$1)

@implementation ImActorModelModulesContacts_$5_$2

- (void)onResultWithImActorModelNetworkParserResponse:(ImActorModelApiRpcResponseSeq *)response {
  JavaUtilArrayList *uids = [[JavaUtilArrayList alloc] init];
  [uids addWithId:JavaLangInteger_valueOfWithInt_(this$0_->val$uid_)];
  ImActorModelApiBaseSeqUpdate *update = [[ImActorModelApiBaseSeqUpdate alloc] initWithInt:[((ImActorModelApiRpcResponseSeq *) nil_chk(response)) getSeq] withByteArray:[response getState] withInt:ImActorModelApiUpdatesUpdateContactsRemoved_HEADER withByteArray:[((ImActorModelApiUpdatesUpdateContactsRemoved *) [[ImActorModelApiUpdatesUpdateContactsRemoved alloc] initWithJavaUtilList:uids]) toByteArray]];
  [((ImActorModelModulesUpdates *) nil_chk([this$0_->this$0_ updates])) onUpdateReceivedWithId:update];
  [this$0_->this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$5_$2_$1 alloc] initWithImActorModelModulesContacts_$5_$2:self]];
}

- (void)onErrorWithAMRpcException:(AMRpcException *)e {
  [this$0_->this$0_ runOnUiThreadWithJavaLangRunnable:[[ImActorModelModulesContacts_$5_$2_$2 alloc] initWithImActorModelModulesContacts_$5_$2:self]];
}

- (instancetype)initWithImActorModelModulesContacts_$5:(ImActorModelModulesContacts_$5 *)outer$
                                 withAMCommandCallback:(id<AMCommandCallback>)capture$0 {
  this$0_ = outer$;
  val$callback_ = capture$0;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$5_$2 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
  other->val$callback_ = val$callback_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$5_$2)

@implementation ImActorModelModulesContacts_$5_$2_$1

- (void)run {
  [((id<AMCommandCallback>) nil_chk(this$0_->val$callback_)) onResultWithId:JavaLangBoolean_valueOfWithBoolean_(YES)];
}

- (instancetype)initWithImActorModelModulesContacts_$5_$2:(ImActorModelModulesContacts_$5_$2 *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$5_$2_$1 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$5_$2_$1)

@implementation ImActorModelModulesContacts_$5_$2_$2

- (void)run {
  [((id<AMCommandCallback>) nil_chk(this$0_->val$callback_)) onErrorWithJavaLangException:[[AMRpcInternalException alloc] init]];
}

- (instancetype)initWithImActorModelModulesContacts_$5_$2:(ImActorModelModulesContacts_$5_$2 *)outer$ {
  this$0_ = outer$;
  return [super init];
}

- (void)copyAllFieldsTo:(ImActorModelModulesContacts_$5_$2_$2 *)other {
  [super copyAllFieldsTo:other];
  other->this$0_ = this$0_;
}

@end

J2OBJC_CLASS_TYPE_LITERAL_SOURCE(ImActorModelModulesContacts_$5_$2_$2)
