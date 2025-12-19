pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'pavelglinskiy/springtodo'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            when {
                branch 'dev'
            }
            steps {
                echo "Сборка Maven для PR в dev"
                bat 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            when {
                branch 'main'
            }
            steps {
                echo "Сборка Docker image для main"
                bat "docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% ."
            }
        }

        stage('Push Docker Image') {
            when {
                branch 'main'
            }
            steps {
                echo "Push Docker image в Docker Hub"
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat "docker login -u %DOCKER_USER% -p %DOCKER_PASS%"
                    bat "docker push %DOCKER_IMAGE%:%DOCKER_TAG%"
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline завершён"
        }
        success {
            echo "Сборка успешно выполнена"
        }
        failure {
            echo "Сборка завершилась с ошибкой"
        }
    }
}
