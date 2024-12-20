package org.devops

def despliegueContenedor(projectGitName){
    // Detener y eliminar el contenedor si ya existe
    sh "docker stop ${projectGitName}"
    sh "docker rm ${projectGitName}"
    
    // Pull de la imagen más reciente desde Docker Hub
    sh "docker pull andreslugo/${projectGitName}"

    // Desplegar el contenedor en la red y puerto definidos
    sh """ docker run -d --name ${projectGitName} \
    --network=${env.NameNetwork} -p 5174:5174 \
    --user root andreslugo/${projectGitName}
    """
}
