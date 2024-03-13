using System;

using System.Collections.Generic;

using System.Text;
 
namespace Nazeel.Common.Enums

{

    /// <summary>

    /// System error codes enum

    /// contains error codes that occurs in the back-end like validation errors and general system errors

    /// Author: Mahmoud Salah Elwakeil

    /// Creation Date: 23-03-2021

    /// </summary>

    public enum SystemErrorCodesEnum

    {

        // NOTE: Please consider if you added any key or made any changes here, 

        // you MUST do the exact same change in the same enum in front end

        // Name convention (Pascal Case - First Letter of each word is capital)

        ThisRecordHasRelatedData = 1,

        AnErrorOccuredPleaseContactSystemAdmin = 2,

        CreateDraftVoucherWithMinGuestErr = 3,

        DataChangedPleaseRefreshPageErr = 4,

        PropertyFloorsOrderErr = 5,

        PropertyIdIsRequired = 6,

        SMSMessageIdIsRequired = 7,

        SMSMessageNotExist = 8,

        PropertySMSBalanceEnded = 9,

        PleaseSelectRelatedReservation = 10,

        CityIsRequired = 11,

        NameIsRequired = 12,

        CanNotCopyMinimumDataGuest = 13,

        InvalidGuestNameMustNotIncludeSpecialCharacters = 14,

        CannotCancelReceiptVoucherLinkedToResAsTotalReceivedWillBeLessThanTotalRefund = 15,

        NotAllowedToRemoveReservationLinkForThisTypeOfVoucher = 16,

        InvalidOperationDraftVoucherIsCollectedOrPartiallyCollected = 17,

        SecurityDepositReportInsufficientSearchData = 18,

        CanNotDeleteTaxItHasRelatedData = 19,

        NazeelRenewalPriceIsMissing = 20,

        PleaseSelectActivityModule = 21,

        PleaseSelectValidCriteria = 22,

        UserMustBeAdmin = 23,

        TtLockNoConfigurationFoundOrPropertyDoNotHaveValidCridentials = 24,

        TtLockTokenizationFailed = 25,

        TtLockNoLocksFound = 26,

        InValidReservationUnitForAddingCardLock = 27,

        TtLockAddCardFailed = 28,

        TtLockCardIsDeleted = 29,

        TtLockRemoveCardFailed = 30,

        TtLockNoSmsBalance = 31,

        TtLockAddPassCodeFailed = 32,

        TtLockPassCodeIsDeleted = 33,

        TtLockAddEKeyFailed = 34,

        TtLockOwnerHasNoMobileNumber = 35,

        TtLockEKeyIsDeleted = 36,

        TtLockRemoveEKeyFailed = 37,

        PayFortConfigurationMissing = 38,

        PayFortTotalAmountNotMatched = 39,

        PayFortCouldNotCreateOrder = 40,

        MonthlyIsNotEnabled = 41,

        TtLockRemovePasscodeFailed = 42,

        DuplicateHouseKeepingTaskCustomization = 43,

        MissingId = 44,

        IsNullObject = 45,

        HouseKeepingTaskTypeIdIsRequired = 46,

        PriorityIdIsRequired = 47,

        ClassIdIsRequired = 48,

        StatusIdIsRequired = 49,

        HouseKeepingTaskCategoryIdIsRequired = 50,

        HouseKeepingUnitAndAreaIsRequired = 51,

        ArNameLengthShouldNotExceed100 = 52,

        EnNameLengthShouldNotExceed100 = 53,

        EmptyName = 54,

        DuplicateName = 55,

        ArDescriptionLengthShouldNotExceed300 = 56,

        EnDescriptionLengthShouldNotExceed300 = 57,

        CommonAreaIdMissing = 58,

        ExpensesVoucherVendorInvoiceNumberMaxLength = 59,

        CommonAreaIsCustomizedBefore = 65,

        HouseKeeperRelatedToActiveTasksOrUnits = 66,

        CanNotDeactivateDefaultCurrency = 67,

        NoOtherDefaultCurrenciesExistsForTheProperty = 68,

        EmptyData = 69,

        OwnerIsRequired = 70,

        BankDoesNotHaveCurrency = 71,

        BankCurrencyIsNotTheSameAsVoucherCurrency = 72,

