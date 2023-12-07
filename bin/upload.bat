@ECHO OFF
CLS
SET PRO_ROOT=E:\Git-Repository\Git-workspace4\cystrixblog
cd %PRO_ROOT%
mvn clean package -DskipTests
call :afterbuild
EXIT /B 0
:afterbuild
scp .\target\cystrixblog-1.0.0.jar root@47.109.110.189:/root/
ECHO 上传成功...
