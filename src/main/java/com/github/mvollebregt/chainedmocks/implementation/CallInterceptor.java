package com.github.mvollebregt.chainedmocks.implementation;

import java.lang.reflect.Method;
import java.util.List;

interface CallInterceptor {

    List<MethodCall> getRecordedCalls();

    Object[] getWildcards();

    Object intercept(Object target, Method method, Object[] arguments);

}
