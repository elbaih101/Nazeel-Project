package org.example;


import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Handling APi Requests and Responses senta nd receaved from the driver to ease th elive of the auto generated vuchers and invoices to insure the created voucher number is the same from the responses as the front end
 */
public class API {
    AtomicReference<Network.GetResponseBodyResponse> asyncResponse = new AtomicReference<>(null);
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


    // return body of the request
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


    public String getResponseBody(EdgeDriver driver, String requestUrl, Runnable requestTrigger) {
        devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.responseReceived(), response -> {
            if (response.getResponse().getUrl().contains(requestUrl) && response.getResponse().getStatus().equals(200)) { //Network.loadingFinished();
                asyncResponse.set(devTools.send(Network.getResponseBody(response.getRequestId())));
            }
        });
        requestTrigger.run();


        int timeout = 30;
        while (asyncResponse.get() == null && timeout > 0) {
            Utils.sleep(1000);
            timeout--;
        }

        if (asyncResponse.get() == null)
            throw new RuntimeException("no body found");
        devTools.disconnectSession();
        devTools.close();
        return asyncResponse.get().getBody();

    }
}
