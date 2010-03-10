@echo off

rem ===========================================================================
rem      Modify following variables to point to the correct directories
rem ===========================================================================

rem Apache ANT directory
set ANT_HOME=D:\Diplomka\Apache-ant-1.7.1

rem Java JDK directory
set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.6.0_16

rem MingW directory
set MINGW_HOME=C:\MingW

rem Perl directory
set PERL_HOME=D:\StanseBuild

rem Uncomment this if you are using the stripped-down Perl from our website
set PERL5LIB=%PERL_HOME%\usr\lib\perl5




rem ===========================================================================
rem                 No modifications needed past this line
rem ===========================================================================



set PATH=%PATH%;%JAVA_HOME%\bin;%ANT_HOME%\bin;%MINGW_HOME%\bin;%PERL_HOME%\bin

rem Add stcc & stpreproc & stparser-c to PATH
set PREPROC_HOME=%CD%\dist\bin
set PATH=%PATH%;%PREPROC_HOME%



rem ===========================================================================
rem             Check to see if paths have been set up correctly
rem ===========================================================================

if not exist "%PREPROC_HOME%\stpreproc" (
	color C
	echo ERROR: stpreproc not found in %PREPROC_HOME%
)

javac -version >nul 2>nul
if "%ERRORLEVEL%" NEQ "0" (
	color C
	echo ERROR: There is something wrong with javac. Is JAVA_HOME okay?
)

call ant -version >nul 2>nul
if "%ERRORLEVEL%" NEQ "0" (
	color C
	echo ERROR: There is something wrong with ant. Is ANT_HOME okay?
)

gcc --version >nul 2>nul
if "%ERRORLEVEL%" NEQ "0" (
	color C
	echo ERROR: There is something wrong with gcc. Is MINGW_HOME okay?
)

perl --version >nul 2>nul
if "%ERRORLEVEL%" NEQ "0" (
	color C
	echo ERROR: There is something wrong with Perl. Is PERL_HOME okay?
)



title Stanse Build Window

cmd /K
