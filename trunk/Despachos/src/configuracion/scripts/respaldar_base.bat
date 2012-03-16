@echo off

set ruta_despachos=c:\Despachos\
set mysqldump=c:\wamp\bin\mysql\mysql5.1.36\bin\mysqldump.exe
set mysql=c:\wamp\bin\mysql\mysql5.1.36\bin\mysql.exe
set dir_backup=D:\RESPALDOSKR\
set FECHA=%DATE%
set FECHA=%FECHA:/=%

echo ---------------------------------------------------------------------
echo %mysqldump%
echo ---------------------------------------------------------------------

IF EXIST %dir_backup% (
	GOTO RESPALDAR
) ELSE (
	ECHO CREAR %dir_backup%
	mkdir %dir_backup%
	GOTO RESPALDAR
)

:RESPALDAR
ECHO -- RESPALDAR BASE FECHA %FECHA%
%mysqldump% -u root -R rastreosatelital > %dir_backup%base-%FECHA%.sql
ECHO -- FIN DEL RESPALDO %FECHA%

pause
