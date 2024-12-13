def call(Map config) {
    pipeline {
        agent any // Puede ejecutarse en cualquier agente disponible

        tools {
            nodejs 'NodeJS' // Asegúrate de tener NodeJS configurado en Jenkins
        }

        stages {
            stage('Clone Repository') {
                steps {
                    script {
                        echo "Clonando el repositorio: ${env.GIT_URL_1}"
                        org.devops.lb_buildartefacto.clone()

                        // Verificar que package.json esté presente
                        sh 'ls -l'  // Listar los archivos en el directorio actual
                        sh 'cat package.json'  // Mostrar el contenido del package.json para asegurarnos de que está allí
                    }
                }
            }

            stage('Install Dependencies') {
                steps {
                    script {
                        echo "Instalando dependencias..."
                        
                        // Verificar si npm está disponible
                        sh 'npm --version'  // Verificar la versión de npm

                        // Llamada al método install de la biblioteca compartida
                        org.devops.lb_buildartefacto.install()

                        // Ejecutar npm install con salida detallada para depuración
                        sh 'npm install --verbose'  // Esto ayudará a ver más detalles si algo falla
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
                        org.devops.lb_analisissonarqube.analisisSonar(env.GIT_BRANCH_1)
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
