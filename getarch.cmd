@echo off

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
