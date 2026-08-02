package com.fitnesstraining.utils;


public final class ValidationErrorMessages {


    public static final String IS_ACTIVE_CANNOT_BE_NULL = "isActive cannot be null";

    public static final String SPECIALIZATION_CANNOT_BE_EMPTY = "Specialization cannot be empty";


    private ValidationErrorMessages() {}



    public static final class Username {

        public static final String CANNOT_BE_BLANK = "Username cannot be blank";

        public static final String SIZE = "Username must be between 4 and 30 characters";

        public static final String PATTERN = "Username can only contain letters, numbers, dots, and underscores";

        public static final String ALREADY_EXISTS = "Username already exists";

        private Username() {}
    }



    public static final class Password {

        public static final String CANNOT_BE_BLANK = "Password cannot be blank";

        public static final String SIZE = "Password must be between 6 and 100 characters";

        public static final String PATTERN = "Password must be between 6 and 100 characters";

        private Password() {}
    }



    public static final class FirstName {

        public static final String CANNOT_BE_BLANK = "First name cannot be blank";

        public static final String SIZE = "First name must be between 2 and 50 characters";

        public static final String PATTERN = "First name can only contain letters, hyphens, and spaces";

        private FirstName() {}
    }



    public static final class LastName {

        public static final String CANNOT_BE_BLANK = "Last name cannot be blank";

        public static final String SIZE = "Last name must be between 2 and 50 characters";

        public static final String PATTERN = "Last name can only contain letters, hyphens, and spaces";

        private LastName() {}
    }



    public static final class Address {

        public static final String SIZE = "Address must be between 5 and 255 characters";

        // todo
        public static final String PATTERN = "Address can only contain letters, numbers, dots, and underscores";

        private Address() {}
    }



    public static final class BirthDate {

        public static final String PAST = "Birth date must be in the past and represent a realistic age";

        // todo
        public static final String SCHEMA = "Birth date must be a valid date";

        private BirthDate() {}
    }


}
