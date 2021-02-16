# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
-keep class com.revenuecat.purchases.* { *; }
-keep class com.google.android.gms.* { *; }

-keep class com.facebook.applinks.* { *; }
-keepclassmembers class com.facebook.applinks.* { *; }
-keep class com.facebook.FacebookSdk { *; }

-keep class com.huawei.hms.ads.* { *; }
-keep interface com.huawei.hms.ads.* { *; }

-dontwarn com.google.android.gms.**


