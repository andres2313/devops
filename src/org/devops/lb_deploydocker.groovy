package org.devops

def despliegueContenedor(projectGitName){
    //sh "docker stop ${projectGitName}"
    //sh "docker rm ${projectGitName}"
    sh "docker pull andreslugo/react-test-jenkisfile"
    sh """ docker run -d --name ${projectGitName} \
    --network=${env.NameNetwork} -p 5174:5174 \
    --user root andreslugo/${projectGitName}
    """
}