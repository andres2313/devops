package org.devops

def buildImageDocker(projectGitName){
    sh "docker build -t andres2313/${projectGitName} ."
}