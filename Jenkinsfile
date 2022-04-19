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
            sh 'ls && cd eureka-server'
          }
        }

        stage('Build Gradle Gateway') {
          steps {
            sh 'ls && cd apigateway'
          }
        }

      }
    }

  }
  environment {
    registryCredential = 'dockerhub_cred'
  }
}