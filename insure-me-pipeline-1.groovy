node{
    stage('insure-me code github'){
        git 'https://github.com/benga1uru/insure-me-app.git'
    }
    
    stage ('insure-me maven package'){
        sh 'mvn clean package'
    }
    
    stage ('insure-me docker container'){
        sh 'docker build -t bengaluru/insure-me:1.0 .'
    }
    
    stage ('insure-me push dockerhub'){
        withCredentials([string(credentialsId: 'dockerHubPwd', variable: 'PASS')]) {
            sh "docker login -u bengaluru -p ${PASS}"
            sh 'docker push bengaluru/insure-me:1.0'
            
        }
    }
    
    stage ('insure-me deploy to test-server'){
        ansiblePlaybook credentialsId: 'sshPrivateKey', disableHostKeyChecking: true, installation: 'ansible', inventory: '/etc/ansible/hosts', playbook: 'configure-test-server.yml', vaultTmpPath: ''
    }
	
}