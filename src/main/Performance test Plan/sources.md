
N ow I just need to add this code as the very first sampler in a test plan and pass environment specific filepath as the sampler parameter args[0] it will load variables from the file and put them as JMeter variables.
var file = new java.io.File(args[0]);
var props = new java.util.Properties();
var is = new java.io.FileInputStream(file);
props.load(is);
is.close();
for(var it = props.entrySet().iterator(); it.hasNext();)
{
    var entry = it.next();
    vars.put(entry.getKey(), entry.getValue());
}


https://stackoverflow.com/questions/22509990/jmeter-environment-specific-configuration     project structure for Jmeter
https://jsonpathfinder.com/                      Json path finder
https://json-diff.com/                           Json compare




https://jmeter.apache.org/usermanual/get-started.html

-n – non-GUI mode – this specifies JMeter is to run in non-GUI mode
-t – JMX file – location of the test plan and the name of JMX file that contains the Test Plan
-l – log file name of JTL file to log sample results to
Example

jmeter -n -t my_test_plan.jmx -l log.jtl


Optional
-j – name of JMeter run log file
-r – Run the test in the servers specified by the JMeter property “remote_hosts”
-R – list of remote servers Run the test in the specified remote servers
-H – proxy server hostname or ip address
-P – proxy server port



Set the “Number of Threads” field with the value ${__P(threads1,1))} as is shown in the image below. This defines the property threads1, with a default value of 1.
A screenshot of thread properties in JMeter.


2. When running the test through command line, add the flag -Jthreads1=15 which sets the property with the value defined:

jmeter … -Jthreads1=15



Problem:
--------

*   How to resolve the “Server failed to start: java.rmi.server.ExportException” issue in JMeter?  
    Or
*   Server (Host) error while running the JMeter test in the distributed (remote) mode.

Explanation:
------------

To run the test in distributed mode, it is mandatory to start ‘jmeter-server’ service on all the slaves. You may get the below exception while starting ‘jmeter-server’ service:

    Server failed to start: java.rmi.server.ExportException: Listen failed on port: 0; nested exception is:
            java.io.FileNotFoundException: rmi_keystore.jks (No such file or directory)
    An error occurred: Listen failed on port: 0; nested exception is:
            java.io.FileNotFoundException: rmi_keystore.jks (No such file or directory)

Error Screenshot:
-----------------

![Server failed to start: java.rmi.server.ExportException](https://www.perfmatrix.com/wp-content/uploads/2019/06/Distributed-Testing-in-JMeter-02-1024x102.jpg)

_Figure 01: Error Screenshot_

Solution:
---------

Follow the below steps to resolve this issue:

1.  Open jmeter.properties file in the /bin folder of Apache JMeter
2.  Search for the keyword ‘server.rmi.ssl.disable’
3.  Uncomment the statement and change the value to ‘true’
4.  Save the file and launch JMeter once again

![Server failed to start: java.rmi.server.ExportException](https://www.perfmatrix.com/wp-content/uploads/2019/06/Distributed-Testing-in-JMeter-03.jpg)

_Figure 02: Changes in jmeter.properties File_

* * *

https://cwiki.apache.org/confluence/display/JMETER/JMeterAutomatedRemoteTesting

"""{\"report\": \"Transactions/MoneyInTransactions_En.trdp\",
\"parameterValues\": {\"PropertyId\":${Property_ID},
\"SearchId\":${searchId},
\"PrintedBy\":\"Load Test\",
\"ShowHeaderFooter\":true,
\"PrintingDate\":\"${printingDate}\",
\"DateFromSearchCriteria\":\"${searchFrom}\",
\"DateToSearchCriteria\":\"${searchTo}\",
\"SelectedSearchCriteriaPropertiesIds\":\"${Property_ID}\",
\"DateTimeFrom\":\"2024-03-24 12:00 AM\",
\"DateTimeTo\":\"2024-03-24 11:59 PM\",
\"ReceiptVoucherTypeId\":1,
\"SecurityDepositReceiptVoucherTypeId\":3,\
\"DraftVoucherTypeId\":2,
\"ExpensesVoucherTypeId\":8,
\"SecurityDepositRefundVoucherTypeId\":5,
\"DepositCashVoucherTypeId\":9,
\"RefundVoucherTypeId\":4,
\"IsSearchCriteriaIncludesMultipleProperties\":false,
\"selectedSearchCriteriaPropertiesEn\":\"${pName}\",
\"selectedSearchCriteriaPropertiesAr\":\"${pArName}\",
\"HeaderImgUrl\":\"http://staging.nazeel.net:9000/api/anonymous/get-property-logo/\"}}"""