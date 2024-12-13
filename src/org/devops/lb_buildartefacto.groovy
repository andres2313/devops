package org.devops

def clone(){
    git branch: "${env.nameBranch}", url: "${env.UrlGitHub}"
}

def install(){
    sh 'npm install'
}

def build() {
    sh 'npm run build'
}

def generateArtefact() {
    sh 'tar -czvf artefacto.tar.gz ./build'
}