Tea Time
========

A tea timer for JavaME enabled mobile phones.

[![Flattr this](http://api.flattr.com/button/button-compact-static-100x17.png "Flattr this")](http://flattr.com/thing/63918/Tea-Time)

Building
--------

To build Tea Time, you will need the [Sun Wireless Toolkit
2.5.2](http://www.oracle.com/technetwork/java/index-jsp-137162.html).
Install it and set the `WTK_HOME` environment variable, e.g.:

	export WTK_HOME=/path/to/WTK2.5.2

You will also need [Apache Maven](http://maven.apache.org/).

First compile the source code:

	mvn compile

To create a package that can be installed on mobile phones, execute:

	mvn j2me:package

To run it on the WTK emulator, execute:

	mvn j2me:run

License
-------

Copyright (C) 2010 Felix H. Dahlke

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; see the file COPYING. If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
Boston, MA 02110-1301, USA.
