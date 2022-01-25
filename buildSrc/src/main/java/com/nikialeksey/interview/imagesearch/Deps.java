package com.nikialeksey.interview.imagesearch;

public class Deps {
    private static final String KOTLIN_VER = "1.6.10";
    private static final String NAVIGATION_VER = "2.3.5";
    private static final String GLIDE_VER = "4.10.0";
    public static final String JACOCO_VER = "0.8.7";

    public static final String JACOCO_ANDROID_PLUGIN = "com.dicedmelon.gradle:jacoco-android:0.1.5";
    public static final String ANDROID_GRADLE = "com.android.tools.build:gradle:7.0.3";
    public static final String KOTLIN_GRADLE = "org.jetbrains.kotlin:kotlin-gradle-plugin:" + KOTLIN_VER;

    // Core
    public static final String KOTLIN_CORE = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:" + KOTLIN_VER;
    public static final String[] ANDROID_CORE = {
            "androidx.appcompat:appcompat:1.4.1",
            "androidx.core:core-ktx:1.7.0"
    };

    // Navigation
    public static final String[] NAVIGATION = {
            "androidx.navigation:navigation-fragment-ktx:" + NAVIGATION_VER,
            "androidx.navigation:navigation-ui-ktx:" + NAVIGATION_VER
    };

    // Views
    public static final String[] CONSTRAINT_LAYOUT = {
            "androidx.constraintlayout:constraintlayout:2.1.3"
    };
    public static final String RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.2.1";

    // Data binding
    public static final String BINDING_DELEGATE = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6";

    // ViewModel
    public static final String[] VIEW_MODEL = {
            "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1",
            "androidx.lifecycle:lifecycle-extensions:2.2.0"
    };
    public static final String VIEW_MODEL_COMPILER = "androidx.lifecycle:lifecycle-compiler:2.3.1";
    public static final String LIVE_DATA = "androidx.lifecycle:lifecycle-livedata-core-ktx:2.3.1";

    // Paging
    public static final String PAGING = "androidx.paging:paging-runtime-ktx:2.1.2";

    // Network
    public static final String[] RETROFIT = {
            "com.squareup.retrofit2:retrofit:2.7.0",
            "com.squareup.retrofit2:converter-gson:2.7.0"
    };
    public static final String[] OKHTTP = {
            "com.squareup.okhttp3:okhttp:4.2.2",
            "com.squareup.okhttp3:logging-interceptor:4.2.2"
    };

    // Images
    public static final String GLIDE = "com.github.bumptech.glide:glide:" + GLIDE_VER;
    public static final String GLIDE_COMPILER = "com.github.bumptech.glide:compiler:" + GLIDE_VER;

    // Tests
    public static final String JUNIT = "junit:junit:4.12";
    public static final String[] INSTRUMENTED = {
            "androidx.test:runner:1.4.0",
            "androidx.test.ext:junit-ktx:1.1.3",
            "androidx.test:rules:1.4.0",
            "androidx.test.espresso:espresso-core:3.4.0",
            "androidx.test.uiautomator:uiautomator:2.2.0"
    };
}
