pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                git 'https://github.com/CarlosZuluagaLaHaus/RestAssuredJM.git'
            }
        }
        stage('Run test') {
            steps {
                withGradle {
                    sh '/var/jenkins_home/tools/hudson.plugins.gradle.GradleInstallation/gradle6.8/bin/gradle clean test'
                }
            }
        }
    }
    post {
        always {
            script {
                allure([
                        includeProperties: false,
                        jdk              : '',
                        properties       : [],
                        reportBuildPolicy: 'ALWAYS',
                        results          : [[path: 'build/allure-results']]
                ])
            }
        }
    }

}