package com.example.assignment07.regex;

public class Regex {
    public static final String TEXT_AND_NUMBER_REGEX = "(?=.*[a-zA-Z])([a-zA-Z0-9 .-]+)$";
    public static final String TEXT_REGEX = "(?=.*[a-zA-Z])([a-zA-Z .]+)$";
    public static final String PHONE_REGEX = "([0-9]+){10}$";
    public static final String NUMBER_REGEX = "^\\d+(\\.\\d{1,2})?$";



}
