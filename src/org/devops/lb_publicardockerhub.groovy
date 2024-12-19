package org.devops

def publicarImage(projectGitName){
    withCredentials([usernamePassword(credentialsId: "${env.TOKEN_ID}",
    passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
        sh "set -x"
        sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}"
        sh "docker tag andreslugo/devops andreslugo/devops:latest"
        sh "docker push andreslugo/devops:latest"
        sh "docker images"  // Para verificar las imágenes locales disponibles.
    }
}

//withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
           // script {
                // Login en Docker Hub
             //   sh "docker login -u ${DOCKERHUB_USERNAME} -p ${DOCKERHUB_PASSWORD}"
                
                // Etiquetar la imagen correctamente
               // sh "docker tag andreslugo/devops andreslugo/devops:latest"
                
                // Publicar la imagen a Docker Hub
                //sh "docker push andreslugo/devops:latest"
           // }