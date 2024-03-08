@echo off
setlocal
set name=reservation-cycle
set dashBoardDataLocation=D:\java maven projects\Nazeel-Project\src\main\Performance test Plan\dashboarddata\%name%
set dashBoard=D:\java maven projects\Nazeel-Project\src\main\Performance test Plan\dash-board\%name%
set csvLog=%dashBoardDataLocation%\log.csv
set runLog=%dashBoardDataLocation%\runlog.txt
set jmxFile=D:\java maven projects\Nazeel-Project\src\main\Performance test Plan\load_Testing.jmx
Echo starting reservation cycle load test
Echo changing directory to jmeter bin
Echo "%jmxFile%"
Echo "%csvLog%"
Echo "%runLog%"
Echo "%dashBoard%"
call cd "D:\java maven projects\Nazeel-Project\src\main\Performance test Plan\apache-jmeter-5.6.3\bin"
Echo running the test
call jmeter -n  -t "%jmxFile%" -l "%csvLog%" -j "%runLog%" -e -o "%dashBoard%"
ECHO test completed