pipeline {
    
agent any

    stages {
        stage('Checkout') {
            steps {
                // R�cup�re le code source depuis GitHub
                checkout scmGit(branches: [[name: '*/sidaoui']], extensions: [], userRemoteConfigs: [[credentialsId: 'sid-github-pwd', url: 'https://github.com/The-Red-Barons/Devops-Project.git']])           
                }}
        stage('Clean the project') {
          steps {
            // Supprime tous les fichiers g�n�r�s par la construction pr�c�dente
            sh 'mvn clean'
        }
    }
       stage('Build') {
        steps {
            // Compile le code source du projet en fichiers de classe ex�cutables
            sh 'mvn package'
        }
    }       
        stage('Unit Tests') {
            steps {
                // Ex�cute les tests unitaires
                sh 'mvn test'
            }
            post {
                always {
                    // Publie les r�sultats des tests sur le rapport de build
                    junit '**/target/surefire-reports/*.xml'
                }
            }
            
        }
        stage('Code Coverage') {
        steps {
            // Ex�cute toujours la t�che JaCoCo
            jacoco(
                runAlways: true,

                // Inclusions pour le rapport de couverture
                inclusionPattern: '**/src/main/java/**/*.class',

                // Exclusions pour le rapport de couverture
                execPattern: '**/test/**/*.class'
            )
        }
    }
         stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                        sh 'mvn sonar:sonar'

}
                
            }
        }
        
                stage('Build Docker Image'){
            steps{
                script{
                    sh 'docker build -t sidaouimohamedamine/gestion-station-ski .'
                }
            }
        }
         stage('Build Docker Image Front'){
            steps{
                script{
                    sh 'docker build -t sidaouimohamedamine/gestion-station-ski-front ./skiFront/ '
                }
            }
        }
        
            stage('Push Docker Backend Image to Docker Hub'){
        steps{
            script{
                // D�finit les variables d'environnement pour Docker Hub
                env.DOCKER_USERNAME = "sidaouimohamedamine"
    
                // Utilise withCredentials pour acc�der aux informations d'identification Docker Hub
                withCredentials([usernamePassword(credentialsId: 'dockerhub-pwd', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    // Se connecte � Docker Hub
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                    // Poussette l'image vers Docker Hub
                    sh 'docker push sidaouimohamedamine/gestion-station-ski'
                }
            }
        }
    }

            stage('Push Docker Frontend Image to Docker Hub'){
        steps{
            script{
                // D�finit les variables d'environnement pour Docker Hub
                env.DOCKER_USERNAME = "sidaouimohamedamine"
    
                // Utilise withCredentials pour acc�der aux informations d'identification Docker Hub
                withCredentials([usernamePassword(credentialsId: 'dockerhub-pwd', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    // Se connecte � Docker Hub
                    sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
                    // Poussette l'image vers Docker Hub
                    sh 'docker push sidaouimohamedamine/gestion-station-ski-front'
                }
            }
        }
    }


            stage('Deploy to Nexus') {
          steps {
                 nexusArtifactUploader artifacts: [
                        [
                            artifactId: 'gestion-station-ski',
                            classifier: '',
                            file: "target/gestion-station-ski-1.0.jar",
                            type: 'jar'
                        ]
                    ],
                    credentialsId: 'nexus-user-credentials',
                    groupId: 'tn.esprit.spring',
                    nexusUrl: '192.168.43.116:8081',
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    repository: 'maven-nexus-repo',
                    version: "1.0.0"          }
        }
        stage('Run Docker Compose') {
            steps {
                script {
                    // Ex�cute Docker Compose pour d�marrer vos services
                    sh 'docker compose up -d'
                }
            }
        }



        
        



        
        
        
        
    }
}