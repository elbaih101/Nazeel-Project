package alia.nazeel.stepDefs;

import alia.nazeel.pages.P02_DashBoardPage;
import alia.nazeel.pages.customers.P22_Corporates;
import alia.nazeel.pages.customers.P34_Vendors;
import alia.nazeel.pages.customers.P35_Guests;
import alia.nazeel.pages.mutlipurposes.P00_3_CorporateSelectionPopUp;
import alia.nazeel.pages.mutlipurposes.P00_multiPurposes;
import alia.nazeel.pojos.Guest;
import alia.nazeel.tools.CustomWebDriverWait;
import alia.nazeel.tools.DriverManager;
import alia.nazeel.tools.Utils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class D11_3_Guets {

    final WebDriver driver = DriverManager.getDriver();
    final SoftAssert asrt = new SoftAssert();
    final CustomWebDriverWait wait = new CustomWebDriverWait(driver, Duration.ofSeconds(20));
    final P02_DashBoardPage dashBoardPage = new P02_DashBoardPage(driver);
    final P22_Corporates corporates = new P22_Corporates(driver);
    final P34_Vendors vendors = new P34_Vendors(driver);
    final P35_Guests guests = new P35_Guests(driver);
    final P00_3_CorporateSelectionPopUp selectCorpPopup = new P00_3_CorporateSelectionPopUp(driver);


    Guest guest;


    @And("go to Guests page")
    public void goToGuestsPage() {
        wait.waitLoading();
        dashBoardPage.customersDropList.click();
        dashBoardPage.guestsLink.click();
    }


    private void fillGuestData(String fName, String lName, String phone, String nat, String gType, String idType, String idNumb, String state, String ign, String inv) {
        if (!fName.isEmpty()) {
            guests.firstNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            WebElement fnArField = new P00_multiPurposes(driver).secondLanguageField(guests.firstNameField);
            fnArField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!fName.equalsIgnoreCase("non")) {
                guests.firstNameField.sendKeys(fName);
                fnArField.sendKeys(fName);
            }
        }
        if (!lName.isEmpty()) {
            guests.lastNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            WebElement lNArField = new P00_multiPurposes(driver).secondLanguageField(guests.lastNameField);
            lNArField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!lName.equalsIgnoreCase("non")) {
                guests.lastNameField.sendKeys(lName);
                lNArField.sendKeys(lName);
            }
        }
        if (!ign.contains("bDate")) {
            guests.dateOfBirth.setDateTime("12/3/1999");
        }
        if (inv.contains("bDate")) {
            guests.dateOfBirth.setDateTime(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
        }
        if (!ign.contains("Gender")) {
            guests.genderComboBox.selectByIndex(0);
        }
        if (!nat.isEmpty()) {
            if (nat.equalsIgnoreCase("non")) {
                guests.nationalityComboBox.clearSelection();
            } else {
                guests.nationalityComboBox.selectByTextIgnoreCase(nat);
            }
        }
        if (!gType.isEmpty()) {
            if (gType.equalsIgnoreCase("non")) {
                guests.guestTypeComboBox.clearSelection();
            } else {
                guests.guestTypeComboBox.selectByIndex(0);
            }
        }

        if (!idType.isEmpty()) {
            if (idType.equalsIgnoreCase("non")) {
                guests.idTypeComboBox.clearSelection();
            } else {
                guests.idTypeComboBox.selectByTextIgnoreCase(idType);
            }
        }
        if (!idNumb.isEmpty()) {
            guests.idNumberField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
            if (!idNumb.equalsIgnoreCase("non")) {
                guests.idNumberField.sendKeys(idNumb);
            }
        }
        if (!phone.isEmpty()) {
            if (phone.equalsIgnoreCase("non")) {
                guests.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                guests.dialCode.clearSelection();
            } else if (phone.equalsIgnoreCase("dialonly")) {
                guests.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                guests.dialCode.selectByIndex(0);
            } else if (phone.equalsIgnoreCase("nodial")) {
                guests.dialCode.clearSelection();
                guests.mobileField.sendKeys("336987897");
            } else {
                guests.dialCode.selectByIndex(0);
                guests.mobileField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
                guests.mobileField.sendKeys(phone);
            }
        }

        if (!ign.contains("idSerial")) {
            guests.idSerialComboBox.selectByIndex(0);
        }
        if (inv.contains("Gender")) {
            guests.genderComboBox.clearSelection();
        }

        if (inv.contains("idSerial")) {
            guests.idSerialComboBox.clearSelection();
        }
        guest = new Guest(fName + " " + lName, idNumb, idType, phone, nat, state);
        if (state.equalsIgnoreCase("new"))
            guest.setStatus("Active");
        else if ((!guests.statusSwitch.isOn() && state.equalsIgnoreCase("active")) || (guests.statusSwitch.isOn() && state.equalsIgnoreCase("inactive")))
            guests.statusSwitch.click();
    }

    @When("create Guest firstname {string} last name {string}  phone {string} nationality {string} Guest Type {string} id type {string} id number {string} ignored Fields {string} invailed Fields {string}")
    public void createGuestFirstnameLastNamePhoneIdTypeIdNumberIgnoredFieldsInvailedFields(String fName, String
            lName, String phone, String nat, String gType, String idType, String idNumb, String ign, String inv) {
        wait.waitLoading();
        guests.newGuestButton.click();
        wait.waitLoading();
        fillGuestData(fName, lName, phone, nat, gType, idType, idNumb, "new", ign, inv);
        guests.submitButton.click();
    }

    @Then("Check msg {string} and Guest is {string}")
    public void checkMsgAndGuestIs(String msg, String existance) {
        new D03_BlocksAndFloors().checkToastMesageContainsText(msg);
        if (msg.contains("successfully")) {
            if (existance.equalsIgnoreCase("added")) {
                wait.waitLoading();
                WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guest.getIdNo())).findAny().orElseThrow();
                asrt.assertTrue(guests.guestNationality(selectedGuest).getText().equalsIgnoreCase(guest.getNationality())
                        , "Expected: " + guest.getNationality() + " ,Actual: " + guests.guestNationality(selectedGuest).getText());

                asrt.assertTrue(guests.guestphone(selectedGuest).getText().contains(guest.getPhone()), "Expected: " + guest.getPhone() + " ,Actual: " + guests.guestphone(selectedGuest).getText());
                asrt.assertTrue(guests.guestIDType(selectedGuest).getText().equalsIgnoreCase(guest.getIdType()), "Expected: " + guest.getIdType() + " ,Actual: " + guests.guestIDType(selectedGuest).getText());
                asrt.assertTrue(guests.guestName(selectedGuest).getText().contains(guest.getName()), "Expected: " + guest.getName() + " ,Actual: " + guests.guestName(selectedGuest).getText());
            } else if (existance.equalsIgnoreCase("deleted")) {
                wait.waitLoading();
                asrt.assertFalse(guests.guestsNames.stream().anyMatch(c -> c.getText().equalsIgnoreCase(guest.getIdNo())));
            }
            asrt.assertAll();
        }
    }

    @When("edit Guest with id number {string} firstname {string} last name {string}  phone {string} nationality {string} Guest Type {string} id type {string} id number {string} status {string} ignored Fields {string} invailed Fields {string}")
    public void editGuestWithIdNumberFirstnameLastNamePhoneNationalityIdTypeIdNumberIgnoredFieldsInvailedFields(String oId, String fName, String lName, String phone, String nat, String gType, String idType, String nId, String state, String ign, String inv) {

        WebElement selectedGuest = getGuestData(oId);
        guests.guestEditButton(selectedGuest).click();
        wait.waitLoading();
        fillGuestData(fName, lName, phone, nat, gType, idType, nId, state, ign, inv);
        guests.submitButton.click();

    }

    private WebElement getGuestData(String id) {
        wait.waitLoading();
        WebElement selectedGuest;
        if (id.equalsIgnoreCase("random"))
            selectedGuest = guests.idNumbers.getFirst();
        else
            selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(id)).findAny().orElseThrow();
        String[] name = guests.guestName(selectedGuest).getText().split("\\s");
        guest = new Guest(name[0] + " " + name[1], guests.guestIDNumber(selectedGuest).getText(), guests.guestIDType(selectedGuest).getText(), guests.guestphone(selectedGuest).getText().split("\\s")[1], guests.guestNationality(selectedGuest).getText(), guests.guestStatus(selectedGuest).getText());
        return selectedGuest;
    }

    @When("delete Guest with id number {string}")
    public void deleteGuestWithIdNumber(String idNum) {
        WebElement selectedGuest = getGuestData(idNum);
        guests.guestDeleteButton(selectedGuest).click();
        guests.confirmDeleteButton.click();
    }


    @When("adding a company note {string} and property ote {string} to guest with id {string}")
    public void addingACompanyNoteAndPropertyOteToGuestWithId(String compNote, String propNote, String guestId) {
        wait.waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        wait.waitLoading();
        guests.guestNotesTab.click();
        guests.companyNotesField.sendKeys(compNote);
        guests.addCompanyNoteButton.click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successed");
        guest.setCompNote(compNote);
        guests.propertyNotesField.sendKeys(propNote);
        guests.addPropertyNoteButton.click();
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successed");
        guest.setPropNote(propNote);
    }

    private List<Boolean> asseertGuestNote(String note) {
        WebElement selectedNote = guests.notesContentes.stream().filter(c -> c.getText().contains(note)).findAny().orElseThrow();
        List<Boolean> aserts = new ArrayList<>();
        aserts.add(guests.noteHeader(selectedNote).getText().contains(new P00_multiPurposes(driver).userName()));
        aserts.add(guests.noteHeader(selectedNote).getText().contains(new P00_multiPurposes(driver).propertyName()));
        return aserts;
    }

    @Then("check the note is added with the name of the user who created it and the property")
    public void checkTheNoteIsAddedWithTheNameOfTheUserWhoCreatedItAndTheProperty() {
        wait.waitLoading();
        asseertGuestNote(guest.getCompNote()).forEach(asrt::assertTrue);
        asseertGuestNote(guest.getPropNote()).forEach(asrt::assertTrue);
        asrt.assertAll();
    }


    @When("editing a company note {string} to be {string} to guest with id {string}")
    public void editingACompanyNoteToBeToGuestWithId(String oldNote, String neNote, String guestId) {
        WebElement selectedNote = selectNoteFromGuest(oldNote, guestId);
        guests.noteEditButton(selectedNote).click();
        guests.editNoteField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        guests.editNoteField.sendKeys(neNote);
        guests.saveEditNoteButton.click();
        guest.setCompNote(neNote);

    }

    @Then("Check the Edited Note")
    public void checkTheEditedNote() {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successed");
        wait.waitLoading();
        asseertGuestNote(guest.getCompNote()).forEach(asrt::assertTrue);
        asrt.assertAll();

    }

    private WebElement selectNoteFromGuest(String oldNote, String guestId) {
        wait.waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        wait.waitLoading();
        guests.guestNotesTab.click();
        wait.waitLoading();
        return guests.notesContentes.stream().filter(c -> c.getText().contains(oldNote)).findAny().orElseThrow();
    }


    @When("deleting guest Note {string} to guest with id {string}")
    public void deletingGuestNoteToGuestWithId(String note, String guestId) {
        WebElement selectedNote = selectNoteFromGuest(note, guestId);
        guests.notedeleteButton(selectedNote).click();
        guests.confirmDeleteButton.click();
        guest.setPropNote(note);
    }

    @Then("Check the success msg and the note is deleted")
    public void checkTheSuccessMsgAndTheNoteIsDeleted() {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Deleted Successfully");
        wait.waitLoading();
        asrt.assertFalse(guests.notesContentes.stream().anyMatch(c -> c.getText().contains(guest.getPropNote())));
    }


    @When("adding a Document to guest with id {string} naming it {string}")
    public void addingADocumentToGuestWithIdNamingIt(String guestId, String docName) {
        wait.waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        wait.waitLoading();
        guests.guestDocumentsTab.click();
        guests.addDocumentButton.click();
        Utils.fileUpload(guests.documentUploadField, "src/main/resources/Images", 1);
        guests.documentNameField.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
        guests.documentNameField.sendKeys(docName);
        guests.submitDocumentButton.click();
        guests.submitButton.click();
        guest.setIdNo(guestId);
        guest.setDocName(docName);

    }


    @Then("Check the added document visible with the name {string}")
    public void checkTheAddedDocumentVisibleWithTheName(String docName) {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successfully");
        guest.setDocument(selectDocumentFromGuest(guest.getDocName(), guest.getIdNo()));
        asrt.assertTrue(guest.getDocument() != null);
        asrt.assertAll();
    }

    private WebElement selectDocumentFromGuest(String docName, String guestId) {
        wait.waitLoading();
        WebElement selectedGuest = guests.idNumbers.stream().filter(c -> c.getText().equalsIgnoreCase(guestId)).findAny().orElseThrow();
        guests.guestEditButton(selectedGuest).click();
        wait.waitLoading();
        guests.guestDocumentsTab.click();
        wait.waitLoading();
        return guests.documentNames.stream().filter(c -> c.getText().contains(docName)).findAny().orElse(null);
    }

    @When("deleting the document")
    public void deletingTheDocument() {
        guests.documentDeleteButton(guest.getDocument()).click();
        guests.confirmDeleteDocumentButton.click();

    }

    @Then("Check the document no more exist")
    public void checkTheDocumentNoMoreExist() {
        new D03_BlocksAndFloors().checkToastMesageContainsText("Saved Successfully");
        guest.setDocument(selectDocumentFromGuest(guest.getDocName(), guest.getIdNo()));
        asrt.assertTrue(guest.getDocument() == null);
        asrt.assertAll();
    }


    @When("Filtering guest {string} as {string}")
    public void filteringGuestAs(String filter, String value) {
        wait.waitLoading();
        guests.filterButton.click();
        switch (filter.toLowerCase()) {
            case "status" -> guests.statusFilter.selectByTextContainsIgnoreCase(value);
            case "name" -> guests.nameFilterField.sendKeys(value);
            case "phone" -> guests.mobileFilterField.sendKeys(value);
            case "nationality" -> guests.nationalityFilter.selectByTextContainsIgnoreCase(value);
            case "class" -> guests.guestClassFilter.selectByTextContainsIgnoreCase(value);
            case "idtype" -> guests.idTypeFilter.selectByTextContainsIgnoreCase(value);
            case "idnum" -> guests.iDFilterField.sendKeys(value);
        }
        wait.waitLoading();
        guests.searchButton.click();
    }

    @Then("Check all guests shown {string} as {string}")
    public void checkAllGuestsShownAs(String filter, String value) {
        wait.waitLoading();
        List<WebElement> theList;
        switch (filter.toLowerCase()) {
            case "status" -> {
                theList = guests.statuses;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
            case "name" -> {
                theList = guests.guestsNames;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().contains(value)));
            }
            case "phone" -> {
                theList = guests.mobiles;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().contains(value)));
            }
            case "nationality" -> {
                theList = guests.nationalities;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
            case "class" -> {
                theList = guests.classes;
                if (!theList.isEmpty()) {
                    P00_multiPurposes p00MultiPurposes = new P00_multiPurposes(driver);
                    asrt.assertTrue(theList.stream().anyMatch(s -> !p00MultiPurposes.toolTip(s.findElement(By.xpath("./div/div"))).getText().toLowerCase().contains(value.toLowerCase())));
                }
            }
            case "idtype" -> {
                theList = guests.iDTypes;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
            case "idnum" -> {
                theList = guests.idNumbers;
                if (!theList.isEmpty())
                    asrt.assertFalse(theList.stream().anyMatch(s -> !s.getText().equalsIgnoreCase(value)));
            }
        }
        asrt.assertAll();
    }

    @And("open guest selection popup")
    public void openGuestSelectionPopup() {
       new D01_Reservations().clickOnSelectguestNowButton();
    }
}
