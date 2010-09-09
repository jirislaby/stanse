@echo off
for %%I in (%0\..\*.cfg) do echo %%~nI.cfg && call %0\..\test.cmd %%~dpnI %*
