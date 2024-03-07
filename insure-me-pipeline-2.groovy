node{
    
    stage('selenium insure-me code github'){
        git 'https://github.com/benga1uru/selenium-insure-me.git'
    }
    
    stage ('selenium scripts maven package'){
        sh 'mvn clean package assembly:single'
    }
    
    stage ('run selenium scripts ansible'){
        ansiblePlaybook credentialsId: 'sshPrivateKey', disableHostKeyChecking: true, installation: 'ansible', inventory: '/etc/ansible/hosts', playbook: '/var/lib/jenkins/workspace/insure-me-pipeline-1/run-selenium-test-server.yml', vaultTmpPath: ''    }
    
    stage('insure-me deploy to prod-server ansible'){
        ansiblePlaybook credentialsId: 'sshPrivateKey', disableHostKeyChecking: true, installation: 'ansible', inventory: '/etc/ansible/hosts', playbook: '/var/lib/jenkins/workspace/insure-me-pipeline-1/configure-prod-server.yml', vaultTmpPath: ''
    }
    
}
    