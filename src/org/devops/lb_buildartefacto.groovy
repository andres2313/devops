package org.devops

class lb_buildartefacto {
    static def clone() {
        git branch: "${env.GIT_BRANCH_1}", url: "${env.GIT_URL_1}"
    }

    static def install() {
        sh 'npm install'
    }
}
