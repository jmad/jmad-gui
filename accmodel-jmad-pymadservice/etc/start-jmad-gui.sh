#!/bin/bash

jname=accmodel-jmad-gui.jar

CLASSPATH='/usr/share/java/*;/usr/share/java/lib/*'
# first some default paths,
# then some wild guesses..
for d in ${JMAD_HOME} ${JAVA_HOME} /usr/lib/java /usr/share/java/ /opt/java/lib/
do
    if [ -f "${d}${jname}" ]
    then
        jhome=${d}
    fi
done
# if still not defined, hoping for CLASSPATH..
if [ ! -n "$jarfile" ]
then
    jarfile=${jname}
fi
cd $jhome
java -cp $CLASSPATH -Dpymad.service.ready.file=/tmp/pymad-service-is-ready.txt -jar ${jarfile} 

