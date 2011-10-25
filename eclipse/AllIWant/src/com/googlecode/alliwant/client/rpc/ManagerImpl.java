/**
 * @file ManagerImpl.java
 * @author Adam Meadows
 *
 * Copyright 2011 Adam Meadows 
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
*/
package com.googlecode.alliwant.client.rpc;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.googlecode.alliwant.client.event.InfoEvent;
import com.googlecode.alliwant.client.event.ModelEvent;
import com.googlecode.alliwant.client.event.ModelListEvent;
import com.googlecode.alliwant.client.logging.Logging;
import com.googlecode.alliwant.client.model.AccessReq;
import com.googlecode.alliwant.client.model.AccessReqImpl;
import com.googlecode.alliwant.client.model.Group;
import com.googlecode.alliwant.client.model.GroupImpl;
import com.googlecode.alliwant.client.model.GroupInvitation;
import com.googlecode.alliwant.client.model.GroupInvitationImpl;
import com.googlecode.alliwant.client.model.ListItem;
import com.googlecode.alliwant.client.model.ListItemImpl;
import com.googlecode.alliwant.client.model.ListOwner;
import com.googlecode.alliwant.client.model.ListOwnerImpl;
import com.googlecode.alliwant.client.model.ListPermission;
import com.googlecode.alliwant.client.model.ListPermissionImpl;
import com.googlecode.alliwant.client.model.User;
import com.googlecode.alliwant.client.model.UserImpl;
import com.googlecode.alliwant.client.model.WishList;
import com.googlecode.alliwant.client.model.WishListImpl;

public class ManagerImpl implements Manager {

  private Rpc rpc;
  private EventBus eventBus;
  
  public ManagerImpl(Rpc rpc, EventBus eventBus) {
    this.rpc = rpc;
    this.eventBus = eventBus;
  }
  
  // ================================================================
  // BEGIN: Manager methods
  // ================================================================
  
