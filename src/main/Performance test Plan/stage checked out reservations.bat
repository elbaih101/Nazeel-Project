@echo off
setlocal
set name=reservation-cycle
set dashBoardDataLocation=F:\java maven projects\Nazeel-Project\src\main\Performance test Plan\dashboarddata\%name%
set dashBoard=F:\java maven projects\Nazeel-Project\src\main\Performance test Plan\dash-board\%name%
set csvLog=%dashBoardDataLocation%\log.csv
set runLog=%dashBoardDataLocation%\runlog.txt
set jmxFile=F:\java maven projects\Nazeel-Project\src\main\Performance test Plan\load_Testing.jmx
set "distributed=-Gremote_hosts=127.0.0.1,192.168.100.135"
Echo starting reservation cycle load test
Echo changing directory to jmeter bin
Echo "%jmxFile%"
Echo "%csvLog%"
Echo "%runLog%"
Echo "%dashBoard%"
call cd "F:\java maven projects\Nazeel-Project\src\main\Performance test Plan\apache-jmeter-5.6.2\bin"
Echo running the test
call jmeter -n  -t "%jmxFile%" -l "%csvLog%" -j "%runLog%" -e -o "%dashBoard%" -Gremote_hosts=127.0.0.1
ECHO test completed