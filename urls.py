"""
#
# File: urls.py
# Description: URL handler for html/js URLs
#
# Copyright 2014 Adam Meadows
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

import webapp2
from webapp2_extras import routes

from api.owners import Owners
from api.permissions import Permissions
from api.requests import Requests
from api.users import Users


def build_api_routes(version, resources=None):
    """Create api routes given the version and list of resources

    :param version: The API version.
    :type version: str.
    :param resources: The API Resources
    :type resources: list.

    """
    if resources is None:
        resources = [Owners, Permissions, Requests, Users]

    sub_routes = [resource.get_routes() for resource in resources]
    return routes.PathPrefixRoute('/api/v{}'.format(version), sub_routes)


static_routes = [
    webapp2.Route('/', handler='views.ListPage'),
    webapp2.Route('/settings', handler='views.SettingsPage'),
    webapp2.Route('/goodbye', handler='views.GoodbyePage'),
    webapp2.Route('/.*', handler='views.NotFoundPage'),
]

api_routes = [
    build_api_routes(version='1')
]

app = webapp2.WSGIApplication(static_routes + api_routes, debug=True)
