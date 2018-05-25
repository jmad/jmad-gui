@echo off

IF exist "%1"=="" (
   set readyfile="%1"
) else (
   set readyfile="pymad-service-ready.out"
)

set jmadhome=%~pd0
set clp=%CLASSPATH%;%jmadhome%*;%jmadhome%lib\*

echo "Using Classpath:" %clp%
java -Dpymad.service.ready.file=%readyfile% -classpath "%clp%" cern.accsoft.steering.jmad.gui.JMad