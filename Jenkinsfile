pipeline{
    agent any
    tools{
        maven 'maven_3.9.4'
    }
    stages{
        stage('Build'){
            bat 'mvn clean package'
        }
        stage('Archive artifacts'){
            archiveArtifacts artifacts: 'target/*.war'
        }
        stage ('Deploy to tomcat server'){
            steps{
                deploy adapters: [tomcat9(path: '', url: 'http://localhost:8081/')], contextPath: null, war: '**/*.war'
            }
        }
    }
}