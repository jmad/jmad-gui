#!/bin/bash

if [ "$1" == "gui" ]
then
    jname=accmodel-jmad-gui.jar
    jstart=cern.accsoft.steering.jmad.gui.JMad
elif [ "$1" == "pymad" ]
then
    jname=accmodel-jmad-pymadservice.jar
    jstart=cern.accmodel.jmad.pymadservice.PyMadService
else
    echo "Wrong input"
fi

# Check correct number of input arguments:

if (( $# != 2 ))
then
    echo "Using default ready-file"
    readyfile=/tmp/pymad-service-ready.out
else
    readyfile=$2
fi

# first some default paths,
# then some wild guesses..
for d in ${JMAD_HOME} ${JAVA_HOME} /usr/lib/java/ /usr/share/java/ /opt/java/lib/
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

java -Dpymad.service.ready.file=$readyfile -classpath "$clp" ${jstart} > /dev/null 
