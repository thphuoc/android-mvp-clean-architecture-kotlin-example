# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class vehicleName to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file vehicleName.
#-renamesourcefileattribute SourceFile
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions

##---------------------------------------------------------------------------
# RETROFIT 2.x
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
##---------------------------------------------------------------------------
## GSON
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-dontwarn com.google.gson.**

-keep class sun.misc.Unsafe { *; }

##---------------------------------------------------------------------------
## OKHTTP 3
-keep class okhttp3.internal.** { *; }
-keep interface okhttp3.internal.** { *; }
-dontwarn okhttp3.internal.**


-keep class android.net.http.** { *; }

##---------------------------------------------------------------------------
# BUTTER KNIFE 8.x
-keep public class * implements butterknife.Unbinder { public <init>(...); }

-dontwarn butterknife.internal.**
-keep class butterknife.** { *; }
-keep class butterknife.**$Finder { *; }
-keep class **$$ViewBinder { *; }
-keep class **$ViewHolder { *; }

-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

##---------------------------------------------------------------------------
# OKIO - A modern I/O API for Java
-dontwarn okio.**

##---------------------------------------------------------------------------
# RXJAVA
-dontwarn rx.**
-keep class rx.internal.util.unsafe.** { *; }

-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}

##---------------------------------------------------------------------------
# RETRO LAMBDA
-dontwarn java.lang.invoke.*
-keep class android.arch.** { *; }

##---------------------------------------------------------------------------
# androidx
-keep class androidx.core.app.CoreComponentFactory { *; }

# PlaceHolderView
-keepattributes *Annotation*
-keepclassmembers class ** {
@com.mindorks.placeholderview.annotations.** <methods>;
}