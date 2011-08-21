#!/bin/bash

jname=accmodel-jmad-pymadservice.jar

# first some default paths,
# then some wild guesses..
for d in ${JMAD_HOME} ${JAVA_HOME} /usr/lib/java /usr/share/java/ /opt/java/lib/
do
    if [ -f "${d}${jname}" ]
    then
        jarfile=${d}${jname}
    fi
done
# if still not defined, hoping for CLASSPATH..
if [ ! -n "$jarfile" ]
then
    jarfile=${jname}
fi

echo "java -jar ${jarfile}"