        PleaseSelectReceivingBankForDropCash = 73,

        Dropped_amount_must_be_of_currency_as_sama_as_selected_bank = 74,

        PropertyTimeZoneIsRequired = 75,

        PropertyDateTimeFormatIsRequired = 75,

        TtLockInvalid_account_or_invalid_password = 77,

        ValidTTLocksExistsForThisProperty = 78,

        CanNotChangeBankCurrencyBecauseBankHasRelatedData = 79,

        DataNotExistInYourProperty = 80,

        InvalidCredentials = 81,

        DraftCreationErrFromRes = 82,

        DrawerBalanceDoesnotExistOrNoCustomizedCurrencies = 83,

        CurrencyIsRequired = 84,

        DateFromCannotBeGreaterThanDateTo = 85,

        NoOtherDefaultGuestIdExistsForTheGuestProfile = 86,

        CompanyIdIsMissing = 87,
 
        AnnouncementTypeIdIsRequired = 88,

        BroadcastStartIsRequired = 89,

        BroadcastEndIsRequired = 90,

        MissingProfileId = 91,

        MissingSystemUpdateId = 92,

        FromDateIsRequired = 93,

        ToDateIsRequired = 94,

        ModifyOrderIssueDateError = 95,

        RejectChangeVoucherPaymentMethodToCashIfIssueDateIsBeforeDropCashDate = 96,

        RejectChangeCashVoucherIssueDateTimeTobeBeforeLastDropCashDate = 97,

        OTAProductDuplicatedName = 98,

        ServiceIsNotPaused = 99,

        OverlappedUnitNoRange = 100,

        CantModifyUnitTypeDueToRelatedReservations = 101,

        MobileNumberIsRegistered = 102,

        EmailIsRegistered = 103,

        InvalidVoucherAmount = 104,

        ReservationsUnitsReportInsufficientSearchData = 105,

        IntroducedMobileNolengthShouldbeNDigits = 106,

        AmenityHasRelatedUnits_Error = 107,

        CountryIdIsRequired = 108,

        RegionIdIsRequired = 109,

        GovernorateIdIsRequired = 110,

        DistrictIsRequired = 111,

        StreetIsRequired = 112,

        PostalCodeIsRequired = 113,

        PostalCodeMaxLength = 114,

        BuildingNumberIsRequired = 115,

        BuildingNumberMaxLength = 116,

        AdditionalBuildingNumberIsRequired = 117,

        AdditionalBuildingNumberMaxLength = 118,

        AddressIsRequired = 119,

        EitherMobileNumberOrPhoneNumberRequired = 120,

        TaxRegistrationNumberIsRequired = 121,
 
        IncorrectHttpVerb = 122,

        IncorrectHttpResponse = 123,

        ServiceIsExpired = 124,

        InventoryIdDeletedOrExpired = 125,

        ServiceIsNotConnected = 126,

        OperationIsNotSupported = 127,

        MaxLenghtPOBoxExceed = 128,

        MaxLenghtShortAddressExceed = 129,

        OTAPropertyCodeNotExist = 130,

        OTAMissingId = 131,

        OTAInvalidReservationStatus = 132,

        OTAReservationNotExist = 133,

        OTAMappingError = 134,

        OTADeserializeIssue = 135,

        OTACannotChangeProductWhileThereIsActiveInventory = 136,

        AutoCancelIsNotValid = 137,

        OTAProductsExceedLimit = 138,

        DateRangeExceedYear = 139,

        VerificationExceedLimit = 140,

        PropertyCodeIsRequired = 141,

        AdminUserNameIsRequired = 142,

        AdminUserPasswordIsRequired = 143,

        PropertyCodeIsNotTheSameAsSelectedProperty = 144,

        OdooRequiredFieldIsMissing = 145,

        OdooInternalError = 146,

        OutletPostingLevelIsRequired = 147,

        PleaseSwitchOffTaxExemptedOptionForAllItems = 148,

        CannotGenerateSequenceNumberNodataFoundForSequenceNumberSetup = 149,

        OdooIntegrationPreventExcludingTaxesForAnyItem = 150,

        IncorrectTypeNameOrAssembly = 151,

        DayOfWeekIntersect = 152,

        OdooInvalidSyncDates = 153,

        SearchIdRequired = 155,

        InternalError = 156,

