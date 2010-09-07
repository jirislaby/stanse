@echo off

set SCRIPT=%0

set FNAME=%1
shift

set EXE=%SCRIPT%\..\..\Debug\cppparser.exe

if not "%1" == "-e" goto no_interpret

shift
set EXE=%1
shift

:no_interpret
%EXE% -j %FNAME%.cpp | python %SCRIPT%\..\test.py %FNAME%.cfg %1 %2 %3 %4 %5 %6
