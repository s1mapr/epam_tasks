pipeline{
    agent any
    tools{
        maven 'maven_3.9.4'
    }
    stages{
        stage('Build'){
            steps{
                bat 'mvn clean package'
            }
        }
        stage('Archive artifacts'){
            steps{
                archiveArtifacts artifacts: 'target/*.war'
            }
        }
        stage ('Deploy to tomcat server'){
            steps{
                deploy adapters: [tomcat9(credentialsId: 'TOMCAT', path: '', url: 'http://localhost:8081/')], contextPath: null, war: 'target/*.war'            }
        }
    }
}