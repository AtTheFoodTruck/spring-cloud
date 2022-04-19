pipeline {
  agent any
  stages {
    stage('Build Gradle') {
      steps {
        sh 'ls'
      }
    }

  }
  environment {
    registryCredential = 'dockerhub_cred'
  }
}