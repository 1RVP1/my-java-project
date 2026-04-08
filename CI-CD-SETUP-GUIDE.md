# CI/CD Integration Guide - GitHub Actions & Jenkins

This guide explains how to set up automated test execution on code push using either GitHub Actions or Jenkins.

## Project Overview

- **Project Type**: Maven-based Java project with JUnit 5
- **Test Files**: `*IntegrationTest.java` (Integration tests), `*UnitTest.java` (Unit tests)
- **Build Tool**: Maven 3.x
- **Java Version**: JDK 17

---

## Option 1: GitHub Actions (Recommended for GitHub Projects)

### File Location
The workflow file must be placed at: `.github/workflows/ci.yml`

### Features
✅ Automatically triggers on push to `main`, `develop`, and `feature/**` branches  
✅ Runs on every pull request to `main`  
✅ Caches Maven dependencies for faster builds  
✅ Runs unit tests and integration tests separately  
✅ Generates HTML test reports  
✅ Uploads test results as artifacts  
✅ Displays test summary directly in GitHub Actions UI  

### Workflow Steps
1. **Checkout code** - Downloads your repository
2. **Set up JDK 17** - Installs Java 17 (Temurin distribution)
3. **Cache Maven dependencies** - Speeds up subsequent runs
4. **Install dependencies** - Downloads all Maven dependencies
5. **Compile** - Compiles source code
6. **Run Unit Tests** - Executes tests ending with `UnitTest`
7. **Run Integration Tests** - Executes tests ending with `IntegrationTest`
8. **Generate Test Report** - Creates HTML report
9. **Upload Test Results** - Saves XML reports as artifacts
10. **Publish Test Summary** - Shows results in GitHub UI

### Setup Instructions

1. **Ensure the workflow file is in the correct location:**
   ```bash
   # The file should be at: .github/workflows/ci.yml
   ```

2. **Push to GitHub:**
   ```bash
   git add .github/workflows/ci.yml
   git commit -m "Add GitHub Actions CI workflow"
   git push origin main
   ```

3. **View the pipeline:**
   - Go to your GitHub repository
   - Click on "Actions" tab
   - You'll see the workflow running automatically

### Triggering Events
- Push to `main` branch
- Push to `develop` branch
- Push to any `feature/*` branch
- Pull request to `main` branch

### Viewing Results
1. Go to **Actions** tab in your GitHub repository
2. Click on the workflow run
3. View detailed console output for each step
4. Download test artifacts from the summary page

---

## Option 2: Jenkins Pipeline

### File Location
The pipeline file should be at: `Jenkinsfile` (root directory)

### Prerequisites
- Jenkins server installed
- Java 17 installed on Jenkins machine
- Maven 3.x installed on Jenkins machine
- Git plugin installed
- JUnit plugin installed
- Pipeline plugin installed

### Pipeline Stages
1. **Checkout** - Clones the repository and prints commit info
2. **Build** - Compiles source code
3. **Unit Tests** - Runs unit tests and archives results
4. **Integration Tests** - Runs integration tests and archives results
5. **Generate Reports** - Creates test reports and archives artifacts

### Setup Instructions

#### Step 1: Install Required Plugins
1. Go to **Manage Jenkins** → **Manage Plugins**
2. Install these plugins (if not already installed):
   - Pipeline
   - Git
   - JUnit
   - GitHub Integration (for webhook support)

#### Step 2: Configure Global Tools
1. Go to **Manage Jenkins** → **Global Tool Configuration**
2. Add/configure:
   - **JDK**: Name it `JDK-17`, point to Java 17 installation
   - **Maven**: Name it `Maven-3.9.0`, point to Maven installation

#### Step 3: Create a New Pipeline Job
1. Click **New Item** in Jenkins dashboard
2. Enter a name (e.g., `my-java-project-ci`)
3. Select **Pipeline** and click **OK**

#### Step 4: Configure the Pipeline
1. **General**: Check "GitHub project" and enter your repository URL
2. **Source Code Management**: 
   - Select **Git**
   - Enter Repository URL: `https://github.com/1RVP1/my-java-project.git`
   - (Optional) Add credentials for private repos
3. **Pipeline**:
   - Select **Pipeline script from SCM**
   - SCM: **Git**
   - Script Path: `Jenkinsfile`
4. **Build Triggers** (choose one):
   - **Poll SCM**: Enter `H/5 * * * *` (checks every 5 minutes)
   - **GitHub hook trigger**: Enable for instant triggers (requires webhook)

#### Step 5: Set Up GitHub Webhook (for instant triggers)
1. In your GitHub repository, go to **Settings** → **Webhooks** → **Add webhook**
2. **Payload URL**: `http://your-jenkins-server:8080/github-webhook/`
3. **Content type**: `application/json`
4. Click **Add webhook**
5. In Jenkins, enable `githubPush()` trigger in Jenkinsfile

#### Step 6: Save and Run
1. Click **Save**
2. Click **Build Now** to manually trigger the first build

### Viewing Results
1. Click on the build number in Jenkins
2. View **Console Output** for detailed logs
3. Scroll down to see **Test Result Trend** and test reports
4. Download archived artifacts from the build page

---

## Console Output Example

Here's what the test execution output looks like:

```
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------< com.app:selenium-parallel-tests >-------------------
[INFO] Building selenium-parallel-tests 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ selenium-parallel-tests ---
[INFO] skip non existing resourceDirectory
[INFO]
[INFO] --- compiler:3.12.1:compile (default-compile) @ selenium-parallel-tests ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 1 source file with javac [debug target 17] to target\classes
[INFO]
[INFO] --- surefire:3.2.5:test (default-test) @ selenium-parallel-tests ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running CalculatorIntegrationTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.135 s
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.686 s
[INFO] Finished at: 2026-04-08T10:46:05+05:30
[INFO] ------------------------------------------------------------------------
```

---

## Troubleshooting

### GitHub Actions Issues

**Workflow not triggering:**
- Check if the file is at `.github/workflows/ci.yml`
- Ensure the file has valid YAML syntax
- Check if you're pushing to a branch listed in the `on:` section

**Tests failing in CI but passing locally:**
- Check Java version consistency (CI uses JDK 17)
- Verify all dependencies are in pom.xml
- Check for environment-specific configurations

### Jenkins Issues

**Pipeline not starting:**
- Verify Git plugin can access the repository
- Check credentials if using private repo
- Ensure Maven and JDK are configured in Global Tool Configuration

**Tests not found:**
- Verify test class naming pattern (`*IntegrationTest`, `*UnitTest`)
- Check if Maven Surefire plugin is configured correctly in pom.xml

---

## Best Practices

1. **Keep CI fast**: The workflow caches dependencies to speed up builds
2. **Separate test types**: Unit tests run before integration tests
3. **Always archive results**: Test reports are saved even if builds fail
4. **Use meaningful commit messages**: Helps track what changed when tests fail
5. **Review test reports**: Check the HTML reports for detailed test analysis

---

## Summary

| Feature | GitHub Actions | Jenkins |
|---------|---------------|---------|
| Setup Complexity | Easy | Moderate |
| Configuration File | `.github/workflows/ci.yml` | `Jenkinsfile` |
| Trigger on Push | ✅ Automatic | ✅ (with webhook) |
| Test Reports | ✅ Built-in | ✅ (with JUnit plugin) |
| Artifacts | ✅ Built-in | ✅ Built-in |
| Cost | Free for public repos | Self-hosted (free) |
| Scalability | GitHub-hosted runners | Self-managed |

Both options provide robust CI/CD capabilities. Choose GitHub Actions for simplicity and tight GitHub integration, or Jenkins for more control and customization options.