'''
#
# File: model.py
# Description: Model classes
# 
# Copyright 2011 Adam Meadows 
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.
#
'''

from core.meta import Model, FieldInt, FieldString, FieldText, FieldModelArray

class GroupInvitation(Model):
    fields = (
        FieldString(name='key'),
        FieldString(name='group_key'),
        FieldString(name='group_name'),
        FieldString(name='owner_email'),
        FieldString(name='member_email'),
    )
    
    @classmethod
    def from_db(cls, db):
        _ = lambda x: str(x.key())
        return cls(key=_(db), group_key=_(db.group),
            group_name=db.group.name(), owner_email=db.group.owner.email(),
            member_email=db.member.email())

class GroupMember(Model):
    fields = (
        FieldInt(name='key'),
        FieldInt(name='group_key'),
        FieldString(name='nickname'),
        FieldString(name='email'),
    )
    
    @classmethod
    def from_db(cls, db):
        return cls(key=str(db.key()), group_key=str(db.group.key()),
            nickname=db.member.nickname(), email=db.member.email())

class Group(Model):
    fields = (
        FieldInt(name='key'),
        FieldString(name='name'),
        FieldText(name='description'),
        FieldModelArray(type=GroupInvitation, name='invitations'),
        FieldModelArray(type=GroupMember, name='members'),
    )

    @classmethod
    def from_db(cls, db):
        invitations = [ GroupInvitation.from_db(i) for i in db.invitations ]
        members = [ GroupMember.from_db(m) for m in db.members ]
        return cls(key=str(db.key()), name=db.name(),
            description=db.description(), invitations=invitations,
            members=members)

class ListItem(Model):
    fields = (
        FieldInt(name='key'),
        FieldString(name='name'),
        FieldText(name='description'),
        FieldString(name='url'),
        FieldString(name='reserved_by'),
        FieldString(name='purchased_by'),
    )
    
    @classmethod
    def from_db(cls, db):
        l = lambda x: '%s (%s)' % (x.nickname(), x.email())
        _ = lambda x: l(x) if x is not None else ''
        return cls(key=str(db.key()), name=db.name,
            description=db.description, url=db.url,
            reserved_by=_(db.reserved_by), purchased_by=_(db.purchased_by))

class WishList(Model):
    fields = (
        FieldInt(name='key'),
        FieldString(name='name'),
        FieldModelArray(type=ListItem, name='items'),
    )

    @classmethod
    def from_db(cls, db):
        items = [ ListItem.from_db(i) for i in db.items ]
        return cls(key=str(db.key()), name=db.name, items=items)

class User(Model):
    fields = (
        FieldString(name='email'),
        FieldString(name='nickname'),
        FieldString(name='user_id'),
        FieldString(name='login_url'),
        FieldString(name='logout_url'),
    )

class FailureReport(Model):
    fields = (
        FieldString(name='error_type'),
        FieldString(name='message'),
        FieldString(name='traceback'),
    )

def get_all_classes():
    from core.meta import ModelManager as manager
    return manager.models

