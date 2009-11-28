#
# spec file for package stanse
#
# Copyright (c) 2008 SUSE LINUX Products GmbH, Nuernberg, Germany.
#
# All modifications and additions to the file contributed by third parties
# remain the property of their copyright owners, unless otherwise agreed
# upon. The license for this file, and modifications and additions to the
# file, is the same license as for the pristine package itself (unless the
# license for the pristine package is not an Open Source License, in which
# case the license is the MIT License). An "Open Source License" is a
# license that conforms to the Open Source Definition (Version 1.9)
# published by the Open Source Initiative.

# Please submit bugfixes or comments via http://bugs.opensuse.org/
#

# norootforbuild


Name:           stanse
Version:        1.0.4
Release:        1.0
License:        GPLv2
Group:          System/Management
AutoReqProv:    on
Summary:        An automatic bug-finding tool for C
Url:            http://stanse.fi.muni.cz/
Source:         http://stanse.fi.muni.cz/download/stanse-%{version}.tar.bz2
Source1:        stanse
Patch:          remove-libs-from-manifest.patch
Patch1:         stpreproc-path-fix.patch
Patch2:         remove_STANSE_HOME.patch
Patch3:         fix_icon_path.patch
Patch4:         dynamic-libantlr.patch
BuildRoot:      %{_tmppath}/%{name}-%{version}-build
BuildRequires:  ant, java-devel >= 1.6.0, gcc, doxygen, graphviz
BuildRequires:  libantlr3c-devel
Requires:	java >= 1.6.0, cpp
%if 0%{?suse_version} >= 1030
BuildRequires:  fdupes
%endif
%if 0%{?suse_version} > 1110
BuildRequires:  dom4j, jaxen, swing-layout, log4j
Requires:	dom4j, jaxen, swing-layout, log4j
%endif
%if 0%{?fedora_version} == 11
#WTF? expansion error...
BuildRequires:  PolicyKit-gnome
%endif

%description
The aim of this project is to research, evaluate and implement modern
approaches to automatic bug finding for programs written in procedural
languages related to C (C/C++/C#/Java). This project is done in close
collaboration with ANF DATA (Siemens AG). The goal is to create a software
tool which is able to automatically discover some types of bugs in real-life
sized projects (e.g. the Linux kernel).

%package -n stanse-web
Summary: Support for Stanse web interface
Group:   Productivity/Networking/Web/Frontends

%description -n stanse-web
Web frontend for presentation of errors found and dumped by Stanse.

%package -n stanse-doc
Summary: Doxygen documentation of stanse project
Group:   Documentation/HTML

%description -n stanse-doc
Doxygen documentation of stanse project. This is useful only when hacking on
Stanse.

%prep
%setup
%patch -p1
%patch1 -p1
%patch2 -p1
%patch3 -p1
%patch4 -p1
%if 0%{?suse_version} > 1110
for LIB in dom4j jaxen swing-layout log4j; do
	rm -f dist/lib/$LIB*
done
%endif

%build
%if 0%{?suse_version} > 1110
ACP=`build-classpath dom4j jaxen swing-layout log4j`
%endif

CLASSPATH=tools/ant/antlr3.jar:tools/ant/cpptasks.jar
if [ -n "$ACP" ]; then
	CLASSPATH="$CLASSPATH:$ACP"
fi
export CLASSPATH
%{ant} jar

sed -i 's/^GENERATE_LATEX.*/GENERATE_LATEX=NO/' stanse.dox
doxygen stanse.dox

%install
%if 0%{?suse_version} > 1110
sed -i 's/@LOCALCLASSPATH@/`build-classpath stanse swing-layout dom4j log4j jaxen`/' %{SOURCE1}
%else
sed -i 's/@LOCALCLASSPATH@/`build-classpath stanse`/' %{SOURCE1}
%endif
strip dist/bin/stcparser-c

%if 0%{?suse_version} == 1110
export NO_BRP_CHECK_BYTECODE_VERSION=true
%endif

mkdir -p $RPM_BUILD_ROOT%{_javadir}/
install -m 644 dist/stanse.jar $RPM_BUILD_ROOT%{_javadir}/stanse.jar
mkdir -p $RPM_BUILD_ROOT%{_bindir}/
install -m 755 %{SOURCE1} $RPM_BUILD_ROOT%{_bindir}/stanse
install -m 755 dist/bin/stcc $RPM_BUILD_ROOT%{_bindir}/stcc
install -m 755 dist/bin/stcparser-c $RPM_BUILD_ROOT%{_bindir}/stcparser-c
install -m 755 dist/bin/stpreproc $RPM_BUILD_ROOT%{_bindir}/stpreproc
install -m 755 dist/www/st_xml2sqlite3 $RPM_BUILD_ROOT%{_bindir}/st_xml2sqlite3
install -d -m 755 $RPM_BUILD_ROOT%{_datadir}/%{name}
install -m 644 statistics/sort.xsl $RPM_BUILD_ROOT%{_datadir}/%{name}/stats-sort.xsl
cd dist/data/
for DATA in `find . -type f`; do
	mkdir -p $RPM_BUILD_ROOT%{_datadir}/%{name}/`dirname $DATA`
	install -m 644 $DATA $RPM_BUILD_ROOT%{_datadir}/%{name}/$DATA
done
cd ../../
for DATA in `find xsd -type f`; do
	mkdir -p $RPM_BUILD_ROOT%{_datadir}/%{name}/`dirname $DATA`
	install -m 644 $DATA $RPM_BUILD_ROOT%{_datadir}/%{name}/$DATA
done
cd dist/
install -m 644 stanse_icon.png $RPM_BUILD_ROOT%{_datadir}/%{name}/stanse_icon.png
for DATA in `find lib -type f`; do
	mkdir -p $RPM_BUILD_ROOT%{_datadir}/%{name}/`dirname $DATA`
	install -m 644 $DATA $RPM_BUILD_ROOT%{_datadir}/%{name}/$DATA
done
for DATA in `find www -type f -name '*.cgi'`; do
	mkdir -p $RPM_BUILD_ROOT%{_datadir}/%{name}/`dirname $DATA`
	install -m 755 $DATA $RPM_BUILD_ROOT%{_datadir}/%{name}/$DATA
done
cd ..
cd dist/doxygen/html/
for DATA in `find . -type f`; do
	mkdir -p $RPM_BUILD_ROOT%{_datadir}/%{name}/doxygen-doc/`dirname $DATA`
	install -m 644 $DATA $RPM_BUILD_ROOT%{_datadir}/%{name}/doxygen-doc/$DATA
done
%if 0%{?suse_version} >= 1030
%fdupes $RPM_BUILD_ROOT%{_datadir}/%{name}/doxygen-doc/
%endif

%clean
rm -rf %{buildroot}

%files
%defattr(-,root,root)
%doc README
%{_bindir}/stanse
%{_bindir}/stcc
%{_bindir}/stcparser-c
%{_bindir}/stpreproc
%dir %{_datadir}/%{name}
%{_datadir}/%{name}/checkers/
%{_datadir}/%{name}/lib/
%{_datadir}/%{name}/xsd/
%{_datadir}/%{name}/stanse_icon.png
%{_datadir}/%{name}/stats-sort.xsl
%{_javadir}/stanse.jar

%files -n stanse-web
%defattr(-,root,root)
%{_bindir}/st_xml2sqlite3
%{_datadir}/%{name}/www/

%files -n stanse-doc
%defattr(-,root,root)
%{_datadir}/%{name}/doxygen-doc/

%changelog
