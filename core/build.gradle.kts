import dependencies.Dependencies

plugins {
    java
    id(Plugins.KOTLIN)
}

java {
    sourceCompatibility = AndroidConfig.javaVersionName
    targetCompatibility = AndroidConfig.javaVersionName
}

dependencies {
    implementation(Dependencies.coroutinesCore)
}