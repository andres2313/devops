package org.devops

def clone() {
    echo "Clonando el repositorio desde: ${env.GIT_URL_1}"
    git branch: "${env.GIT_BRANCH_1}", url: "${env.GIT_URL_1}"
}

def build() {
    sh 'npm run build'
}

def generateArtefact() {
    sh 'tar -czvf artefacto.tar.gz ./build'
}