pipeline {
    agent any
    stages {
        stage('Build & Test') {
            steps {
                bat 'mvn clean test --no-transfer-progress'
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