  /**
   * Request the currently logged in user, or a url for them to login
   * @param currentURL the current URL (used to redirect back after login)
   */
  @Override
  public void getCurrentUser() {    
    String url = "/rpc/user/get_current_user?";
    url += rpc.add("url", Window.Location.getHref(), true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<User>(User.class, 
         UserImpl.decode(result)));
      }
    });
  } // getCurrentUser //

  /**
   * Request a listing of all AccessReq objects
   */
  @Override
  public void getAccessRequests() {
    String url = "/rpc/user/get_requests";
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelListEvent<AccessReq>(AccessReq.class, 
         AccessReqImpl.decodeList(result)));
      }
    });
  } // getAccessRequests //

  @Override
  public void getOwner(int ownerId) {
    String url = "/rpc/user/get_owner?";
    url += rpc.add("owner_id", ownerId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListOwner>(ListOwner.class,
         ListOwnerImpl.decode(result)));
      }
    });
  } // getOwner //

  @Override
  public void getPermissions(int ownerId) {
    String url = "/rpc/user/get_permissions?";
    url += rpc.add("owner_id", ownerId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelListEvent<ListPermission>(
         ListPermission.class, ListPermissionImpl.decodeList(result)));
      }
    });
  } // getPermissions //
 
  @Override
  public void addPermission(int ownerId, String email) {
    String url = "/rpc/user/add_permission?";
    url += rpc.add("owner_id", ownerId, true);
    url += rpc.add("email", email);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListPermission>(
         ListPermission.class, ListPermissionImpl.decode(result)));
      }
    });
  } // addPermission //
 
  @Override
  public void removePermission(int permissionId) {
    String url = "/rpc/user/remove_permission?";
    url += rpc.add("permission_id", permissionId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.PERMISSION_DELETED));
      }
    });
  } // removePermission //
  
  @Override
  public void updateOwner(int ownerId, String name, String nickname) {
    String url = "/rpc/user/update_owner?";
    url += rpc.add("owner_id", ownerId, true);
    url += rpc.add("name", name);
    url += rpc.add("nickname", nickname);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListOwner>(ListOwner.class,
         ListOwnerImpl.decode(result)));
      }
    });
  } // updateOwner //
  
  @Override
  public void approveRequest(int reqId) {
    String url = "/rpc/user/approve_request?";
    url += rpc.add("req_id", reqId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.REQ_APPROVED));
      }
    });
  } // approveRequest //
 
  @Override
  public void denyRequest(int reqId) {
    String url = "/rpc/user/deny_request?";
    url += rpc.add("req_id", reqId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.REQ_DENIED));
      }
    });
  } // denyRequest //
  
  
  @Override
  public void addGroup(String name, String description) {
    String url = "/rpc/group/add_group?";
    url += rpc.add("name", name, true);
    url += rpc.add("desc", description);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<Group>(Group.class,
         GroupImpl.decode(result)));
      }
    });
  } // addGroup //
  
  @Override
  public void updateGroup(int groupId, String name, String description) {
    String url = "/rpc/group/update_group?";
    url += rpc.add("id", groupId, true);
    url += rpc.add("name", name);
    url += rpc.add("desc", description);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<Group>(Group.class,
         GroupImpl.decode(result)));
      }
    });
  } // updateGroup //
 
  @Override
  public void getGroups() {
    String url = "/rpc/group/get_groups";
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelListEvent<Group>(Group.class,
         GroupImpl.decodeList(result)));
      }
    });
  } // getGroups //
  
  @Override
  public void inviteMember(int groupId, String email) {
    String url = "/rpc/group/invite_member?";
    url += rpc.add("group_id", groupId, true);
    url += rpc.add("email", email);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.MEMBER_INVITED));
      }
    });
  } // inviteMember //
 
  @Override
  public void getGroupInvites() {
    String url = "/rpc/group/get_invitations";
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelListEvent<GroupInvitation>(GroupInvitation.class,
         GroupInvitationImpl.decodeList(result)));
      }
    });
  } // getGroupInvites //

  @Override
  public void acceptInvite(int inviteId) {
    String url = "/rpc/group/accept_invitation?";
    url += rpc.add("invite_id", inviteId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.INVITE_ACCEPTED));
      }
    });
  } // acceptInvite //
  
  @Override
  public void declineInvite(int inviteId) {
    String url = "/rpc/group/decline_invitation?";
    url += rpc.add("invite_id", inviteId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.INVITE_DECLINED));
      }
    });
  } // declineInvite //

  @Override
  public void getAvailableOwners(int ownerId) {
    String url = "/rpc/group/get_available_owners?";
    url += rpc.add("owner_id", ownerId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelListEvent<ListOwner>(ListOwner.class,
         ListOwnerImpl.decodeList(result)));
      }
    });
  } // getAvailableOwners //
  
  @Override
  public void getLists(int ownerId) {
    String url = "/rpc/list/get_lists?";
    url += rpc.add("owner_id", ownerId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelListEvent<WishList>(WishList.class,
         WishListImpl.decodeList(result)));
      }
    });
  } // getLists //
  
  @Override
  public void addList(int ownerId, String name, String description) {
    String url = "/rpc/list/add_list?";
    url += rpc.add("owner_id", ownerId, true);
    url += rpc.add("name", name);
    url += rpc.add("desc", description);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<WishList>(WishList.class,
         WishListImpl.decode(result)));
      }
    });
  } // addList //
  
  @Override
  public void updateList(int listId, String name, String description) {
    String url = "/rpc/list/update_list?";
    url += rpc.add("list_id", listId, true);
    url += rpc.add("name", name);
    url += rpc.add("desc", description);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<WishList>(WishList.class,
         WishListImpl.decode(result)));
      }
    });
  } // updateList //
  
  @Override
  public void deleteList(int listId) {
    String url = "/rpc/list/delete_list?";
    url += rpc.add("list_id", listId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.LIST_DELETED));
      }
    });
  } // deleteList //

  @Override
  public void addItem(int listId, String name, String category,
   String description, String url, boolean isSurprise) {
    String rpcUrl = "/rpc/list/add_item?";
    rpcUrl += rpc.add("list_id", listId, true);
    rpcUrl += rpc.add("name", name);
    rpcUrl += rpc.add("cat", category);
    rpcUrl += rpc.add("desc", description);
    rpcUrl += rpc.add("url", url);
    rpcUrl += rpc.add("surprise", isSurprise);
    Logging.logger().info("ManagerImpl::addItem: url:");
    Logging.logger().info(" " + rpcUrl);
    rpc.send(rpcUrl, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListItem>(ListItem.class, 
         ListItemImpl.decode(result)));
      }
    });
  } // add Item //
  
  @Override
  public void updateItem(int itemId, String name, String category,
   String description, String url) {
    String rpcUrl = "/rpc/list/update_item?";
    rpcUrl += rpc.add("item_id", itemId, true);
    rpcUrl += rpc.add("name", name);
    rpcUrl += rpc.add("cat", category);
    rpcUrl += rpc.add("desc", description);
    rpcUrl += rpc.add("url", url);
    Logging.logger().info("ManagerImpl::updateItem: url:");
    Logging.logger().info(" " + rpcUrl);
    rpc.send(rpcUrl, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListItem>(ListItem.class, 
         ListItemImpl.decode(result)));
      }
    });
  } // updateItem //
  
  @Override
  public void deleteItem(int itemId) {
    String url = "/rpc/list/remove_item?";
    url += rpc.add("item_id", itemId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new InfoEvent(InfoEvent.ITEM_DELETED));
      }
    });
  } // deleteItem //
    
  @Override
  public void reserveItem(int itemId) {
    String url = "/rpc/list/reserve_item?";
    url += rpc.add("item_id", itemId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListItem>(ListItem.class, 
         ListItemImpl.decode(result)));
      }
    });
  } // reserveItem //
  
  @Override
  public void purchaseItem(int itemId) {
    String url = "/rpc/list/purchase_item?";
    url += rpc.add("item_id", itemId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListItem>(ListItem.class, 
         ListItemImpl.decode(result)));
      }
    });
  } // purchaseItem //
    
  @Override
  public void unPurchaseItem(int itemId) {
    String url = "/rpc/list/unpurchase_item?";
    url += rpc.add("item_id", itemId, true);
    rpc.send(url, new Rpc.Handler() {
      public void onComplete(String result) {
        eventBus.fireEvent(new ModelEvent<ListItem>(ListItem.class, 
         ListItemImpl.decode(result)));
      }
    });
  } // unPurchaseItem //
    
  // ================================================================
  // END: Manager methods
  // ================================================================
  
} // ManagerImpl //
