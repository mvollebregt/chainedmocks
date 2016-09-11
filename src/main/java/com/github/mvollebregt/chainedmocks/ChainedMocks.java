package com.github.mvollebregt.chainedmocks;

import com.github.mvollebregt.chainedmocks.fluentinterface.When;
import com.github.mvollebregt.chainedmocks.function.Action;

public class ChainedMocks {

    public static <T> T mock(Class<T> classToBeMocked) {
        try {
            return classToBeMocked.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static When when(Action action) {
        return new When(action);
    }

}
