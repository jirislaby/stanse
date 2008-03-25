#!/bin/bash
echo  `svn info . | grep "Revision" | awk '{print $2}'`
