pipeline {
    agent {
        node {
            label 'jdk10-maven-slim'
        }
    }
    stages {
        stage('Checking') {
            sh """echo 'Checking Branch Build: ' + ${env.BRANCH_NAME}"""
        }
    }
}