        OtaProductInventoryInvalidSettingsMinLengthOfStayAndMaxLengthOfStayValueCannotBeZero = 157,

        InvalidTaxes = 158,

        OdooUniqueAnalyticalAccountError = 159,

        BaabConfigurationNotExist = 160,

        BaabDeleteProductFailed = 161,

        ServiceIsNotSupported = 162,

        BaabServiceUnAvailable = 163,

        BaabServiceUnKnownError = 164,

        BaabProductHasrelatedData = 165,

        InvalidShomoosCredentials = 166,

        ServicePostingIsOff = 167,

        OTAInventoryInvalidProductChannels = 168,

        Access_Code_Not_Present,

        AddressMaxLenghtExceed,

        AdminSupportHasNoAccessCode,

        AllDroppedAmountsCanNotBeZeros,

        AllUnitsByNumberError,

        AmountExceededDrawerBalance,

        AmountOfUndoCancellingReceiptIsGreaterThanParentDraftRemainingAmount,

        AnnualRenewalPriceReruired,

        apiKeyIsRequired,

        AppliedOnUnits,

        AssigenId,

        AutoExtendCannotBeEnabledDueToCheckoutTime,

        AutoNoShowCancelReasonError,

        AutoNoShowTimeError,

        Bank_AccountNumber_Lenght,

        Bank_Description_Lenght,

        Bank_IBAN_Lenght,

        Bank_Name_Lenght,

        BaseRateIdIsRequired,

        BirthDateIsRequired,

        BlockIsDeleted,

        BlockIsRequired,

        BlockUnitReason_Name_Lenght,

        CancelledReceiptAmountExceededCashDrawerBalance,

        CancelReason_Description_Lenght,

        CancelReason_Name_Lenght,

        CannotCancelCollectedOrPartiallyCollectedDraftVouchers,

        CannotCancelInvoicedOrder,

        CannotCancelOrUndoCancelVoucherRelatedToClosedReservation,

        CannotCancelReservationIncludingActiveInsuranceBalance,

        CannotCancelReservationIncludingDraftVouchers,

        CannotCancelReservationIncludingValidInvoices,

        CannotCancelTransferSubscriptionType,

        CannotCancelVoucherAsTotalReceivedWillBeLessThanTotalRefund,

        CanNotDeleteCustomizedPaymentMethod,

        CannotDeleteDefaultCurrency,

        CannotDeleteDefaultGuestID,

        CannotDeleteDefaultRole,

        CannotEvaluateCorporateWithoutGuest,

        CannotUndoPassedAwayReservation,

        CanNotUndoReceiptVoucherOfACancelledDraftVoucher,

        CannotUpdateVoucherAsTotalReceivedWillBeLessThanTotalRefund,

        CantModifyInvoicedOrder,

        Cant_be_deleted,

        CategoryHaselatedItems,

        CategoryNameAlreadyExists,

        ChangeRateTypeIsNotAllowed,

        ChangeUnitReasonNameMaxLengthExceed,

        CheckOutTimeExceededSettings,

        City_DuplicateName_OrDialCode,

        City_Name_Lenght,

        CompanyIdRequired,

        company_not_Exist,

        CompletePropertyData,

        ConditionTextIsRequired,

        ConflictedMarqueeDates,

        ConflictedSeasonalRateDates,

        ConflictedSpecialRateDates,

        ContainProperties,

        ContainsChangeUnitReason,

        ContainsReservationSourceCustomization,

        CostCenter_CategoryDescription_Lenght,

        CostCenter_Category_Lenght,

        CouldNotAddAnyShomoosRoom,

        CouldNotGetHighWeekDays,

        countryAlreadyExist,

        CountryCannotBeDeleted,

        CountryCantIncludeMoreThanOneVat,

        CountryDialCodeIsRequired,

        CountryIsRequired,

        CountryNameRequired,

        Currency_Customization_NullObject,

        Currency_Customization_Unique,

        Currency_Is_Required,

        CurrentPasswordIsRequired,

        DateFormatRequired,

        DateFromNotLessOrEqualToDay,

        DatesNotFlowAfterEachOther,

        DefaultBlockRequired,

        DefaultFloorRequired,

        DefaultResouceNotDeleted,

        DefaultRoleFKConstrain,

