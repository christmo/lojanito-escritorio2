@echo on

schtasks /create /tn "Actualizador KRADAC" /tr "java -jar c:\Despachos\ActualizarServidor.jar" /sc hourly /st 03:00:00
:schtasks /create /tn "Actualizador KRADAC" /tr "java -jar c:\Despachos\ActualizarServidor.jar" /sc minute /st 03:00:00

:schtasks /delete /tn "Actualizador KRADAC"

pause