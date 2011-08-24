#!/bin/bash

jname=accmodel-jmad-pymadservice.jar

# Check correct number of input arguments:

if (( $# != 1 ))
then
    echo "Wrong number of input arguments"
    exit 1
fi

# first some default paths,
# then some wild guesses..
for d in ${JMAD_HOME} ${JAVA_HOME} /usr/lib/java /usr/share/java/ /opt/java/lib/
do
    if [ -f "${d}${jname}" ]
    then
        jhome=${d}
    fi
done

if [ ! -f "${jhome}${jname}" ]
then
    echo "Could not find $jname"
    exit 1
fi

if [ $jhome ]
then
    cd $jhome
fi

java -Dcern.jmad.output.path=/tmp/ -Dpymad.service.ready.file=$1 -jar ${jname} > /dev/null 
