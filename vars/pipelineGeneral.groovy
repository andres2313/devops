def call(Map config) {
    pipeline {
        agent any // Puede ejecutarse en cualquier agente disponible

        tools {
            nodejs 'NodeJS' // Asegúrate de tener NodeJS configurado en Jenkins
        }

        environment {
            // Toma automáticamente el nombre del proyecto desde la URL del repositorio
            PROJECT_NAME = config.repoUrl.tokenize('/').last().replace('.git', '')
            GIT_BRANCH = config.branch ?: 'main'
            GIT_REPO_URL = config.repoUrl
            SOURCE_PATH = config.sourcePath ?: 'src'
        }

        stages {
            stage('Clone Repository') {
                steps {
                    script {
                        echo "Clonando el repositorio: ${env.GIT_REPO_URL}"
                        org.devops.lb_buildartefacto.clone()
                    }
                }
            }

            stage('Install Dependencies') {
                steps {
                    script {
                        echo "Instalando dependencias..."
                        org.devops.lb_buildartefacto.install()
                    }
                }
            }

            stage('Build Artefact') {
                steps {
                    script {
                        echo "Construyendo el artefacto..."
                        org.devops.lb_buildartefacto.build()
                        org.devops.lb_buildartefacto.generateArtefact()
                    }
                }
            }

            stage('Run Tests and Coverage') {
                steps {
                    script {
                        echo "Ejecutando pruebas y generando cobertura..."
                        org.devops.lb_analisissonarqube.testCoverage()
                    }
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        echo "Iniciando análisis con SonarQube..."
                        org.devops.lb_analisissonarqube.analisisSonar(env.PROJECT_NAME)
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