@echo off

set ruta_despachos=c:\Despachos\

set despachos=%ruta_despachos%Despachos_m.jar
set logback=%ruta_despachos%logback_modem.xml

echo ---------------------------------------------------------------------
echo %despachos%
echo %logback%
echo ---------------------------------------------------------------------
java -Dlogback.configurationFile=%logback% -jar %despachos%

pause