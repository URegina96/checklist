package com.example.checklist;

import androidx.annotation.StringRes;

public interface ResourceProvider {
    String string(@StringRes int resource);

    String string(@StringRes int resource, Object... args);
}
