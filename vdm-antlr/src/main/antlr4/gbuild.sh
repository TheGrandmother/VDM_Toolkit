#!/bin/bash

# delete previous builds
echo 'Removing out generated grammars'
rm -R ./.antlr/output

mkdir ~/.local/lib/
curl https://www.antlr.org/download/antlr-4.11.1-complete.jar -o ~/.local/lib/antlr-4.11.1-complete.jar
ANTLR4=~/.local/lib/antlr-4.11.1-complete.jar
CP="$ANTLR4:$CLASSPATH"

GRAMMARS='VDM.g4 VDMLex.g4' #Test.g4 

rm -R ./.antlr/output 2> /dev/null
echo 'Calling ANTLR4 parser generator with GRAMMARS = ' $GRAMMARS ' and ARGS = ' ${@}
java -Xmx500M -cp $CP org.antlr.v4.Tool $GRAMMARS ${@} -listener -visitor -atn -package vdmantlr.generated -o ./.antlr/output

# compile generated files
echo 'Compiling generarted parser'
# javac -cp $CP ./.antlr/output/*.java
cp ./.antlr/output/*.java ../java/vdmantlr/generated/
cp ./.antlr/output/*.tokens ../java/vdmantlr/generated/


#source gtest.sh "$@"
#TODO allow starting in inner rules beyond sl_document?
#TODO grun has to be on the same directory as VDM.class from VDM.g4->VDM.java generated above, hence the "../$@" :-(
#grun VDM sl_document -tree -gui "$@"
# echo 'Calling ANTLR grun test rig'
# cd output

# START_PRODUCTION=sl_document
# if [[ "$#" -gt 1 ]]; then
#     START_PRODUCTION=$1
# fi
# echo 'Parsing from ' $START_PRODUCTION ' for file ' "${@:2}"
# java -Xmx500M -cp /usr/local/lib/antlr4-4.11.1-complete.jar:$CLASSPATH org.antlr.v4.gui.TestRig VDM $START_PRODUCTION -tree -gui ../"${@:2}"

# echo 'Restoring default java version'
# cd ..
# export JAVA_HOME=$OLD_JAVA_HOME

#echo 'Done'
