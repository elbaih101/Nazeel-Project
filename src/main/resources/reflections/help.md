the x-path //h2[@class='nameclass...'] will search from top. To search from current node. you can use .(dot) like .//h2[@class='nameclass...'].

for card in cards:
    name = card.find_element_by_xpath(".//h2[@class='nameclass...']")
    print(name.text)


https://regex101.com/                         for RegEx generating and testing

https://www.regexplanet.com/advanced/java/index.html            for RegEx generating and testing


https://stackoverflow.com/questions/76482305/faker-generate-a-phone-number   java faker scenarios


https://stackoverflow.com/questions/40956546/selenium-html-input-text-displays-text-but-doesnt-contain-value  getting value of input element



https://www.selenium.dev/documentation/webdriver/interactions/windows/   reflections on selenium

https://stackoverflow.com/questions/6252678/converting-a-date-string-to-a-datetime-object-using-joda-time-library  Date Time Handling

https://toolsqa.com/extent-report/extent-report-for-cucumber-testng-project/       Reports creation and maintaining   [Extent Reports]
    continue  https://www.linkedin.com/pulse/creating-cucumber-extent-report-right-way-praveen-mathew/


https://learn.microsoft.com/en-us/microsoft-edge/webdriver-chromium/capabilities-edge-options

https://www.w3.org/TR/webdriver/#capabilities