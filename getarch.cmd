@echo off

rem Uncomment this to force 32bit executables
rem (Useful if you don't have a 64bit version of GCC)
rem echo i386
rem goto :eof





if /I "%PROCESSOR_ARCHITECTURE%"=="AMD64" (
	echo x86_64
	goto :eof
)

if /I "%PROCESSOR_ARCHITECTURE%"=="x86" (
	echo i386
	goto :eof
)


rem Either something went really wrong or it's an Itanium, which is unsupported
echo unknown
