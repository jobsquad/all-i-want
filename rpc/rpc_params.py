"""
#
# File: rpc_params.py
# Description: Module for defining RPC Parameter classes
#
# Copyright 2011-2013 Adam Meadows
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
"""
import json


class RpcParam(object):
    """Base class for RPC parameters"""

    def __init__(self, name):
        self.name = name

    def get_value(self, value):
        return value


class RpcParamInt(RpcParam):
    java_type = 'int'

    def get_value(self, value):
        return int(value)


class RpcParamString(RpcParam):
    java_type = 'String'

    def get_value(self, value):
        return str(value)


class RpcParamFloat(RpcParam):
    java_type = 'double'

    def get_value(self, value):
        return float(value)


class RpcParamBoolean(RpcParam):
    java_type = 'boolean'

    def get_value(self, value):
        return str(value) not in ('false', 'False', '0')


class RpcParamModel(RpcParam):

    def __init__(self, name, model):
        self.name = name
        self.model = model

    def get_java_type(self):
        return self.model.get_java_iface_type()

    def get_value(self, value):
        return self.model.from_json_dict(json.loads(value))


class RpcParamList(RpcParam):

    def __init__(self, name, param):
        self.name = name
        self.param = param

    def get_java_type(self):
        return 'java.util.ArrayList<%s>' % self.param.get_java_type()

    def get_value(self, value):
        return [self.param.get_value(v) for v in json.loads(value)]
