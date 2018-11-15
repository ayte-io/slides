package io.ayte.presentation.etki.annotation.processing;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String MAIN_PACKAGE = "io.ayte.presentation.etki.annotation.processing";
    public static final String MAIN_PACKAGE_PATH = MAIN_PACKAGE.replace('.', '/');
    public static final String ANNOTATION_NAME = "Explosive";

    public static final String ANNOTATION = MAIN_PACKAGE + '.' + ANNOTATION_NAME;
}
