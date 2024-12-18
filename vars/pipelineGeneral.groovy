import org.devops.lb_buildimagen
import org.devops.lb_deploydocker
import org.devops.lb_owasp
import org.devops.lb_publicardockerhub

def call(Map config) {
    def lb_buildimagen = new lb_buildimagen()
    def lb_deploydocker = new lb_deploydocker()
    def lb_owasp = new lb_owasp()
    def lb_publicardockerhub = new lb_publicardockerhub()
    pipeline {
        agent any 
        tools {
            nodejs 'NodeJS' 
        }

        
        stages {

            stage('Extract Project Name') {
                steps {
                    script {
                        def urlGitHub = sh(script: 'git config --get remote.origin.url', returnStdout: true).trim()
                        echo "URL del repositorio Git: ${urlGitHub}"

                        def projectGitName = urlGitHub.replaceAll(/^.*\/([^\/]+)\.git$/, '$1')
                        echo "Nombre del proyecto extraído: ${projectGitName}"

                        env.projectGitName = projectGitName
                    }
                }
            }
            
            stage('Construccion de imagen') {
                steps {
                    script {
                        lb_buildimagen.buildImageDocker()
                    }
                }
            }


            stage('Publicacion Docker Hub') {
                steps {
                    script {
                        lb_publicardockerhub.publicarImage()
                    }
                }
            }

            stage('Despliegue imagen') {
                steps {
                    script {
                        lb_deploydocker.despliegueContenedor()
                    }
                }
            }
                        
            stage('Analisis OWASP') {
                steps {
                    script {
                        lb_owasp.analisisOwasp()
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