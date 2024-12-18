package org.devops

def despliegueContenedor(projectGitName){
    //sh "docker stop ${projectGitName}"
    //sh "docker rm ${projectGitName}"
    sh "docker pull andres2313/Jenkisfile"
    sh """ docker run -d --name ${projectGitName} \
    --network=${env.NameNetwork} -p 5174:5174 \
    --user root andres2313/${projectGitName}
    """
}