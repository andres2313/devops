package org.devops

def clone() {
    echo "Iniciando clonación del repositorio: ${env.GIT_URL_1}, rama: ${env.GIT_BRANCH_1}"
    git branch: "${env.GIT_BRANCH_1}", url: "${env.GIT_URL_1}"
}

def install() {
    echo "Ejecutando npm install en el directorio actual:"
    sh 'ls -l' // Muestra los archivos presentes para verificar si está package.json
    sh 'npm install'
}
