@echo off
for %%I in (%0\..\*.cfg) do call %0\..\test.cmd %%~dpnI %*
