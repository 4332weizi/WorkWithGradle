# WorkWithGradle
the basic usage of gradle

1.使用`buildConfigField`
--
`buildConfigField`可以在`pakagename`包下生成一个`BuildConfig`类，其中包含`buildConfigField`中声明的字段。
在未声明任何`buildConfigField`的情况下，也会生成默认的`BuildConfig`，其中包含：
```java
public static final boolean DEBUG = Boolean.parseBoolean("true");  
public static final String APPLICATION_ID = "net.funol.workwithgradle";  
public static final String BUILD_TYPE = "debug";  
public static final String FLAVOR = "";  
public static final int VERSION_CODE = 1;  
public static final String VERSION_NAME = "1.0";
```
也可以声明自己的`buildConfigField`。同一字段在不同的`buildTypes`中声明不同的值，可以在构建的不同版本中使用，加以区分它们。比如在`release`版本中使用生产环境的`host`,在`debug`版本中使用测试环境的`host`，发布的时候就可以免去手动修改造成的一些不必要的麻烦。当然用gradle也可以一键打包所有版本。
```gradle
buildTypes {
        debug {
            ......
            buildConfigField "String", "HOST", '"https://debug.funol.net"'
            buildConfigField "boolean", "isRelease", 'false'
            buildConfigField "int", "versionCode", "${defaultConfig.versionCode}"
        }
        release {
            ......
            buildConfigField "String", "HOST", '"https://release.funol.net"'
            buildConfigField "boolean", "isRelease", 'true'
            buildConfigField "int", "versionCode", "${defaultConfig.versionCode}"
        }
}
```