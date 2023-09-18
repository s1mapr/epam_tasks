pipeline{
    agent any
    tools{
        maven 'maven_3.9.4'
    }
    stages{
        stage('Build'){
            steps {
                bat 'mvn clean package'
                }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war'
                }
            }
          }
        stage ('Deploy to tomcat server'){
            steps{
                deploy adapters: [tomcat9(path: '', url: 'http://localhost:8081/')], contextPath: null, war: '**/*.war'
            }
        }
    }
}