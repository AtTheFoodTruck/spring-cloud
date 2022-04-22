pipeline {
  agent any
  stages {
    stage('Discovery, Gateway') {
      environment {
        registryCredential = 'dockerhub_cred'
      }
      parallel {
        stage('Build Gradle Discovery') {
          steps {
            sh 'cd eureka-server && ls'
            sh '''sudo chmod 777 gradlew
./gradlew clean build --exclude-task test'''
            sh 'ls'
          }
        }

        stage('Build Gradle Gateway') {
          steps {
            sh 'cd apigateway && ls'
            sh '''sudo chmod 777 gradlew
./gradlew clean build --exclude-task test'''
            sh 'ls'
          }
        }

      }
    }

    stage('Discovery, Gateway Docker Build') {
      parallel {
        stage('Discovery Docker Build') {
          steps {
            sh 'cd eureka-server && ls'
            dir(path: './eureka-server') {
              script {
                backend_discovery = docker.build("goalgoru/discovery-service")
              }

            }

          }
        }

        stage('Gateway Docker Build') {
          steps {
            sh 'cd apigateway && ls'
            dir(path: './apigateway') {
              script {
                backend_gateway = docker.build("goalgoru/gateway-service")
              }

            }

          }
        }

      }
    }

    stage('Discovery, Gateway Docker Push') {
      parallel {
        stage('Discovery Docker Push') {
          steps {
            script {
              docker.withRegistry('https://registry.hub.docker.com/', registryCredential) {
                backend_discovery.push("latest")
                backend_discovery.push("${BUILD_NUMBER}")
              }
            }

          }
        }

        stage('Gateway Docker Push') {
          steps {
            script {
              docker.withRegistry('https://registry.hub.docker.com/', registryCredential) {
                backend_gateway.push("latest")
                backend_gateway.push("${BUILD_NUMBER}")
              }
            }

          }
        }

      }
    }

    stage('docker-compose') {
      steps {
        sh 'cd /project && docker-compose up -d'
      }
    }

  }
  environment {
    registryCredential = 'dockerhub_cred'
  }
}