/**
 * Jenkins Declarative Pipeline for Java/Maven Project
 * 
 * This pipeline triggers on every code push and runs tests automatically.
 * Prerequisites:
 *   - Jenkins with Java 17 and Maven installed
 *   - Git plugin installed
 *   - JUnit plugin installed (for test reports)
 * 
 * Setup Instructions:
 *   1. Create a new Jenkins job (Pipeline type)
 *   2. Configure "Pipeline script from SCM"
 *   3. Select Git and point to your repository
 *   4. Set "Script Path" to "Jenkinsfile"
 *   5. Under "Build Triggers", enable "Poll SCM" or use GitHub hook trigger
 */

pipeline {
    agent any
    
    tools {
        // Configure these in Jenkins > Manage Jenkins > Global Tool Configuration
        maven 'Maven-3.9.0'
        jdk 'JDK-17'
    }
    
    environment {
        // Define environment variables
        MAVEN_OPTS = '-Xmx512m'
    }
    
    triggers {
        // Option 1: Poll SCM every 5 minutes for changes
        pollSCM('H/5 * * * *')
        
        // Option 2: Use GitHub hook trigger for GITScm pushing (recommended)
        // Uncomment below and configure GitHub webhook
        // githubPush()
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code from Git repository...'
                checkout scm
                script {
                    // Print current commit info
                    env.GIT_COMMIT_SHORT = sh(
                        script: 'git rev-parse --short HEAD',
                        returnStdout: true
                    ).trim()
                    echo "Building commit: ${env.GIT_COMMIT_SHORT}"
                }
            }
        }
        
        stage('Build') {
            steps {
                echo 'Compiling source code with Maven...'
                sh 'mvn clean compile -DskipTests'
            }
        }
        
        stage('Unit Tests') {
            steps {
                echo 'Running Unit Tests (*UnitTest)...'
                sh 'mvn test -Dtest="*UnitTest" --no-transfer-progress'
            }
            post {
                always {
                    // Archive unit test results
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Integration Tests') {
            steps {
                echo 'Running Integration Tests (*IntegrationTest)...'
                sh 'mvn test -Dtest="*IntegrationTest" --no-transfer-progress'
            }
            post {
                always {
                    // Archive integration test results
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                echo 'Generating test reports...'
                sh 'mvn surefire-report:report -q'
            }
            post {
                always {
                    // Archive test reports
                    archiveArtifacts artifacts: 'target/surefire-reports/**/*', 
                                     fingerprint: true, 
                                     allowEmptyArchive: true
                    archiveArtifacts artifacts: 'target/site/**/*', 
                                     fingerprint: true, 
                                     allowEmptyArchive: true
                }
            }
        }
    }
    
    post {
        always {
            // Clean up workspace
            cleanWs()
            
            // Notify about build status
            script {
                if (currentBuild.result == 'FAILURE' || currentBuild.result == 'UNSTABLE') {
                    echo '⚠️ Build failed! Check test results.'
                } else {
                    echo '✅ Build successful! All tests passed.'
                }
            }
        }
        success {
            echo '🎉 Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed. Please check the console output.'
        }
    }
}