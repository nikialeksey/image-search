package com.nikialeksey.interview.imagesearch;

public class Deps {
    private static final String KOTLIN_VER = "1.3.50";
    private static final String NAVIGATION_VER = "2.1.0";
    private static final String CONSTRAINT_LAYOUT_VER = "1.1.3";

    public static final String ANDROID_GRADLE = "com.android.tools.build:gradle:3.5.3";
    public static final String KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + KOTLIN_VER;

    // Core
    public static final String KOTLIN_CORE = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:" + KOTLIN_VER;
    public static final String[] ANDROID_CORE = {
            "androidx.appcompat:appcompat:1.1.0",
            "androidx.core:core-ktx:1.1.0"
    };

    // Navigation
    public static final String[] NAVIGATION = {
            "androidx.navigation:navigation-fragment-ktx:" + NAVIGATION_VER,
            "androidx.navigation:navigation-ui-ktx:" + NAVIGATION_VER
    };

    // Views
    public static final String[] CONSTRAINT_LAYOUT = {
            "androidx.constraintlayout:constraintlayout:" + CONSTRAINT_LAYOUT_VER,
            "androidx.constraintlayout:constraintlayout-solver:" + CONSTRAINT_LAYOUT_VER
    };
    public static final String RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.1.0";

    // Tests
    public static final String JACOCO_ANDROID_PLUGIN = "com.dicedmelon.gradle:jacoco-android:0.1.4";
    public static final String JUNIT = "junit:junit:4.12";
}
