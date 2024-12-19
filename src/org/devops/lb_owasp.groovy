package org.devops

def analisisOwasp(projectGitName){
    //Ejecutar el análisis OWASP con ZAP, asegurando volumen y variables correctas
    sh """ docker run --rm -v /path/to/local/ProjectOwasp:/zap/wrk/:rw \
          --user root --network=${env.NameNetwork} \
          -t edansama96/zap2docker-stable \
           zap-full-scan.py \
          -t ${env.dominio} \
          -r /zap/wrk/ProjectOwasp.html -I
       """
}
//def analisisOwasp(projectGitName){
//sh """ docker run --rm -v ProjectOwasp:/zap/wrk/:rw \
//       --user root --network=${env.NameNetwork} \
//       -t owasp/zap2docker-stable \
//       zap-full-scan.py \
//       -t ${env.dominio} \
//       -r ProjectOwasp.html -I
//       """ 
//}

