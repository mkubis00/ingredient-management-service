package com.mkvbs.ingredient_management_service.resource.api;

public class ValidationMessage {

    public static final String INCORRECT_NAME_LEN = "name must be between 3 and 50 characters";
    public static final String NAME_NOT_NULL = "name cannot be null";
    public static final String TYPE_OF_QUA_NOT_NULL = "type of quantity cannot be null";
    public static final String ALLERGEN_NOT_NULL = "type of allergen cannot be null";
    public static final String HTTP_MESSAGE_NOT_READABLE = "Some of the values aren't assigned correctly. Check structure of the json.";
}