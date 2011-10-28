/**
 * @file SettingsViewTestImpl.java
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
package com.googlecode.alliwant.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.alliwant.client.i18n.AiwConstants;

public class SettingsViewTestImpl implements SettingsView {

  private boolean processing = false;
  private IsWidget header = null;
  
  @Override
  public Widget asWidget() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void showProcessingOverlay() {
    processing = true;
  }

  @Override
  public void hideProcessingOverlay() {
    processing = false;
  }
  
  public boolean isProcessing() {
    return processing;
  }

  public void setHeader(IsWidget header) {
    this.header = header;
  }
 
  public IsWidget getHeader() {
    return header;
  }

  @Override
  public void setOwnerName(String name) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getOwnerName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setOwnerNickname(String nickname) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getOwnerNickname() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setNumPermissions(int count) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void setPermissionEmail(int index, String email) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public AiwConstants getAiwc() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    // TODO Auto-generated method stub
    
  }
  
} // SettingsViewTestImpl //