        DefaultUnitClassRequired,

        deleteFloorOrderError,

        DepartmentDescrptionMaxLengthExcessd,

        DepartmentNameMaxLengthExcessd,

        DepositAmountError,

        DepositAmountLength,

        DescriptionLenght,

        DescriptionLengthEceeded600Chars,

        DescriptionLengthExceed,

        DialCodeLength10,

        DiscountTypeCustomization_ReportName_Lenght,

        DiscountTypeGeneralCustomizationDelete,

        DiscountType_Description_Lenght,

        DiscountType_Name_Lenght,

        Discount_Type_Has_Related_Reservations,

        DistrictHaveProperties,

        DropCashDateToMustBeLessThanOrEqualToCurrentDateTime,

        dropCashIdIsRequired,

        DropCashIsCancelledAlready,

        DropCashPurposeMaxLength200,

        DropCashReceivedByMaxLength200,

        DropFromIsRequired,

        DroppedAmountCanNoBeNull,

        DroppedAmountCanNotBeLessThanOrGraterThanTotal,

        DroppedAmountExceededCurrentDrawerBalance,

        DropToIsRequired,

        DuplicatedBaseRate,

        DuplicatedChangeUnitReason,

        DuplicatedCompanyEmail,

        DuplicatedCompanyMobileNumber,

        DuplicatedCompanyName,

        DuplicatedDepartment,

        duplicatedDistrict,

        DuplicatedEmail,

        DuplicateDiscountTypeDedicated,

        DuplicatedIsForGeneralOtaChannels,

        DuplicatedOTAChannel,

        duplicatedPenality,

        duplicatedPropertyName,

        DuplicatedRateName,

        DuplicatedReservationSource,

        DuplicatedShift,

        DuplicatedShomoosUnitNumber,

        Duplicated_HouseKeeper,

        Duplicated_Mobile_Number,

        Duplicated_ScoreWithCriteria,

        Duplicated_User_Name,

        DuplicateGuestBlackListed,

        DuplicateRoutin,

        DuplicateTaxReportName,

        DuplicateUnitTypeCustomization,

        Either_country_or_company_or_property_is_required,

        EmailIsRequired,

        EmailLength100,

        EmailLength50,

        EmptyAmountValue,

        EmptyBank,

        EmptyBankAccountNumber,

        EmptyCity,

        EmptyComment,

        EmptyCompany,

        EmptyCorporateData,

        EmptyCorporateName,

        EmptyCostCenterCategory,

        EmptyCountry,

        EmptyDepartment,

        EmptyDiscountAmount,

        EmptyDiscountType,

        emptyDistrict,

        EmptyFile,

        EmptyFundTransactionType,

        EmptyGuestClass,

        EmptyGuestClassName,

        EmptyGuestDocument,

        EmptyGuestRelationShip,

        EmptyItemDiscountType,

        EmptyOutlet,

        EmptyOutletCategory,

        EmptyOutletCode,

        EmptyOutletItem,

        EmptyPaymentMethod,

        EmptyPenalty,

        EmptyReservation,

        EmptyReservationGuestEvaluation,

        EmptyReservationSettings,

        EmptyReservationSource,

        EmptySelectNTMP,

        EmptySelectOutlet,

        EmptyShift,

        emptyStreet,

        EmptyTax,

        EmptyTaxCountries,

        EmptyTaxType,

        EmptyType,

        EmptyUnitNumber,

        EmptyUnitSetup,

        EmptyUnitTpeDeposit,

        emptyUnitType,

        EmptyUserName,

        EmptyVoucherType,

        Empty_Attachment,

        endDateBeforeExpiryDate,

        EndDateIsRequired,

        EndTimeRequired,

        ErrorHaveUnits,

        ExceedCompanyMaxUsers,

        Exchange_Rate_Is_Required,

        ExpensesVoucherCostCenterIsRequired,

        ExpiaryDateCannotbeTodayOrLess,

        expired_user,

        ExpiryDateMustAfterToday,

        FeeChargedOn,

        FileUploadSuppoertedDimensions,

        FillAllRequiredData,

        FirstNameIsRequired,

        FirstNameMaxLenghtExceed,

        FloorIsDeleted,

        FromDateCannotbeLessThanToday,

        GeneralFeatureRelatedToOtherFeature,

