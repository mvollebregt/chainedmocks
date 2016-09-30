package com.github.mvollebregt.chainedmocks.implementation.base;

import java.lang.reflect.Method;
import java.util.List;

public interface CallInterceptor {

    List<MethodCall> getRecordedCalls();

    Object intercept(Object target, Method method, Object[] arguments);

}
