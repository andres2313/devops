package org.devops

def testCoverage() {
    echo "Ejecutando pruebas con npm test:"
    sh 'npm test || echo "Las pruebas fallaron"'
}

def analisisSonar(gitName) {
    echo "Ejecutando análisis SonarQube con el proyecto: ${gitName}"
    def scannerHome = tool 'sonar-scanner'
    if (scannerHome) {
        withSonarQubeEnv('sonar-scanner') {
            sh "${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=${gitName} \
            -Dsonar.projectName=${gitName} \
            -Dsonar.sources=${env.source} \
            -Dsonar.tests=src/__test__ \
            -Dsonar.exclusions='**/*.test.js' \
            -Dsonar.testExecutionReportPaths=./test-report.xml \
            -Dsonar.javascript.lcov.reportPaths=./coverage/lcov.info"
        }
    } else {
        error 'SonarQube Scanner not found'
    }
}
