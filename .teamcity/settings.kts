import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.buildFeatures.approval
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildFeatures.parallelTests
import jetbrains.buildServer.configs.kotlin.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.buildSteps.dotnetTest
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2022.10"

project {

    vcsRoot(HttpsGithubComChubatovaTigerChubatovaGradleTestsBackup)

    buildType(RunTests_4)

    subProject(Suproj)
}

object RunTests_4 : BuildType({
    id("RunTests")
    name = "RunTests"

    buildNumberPattern = "Chubatova.1.1.%build.counter%"

    params {
        param("par1", "1")
        password("par2sec", "credentialsJSON:18080f50-717a-400f-9888-f3244ed6a471")
    }

    vcs {
        root(DslContext.settingsRoot)

        branchFilter = """
            +:*
            -:refs/heads/post
        """.trimIndent()
    }

    steps {
        dotnetTest {
            projects = "TestProject5/TestProject5.csproj"
            //filter = "FullyQualifiedName!~UnitTest"
            sdk = "6"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
        }
        script {
            scriptContent = "ls"
        }
        dotnetTest {
            name = "New build step"
            projects = "TestProject1/TestProject1.csproj"
            filter = "FullyQualifiedName!~UnitTest1"
            sdk = "6"
            param("dotNetCoverage.dotCover.home.path", "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%")
        }
    }

    triggers {
        vcs {
            branchFilter = ""

            buildParams {
                param("parFromTrigger", "parFromTriggervalue")
            }
        }
    }

    features {
        parallelTests {
            numberOfBatches = 3
        }
        commitStatusPublisher {
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:5aba948a-54a9-4618-97c1-ae1b426876c1"
                }
            }
        }
        /*approval {
            approvalRules = "user:approve"
        }*/
        swabra {
            verbose = true
        }
        pullRequests {
            vcsRootExtId = "${DslContext.settingsRoot.id}"
            provider = github {
                authType = token {
                    token = "credentialsJSON:5aba948a-54a9-4618-97c1-ae1b426876c1"
                }
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }

    /*dependencies {
        snapshot(Dep) {
        }
        artifacts(Artdep) {
            buildRule = lastSuccessful()
            cleanDestination = true
            artifactRules = """
                a* => .
                b* => .
            """.trimIndent()
        }
    }*/

    requirements {
        contains("teamcity.agent.name", "Default")
    }
})

object HttpsGithubComChubatovaTigerChubatovaGradleTestsBackup : GitVcsRoot({
    name = "https://github.com/ChubatovaTiger/ChubatovaGradleTestsBackup"
    url = "https://github.com/ChubatovaTiger/ChubatovaGradleTestsBackup"
    branch = "refs/heads/master"
})


object Suproj : Project({
    name = "suproj"

    buildType(Artdepdep)
    buildType(Artdep)
    buildType(Dep)
    buildType(Head)
})

object Artdep : BuildType({
    name = "artdep"

    artifactRules = """
        a*
        b*
    """.trimIndent()

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = "echo a > a%build.counter%"
        }
    }

    dependencies {
        artifacts(Artdepdep) {
            buildRule = lastSuccessful()
            artifactRules = "b* => ."
        }
    }
})

object Artdepdep : BuildType({
    name = "artdepdep"

    artifactRules = "b*"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = "echo a > b%build.number%"
        }
    }
})

object Dep : BuildType({
    name = "dep"

    vcs {
        root(DslContext.settingsRoot)
        root(HttpsGithubComChubatovaTigerChubatovaGradleTestsBackup, "+:. => gradle")
    }

    steps {
        script {
            scriptContent = "sleep 1"
        }
        gradle {
            tasks = "clean build"
            buildFile = "gradle/build.gradle"
            gradleWrapperPath = "gradle"
        }
    }
})

object Head : BuildType({
    name = "xhead"

    type = BuildTypeSettings.Type.COMPOSITE

    vcs {
        root(DslContext.settingsRoot)

        showDependenciesChanges = true
    }
})
