pipeline {
    agent any
    tools {
        maven 'Maven-3.9.0'
        jdk 'JDK-17'
    }
    stages {
        stage('Build & Test') {
            steps {
                sh 'mvn clean test --no-transfer-progress'
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            cleanWs()
        }
    }
}