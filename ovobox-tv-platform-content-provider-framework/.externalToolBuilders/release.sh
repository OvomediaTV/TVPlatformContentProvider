#!/bin/sh
#
# Use this script to generate debug apk
SRC=$CUR_PATH/bin/ovobox-tv-platform-content-provider-framework.jar
DEST=$CUR_PATH/../ovobox-tv-platform-content-provider-sample/libs/ovobox-tv-platform-content-provider-framework.jar
echo copy from $SRC to $DEST
cp $SRC $DEST
