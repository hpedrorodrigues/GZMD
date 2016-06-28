# Rules

# Kotlin
-dontwarn kotlin.**
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

# Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
-printmapping mapping.txt

# Fabric SDK
-keep class io.fabric.sdk.android.** { *; }
-keep interface io.fabric.sdk.android.** { *; }
-keep class io.fabric.** { *; }
-keep interface io.fabric.** { *; }

# General Libraries
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes Exceptions

# R class
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Serializable classes
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Local code
-keep class com.hpedrorodrigues.gizmodobr.** { *; }

# Support library v4
-dontwarn android.support.v4.app.**
-dontwarn android.support.v4.view.**
-dontwarn android.support.v4.widget.**

# Support library v7
-dontwarn android.support.v7.media.**

# Support Design
-keep class android.support.design.widget.** { *; }
-keep interface android.support.design.widget.** { *; }
-dontwarn android.support.design.**

# SuperRecyclerView
-dontwarn com.malinskiy.superrecyclerview.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# Okhttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.* { *; }
-dontwarn okhttp3.*

# Okio
-dontwarn okio.**

# Rx
-dontwarn rx.**

# Gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }

# Picasso
#-dontwarn com.squareup.picasso.**
-dontwarn com.squareup.okhttp.**

# Firebase
-keep class com.google.firebase. { *; }
-dontwarn com.google.firebase.

# Licenses Dialog
-dontwarn de.psdev.licensesdialog.**