        GeneralUnitFeatureIdIsRequired,

        GivenToMinLengthis3Characters,

        GracePeriodError,

        GuestAlreadyExistFortheTargetCompany,

        GuestClassOrder,

        GuestClassOrderLimit,

        GuestFeedbackMetricsDoesnotExist,

        GuestListNotExist,

        GuestProfileMustHaveAtLeastOneId,

        GuestsWithWrongUnits,

        GuestTypeRequired,

        GuestUnitsIdsMissing,

        HadRelatedData,

        HouseKeepingTask_Name_Lenght,

        inactive_user,

        InConsistantIdsWithGuestType,

        InCorrectDataInCaseOfCollect,

        InCorrectInvoiceStateInCaseOfCollect,

        InCorrectTransactionData,

        InsufficientBalance,

        InsuranceRefundAmountMustNotExceedReservationInsuranceReceivedAmount,

        InsuranceValueCannotBeNull,

        IntersectedTaxes,

        InvalidAction,

        InvalidActionDeleteOrInactiveCancelReason,

        InvalidActionOrderIsAlreadyCaancelled,

        InvalidActionOrderIsAlreadyCaancelledAndRefunded,

        InvalidActionOrderMustBeCancelledBeforeRefund,

        InvalidBaseRateSettings,

        InvalidBusinessPhoneNo_Length,

        InvalidCount,

        InvalidCreateFutureVoucherIssueDateTime,

        InvalidCreateVoucherIssueDateTime,

        InvalidDateFormat,

        InvalidDescriptionAr_Length,

        InvalidDescriptionEn_Length,

        InvalidDiscountAmount,

        InvalidFullNameAr_Length,

        InvalidFullNameEn_Length,

        InvalidGuestProfileId,

        InvalidHKTaskCategory,

        InvalidInput,

        InvalidInputFormat,

        InvalidItemDiscountAmount,

        InvalidMobileNo_Length,

        InvalidNTMPCredentials,

        InvalidNTMPNationalityCode,

        InvalidNTMPUserNameOrPassword,

        InvalidOrderStatus,

        InvalidOrderType,

        InvalidPhotoURL_Length,

        InvalidPosition_Length,

        InvalidReservationSourceType,

        InvalidShomoosCredential,

        InvalidSignatureURL_Length,

        InvalidSpecialRateSettings,

        invalid_mobile_dial_code,

        invalid_mobile_number,

        Invalid_new_password,

        Invalid_Password_Length,

        invalid_role,

        Invalid_UserName_OrAccessCode,

        Invalid_User_Gender,

        Invalid_User_Type,

        Invalid_Verification_code,

        InvoicePeriodCantExceedReservationPeriod,

        IsActiveRequired,

        IsCommentRequired,

        IsExpensesAlreadyExisted,

        IsMatchIdNumber,

        IsNullIdObject,

        IssueDateTimeIsRequired,

        ItemDiscountGreaterThanItemTotalAmount,

        ItemHadRelatedDataAndCanNotBeDeleted,

        ItemHasRelatedOrders,

        ItemsChangedWhileCreatingInvoice,

        ItemsQuantitiesAreRequired,

        LastNameIsRequired,

        LastNameMaxLenghtExceed,

        LastUnitCannotBeCheckedOut,

        LenghtCorporateAddress,

        LenghtCorporateContactPerson,

        LenghtCorporateContactPersonPhone,

        LenghtCorporateName,

        LenghtCorporateVatRegistrationNum,

        LenghtEmail,

        LenghtGuestClassName,

        LenghtPhone,

        LinkExists,

        LoyaltyProgramCriteriaIsRequired,

        MandatoryCheckInExistError,

        MaxLenghtExceed,

        MaxNumberOfUsersRequired,

        Message_Body_Empty_Or_Null,

        Message_Body_Lenght,

        Message_Body_Minimum_Length,

        MiddleNameMaxLenghtExceed,

        MissingCompanyId,

        MissingData,

        MissingDataDetailCreationCode6,

        MissingDataReservationDataCode2,

        MissingDataSetupDataCode5,

        MissingDataTimeZoneCode4,

        MissingDataUndoFlagCode3,

        MissingDependantsRelations,

        MissingFloorId,

        MissingPropertyId,

        MissingPurpose,

