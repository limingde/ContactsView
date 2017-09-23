-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes SourceFile,LineNumberTable
-dontoptimize
-dontpreverify
-useuniqueclassmembernames

-ignorewarnings
# 保持@JavascriptInterface annotations 不被混淆掉
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

-keep class com.google.** {*;}
-keep class android.support.** {*;}
-keep class com.android.** {*;}
-keep class android.** {*;}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.preference.Preference

-keep class com.bumptech.glide.**{ *; }

-keep class com.dd.ddapplication.ui.**{*;}