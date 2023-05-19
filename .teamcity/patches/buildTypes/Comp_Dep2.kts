package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a buildType with id = 'Comp_Dep2'
in the project with id = 'Comp', and delete the patch script.
*/
create(RelativeId("Comp"), BuildType({
    id("Comp_Dep2")
    name = "dep2"

    vcs {
        root(RelativeId("HttpsGithubComChubatovaTigerChubatovaGradleTestsBackup"))
    }
}))
