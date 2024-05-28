package alia.nazeel.tools;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v125.network.Network;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Handling APi Requests and Responses sent and receaved from the driver to ease th elive of the auto generated vuchers and invoices to insure the created voucher number is the same from the responses as the front end
 */
public class API {
    final AtomicReference<Network.GetResponseBodyResponse> asyncResponse = new AtomicReference<>(null);
    DevTools devTools = null;

    public void asyncRequestListener(EdgeDriver driver, String requestUrl) {
        devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.responseReceived(), response -> {
            if (response.getResponse().getUrl().contains(requestUrl) && response.getResponse().getStatus().equals(200)) { //Network.loadingFinished();
                asyncResponse.set(devTools.send(Network.getResponseBody(response.getRequestId())));
            }
        });
    }


    /**
 * Awaits the response body after an asynchronous request.
 *
 * @return The response body as a {@link Network.GetResponseBodyResponse} object.
 * @throws RuntimeException if no response body is found within the specified timeout.
 */
public Network.GetResponseBodyResponse await() {
    int timeout = 30;
    while (asyncResponse.get() == null && timeout > 0) {
        Utils.sleep(1000);
        timeout--;
    }

    if (asyncResponse.get() == null)
        throw new RuntimeException("no body found");
    devTools.disconnectSession();
    devTools.close();
    return asyncResponse.get();
}

    /**
     * starts a devTools session using provided WebDriver then adding a listener on responses associated with a provided request url
     * and then runs an Runnale function to triger the request then retreaves the response body
     * @param driver WebDriver
     * @param requestUrl String Request Uri
     * @param requestTrigger Runnable Request Trigger function or lambda ()->trigger;
     * @return String the request full body
     */

    public String getResponseBody(WebDriver driver, String requestUrl, Runnable requestTrigger) {
        int timeout = 30;
        ChromiumDriver driver1 = (ChromiumDriver) driver;
//        if (driver instanceof ChromeDriver)
//             driver1=(ChromeDriver)driver;
//        else if (driver instanceof EdgeDriver)
//            driver1=(EdgeDriver)driver;

        devTools = driver1.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.responseReceived(), response -> {
            if (response.getResponse().getUrl().contains(requestUrl) && response.getResponse().getStatus().equals(200)) { //Network.loadingFinished();
                asyncResponse.set(devTools.send(Network.getResponseBody(response.getRequestId())));
            }
        });
        requestTrigger.run();

        while (asyncResponse.get() == null && timeout > 0) {
            Utils.sleep(1000);
            timeout--;
        }

        devTools.disconnectSession();
        devTools.close();
        if (asyncResponse.get() == null) {
            throw new RuntimeException("no body found");
        }
        return asyncResponse.get().getBody();

    }

    /**
     * retuns a response body of aresponse after atrigger with provided url of the response
     * @param driver driver element to initiate the devtools
     * @param requestUrl String response url or partial url
     * @param requestTrigger runnable code to trigger request
     * @return String respone body
     */
    public String getResponseBody(ChromeDriver driver, String requestUrl, Runnable requestTrigger) {
        int timeout = 20;

        devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.responseReceived(), response -> {

            if (response.getResponse().getUrl().contains(requestUrl) && response.getResponse().getStatus().equals(200)) { //Network.loadingFinished();
                asyncResponse.set(devTools.send(Network.getResponseBody(response.getRequestId())));
            }
        });

        requestTrigger.run();

        while (asyncResponse.get() == null && timeout > 0) {
            Utils.sleep(1000);
            timeout--;
        }

        devTools.disconnectSession();
        devTools.close();
        if (asyncResponse.get() == null) {
            throw new RuntimeException("no body found");
        }
        return asyncResponse.get().getBody();

    }
}