        MissingReceivedBy,

        missingStepsPleaseCompleteData,

        MissingUnitTypeId,

        MobileNumberIsRequired,

        Mobile_number_not_exist,

        NameLength100,

        NameLength50,

        NameLengthEceeded200Chars,

        NationalityIsRequired,

        NewPasswordIsRequired,

        NoAvailabilityForCount,

        NoAvailabilityForUnitNumber,

        NoAvailabilityForUnitType,

        NoBaseRatesForSelectedUnits,

        NOData,

        NoDataFound,

        noDataInMemoryStorePleaseAddReservationAgain,

        NoEnterKey,

        NoExpirayOnConfirmedError,

        NoExpiryCouldBeSet,

        NoItemsToInvoice,

        NoMultipleExceptMerged,

        NoReservationSummaryExistPleaseUpdateReservation,

        NoSelectedUnits,

        NotAccessedToProperty,

        NotActiveUser,

        NotAuthorized,

        NotLastInvoice,

        NotUniqueIdInNazeelGuests,

        NotUniqueIdObject,

        NotUniqueTypeId,

        NotVaildIdNumber,

        NotVaildIdSerial,

        NotVaildIdType,

        NotVaildMobileNumber,

        NotVaildWorkPhoneNo,

        NotValidContactPersonPhoneExtension,

        NotValidEmail,

        NotValidFirstUnit,

        NotValidGenderId,

        NotValidHallTypeId,

        NotValidKitchenTypeId,

        notValidModel,

        NotValidNumberOfDoubleBeds,

        NotValidNumberOfSingleBeds,

        NotValidNumberOfToilets,

        NotValidPhoneExtension,

        NotValidSecondUnit,

        NotValidTimes,

        NotValidUnitArea,

        NotValidUnitClassId,

        NotValidUnitGroupId,

        NotValidUnitNumber,

        NTMPGeneralFailureMessage,

        NullableSeasonalRateObject,

        NullableSpecialRateObject,

        NumberMaxLenghtExceed,

        OdooMappingFailure,

        onlyCommentChangingAllowed,

        OrderGeneralDiscountGreaterThanOrderTotalAmount1,

        OrderHasDuplicatedPaymentMethods,

        OrderIdIsRequired,

        OrderIsRequired,

        OrderMustBeUnique,

        OrderStatusIsRequired,

        OrderTypeIsRequired,

        Order_generalComment_Lenght,

        OTAInventoryUnitsCountNotAvailable,

        OutletCategoryIdIsRequired,

        OutletCategory_Description_Lenght,

        OutletCategory_Name_Lenght,

        OutletIdIsRequired,

        OutletItemPriceRequired,

        OutletItem_Description_Lenght,

        OutletItem_Name_Lenght,

        OutletRelatedToCatoriesDelete,

        Outlet_Description_Lenght,

        Outlet_DuplicateCode,

        Outlet_Name_Lenght,

        Outlet_OutletCode_OnlyDigitUpTo3Allowed,

        OverLappedRanges,

        OverlappedReservations,

        OwnerDataNotExist,

        PaidAmountExceededRemainingBalance,

        Password_is_required,

        PaymentMethodDoesnotExist,

        Payment_Method_HasRelated_Vouchers,

        PenalityAmountShouldBeGreaterThan0,

        PenaltyHelpLength,

        PenaltyNameLength,

        PhotoIsRequired,

        PhotoMaxNumber,

        pleaseCorrectStepsErrors,

        PleaseInsertDataToFile,

        ProcessError,

        ProductOTAOverlappedUnits,

        PropertyAccountRequired,

        PropertyCanHasOnlyOneDefaultCurrency,

        PropertyCityRequired,

        PropertyCountryRequired,

        PropertyFacilitlyAlreadyExists,

        PropertyFeatureIdIsRequired,

        PropertyFeatureIdNameRequired,

        PropertyFeatureIsNull,

        PropertyFeatureNameAlreadyExists,

        PropertyHasCondition,

        PropertyHasCurrencies,

        PropertyHasPenalties,

        PropertyHasUnitTypes,

        PropertyIdName,

        PropertyIsNull,

        propertyNameMaxLength,

        PropertyNameReruired,

        PropertyUnitIdIsRequired,

        PurposeMinLengthIs3Chars,

