:: Created by npm, please don't edit manually.
@ECHO OFF

SETLOCAL

SET "NODE_EXE=%~dp0\node.exe"
IF NOT EXIST "%NODE_EXE%" (
  SET "NODE_EXE=node"
)

set PATH=%PATH%;C:\Users\geeta\CFA-l2\spring-angular\spring-angular\node
rem SET "NPM_CLI_JS=%~dp0\node_modules\@angular\cli\bin"
SET "NPM_CLI_JS=C:\Users\geeta\CFA-l2\spring-angular\spring-angular\node_modules\@angular\cli\bin\ng.js"
echo %NPM_CLI_JS%

"%NODE_EXE%" "%NPM_CLI_JS%" %*
