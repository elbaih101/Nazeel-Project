package org.example;

import org.openqa.selenium.WebElement;

import java.io.File;

public class Utils {



   public static void fileUpload(WebElement element,String folderPath,int count){
       File folder = new File(folderPath);
       File[] photos =folder.listFiles();
       for (int i=0 ;i<count;i++){
            File uploadFile = photos != null ? photos[i] : null;
            element.sendKeys(uploadFile != null ? uploadFile.getAbsolutePath() : null);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException e) {
              e.printStackTrace();
           }
       }

    }
}
