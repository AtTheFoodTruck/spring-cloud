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

    stage('Discovery Docker Build') {
      parallel {
        stage('Discovery Docker Build') {
          steps {
            sh 'cd eureka-server && ls'
            script {
              backend_user = docker.build("goalgoru/discovery-service")
            }

          }
        }

        stage('Gateway Docker Build') {
          steps {
            sh 'cd apigateway && ls'
            script {
              backend_user = docker.build("goalgoru/gateway-service")
            }

          }
        }

      }
    }

    stage('Discovery Docker Push') {
      parallel {
        stage('Discovery Docker Push') {
          steps {
            script {
              docker.withRegistry('https://registry.hub.docker.com/', registryCredential) {
                backend_user.push("latest")
                backend_user.push("${BUILD_NUMBER}")
              }
            }

          }
        }

        stage('Gateway Docker Push') {
          steps {
            script {
              docker.withRegistry('https://registry.hub.docker.com/', registryCredential) {
                backend_user.push("latest")
                backend_user.push("${BUILD_NUMBER}")
              }
            }

          }
        }

      }
    }

  }
  environment {
    registryCredential = 'dockerhub_cred'
  }
}