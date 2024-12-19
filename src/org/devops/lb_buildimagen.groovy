package org.devops

def buildImageDocker(projectGitName){
    sh "docker tag andreslugo/${projectGitName} andreslugo/${projectGitName}:latest"
    sh "docker build -t andreslugo/${projectGitName} ." 
}