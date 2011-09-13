'''
#
# File: access.py
# Description: Unit tests for core.access.MemoryAccess
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

import unittest
from core.access import MemoryAccess
from core.model import Group, GroupMemeber, ListItem

class MemoryAccessTest(unittest.TestCase):
    def setUp(self):
        self.access = MemoryAccess()
        
        #self.accounts = [
        #    Account(id=1, name='Old Checking', description='', is_active=False),
        #    Account(id=2, name='Savings', description='', is_active=True),
        #    Account(id=3, name='Checking', description='', is_active=True),
        #]

        #self.categories = [
        #    Category(id=1, aid=1, name='Food', description='',
        #        balance=0.00, is_active=False),
        #    Category(id=2, aid=1, name='Gas', description='',
        #        balance=0.00, is_active=False),
        #    Category(id=3, aid=2, name='Insurance', description='',
        #        balance=200.00, is_active=True),
        #    Category(id=4, aid=2, name='Taxes', description='',
        #        balance=3000.00, is_active=True),
        #    Category(id=5, aid=3, name='Food', description='',
        #        balance=200.00, is_active=True),
        #    Category(id=6, aid=3, name='Gas', description='',
        #        balance=150.00, is_active=True),
        #    Category(id=7, aid=3, name='Cable', description='',
        #        balance=100.00, is_active=True),
        #]


    def test_filter(self):
        pass

    def test_get(self):
        pass

    def test_save(self):
        pass

if __name__ == '__main__':
    unittest.main()

