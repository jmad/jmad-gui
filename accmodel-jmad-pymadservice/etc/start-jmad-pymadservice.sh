#!/bin/bash

jname=accmodel-jmad-pymadservice.jar

# Check correct number of input arguments:

if (( $# != 1 ))
then
    echo "using default ready-file"
    readyfile=pymad-service-ready.out
else
	readyfile=$1
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

clp=$CLASSPATH:${jhome}*;${jhome}lib/*

java -Dpymad.service.ready.file=$readyfile -classpath "$clp" cern.accmodel.jmad.pymadservice.PyMadService > /dev/null 
