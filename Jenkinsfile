pipeline {
    agent any

    tools {
        maven 'Maven3'
        dockerTool 'DOCKER'
    }

    environment {
            // Define Docker Hub credentials ID
            DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
            // Define Docker Hub repository name
            DOCKERHUB_REPO = 'sergeivilka/shoppingcart'
            // Define Docker image tag
            DOCKER_IMAGE_TAG = 'latest_v1'
        }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/S-Vilka/ShoppingCart.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Code Coverage') {
            steps {
                sh 'mvn jacoco:report'
            }
        }
        stage('Publish Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Publish Coverage Report') {
            steps {
                jacoco()
            }
        }

        stage('Set Docker Host') {
            steps {
                script {
                    if (isUnix()) {
                        env.DOCKER_HOST = 'unix:///var/run/docker.sock'
                    } else {
                        env.DOCKER_HOST = 'npipe:////./pipe/docker_engine'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build Docker image
                script {
                    docker.build("${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}")
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        // Log in to Docker Hub
                        sh "echo ${DOCKERHUB_PASSWORD} | docker login -u ${DOCKERHUB_USER} --password-stdin"

                        // Push Docker images to Docker Hub
                        sh "docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}"
                    }
                    }
                }
            }
        }
    }
}