
//TODO add assertions to the jmeter script

N ow I just need to add this code as the very first sampler in a test plan and pass environment specific filepath as the sampler parameter args[0] it will load variables from the file and put them as JMeter variables.
//TODO fix tax in inclusive state for reservation making in Jmeter

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