        RateDetailIdIsRequired,

        RateIdIsRequired,

        RateIsNotCorrect,

        RateSettings_EmptyMinRate,

        RateTypeIsRequired,

        ReasonNameRequired,

        ReceivedByMinLengthIs3Chars,

        ReceivedFromLengthMinLengthis3Characters,

        RefundAmountMustBeEqualToTheCurrentBalance,

        RefundAmountMustNotExceedReservationReceivedAmount,

        RelatedCostCenter,

        RelatedDiscountTypeCustomization,

        RelatedToProfiles,

        ReleaseNumberIsRequired,

        RentFromMustStartFromTheLastInvoiceDate,

        RepeatedUnitTypeDeposit,

        reservationCannotBeIntegrated_NoMessageToBeIntegrated,

        reservationCannotBeIntegrated_NoShomoosRoom,

        reservationCannotBeIntegrated_Undo,

        ReservationCommentLengthExceeded,

        ReservationDateFromError,

        ReservationDateToError,

        ReservationFromDateCannotBeChanged,

        ReservationGuestEvaluationInvalidData,

        ReservationGuestEvaluationInvalidRating,

        ReservationIdIsRequired,

        ReservationPurposeError,

        ReservationRentalTypeError,

        ReservationSourceError,

        ReservationSourceHasBeenCustomized,

        ReservationSourceUnique,

        ReservationStatusError,

        ReservationStatusInValidError,

        ReservationTypeError,

        RoleObjectIsNull,

        salesMen,

        ScoreIsRequired,

        SecondNameMaxLenghtExceed,

        SelectedHighWeekDaysMustBeConnected,

        SelectedOutletIsClosed,

        SelectedOutletIsInactive,

        ShiftDaysRequired,

        ShiftNameRequired,

        smsPricing,

        SMSPricingBalanceInCorrectRanges,

        SMSPricingBalanceInCorrectValues,

        SMSPricingBalanceRangeRequired,

        SPSCIDIsRequired,

        StartDateIsRequired,

        StartDateTimeAfterEndDateTime,

        StartingAmountReruired,

        StartingAmountTaxReruired,

        StartTimeRequired,

        String1,

        SubCategoryNameAlreadyExists,

        SubTitleIsRequired,

        suspended_Account,

        SystemError,

        SystemUpdateTypeIsRequired,

        TargetPropertyContainsPenalites,

        TaxApplyAfterCycle,

        TaxApplyAfterInvalid,

        TaxCustomizationAppliedForNotVaild,

        TaxCustomizationCalculationNotVaild,

        TaxCustomizationHasChargedOnDelete,

        TaxCustomizationHasDependants,

        TaxCustomizationNotValidValidity,

        TaxCustomizationRelatedData,

        TaxCustomizationTypeNotVaild,

        TaxCustomizationValidityEndNotVaild,

        TaxCustomizationValidityStartNotVaild,

        TaxCustomization_AbsoluteAmount_Lenght,

        TaxCustomizedRelatedToTransaction,

        Taxes_Property_Customized_Before,

        TaxNameLength,

        TaxNameRequired,

        TaxPropertySettingsEmpty,

        TaxRegistrationNoIsRequired,

        TaxRegistrationNoMaxLenghtExceed,

        TaxRelatedToCustomizedDelete,

        TaxReportNameLength,

        TaxTypeMisMatch,

        Tax_Default_Settings_Exists,

        Tax_Description_Lenght,

        TemplateNotFound,

        ThisNumberUsedBefore,

        TicketCategoryHasRelatedTickets,

        TimeEnableMissMatch,

        TitleIsRequired,

        ToDateCannotBeLessThanFromDate,

        ToDateCannotbeLessThanToday,

        TypeCannotBeChangedAfterCheckedin,

        UnAuthorizedAccess,

        UnAuthorizedAddFutureReservations,

        UnAuthorizedAddModifyExpiryDate,

        UnAuthorizedAddPastReservations,

        UnAuthorizedAddReservations,

        UnAuthorizedAdjustDiscountBalance,

        UnAuthorizedAutoExtendReservationPeriod,

        UnAuthorizedChangeCalendarOfSavedReservations,

        UnAuthorizedChangeRentalTypeOfSavedReservations,

        UnAuthorizedEditMainOwnerGuestOfCheckedInReservations,

