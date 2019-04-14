package io.renren.modules.android.base;


import io.renren.common.utils.QuarkResult;

@FunctionalInterface
public interface ResultProcessor {
    QuarkResult process();
}
