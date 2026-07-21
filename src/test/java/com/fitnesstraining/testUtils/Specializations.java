package com.fitnesstraining.testUtils;


import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.testUtils.entity.SessionTypes;

import java.util.ArrayList;
import java.util.List;

// CHECKSTYLE.OFF
public final class Specializations {

    private Specializations() {}

    public static List<SessionType> yogaPilates() {
        return new ArrayList<>(
                List.of(
                        SessionTypes.yoga(),
                        SessionTypes.pilates()
                )
        );
    }

    public static List<SessionType> pilates() {
        return new ArrayList<>(
                List.of(SessionTypes.pilates())
        );
    }

    public static List<SessionType> cardioStrengthTraining() {
        return new ArrayList<>(
                List.of(
                        SessionTypes.cardio(),
                        SessionTypes.strengthTraining()
                )
        );
    }

    public static List<SessionType> allSpecializations() {
        return new ArrayList<>(
                List.of(
                        SessionTypes.yoga(),
                        SessionTypes.crossfit(),
                        SessionTypes.pilates(),
                        SessionTypes.cardio(),
                        SessionTypes.strengthTraining(),
                        SessionTypes.zumba()
                )
        );
    }

    public static List<SessionType> cardio() {
        return new ArrayList<>(
                List.of(SessionTypes.cardio())
        );
    }
}
