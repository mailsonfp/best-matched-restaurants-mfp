plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "best-matched-restaurants-mfp"
include("input-boundary")
include("service")
include("web")
include("application")
include("configuration")
include("repository")
include("output-boundary")
