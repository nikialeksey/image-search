package com.nikialeksey.interview.imagesearch;

public class Deps {
    private static final String KOTLIN = "1.3.50";

    public static final String ANDROID_GRADLE = "com.android.tools.build:gradle:3.5.3";
    public static final String KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + KOTLIN;

    // Core
    public static final String KOTLIN_CORE = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:" + KOTLIN;
    public static final String[] ANDROID_CORE = {
            "androidx.appcompat:appcompat:1.1.0",
            "androidx.core:core-ktx:1.1.0"
    };

    // Tests
    public static final String JACOCO_ANDROID_PLUGIN = "com.dicedmelon.gradle:jacoco-android:0.1.4";
    public static final String JUNIT = "junit:junit:4.12";
}
