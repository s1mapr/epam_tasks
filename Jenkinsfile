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
        stage ('Jacoco Static Analysis'){
            steps{
                junit 'target/surefire-reports/**/*.xml'
                jacoco()
            }
        }
        stage ('Sonar Scanner Coverage'){
            steps{
                bat "./mvn sonar:sonar -Dsonar.login=squ_72cab28ef47612d64dfdceb5e2190ea8af12f930 -Dsonar.host.url=http://localhost:9000"
            }
        }
        stage ('Deploy to tomcat server'){
            steps{
                deploy adapters: [tomcat9(credentialsId: 'TOMCAT', path: '', url: 'http://localhost:8081/')], contextPath: null, war: 'target/*.war'
            }
        }
    }
}