        UnAuthorizedEditReservations,

        UnAuthorizedManageDiscount,

        UnAuthorizedModifyPenaltyFees,

        UnauthorizedToAddOrderOverOutlet,

        UnauthorizedToAddOrderOverProperty,

        UnAuthorizedToCancelReservation,

        UnClosedShomoosReservation,

        UnitFeatureIdIsRequired,

        UnitFeatureNameAlreadyExists,

        UnitIsRelatedToAnotherCheckedInReservation,

        UnitNotAvailable,

        UnitNumber,

        UnitNumbersExistsBefore,

        UnitOnReservationTypeError,

        UnitsExceedMaxLimit,

        UnitTypeCustomizationIdIsRequired,

        UnitTypeHasRelatedSeasonalOrSpecialRates,

        UnitTypeIdName,

        UnitTypeShortage,

        Unit_is_currently_locked_by_unhandled_reservation,

        Unit_Is_not_Available_at_the_selected_dates,

        UpdatedVoucherIsRelatedToDropCash,

        UpgradeToGuestClassIsRequired,

        UploadFileEmpty,

        UploadFileInvalidType,

        UploadFileRequired,

        UploadFileSizeExceeded,

        URLLength,

        UsedCityDelete,

        UserNameHasSpecialChar,

        username_is_Changed,

        username_is_required,

        username_length_not_valid,

        Username_NotPresent,

        UserPasswordLength,

        UserProfile_DuplicatedData,

        user_email_or_mobile_not_exist,

        User_Full_Name_Must_Two_Words,

        User_Is_Required,

        ValidateMergedUnit,

        Verification_code_expired,

        VisaNumberMaxLenghtExceed,

        VoucherComment500charMaxLengthExceeded,

        VoucherIdIsRequired,

        VoucherIsRelatedToDropCash,

        VouchersNumbering_EmptyPrefix,

        VouchersNumbering_EmptySequenceStartingNo,

        VouchersNumbering_Prefix_OnlyCapitalCharactersAllowed,

        VouchersNumbering_SequenceStartingNo_OnlyNumbersAllowed,

        WalkinNotCreatedPerformComplatePaymentForOrderBalance,

        WorkPlaceMaxLenghtExceed,

        WrongExtention,

        Wrong_password,

        YouCannotAddReservationTwice = 746,

        AmenityIdIsRequired = 747,

        UnitTypeNameMaxLength = 748,

        OnlyOneDigitalPaymentMapping = 748,

        ElasticSearchNotAvailable = 749,

        ElasticSearchSaveFault = 750,

        ElasticClientNotExists = 751,

        Unittype_Had_Related = 752,

        UnitTypeIsExist = 753,

        DuplicatedPayTabsRequest = 755,

        ZatcaPropertyDataAlreadyExists = 754,

        PaymentMethodHasRelatedService = 756,

        ZatcaPending = 757,

        ZatcaNoDataFound = 758,

        ZatcaUnauthorized = 759,

        ZatcaNazeelError = 760,

        ZatcaSuccess = 761,

        ZatcaError = 762,

        ZatcaOwnerCannotBeChanged = 763,

        PayTabServiceIsNotAvailableAtTheMoment = 764,

        DuplicatedPayTabsAuth=765,

        PayTabsCurrencyNotAvailable =766,

        ZatcaRejected = 767,

        PayTabsAmountExceedsDraftRemainingAmount=768,

        PayTabsTransactionNotAvailableToQueryYet = 769,

        InactiveUnitIsNotAllowedWithProduct = 770,

        TaxIsNotMappedToSaudiBeds = 771,

        FloorCreationIsLimited = 772,

        BlockCreationIsLimited = 773,

        InvalidAccessTokenOrRefreshToken = 774,

        NoDuplicatesSubPrice = 775,

        DateRangeValidation= 776,

        CommercialRegistrationNumberIsRequired = 777,

        SearchNotAllowedOverOneDay = 778,

        OtaProductInventoryInvalidSettingsMinLengthOfStayIsBiggerThanMaxLengthOfStay = 779,

        ZatcaDownloadError = 780,

        VATLengthRequired =781,

        VATShouldBeginAndEndBy3 = 782,

        CommercialRegistrationNumberLength = 783

    }

}
