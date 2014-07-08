#!/bin/bash

export SCRIPTHOME=`pwd`
java -classpath ${SCRIPTHOME}/../lib/keepass2-to-keepassx.jar:${SCRIPTHOME}/../lib/xpp3-1.1.4c.jar com.mindforger.keepass.Keepass2DatabaseToKeepassXDatabase ${1} ${2}

# eof
