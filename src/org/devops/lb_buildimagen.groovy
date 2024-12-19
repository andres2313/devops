package org.devops

def buildImageDocker(projectGitName){
    sh "docker tag andreslugo/${projectGitName}, andreslugo/${projectGitName} ."
    sh "docker build -t andreslugo/${projectGitName} ." 
}