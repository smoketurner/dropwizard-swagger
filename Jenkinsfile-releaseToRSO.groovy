/*
 * Copyright (c) 2011-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/clm/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */

@Library([
    'pipeline-jenkins-common',
    'jenkins-shared',
    'private-pipeline-library',
]) _
/**
 * GitHub Pull Request builder.
 *
 * The real Jenkinsfile logic is in our shared Jenkins library:
 *   https://github.com/sonatype/pipeline-jenkins-common/
 * That library must be added to the Jenkins job as a Pipeline Library for this to work.
 *
 * We're using a style from this example, to get code reuse across scheduled-services:
 * https://jenkins.io/doc/book/pipeline/shared-libraries/#defining-a-more-structured-dsl
 */
commonBuild {
    gitHubRepoPath = 'sonatype/dropwizard-swagger'
    release = true
    jdk='OpenJDK 11'
}
