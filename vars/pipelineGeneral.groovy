def call(Map config) {
    pipeline {
        agent any // Puede ejecutarse en cualquier agente disponible

        tools {
            nodejs 'NodeJS' // Asegúrate de tener NodeJS configurado en Jenkins
        }

        stages {
            stage('Initialize Environment') {
                steps {
                    script {
                        // Validar si config.repoUrl y config.branch tienen valores; si no, usar predeterminados
                        def repoUrl = config.repoUrl ?: 'https://github.com/default/repository.git'
                        def branch = config.branch ?: 'main'

                        // Calcular y asignar variables de entorno
                        env.PROJECT_NAME = repoUrl.tokenize('/').last().replace('.git', '')
                        env.GIT_BRANCH_1 = branch
                        env.GIT_URL_1 = repoUrl

                        echo "Variables de entorno inicializadas:"
                        echo "PROJECT_NAME: ${env.PROJECT_NAME}"
                        echo "GIT_BRANCH_1: ${env.GIT_BRANCH_1}"
                        echo "GIT_URL_1: ${env.GIT_URL_1}"
                    }
                }
            }

            stage('Clone Repository') {
                steps {
                    script {
                        echo "Clonando el repositorio: ${env.GIT_URL_1}"

                        // Llamada al método clone de la biblioteca compartida
                        org.devops.lb_buildartefacto.clone()
                    }
                }
            }

            stage('Install Dependencies') {
                steps {
                    script {
                        echo "Instalando dependencias..."

                        // Llamada al método install de la biblioteca compartida
                        org.devops.lb_buildartefacto.install()
                    }
                }
            }

            stage('Run Tests and Coverage') {
                steps {
                    script {
                        echo "Ejecutando pruebas y generando cobertura..."

                        // Llamada al método testCoverage de la biblioteca compartida
                        org.devops.lb_analisissonarqube.testCoverage()
                    }
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        echo "Iniciando análisis con SonarQube..."

                        // Llamada al método analisisSonar de la biblioteca compartida
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