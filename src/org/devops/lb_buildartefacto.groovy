package org.devops

def clone(){
    git branch: "${env.nameBranch}", url: "${env.GIT_URL_1}"
}

def install(){
    sh 'npm install'
}
