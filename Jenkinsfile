pipeline {
    agent any

    tools{
      maven 'Maven3'
      jdk 'JDK_21'
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
                        script {
                            if (isUnix()) {
                                sh "docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG} ."
                            } else {
                                bat "docker build -t ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG} ."
                            }
                        }
                    }
                }

                stage('Push Docker Image to Docker Hub') {
                    steps {
                        script {
                            docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS_ID) {
                                if (isUnix()) {
                                    sh "docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}"
                                } else {
                                    bat "docker push ${DOCKERHUB_REPO}:${DOCKER_IMAGE_TAG}"
                                }
                            }
                        }
                    }
                }
            }
        }