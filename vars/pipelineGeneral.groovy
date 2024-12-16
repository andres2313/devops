import org.devops.lb_buildartefacto
import org.devops.lb_analisissonarqube

def call(Map config) {
    def lb_buildartefacto = new lb_buildartefacto()
    def lb_analisissonarqube = new lb_analisissonarqube()
    pipeline {
        agent any 
        tools {
            nodejs 'NodeJS' 
        }
        stages {
            stage('Clonar repositorio') {
                steps {
                    script {
                        lb_buildartefacto.clone()
                    }
                }
            }

            stage ('mirar algo'){
                steps {
                    checkout scm
                    sh 'ls -la'
                }
            }

            stage('Correr el test para analisis en sonarqube') {
                steps {
                    script {
                        lb_analisissonarqube.testCoverage()
                    }
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        lb_analisissonarqube.analisisSonar(env.GIT_BRANCH_1)
                    }
                }
            }
        }

        post {
            always {
                echo "Pipeline finalizado."
            }
            success {
                echo "Pipeline ejecutado correctamente."
            }
            failure {
                echo "El pipeline falló. Revisar los logs."
            }
        }
    }
}