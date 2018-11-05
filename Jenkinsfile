pipeline {
	agent {
        label 'cc-deploy-demo'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr:'30'))
        disableConcurrentBuilds()
    }
    environment {
        VERSION="1.0.0.${env.BUILD_NUMBER}"
        REPO="assets-${env.VERSION}"
		NODEALIAS="local"
        //CC_CLI_HOME="`pwd`/build/cli/CommandCentral/client"
		//def output
    }
    tools {
        ant "ant-1.9.7"
        jdk "jdk-1.8"
    }    
    stages {
        stage('Tools') {
            steps {
                // boot CC CLI and ABE
                //sh "ant client"
				dir('../cc-deploy-demo'){
					sh "chmod 777 -R *"
					//echo subdir
				}
                sh './sagccantw -version'
				
            }
        }
		stage('Build') {
			steps {
                // FIXME: this should be sagccantw with full ant distribution
				sh "ant build -Dbuild.number=${env.VERSION}"
			}
		}
		stage('Publish to Jenkins') {
			steps {
			    dir('build/assets') {
                    archive '**'
                    stash 'artifacts'
                }
			}
		}
		stage('Publish to CC') {
			steps {
                // publish to CI CC
                sh "ant publish test -Dasset.repo=${env.REPO}"
                //junit 'build/tests/**/*.xml'
			}
		}
		stage('Deploy to CI IS') {
			steps {
				script {
					try{
						echo "Deploy to CI IS runtime instance"						
						//call java method to get the job ID
						echo "ant deployToCIIS started"
						sh "ant deployToCIIS"
						echo "ant deployToCIIS finished"
						echo "IS asset deployment tests"
						sh "ant asset-deploy"
						//sh "ant GetJobStatus"
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=Deploy to CI IS"
						//throw e
					}
					finally {
						echo "finally block executed. stage=Deploy to CI IS"
					}
				}
			}
		}
/*		stage('Deploy to CI MWS') {
			steps {
				script {
					try{
						echo "Deploy to CI MWS runtime instance"
						echo "ant deployToCIMWS started"
						sh "ant deployToCIMWS"
						echo "ant deployToCIMWS finished"
						echo "MWS asset deployment tests"
						sh "ant asset-deploy"
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=Deploy to CI MWS"
						//throw e
					}
					finally {
						echo "finally block executed. stage=Deploy to CI MWS"
					}
				}
			}
		}		
		stage('Deploy to CI BRMS-IS') {
			steps {
				script {
					try{
						echo "Deploy to CI BRMS_IS runtime instance"
						echo "ant deployToCIBRMS_IS started"
						sh "ant deployToCIBRMS_IS"
						echo "ant deployToCIBRMS_IS finished"
						echo "BRMS_IS asset deployment tests"
						sh "ant asset-deploy"
						//BRMS aassets deploy test. check for jar files
						sh "ant asset-BRMS-deploy-test -Dbrmsdir=/home/saguser/sag/cc/IntegrationServer/instances/default/packages/WmBusinessRules/ -Drulesjar=RulesProject.jar"
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "tage=Deploy to CI BRMS-IS failed"
						//throw e
					}
					finally {
						echo "finally block executed. stage=Deploy to CI BRMS-IS"
					}
				}
			}		
		}		
		stage('Deploy to CI App-Platform') {
			steps {
				script {
					try{
						echo "Deploy to CI App-Platform runtime instance"
						echo "ant deployToCIAppPlatform started"
						sh "ant deployToCIAppPlatform"
						echo "ant deployToCIAppPlatform finished"
						echo "AppPlatform asset deployment tests"
						sh "ant asset-deploy"
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=Deploy to CI App-Platform failed"
						//throw e
					}
					finally {
						echo "finally block executed. stage=Deploy to CI App-Platform"
					}
				}
			}		
		}	*/
		stage('List commands') {
			steps {	
				script {
					try{
						sh "ant listRepository_TypeAsset"
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=List commands failed"
						//throw e
					}
					finally {
						echo "finally block executed. stage=List commands"
					}
				}
			}
		}
		stage ('Starting CCE+ABE_AssetsVerification job') {
			steps {
				script {
					try{
							build job: 'CCE+ABE_AssetsVerification'
							
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=Starting CCE+ABE_AssetsVerification job failed"
						//throw e
					}
					finally {
						echo "finally block executed. stage=Starting CCE+ABE_AssetsVerification job"
					}
				}
			}
		}
		
		stage('undeploy IS assets'){
			steps{
				script {
					try{
						sh "ant unDeployCIIS"
						//check the status of the job. here calling the same target as deploy
						sh "ant asset-deploy"
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=undeploy IS assets job failed"
						//throw e
					}
					finally {
						echo "finally block executed. stage=undeploy IS assets"
					}
				}
			}
		}
		stage('Generate HTML report for ant unit testing'){
			steps{
				script {
					try{
					echo "Generate JUnit report for ant unit testing"
					sh "ant generateHTMLReport"
					
					//step([$class: 'JUnitResultArchiver', testResults: '**/build/tests/**/*.xml', healthScaleFactor: 1.0])
					publishHTML (target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: '/home/saguser/Public/Softwares/jenkins/workspace/workspace/cc-deploy-demo/build/tests/HTMLreport/html',
                    reportFiles: 'index.html',
                    reportName: "Junit Report"
					])
					
					
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=Generate HTML report for ant unit testing failed"
						//throw e
					}
					finally {
						echo "finally block executed. stage=Generate HTML report for ant unit testing"
					}
				}
			}
		}
/*		stage ('Starting HTML_Report_Ant_Testing job') {
			steps {
				script {
					try{
							build job: 'HTML_Report_Ant_Testing'
							
					}
					catch(Exception e) {
						// Do something with the exception 
						//currentBuild.result = 'SUCCESS'
						echo "stage=Starting HTML_Report_Ant_Testing job failed"
						//throw e
					}
					finally {
						echo "finally block executed. stage=Starting HTML_Report_Ant_Testing job"
					}
				}
			}
		}*/
        stage('Notify QA') {
            steps {
                mail to: 'abg@softwareag.com',
                    subject: "Pipeline completed: ${currentBuild.fullDisplayName}",
                    body: "Checkout the artifacts at ${env.BUILD_URL}"
            }
        }
	}
}